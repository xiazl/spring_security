package com.cims.business.card.mapper;

import com.cims.business.card.entity.CardProject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CardProjectMapper {
    /**
     * 项目列表查询
     * @param condition
     * @return
     */
    List<CardProject> list(CardProject condition);

    /**
     * 根据Ids查询
     * @param ids
     * @return
     */
    List<CardProject> listByIds(List<Integer> ids);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteByPrimaryKeys(List<Integer> ids);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(CardProject record);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    CardProject selectByPrimaryKey(Integer id);

    /**
     * 通过名称查询
     * @param projectName
     * @return
     */
    CardProject selectByProjectName(String projectName);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(CardProject record);
}