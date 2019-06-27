package com.cims.business.settlement.controller;

import com.cims.business.settlement.entity.CardSettlement;
import com.cims.business.settlement.service.CardSettlementService;
import com.cims.business.settlement.vo.*;
import com.cims.common.Constants;
import com.cims.common.excel.ExcelReader;
import com.cims.common.excel.ExcelUtils;
import com.cims.common.excel.ExcelWriter;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.response.ResponseData;
import com.cims.framework.response.Result;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author baidu
 * @date 2019-04-19 14:25
 * @description 卡片结算
 **/

@RestController
@RequestMapping("/settlement")
@RolesAllowed({Constants.SETTLER,Constants.ADMIN,Constants.OP})
public class CardSettlementController {

    @Autowired
    private CardSettlementService settlementService;

    /**
     * 结算选卡列表
     * @param condition
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/listCard")
    public Result listCard(CardInfoCondition condition, PageBean pageBean) {
        PageDataResult<CardInfoListVO> list = settlementService.listCard(condition,pageBean);
        return ResponseData.success(list);
    }

    /**
     * 结算记录列表（出库结算列表和入库结算列表共用，type 区分 默认入库）
     * @param condition
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/listSettlement")
    public Result listSettlement(SettlementCondition condition, PageBean pageBean) {
        PageDataResult<CardSettlementListVO> list = settlementService.listSettlement(condition,pageBean);
        return ResponseData.success(list);
    }

    /**
     * 获取默认选择时间
     * @param cardNos
     * @param type 1 入库 2 出库
     * @return
     */
    @RequestMapping(value = "/getDate")
    public Result getDate(@RequestParam(value = "cardNos") List<String> cardNos, @RequestParam(value = "type")Integer type) {
        Date date  = settlementService.getDate(cardNos,type);
        return ResponseData.success(date);
    }

    /**
     * 新增入库结算
     * @param inSettlementVO
     * @return
     */
    @RequestMapping(value = "/addInData",method = RequestMethod.POST)
    public Result addInData(@Valid @RequestBody CardInSettlementVO inSettlementVO) throws BizException {
        CardSettlement settlement = new CardSettlement();
        BeanUtils.copyProperties(inSettlementVO,settlement);
        List<String> result = settlementService.addInData(settlement,inSettlementVO.getCardNos());
        if (result.size() > 0){
            return ResponseData.failed(result);
        }
        return ResponseData.success();
    }

    /**
     * 新增出库结算
     * @param outSettlementVO
     * @return
     */
    @RequestMapping(value = "/addOutData",method = RequestMethod.POST)
    public Result addOutData(@Valid @RequestBody CardOutSettlementVO outSettlementVO) throws BizException {
        CardSettlement settlement = new CardSettlement();
        BeanUtils.copyProperties(outSettlementVO,settlement);
        List<String> result = settlementService.addOutData(settlement,outSettlementVO.getCardNos());
        if(result.size() > 0){
            return ResponseData.failed(result);
        }
        return ResponseData.success();
    }

    /**
     * 修改结算记录
     * @param settlementListVO
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@Valid @RequestBody CardSettlementListVO settlementListVO) throws BizException {
        settlementService.update(settlementListVO);
        return ResponseData.success();
    }


    /**
     * 导出入库结算数据
     * @param condition
     * @param response
     */
    @RequestMapping("/exportInData")
    public void exportInData(SettlementCondition condition, HttpServletResponse response) {

        PageBean pageBean = new PageBean();
        pageBean.setPageSize(0);

        PageDataResult<CardSettlementListVO> list = settlementService.listSettlement(condition,pageBean);

        List<CardInSettlementVO> settlementVOS = new ArrayList<>();
        list.getList().stream().forEach(item -> {
            CardInSettlementVO settlementVO = new CardInSettlementVO();
            BeanUtils.copyProperties(item,settlementVO);
            settlementVOS.add(settlementVO);
        });

        InputStream inputStream = this.getClass().getResourceAsStream("/xlsx/card_in_settlement_template.xlsx");;
        String fileName = "入库结算数据";

        SXSSFWorkbook wb = ExcelWriter.getDataForExcel(inputStream,settlementVOS);

        ExcelUtils.writeExcelResponse(wb,fileName,response);
    }

    /**
     * 导入入库结算数据更新付款日期
     * @param file
     */
    @RequestMapping("/importInData")
    public Result importInData(@RequestParam("file") MultipartFile file) throws IOException, BizException {

        InputStream in = null;
        try {

            //从第1行、第0列开始读取，不包含表头信息。
            in = file.getInputStream();
            int rowOffset = 1;
            int colOffset = 0;

            List<CardInSettlementVO> list = ExcelReader.readExcel(CardInSettlementVO.class, in, rowOffset, colOffset);

            List<CardSettlement> settlements = listCopy(list);

            settlementService.updateFinishDate(settlements);

        } catch (Exception e) {
            throw e;
        } finally {
            if(in != null){
                in.close();
            }
        }
        return ResponseData.success();
    }

    /**
     * 导出出库结算数据
     * @param condition
     * @param response
     */
    @RequestMapping("/exportOutData")
    public void exportOutData(SettlementCondition condition, HttpServletResponse response) {

        PageBean pageBean = new PageBean();
        pageBean.setPageSize(0);

        PageDataResult<CardSettlementListVO> list = settlementService.listSettlement(condition,pageBean);
        List<CardOutSettlementVO> outData = new ArrayList<>();
        list.getList().stream().forEach(item -> {
            CardOutSettlementVO settlementVO = new CardOutSettlementVO();
            BeanUtils.copyProperties(item,settlementVO);
            outData.add(settlementVO);
        });

        InputStream inputStream = this.getClass().getResourceAsStream("/xlsx/card_out_settlement_template.xlsx");;

        String fileName = "出库结算数据";

        SXSSFWorkbook wb = ExcelWriter.getDataForExcel(inputStream,outData);

        ExcelUtils.writeExcelResponse(wb,fileName,response);
    }


    /**
     * 导入出库结算数据更新付款日期
     * @param file
     */
    @RequestMapping("/importOutData")
    public Result importOutData(@RequestParam("file") MultipartFile file) throws IOException, BizException {

        InputStream in = null;
        try {

            //从第1行、第0列开始读取，不包含表头信息。
            in = file.getInputStream();
            int rowOffset = 1;
            int colOffset = 0;

            List<CardOutSettlementVO> list = ExcelReader.readExcel(CardOutSettlementVO.class, in, rowOffset, colOffset);
            if(list.size() == 0){
                throw new BizException("请填写有效数据");
            }

            List<CardSettlement> settlements = listCopy(list);

            settlementService.updateFinishDate(settlements);

        } catch (Exception e) {
            throw e;
        } finally {
            if(in != null){
                in.close();
            }
        }
        return ResponseData.success();
    }

    /**
     * List内容复制到新的对象
     * @param list
     * @param <T>
     * @return
     * @throws BizException
     */
    private <T> List<CardSettlement> listCopy(List<T> list) throws BizException {
        if(list.size() == 0){
            throw new BizException("请填写有效数据");
        }
        List<CardSettlement> settlements = new ArrayList<>();
        list.stream().forEach(item -> {
            CardSettlement settlement = new CardSettlement();
            BeanUtils.copyProperties(item,settlement);
            settlements.add(settlement);
        });

        return settlements;
    }

}
