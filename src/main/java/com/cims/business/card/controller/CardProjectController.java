package com.cims.business.card.controller;

import com.cims.business.card.entity.CardProject;
import com.cims.business.card.service.CardProjectService;
import com.cims.common.Constants;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.response.ResponseData;
import com.cims.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @author baidu
 * @date 2019-04-20 21:19
 * @description 出库项目
 **/

@RestController
@RequestMapping("/project")
@RolesAllowed({Constants.OUT_CARD,Constants.ADMIN,Constants.OP})
public class CardProjectController {

    @Autowired
    private CardProjectService projectService;

    /**
     * 项目列表
     * @param condition
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/list")
    public Result list(CardProject condition, PageBean pageBean) {
        PageDataResult<CardProject> list = projectService.list(condition,pageBean);
        return ResponseData.success(list);
    }

    /**
     * 项目新增
     * @param projectName
     * @return
     */
    @RequestMapping(value = "/add")
    public Result add(@RequestParam(value = "projectName") String projectName) throws BizException {
        CardProject project  = new CardProject();
        project.setProjectName(projectName);
        projectService.add(project);
        return ResponseData.success();
    }

    /**
     * 项目修改
     * @param id
     * @param projectName
     * @return
     */
    @RequestMapping(value = "/update")
    public Result update(@RequestParam(value = "id")Integer id,
                         @RequestParam(value = "projectName")String projectName) throws BizException {
        CardProject project  = new CardProject();
        project.setId(id);
        project.setProjectName(projectName);
        projectService.update(project);
        return ResponseData.success();
    }

    /**
     * 项目删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") List<Integer> ids) {
        projectService.delete(ids);
        return ResponseData.success();
    }
}
