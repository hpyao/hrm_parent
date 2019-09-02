package cn.itsource.hrm.web.controller;

import cn.itsource.hrm.service.IMenuService;
import cn.itsource.hrm.domain.Menu;
import cn.itsource.hrm.query.MenuQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    public IMenuService menuService;

    /**
    * 保存和修改公用的
    * @param menu  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Menu menu){
        try {
            if(menu.getId()!=null){
                menuService.updateById(menu);
            }else{
                menuService.insert(menu);
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
            menuService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Menu get(@PathVariable("id")Long id)
    {
        return menuService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Menu> list(){

        return menuService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Menu> json(@RequestBody MenuQuery query)
    {
        Page<Menu> page = new Page<Menu>(query.getPage(),query.getRows());
            page = menuService.selectPage(page);
            return new PageList<Menu>(page.getTotal(),page.getRecords());
    }
}
