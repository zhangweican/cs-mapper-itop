package com.chinanetcenter.itop.mapper.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chinanetcenter.itop.mapper.mybatis.entry.TUserLogin;

@Component
public class TUserLoginService extends _TUserLoginService{
	public List<TUserLogin> findAll(){
		return tUserLoginMapper.findAll();
	}
	public boolean insertByRollBack(TUserLogin record) throws RuntimeException{
        tUserLoginMapper.insert(record);
        throw new RuntimeException("insert error");
    }
}