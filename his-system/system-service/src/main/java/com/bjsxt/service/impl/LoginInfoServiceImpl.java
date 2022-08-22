package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.dto.LoginInfoDto;
import com.bjsxt.vo.DataGridView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.LoginInfo;
import com.bjsxt.mapper.LoginInfoMapper;
import com.bjsxt.service.LoginInfoService;
/**
* @Author: 尚学堂 雷哥
*/

@Service
public class LoginInfoServiceImpl implements LoginInfoService{

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Override
    public int insertLoginInfo(LoginInfo loginInfo) {
        return this.loginInfoMapper.insert(loginInfo);
    }

    @Override
    public DataGridView listForPage(LoginInfoDto loginInfoDto) {
        Page<LoginInfo> page=new Page<>(loginInfoDto.getPageNum(),loginInfoDto.getPageSize());
        QueryWrapper<LoginInfo> qw=new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(loginInfoDto.getUserName()),LoginInfo.COL_USER_NAME,loginInfoDto.getUserName());
        qw.like(StringUtils.isNotBlank(loginInfoDto.getIpAddr()),LoginInfo.COL_IP_ADDR,loginInfoDto.getIpAddr());
        qw.like(StringUtils.isNotBlank(loginInfoDto.getLoginAccount()),LoginInfo.COL_LOGIN_ACCOUNT,loginInfoDto.getLoginAccount());
        qw.eq(StringUtils.isNotBlank(loginInfoDto.getLoginStatus()),LoginInfo.COL_LOGIN_STATUS,loginInfoDto.getLoginStatus());
        qw.eq(StringUtils.isNotBlank(loginInfoDto.getLoginType()),LoginInfo.COL_LOGIN_TYPE,loginInfoDto.getLoginType());
        qw.ge(null!= loginInfoDto.getBeginTime(), LoginInfo.COL_LOGIN_TIME, loginInfoDto.getBeginTime());
        qw.le(null!= loginInfoDto.getEndTime(), LoginInfo.COL_LOGIN_TIME, loginInfoDto.getEndTime());
        qw.orderByDesc(LoginInfo.COL_LOGIN_TIME);
        this.loginInfoMapper.selectPage(page,qw);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @Override
    public int deleteLoginInfoByIds(Long[] infoIds) {
        List<Long> ids = Arrays.asList(infoIds);
        if(ids.size()>0){
            return this.loginInfoMapper.deleteBatchIds(ids);
        }
        return 0;
    }

    @Override
    public int clearLoginInfo() {
        return this.loginInfoMapper.delete(null);
    }
}
