package cn.itsource.hrm.service;

import cn.itsource.hrm.domain.Systemdictionaryitem;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yhptest
 * @since 2019-08-30
 */
public interface ISystemdictionaryitemService extends IService<Systemdictionaryitem> {

    /**
     * 通过parent的sn查询明细
     * @param sn
     * @return
     */
    List<Systemdictionaryitem> listByParentSn(String sn);
}
