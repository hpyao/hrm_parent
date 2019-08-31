package cn.itsource.hrm.web.controller;

import cn.itsource.hrm.service.ISystemdictionaryService;
import cn.itsource.hrm.domain.Systemdictionary;
import cn.itsource.hrm.query.SystemdictionaryQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systemdictionary")
public class SystemdictionaryController {
    @Autowired
    public ISystemdictionaryService systemdictionaryService;

    /**
    * 保存和修改公用的
    * @param systemdictionary  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Systemdictionary systemdictionary){
        try {
            if(systemdictionary.getId()!=null){
                systemdictionaryService.updateById(systemdictionary);
            }else{
                systemdictionaryService.insert(systemdictionary);
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
            systemdictionaryService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Systemdictionary get(@PathVariable("id")Long id)
    {
        return systemdictionaryService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Systemdictionary> list(){

        return systemdictionaryService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Systemdictionary> json(@RequestBody SystemdictionaryQuery query)
    {
        Page<Systemdictionary> page = new Page<Systemdictionary>(query.getPage(),query.getRows());
            page = systemdictionaryService.selectPage(page);
            return new PageList<Systemdictionary>(page.getTotal(),page.getRecords());
    }
}
