package com.ruoyi.customer.service;

import com.ruoyi.customer.domain.TbCustomer;

import java.util.List;

/**
 * 测试用Service接口
 * 
 * @author ruoyi
 * @date 2021-01-22
 */
public interface ITbCustomerService 
{
    /**
     * 查询测试用
     * 
     * @param id 测试用ID
     * @return 测试用
     */
    public TbCustomer selectTbCustomerById(Long id);

    /**
     * 查询测试用列表
     * 
     * @param tbCustomer 测试用
     * @return 测试用集合
     */
    public List<TbCustomer> selectTbCustomerList(TbCustomer tbCustomer);

    /**
     * 新增测试用
     * 
     * @param tbCustomer 测试用
     * @return 结果
     */
    public int insertTbCustomer(TbCustomer tbCustomer);

    /**
     * 修改测试用
     * 
     * @param tbCustomer 测试用
     * @return 结果
     */
    public int updateTbCustomer(TbCustomer tbCustomer);

    /**
     * 批量删除测试用
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbCustomerByIds(String ids);

    /**
     * 删除测试用信息
     * 
     * @param id 测试用ID
     * @return 结果
     */
    public int deleteTbCustomerById(Long id);
}
