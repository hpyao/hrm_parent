package cn.itsource.hrm.web.controller;

import cn.itsource.hrm.service.IEmployeeService;
import cn.itsource.hrm.domain.Employee;
import cn.itsource.hrm.query.EmployeeQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    public IEmployeeService employeeService;

    /**
    * 保存和修改公用的
    * @param employee  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Employee employee){
        try {
            if(employee.getId()!=null){
                employeeService.updateById(employee);
            }else{
                employeeService.insert(employee);
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
            employeeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Employee get(@PathVariable("id")Long id)
    {
        return employeeService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Employee> list(){

        return employeeService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Employee> json(@RequestBody EmployeeQuery query)
    {
        Page<Employee> page = new Page<Employee>(query.getPage(),query.getRows());
            page = employeeService.selectPage(page);
            return new PageList<Employee>(page.getTotal(),page.getRecords());
    }
}
