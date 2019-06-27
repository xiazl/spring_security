package com.cims.business.card.mapper;

import com.cims.business.card.entity.Bank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BankMapper {
    /**
     * 银行下拉列表
     * @return
     */
    List<Bank> listBank();

    /**
     * 查询银行名称
     * @param bankNameList
     * @return
     */
    List<Bank> listByNames(@Param("bankNameList") List<String> bankNameList);

    /**
     * 通过名称查询
     * @param bankName
     * @return
     */
    Bank selectByBankName(@Param("bankName") String bankName);

    /**
     * 通过Id查询
     * @param id
     * @return
     */
    Bank selectById(@Param("id") Long id);

    /**
     * 银行页面列表
     * @param bank
     * @return
     */
    List<Bank> list(Bank bank);

    /**
     * 增加银行
     * @param bank
     * @return
     */
    int insert(Bank bank);

    /**
     * 更新银行
     * @param bank
     * @return
     */
    int updateByPrimaryKey(Bank bank);


    /**
     * 删除银行
     * @param ids
     * @return
     */
    int deleteByPrimaryKeys(List<Long> ids);

}