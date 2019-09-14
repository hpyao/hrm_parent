package cn.itsource.hrm.service;


import cn.itsource.hrm.doc.EsCourse;
import cn.itsource.hrm.query.EsCourseQuery;
import cn.itsource.hrm.util.PageList;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yhptest
 * @since 2019-09-05
 */
public interface IEsCourseService {

    void updateById(EsCourse esCourse);

    void insert(EsCourse esCourse);

    void deleteById(Long id);

    EsCourse selectById(Long id);

    List<EsCourse> selectList(Object o);

    PageList<EsCourse> selectListPage(EsCourseQuery query);

    void batchSave(List<EsCourse> ids);

    void batchDel(List<EsCourse> esCourseList);

    PageList<Map<String,Object>> query(Map<String, Object> params);
}
