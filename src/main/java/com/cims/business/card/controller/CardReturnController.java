package com.cims.business.card.controller;

import com.cims.business.card.service.CardReturnService;
import com.cims.business.card.vo.CardReturnListVO;
import com.cims.business.card.vo.Condition;
import com.cims.common.Constants;
import com.cims.common.excel.ExcelUtils;
import com.cims.common.excel.ExcelWriter;
import com.cims.common.excel.ExcelReader;
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
 * @date 2019-04-17 14:24
 * @description 退库
 **/

@RestController
@RequestMapping("/return")
@RolesAllowed({Constants.RETURN,Constants.ADMIN,Constants.OP})
public class CardReturnController {

    @Autowired
    private CardReturnService returnService;

    /**
     * 列表
     * @param condition
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/list")
    public Result list(Condition condition, PageBean pageBean) {
        PageDataResult<CardReturnListVO> list = returnService.list(condition,pageBean);
        return ResponseData.success(list);
    }

    /**
     * 新增退库记录
     * @param cardVO
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@Valid @RequestBody CardReturnListVO cardVO) throws BizException {
        returnService.add(cardVO);
        return ResponseData.success();
    }

    /**
     * 修改退库记录
     * @param cardVO
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@Valid @RequestBody CardReturnListVO cardVO) throws BizException {
        if(cardVO.getId() == null){
            throw new BizException("id不能为空");
        }
        returnService.update(cardVO);
        return ResponseData.success();
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestParam("ids") List<Long> ids) {
        returnService.delete(ids);
        return ResponseData.success();
    }

    /**
     * 下载导入模版
     * @return
     */
    @RequestMapping("/download")
    public void downloadTemplate(HttpServletResponse response) {

        String fileName = "退库导入模版";
        InputStream inputStream = this.getClass().getResourceAsStream("/xlsx/card_return_template.xlsx");

        SXSSFWorkbook wb = ExcelWriter.getDataForExcel(inputStream,null);
        ExcelUtils.writeExcelResponse(wb,fileName,response);

    }

    /**
     * 导入退库数据
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public Result importData(@RequestParam("file") MultipartFile file) throws Exception {

        InputStream in = null;
        try {

            //从第1行、第0列开始读取，不包含表头信息。
            in = file.getInputStream();
            int rowOffset = 1;
            int colOffset = 0;

            List<CardReturnListVO> list = ExcelReader.readExcel(CardReturnListVO.class, in, rowOffset, colOffset);
            if(list.size() == 0){
                throw new BizException("请填写有效数据");
            }

            List<String> messages = returnService.addBatch(list);
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
     * 退库导出数据
     * @param condition
     * @param response
     */
    @RequestMapping("/export")
    public void exportData(@Valid Condition condition, HttpServletResponse response) {

        PageBean pageBean = new PageBean();
        pageBean.setPageSize(0);

        PageDataResult<CardReturnListVO> list = returnService.list(condition,pageBean);

        InputStream inputStream = this.getClass().getResourceAsStream("/xlsx/card_return_template.xlsx");;
        String fileName = "退库数据";

        SXSSFWorkbook wb = ExcelWriter.getDataForExcel(inputStream,list.getList());

        ExcelUtils.writeExcelResponse(wb,fileName,response);
    }


}
