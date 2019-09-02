package cn.itsource.hrm.web.controller;

import cn.itsource.hrm.service.IDepartmentService;
import cn.itsource.hrm.domain.Department;
import cn.itsource.hrm.query.DepartmentQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    public IDepartmentService departmentService;

    /**
    * 保存和修改公用的
    * @param department  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Department department){
        try {
            if(department.getId()!=null){
                departmentService.updateById(department);
            }else{
                departmentService.insert(department);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            departmentService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Department get(@PathVariable("id")Long id)
    {
        return departmentService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Department> list(){

        return departmentService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Department> json(@RequestBody DepartmentQuery query)
    {
        Page<Department> page = new Page<Department>(query.getPage(),query.getRows());
            page = departmentService.selectPage(page);
            return new PageList<Department>(page.getTotal(),page.getRecords());
    }
}
