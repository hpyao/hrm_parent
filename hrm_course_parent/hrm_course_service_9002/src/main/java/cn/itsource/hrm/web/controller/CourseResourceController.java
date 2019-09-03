package cn.itsource.hrm.web.controller;

import cn.itsource.hrm.service.ICourseResourceService;
import cn.itsource.hrm.domain.CourseResource;
import cn.itsource.hrm.query.CourseResourceQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseResource")
public class CourseResourceController {
    @Autowired
    public ICourseResourceService courseResourceService;

    /**
    * 保存和修改公用的
    * @param courseResource  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody CourseResource courseResource){
        try {
            if(courseResource.getCourseId()!=null){
                courseResourceService.updateById(courseResource);
            }else{
                courseResourceService.insert(courseResource);
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
            courseResourceService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CourseResource get(@PathVariable("id")Long id)
    {
        return courseResourceService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<CourseResource> list(){

        return courseResourceService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<CourseResource> json(@RequestBody CourseResourceQuery query)
    {
        Page<CourseResource> page = new Page<CourseResource>(query.getPage(),query.getRows());
            page = courseResourceService.selectPage(page);
            return new PageList<CourseResource>(page.getTotal(),page.getRecords());
    }
}
