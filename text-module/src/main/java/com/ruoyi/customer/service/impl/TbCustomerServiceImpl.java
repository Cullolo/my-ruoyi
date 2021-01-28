package com.ruoyi.customer.service.impl;

import java.util.List;

import com.ruoyi.customer.domain.TbCustomer;
import com.ruoyi.customer.mapper.TbCustomerMapper;
import com.ruoyi.customer.service.ITbCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;

/**
 * 测试用Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-01-22
 */
@Service
public class TbCustomerServiceImpl implements ITbCustomerService
{
    @Autowired
    private TbCustomerMapper tbCustomerMapper;

    /**
     * 查询测试用
     * 
     * @param id 测试用ID
     * @return 测试用
     */
    @Override
    public TbCustomer selectTbCustomerById(Long id)
    {
        return tbCustomerMapper.selectTbCustomerById(id);
    }

    /**
     * 查询测试用列表
     * 
     * @param tbCustomer 测试用
     * @return 测试用
     */
    @Override
    public List<TbCustomer> selectTbCustomerList(TbCustomer tbCustomer)
    {
        return tbCustomerMapper.selectTbCustomerList(tbCustomer);
    }

    /**
     * 新增测试用
     * 
     * @param tbCustomer 测试用
     * @return 结果
     */
    @Override
    public int insertTbCustomer(TbCustomer tbCustomer)
    {
        return tbCustomerMapper.insertTbCustomer(tbCustomer);
    }

    /**
     * 修改测试用
     * 
     * @param tbCustomer 测试用
     * @return 结果
     */
    @Override
    public int updateTbCustomer(TbCustomer tbCustomer)
    {
        return tbCustomerMapper.updateTbCustomer(tbCustomer);
    }

    /**
     * 删除测试用对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTbCustomerByIds(String ids)
    {
        return tbCustomerMapper.deleteTbCustomerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除测试用信息
     * 
     * @param id 测试用ID
     * @return 结果
     */
    @Override
    public int deleteTbCustomerById(Long id)
    {
        return tbCustomerMapper.deleteTbCustomerById(id);
    }
}
