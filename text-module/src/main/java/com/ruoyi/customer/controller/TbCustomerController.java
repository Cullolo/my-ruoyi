package com.ruoyi.customer.controller;

import com.ruoyi.customer.domain.TbCustomer;
import com.ruoyi.customer.service.ITbCustomerService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测试用Controller
 * 
 * @author ruoyi
 * @date 2021-01-22
 */
@Controller
@RequestMapping("/system/customer")
public class TbCustomerController extends BaseController
{
    private String prefix = "system/customer";

    @Autowired
    private ITbCustomerService tbCustomerService;

    @GetMapping("/getStr")
    public String getStr(){
        String s = "ssfsfs";
        return s;
    }


    @RequiresPermissions("system:customer:view")
    @GetMapping()
    public String customer()
    {
        return prefix + "/customer";
    }

    /**
     * 查询测试用列表
     */
    @RequiresPermissions("system:customer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TbCustomer tbCustomer)
    {
        startPage();
        List<TbCustomer> list = tbCustomerService.selectTbCustomerList(tbCustomer);
        return getDataTable(list);
    }

    /**
     * 导出测试用列表
     */
    @RequiresPermissions("system:customer:export")
    @Log(title = "测试用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TbCustomer tbCustomer)
    {
        List<TbCustomer> list = tbCustomerService.selectTbCustomerList(tbCustomer);
        ExcelUtil<TbCustomer> util = new ExcelUtil<TbCustomer>(TbCustomer.class);
        return util.exportExcel(list, "customer");
    }

    /**
     * 新增测试用
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存测试用
     */
    @RequiresPermissions("system:customer:add")
    @Log(title = "测试用", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TbCustomer tbCustomer)
    {
        return toAjax(tbCustomerService.insertTbCustomer(tbCustomer));
    }

    /**
     * 修改测试用
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TbCustomer tbCustomer = tbCustomerService.selectTbCustomerById(id);
        mmap.put("tbCustomer", tbCustomer);
        return prefix + "/edit";
    }

    /**
     * 修改保存测试用
     */
    @RequiresPermissions("system:customer:edit")
    @Log(title = "测试用", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TbCustomer tbCustomer)
    {
        return toAjax(tbCustomerService.updateTbCustomer(tbCustomer));
    }

    /**
     * 删除测试用
     */
    @RequiresPermissions("system:customer:remove")
    @Log(title = "测试用", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tbCustomerService.deleteTbCustomerByIds(ids));
    }
}
