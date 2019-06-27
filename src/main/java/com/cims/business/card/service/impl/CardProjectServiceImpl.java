package com.cims.business.card.service.impl;

import com.cims.business.card.entity.CardProject;
import com.cims.business.card.mapper.CardProjectMapper;
import com.cims.business.card.service.CardProjectService;
import com.cims.common.context.UserContext;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.page.PageHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baidu
 * @date 2019-04-20 21:21
 * @description TODO
 **/

@Service("projectService")
public class CardProjectServiceImpl implements CardProjectService {
    @Autowired
    private CardProjectMapper projectMapper;

    @Override
    public PageDataResult<CardProject> list(CardProject condition, PageBean pageBean) {
        PageHelp.pageAndOrderBy(pageBean);
        List<CardProject> list = projectMapper.list(condition);
        return PageHelp.getDataResult(list, pageBean);
    }

    @Override
    public List<CardProject> list() {
        return projectMapper.list(new CardProject() {{ setIsDelete(0); }});
    }

    @Override
    public void add(CardProject cardProject) throws BizException {
        CardProject project = projectMapper.selectByProjectName(cardProject.getProjectName());
        if(project != null){
            throw new BizException("项目名已存在");
        }
        cardProject.setCreateUserId(UserContext.getUserId());
        projectMapper.insert(cardProject);
    }

    @Override
    public void update(CardProject cardProject) throws BizException {
        CardProject project = projectMapper.selectByProjectName(cardProject.getProjectName());
        if(project != null && cardProject.getId().longValue() != project.getId()){
            throw new BizException("项目名已存在");
        }
        projectMapper.updateByPrimaryKey(cardProject);
    }

    @Override
    public void delete(List<Integer> ids) {
        projectMapper.deleteByPrimaryKeys(ids);
    }
}
