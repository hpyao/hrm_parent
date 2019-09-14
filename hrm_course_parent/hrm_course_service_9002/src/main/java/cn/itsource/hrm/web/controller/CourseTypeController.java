package cn.itsource.hrm.web.controller;

import cn.itsource.hrm.service.ICourseTypeService;
import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.query.CourseTypeQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courseType")
public class CourseTypeController {
    @Autowired
    public ICourseTypeService courseTypeService;

    /**
    * 保存和修改公用的
    * @param courseType  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody CourseType courseType){
        try {
            if(courseType.getId()!=null){
                courseTypeService.updateById(courseType);
            }else{
                courseTypeService.insert(courseType);
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
            courseTypeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CourseType get(@PathVariable("id")Long id)
    {
        return courseTypeService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<CourseType> list(){

        return courseTypeService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<CourseType> json(@RequestBody CourseTypeQuery query)
    {

        // 分页+高级查询+关联查询
//        Page<CourseType> page = new Page<CourseType>(query.getPage(),query.getRows());
//            page = courseTypeService.selectPage(page);
//            return new PageList<CourseType>(page.getTotal(),page.getRecords());
        return courseTypeService.selectListPage(query);
    }

    /**
     * 查看所有的员工信息
     * @return
     */
    @RequestMapping(value = "/treeData",method = RequestMethod.GET)
    public List<CourseType> treeData(){

        return courseTypeService.queryTypeTree(0L);
    }

    /**
     *  通过类型查询面包屑数据
     *     有层次(Node): path
     *     Node: 自己和兄弟  path里面就是自己 通过自己查询父亲,再通过父亲找到儿子,删除自己就ok
     * @return
     */
    @RequestMapping(value = "/crumbs",method = RequestMethod.GET)
    public List<Map<String,Object>> getCrumbs(Long courseTypeId){
        return courseTypeService.getCrumbs(courseTypeId);
    }
}
