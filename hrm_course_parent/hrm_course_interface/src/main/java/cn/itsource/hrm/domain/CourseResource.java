package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yhptest
 * @since 2019-09-03
 */
@TableName("t_course_resource")
public class CourseResource extends Model<CourseResource> {

    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    @TableId("course_id")
    private Long courseId;
    /**
     * 图片id
     */
    private String resources;


    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    @Override
    protected Serializable pkVal() {
        return this.courseId;
    }

    @Override
    public String toString() {
        return "CourseResource{" +
        ", courseId=" + courseId +
        ", resources=" + resources +
        "}";
    }
}
