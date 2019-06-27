package com.cims.business.card.controller;

import com.cims.business.card.service.CardSaleService;
import com.cims.business.card.vo.*;
import com.cims.common.Constants;
import com.cims.common.excel.ExcelReader;
import com.cims.common.excel.ExcelUtils;
import com.cims.common.excel.ExcelWriter;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.response.ResponseData;
import com.cims.framework.response.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author belly
 * @create 4/21/19 7:58 PM
 * @description 卡片出库信息
 */

@RestController
@RequestMapping("/card/sale")
@RolesAllowed({Constants.ADMIN,Constants.OP, Constants.OUT_CARD})
public class CardSaleController {

    @Autowired
    private CardSaleService cardSaleService;

    /**
     * 未出卡片列表
     * @param conditionVO
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/list")
    public Result list(SaleCondition conditionVO, PageBean pageBean) {
        PageDataResult<CardSaleListVO> list = cardSaleService.list(conditionVO, pageBean);
        return ResponseData.success(list);
    }


    /**
     * 多选卡片出库
     * @param cardSaleVO
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/save")
    public Result saleCards(@Valid @RequestBody CardSaleVO cardSaleVO) throws BizException {
        if (CollectionUtils.isEmpty(cardSaleVO.getCardNos())){
            throw new BizException("卡号不能为空，请刷新页面后重试");
        }
        cardSaleService.saleCards(cardSaleVO);
        return ResponseData.success();
    }

    /**
     * 出库记录列表
     * @param conditionVO
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/details")
    public Result listDetails(SaleCondition conditionVO, PageBean pageBean){
        PageDataResult<CardSaleDetailVO> list = cardSaleService.listDetails(conditionVO, pageBean);
        return ResponseData.success(list);
    }

    /**
     * 更新出库记录
     * @param cardSaleVO
     * @return
     */
    @RequestMapping(value = "/update")
    public Result updateDetials(@Valid @RequestBody CardSaleVO cardSaleVO) throws BizException{
        if (cardSaleVO.getId() == null){
            throw new BizException("参数错误，请刷新页面后重试");
        }
        cardSaleService.updateCardSaleDetail(cardSaleVO);
        return ResponseData.success();
    }

    /**
     * 导出出库记录
     * @param condition
     * @param response
     */
    @RequestMapping(value = "/export")
    public void exportSaleDetails(SaleCondition condition, HttpServletResponse response) {

        PageBean pageBean = new PageBean();
        pageBean.setCurrent(1);
        pageBean.setPageSize(0);

        PageDataResult<CardSaleDetailVO> list = cardSaleService.listDetails(condition, pageBean);

        InputStream inputStream = this.getClass().getResourceAsStream("/xlsx/card_out_template.xlsx");
        String fileName = "出库数据";

        SXSSFWorkbook wb = ExcelWriter.getDataForExcel(inputStream,list.getList());
        ExcelUtils.writeExcelResponse(wb,fileName,response);
    }

    /**
     * 导入出库记录
     * @param file
     * @return
     * @throws IOException
     * @throws BizException
     */
    @RequestMapping(value = "/import")
    public Result importCardSaleDetail(@RequestParam("file") MultipartFile file) throws IOException, BizException{
        List<CardSaleImportVO> list = readFile(file);
        cardSaleService.importCardSaleDetails(list);
        return ResponseData.success();
    }


    /**
     * 下载出库导入模版
     * @return
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response) {

        String fileName = "出库导入模版";
        InputStream inputStream = this.getClass().getResourceAsStream("/xlsx/card_out_template.xlsx");

        SXSSFWorkbook wb = ExcelWriter.getDataForExcel(inputStream,null);
        ExcelUtils.writeExcelResponse(wb,fileName,response);

    }



    private List<CardSaleImportVO> readFile(MultipartFile file) throws IOException, BizException {
        if (file.isEmpty()) {
            throw new BizException("文件大小为空");
        }
        InputStream in = null;
        List<CardSaleImportVO> list;
        try {
            in = file.getInputStream();
            int rowOffSet = 1;
            int colOffSet = 0;

            list = ExcelReader.readExcel(CardSaleImportVO.class, in, rowOffSet, colOffSet);
            list = list.stream().filter(m -> StringUtils.isNotEmpty(m.getCardNo())).collect(Collectors.toList());
            if (org.apache.commons.collections4.CollectionUtils.isEmpty(list)){
                throw new BizException("文件内容为空");
            }
        }catch(IOException e){
            throw e;
        }finally{
            if (in != null){
                in.close();
            }
        }
        return list;
    }




}
