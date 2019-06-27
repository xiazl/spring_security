package com.cims.business.card.controller;

import com.cims.business.card.service.CardPurchaseService;
import com.cims.business.card.vo.CardPurchaseVO;
import com.cims.business.card.vo.PurchaseCondition;
import com.cims.common.Constants;
import com.cims.common.excel.ExcelUtils;
import com.cims.common.excel.ExcelWriter;
import com.cims.common.excel.ExcelReader;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.response.ResponseData;
import com.cims.framework.response.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author belly
 * @create 4/15/19 5:48 PM
 * @description 卡片入库信息
 */



@RestController
@RequestMapping("/card/purchase")
public class CardPurchaseController {

    @Autowired
    private CardPurchaseService cardPurchaseService;

    /**
     * 卡片入库信息列表
     * @param conditionVO
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/list")
    @RolesAllowed({Constants.ADMIN,Constants.OP, Constants.IN_CARD})
    public Result list(PurchaseCondition conditionVO, PageBean pageBean) {
        PageDataResult<CardPurchaseVO> cardPurchaseList = cardPurchaseService.list(conditionVO, pageBean);
        return ResponseData.success(cardPurchaseList);
    }

    /**
     * 新增单张卡片与入库信息
     * @param cardPurchaseVO
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/save")
    @RolesAllowed({Constants.ADMIN,Constants.OP, Constants.IN_CARD})
    public Result saveCardAndPurchase(@RequestBody CardPurchaseVO cardPurchaseVO) throws BizException {
        cardPurchaseService.saveCardAndPurchase(cardPurchaseVO);
        return ResponseData.success();
    }

    /**
     * 更新单张卡片与入库信息
     * @param cardPurchaseVO
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/update")
    @RolesAllowed({Constants.ADMIN,Constants.OP, Constants.IN_CARD})
    public Result updateCardAndPurchase(@RequestBody CardPurchaseVO cardPurchaseVO) throws BizException {
        cardPurchaseService.updateCardAndPurchase(cardPurchaseVO);
        return ResponseData.success();
    }

    /**
     * 批量删除卡片
     * @param ids
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/delete")
    @RolesAllowed({Constants.ADMIN,Constants.OP})
    public Result deleteCardAndPurchase(@RequestParam(value = "ids") List<Long> ids) throws BizException {
        cardPurchaseService.deleteCardAndPurchase(ids);
        return ResponseData.success();
    }

    /**
     * 导入卡片和入库数据
     * @param file
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/import")
    @RolesAllowed({Constants.ADMIN,Constants.OP, Constants.IN_CARD})
    public Result importCardAndPurchase(@RequestParam("file") MultipartFile file) throws IOException, BizException {
        List<CardPurchaseVO> list = readFile(file);
        cardPurchaseService.importCardAndPurchase(list);
        return ResponseData.success();
    }


    /**
     * 批量更新入库价格
     * @param file
     * @return
     * @throws IOException
     * @throws BizException
     */
    @RequestMapping(value = "/importPrice")
    @RolesAllowed({Constants.ADMIN,Constants.OP, Constants.IN_CARD})
    public Result importUpdatePrice(@RequestParam("file") MultipartFile file) throws IOException, BizException {
        List<CardPurchaseVO> list = readFile(file);
        cardPurchaseService.importUpdatePrice(list);
        return ResponseData.success();
    }

    /**
     * 下载入库导入模版
     * @return
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response) {

        String fileName = "入库表导入模版";
        InputStream inputStream = this.getClass().getResourceAsStream("/xlsx/card_in_template.xlsx");

        SXSSFWorkbook wb = ExcelWriter.getDataForExcel(inputStream,null);
        ExcelUtils.writeExcelResponse(wb,fileName,response);

    }


    private List<CardPurchaseVO> readFile(MultipartFile file) throws IOException, BizException {
        if (file.isEmpty()) {
            throw new BizException("文件大小为空");
        }
        InputStream in = null;
        List<CardPurchaseVO> list;
        try {
            in = file.getInputStream();
            int rowOffSet = 1;
            int colOffSet = 0;

            list = ExcelReader.readExcel(CardPurchaseVO.class, in, rowOffSet, colOffSet);
            list = list.stream().filter(m -> StringUtils.isNotEmpty(m.getCardNo())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(list)){
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
