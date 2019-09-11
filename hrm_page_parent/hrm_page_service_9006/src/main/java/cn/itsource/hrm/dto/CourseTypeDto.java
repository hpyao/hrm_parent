package cn.itsource.hrm.dto;

import java.util.ArrayList;
import java.util.List;

public class CourseTypeDto {
    private Long id;
    private  String name;
    private String logo;
    private List<CourseTypeDto> children = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<CourseTypeDto> getChildren() {
        return children;
    }

    public void setChildren(List<CourseTypeDto> children) {
        this.children = children;
    }
}
