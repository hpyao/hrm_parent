package cn.itsource.hrm.query;


/**
 *
 * @author yhptest
 * @since 2019-09-03
 */
public class CourseQuery extends BaseQuery{
    private Long courseType;
    private Long priceMin; //最小价格
    private Long priceMax; //最大价格
    private String sortField; //排序字段
    private String sortType = "desc"; //排序方式

    public Long getCourseType() {
        return courseType;
    }

    public void setCourseType(Long courseType) {
        this.courseType = courseType;
    }

    public Long getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Long priceMin) {
        this.priceMin = priceMin;
    }

    public Long getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Long priceMax) {
        this.priceMax = priceMax;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return "CourseQuery{" +
                "courseType=" + courseType +
                ", priceMin=" + priceMin +
                ", priceMax=" + priceMax +
                ", sortField='" + sortField + '\'' +
                ", sortType='" + sortType + '\'' +
                '}';
    }
}