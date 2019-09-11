package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yhptest
 * @since 2019-09-08
 */
@TableName("t_page_config")
public class PageConfig extends Model<PageConfig> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("template_url")
    private String templateUrl;
    @TableField("templateName")
    private String templateName;
    /**
     * redis中数据所对应key
     */
    @TableField("data_key")
    private String dataKey;
    private String physicalPath;
    /**
     * 文件系统类型
     */
    @TableField("dfs_type")
    private Long dfsType;
    /**
     * 静态页面地址
     */
    @TableField("page_url")
    private String pageUrl;
    @TableField("page_id")
    private Long pageId;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getPhysicalPath() {
        return physicalPath;
    }

    public void setPhysicalPath(String physicalPath) {
        this.physicalPath = physicalPath;
    }

    public Long getDfsType() {
        return dfsType;
    }

    public void setDfsType(Long dfsType) {
        this.dfsType = dfsType;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PageConfig{" +
        ", id=" + id +
        ", templateUrl=" + templateUrl +
        ", dataKey=" + dataKey +
        ", physicalPath=" + physicalPath +
        ", dfsType=" + dfsType +
        ", pageUrl=" + pageUrl +
        ", pageId=" + pageId +
        "}";
    }
}
