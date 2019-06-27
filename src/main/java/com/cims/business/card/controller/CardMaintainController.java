package com.cims.business.card.controller;

import com.cims.business.card.service.CardMaintainService;
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
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;
import java.util.List;

/**
 * @author baidu
 * @date 2019-04-18 15:44
 * @description 卡片维护
 **/

@RestController
@RequestMapping("/maintain")
public class CardMaintainController {

    @Autowired
    private CardMaintainService maintainService;

    /**
     * 列表
     * @param condition
     * @param pageBean
     * @return
     */
    @RolesAllowed({Constants.STOCK,Constants.RETURN,Constants.ADMIN,Constants.OP})
    @RequestMapping(value = "/list")
    public Result list(MaintainCondition condition, PageBean pageBean) {
        PageDataResult<CardMaintainListVO> list = maintainService.list(condition,pageBean);
        return ResponseData.success(list);
    }

    /**
     * 新增停用记录
     * @param disableVOS
     * @return
     */
    @RolesAllowed({Constants.STOCK,Constants.ADMIN,Constants.OP})
    @RequestMapping(value = "/disable",method = RequestMethod.POST)
    public Result disable(@Valid @RequestBody CardDisableVO disableVOS) throws BizException {
        List<String> result = maintainService.disable(disableVOS);
        if(result.size() != 0){
            return ResponseData.failed(result);
        }
        return ResponseData.success();
    }

    /**
     * 新增冻结记录
     * @param freezeVOS
     * @return
     */
    @RolesAllowed({Constants.STOCK,Constants.ADMIN,Constants.OP})
    @RequestMapping(value = "/freeze",method = RequestMethod.POST)
    public Result freeze(@Valid @RequestBody CardFreezeVO freezeVOS) throws BizException {
        List<String> result = maintainService.freeze(freezeVOS);
        if(result.size() != 0){
            return ResponseData.failed(result);
        }
        return ResponseData.success();
    }

    /**
     * 新增问题卡
     * @param problemVOS
     * @return
     */
    @RolesAllowed({Constants.STOCK,Constants.ADMIN,Constants.OP})
    @RequestMapping(value = "/convertProblem",method = RequestMethod.POST)
    public Result convertProblem(@Valid @RequestBody CardProblemVO problemVOS) throws BizException {
        List<String> result = maintainService.convertProblem(problemVOS);
        if(result.size() != 0){
            return ResponseData.failed(result);
        }
        return ResponseData.success();
    }


    /**
     * 修改维护记录
     * @param cardVO
     * @return
     */
    @RolesAllowed({Constants.STOCK,Constants.ADMIN,Constants.OP})
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@Valid @RequestBody CardMaintainListVO cardVO) {
        maintainService.update(cardVO);
        return ResponseData.success();
    }

    /**
     * 修改维护卡状态
     * @param status
     * @return
     */
    @RolesAllowed({Constants.STOCK,Constants.ADMIN,Constants.OP})
    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    public Result updateStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        maintainService.updateStatus(id,status);
        return ResponseData.success();
    }

    /**
     * 删除卡片维护记录
     * @param ids
     * @return
     */
    @RolesAllowed({Constants.STOCK,Constants.ADMIN,Constants.OP})
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestParam("ids") List<Long> ids) {
        maintainService.delete(ids);
        return ResponseData.success();
    }

    /**
     * 下载停用导入模版
     * @return
     */
    @RolesAllowed({Constants.STOCK,Constants.ADMIN,Constants.OP})
    @RequestMapping("/downloadDisable")
    public void downloadDisable(HttpServletResponse response) {

        String fileName = "停用导入模版";
        InputStream inputStream = this.getClass().getResourceAsStream("/xlsx/card_disable_template.xlsx");

        SXSSFWorkbook wb = ExcelWriter.getDataForExcel(inputStream,null);
        ExcelUtils.writeExcelResponse(wb,fileName,response);

    }

    /**
     * 下载冻结导入模版
     * @return
     */
    @RolesAllowed({Constants.STOCK,Constants.ADMIN,Constants.OP})
    @RequestMapping("/downloadFreeze")
    public void downloadFreeze(HttpServletResponse response) {

        String fileName = "冻结导入模版";
        InputStream inputStream = this.getClass().getResourceAsStream("/xlsx/card_freeze_template.xlsx");

        SXSSFWorkbook wb = ExcelWriter.getDataForExcel(inputStream,null);
        ExcelUtils.writeExcelResponse(wb,fileName,response);

    }

    /**
     * 导入停用数据
     * @return
     */
    @RolesAllowed({Constants.STOCK,Constants.ADMIN,Constants.OP})
    @RequestMapping(value = "/importDisable", method = RequestMethod.POST)
    public Result importDisable(@RequestParam("file") MultipartFile file) throws Exception {

        InputStream in = null;
        try {

            //从第1行、第0列开始读取，不包含表头信息。
            in = file.getInputStream();
            int rowOffset = 1;
            int colOffset = 0;

            List<CardDisableVO> list = ExcelReader.readExcel(CardDisableVO.class, in, rowOffset, colOffset);
            if(list.size() == 0){
                throw new BizException("请填写有效数据");
            }

            List<String> messages = maintainService.disable(list);
            if(messages.size() > 0) {
                return ResponseData.failed(messages);
            }

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
     * 导入冻结数据
     * @return
     */
    @RolesAllowed({Constants.STOCK,Constants.ADMIN,Constants.OP})
    @RequestMapping(value = "/importFreeze", method = RequestMethod.POST)
    public Result importFreeze(@RequestParam("file") MultipartFile file) throws Exception {

        InputStream in = null;
        try {

            //从第1行、第0列开始读取，不包含表头信息。
            in = file.getInputStream();
            int rowOffset = 1;
            int colOffset = 0;

            List<CardFreezeVO> list = ExcelReader.readExcel(CardFreezeVO.class, in, rowOffset, colOffset);
            if(list.size() == 0){
                throw new BizException("请填写有效数据");
            }

            List<String> messages = maintainService.freeze(list);
            if(messages.size() > 0) {
                return ResponseData.failed(messages);
            }

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
     * 导出维护数据
     * @param condition
     * @param response
     */
    @RolesAllowed({Constants.STOCK,Constants.ADMIN,Constants.OP})
    @RequestMapping("/export")
    public void exportData(MaintainCondition condition, HttpServletResponse response) {

        PageBean pageBean = new PageBean();
        pageBean.setPageSize(0);

        PageDataResult<CardMaintainListVO> list = maintainService.list(condition,pageBean);

        InputStream inputStream = this.getClass().getResourceAsStream("/xlsx/card_maintain_template.xlsx");;
        String fileName = "维护数据";

        SXSSFWorkbook wb = ExcelWriter.getDataForExcel(inputStream,list.getList());

        ExcelUtils.writeExcelResponse(wb,fileName,response);
    }


}
