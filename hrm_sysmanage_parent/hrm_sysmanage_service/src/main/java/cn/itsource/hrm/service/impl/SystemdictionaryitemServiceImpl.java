package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.Systemdictionary;
import cn.itsource.hrm.domain.Systemdictionaryitem;
import cn.itsource.hrm.mapper.SystemdictionaryMapper;
import cn.itsource.hrm.mapper.SystemdictionaryitemMapper;
import cn.itsource.hrm.service.ISystemdictionaryitemService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yhptest
 * @since 2019-08-30
 */
@Service
public class SystemdictionaryitemServiceImpl extends ServiceImpl<SystemdictionaryitemMapper, Systemdictionaryitem> implements ISystemdictionaryitemService {

    private  Logger logger = LoggerFactory.getLogger(SystemdictionaryitemServiceImpl.class);
    @Autowired
    private SystemdictionaryMapper systemdictionaryMapper;
    @Autowired
    private SystemdictionaryitemMapper systemdictionaryitemMapper;
    @Override
    public List<Systemdictionaryitem> listByParentSn(String sn) {
        Wrapper<Systemdictionary> wrapper = new EntityWrapper<Systemdictionary>().eq("sn", sn);
        //select * from t_Systemdictionary
        //Wrapper(EntityWrapper)可以用它封装查询条件
        //Wrapper.eq表示等于 eq("sn","courseLevel")
        //select * from t_Systemdictionary where sn = courseLevel
        List<Systemdictionary> systemdictionaries = systemdictionaryMapper.selectList(wrapper);
        if (systemdictionaries == null || systemdictionaries.size()<1){
            logger.error(" systemdictionaries not exist!");
            return null;
        }
        Systemdictionary systemdictionary = systemdictionaries.get(0);
        EntityWrapper<Systemdictionaryitem> wrapper1 = new EntityWrapper<>();

        // where parent_id = #{id}
        wrapper1.eq("parent_id",systemdictionary.getId());
        return systemdictionaryitemMapper.selectList(wrapper1);
    }
}
