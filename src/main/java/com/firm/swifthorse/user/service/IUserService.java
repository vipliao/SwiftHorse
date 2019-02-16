package com.firm.swifthorse.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.firm.swifthorse.base.service.IBaseService;
import com.firm.swifthorse.user.entity.UserEntity;
import com.firm.swifthorse.user.vo.UserVO;
import com.firm.swifthorse.user.vo.WeeklyGrowthVO;

public interface IUserService extends IBaseService<UserEntity,UserVO>{

	UserVO login(UserVO vo) throws Exception;

	void deleteByPhone(String phone) throws Exception;

	UserVO queryUserByPhone(String phone) throws Exception;

	UserVO saveSignAndPoints(Map<String, Object> param) throws Exception;

	Page<UserVO> queryUserList(Pageable pageable, Map<String, Object> map) throws Exception;

	UserVO recomCodePoints(String recomCode, String currentUserId,int points) throws Exception;

	int dailyActivity(String day) throws Exception;

	int dailyRegistCount(String day) throws Exception;

	List<WeeklyGrowthVO> weeklyGrowth(String day) throws Exception;

	void frozenUser(String id,int type) throws Exception;

}
