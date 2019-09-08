package cn.itsource.hrm.web.controller;

import cn.itsource.hrm.service.ISiteService;
import cn.itsource.hrm.domain.Site;
import cn.itsource.hrm.query.SiteQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/site")
public class SiteController {
    @Autowired
    public ISiteService siteService;

    /**
    * 保存和修改公用的
    * @param site  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Site site){
        try {
            if(site.getId()!=null){
                siteService.updateById(site);
            }else{
                siteService.insert(site);
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
            siteService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Site get(@PathVariable("id")Long id)
    {
        return siteService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Site> list(){

        return siteService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Site> json(@RequestBody SiteQuery query)
    {
        Page<Site> page = new Page<Site>(query.getPage(),query.getRows());
            page = siteService.selectPage(page);
            return new PageList<Site>(page.getTotal(),page.getRecords());
    }
}
