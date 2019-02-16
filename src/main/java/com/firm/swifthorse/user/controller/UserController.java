package com.firm.swifthorse.user.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.firm.swifthorse.base.utils.JsonBackData;
import com.firm.swifthorse.user.entity.UserEntity;
import com.firm.swifthorse.user.service.IUserService;
import com.firm.swifthorse.user.vo.UserVO;
import com.firm.swifthorse.user.vo.WeeklyGrowthVO;

@Controller
@RequestMapping(value="user")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService service;
	
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonBackData save(@RequestBody UserVO vo) {
		JsonBackData back = new JsonBackData();
		try {

			UserVO userVO = service.save(vo, UserEntity.class, UserVO.class);
			String backData = null;
			if(userVO != null){
				backData = JSON.toJSON(userVO).toString();
			}
			back.setBackData(backData);
			back.setSuccess(true);
			back.setBackMsg("用户信息保存成功！");

		} catch (Exception e) {
			logger.error("用户信息保存方法：", e);
			back.setSuccess(false);
			back.setBackMsg("用户信息保存失败," + e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "qureyOneUser")
	@ResponseBody
	public JsonBackData qureyOneUser(String id) {
		JsonBackData back = new JsonBackData();
		try {
				UserVO userVO = service.findVOById(id, UserVO.class);
				String backData = null;
				if(userVO != null){
					backData = JSON.toJSON(userVO).toString();
				}
				back.setBackData(backData);
				back.setSuccess(true);
				back.setBackMsg("查询用户信息成功！");
			
		}catch (Exception e) {
			logger.error("查询用户信息方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询用户信息失败,"+e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "userList")
	@ResponseBody
	public JsonBackData userList(@RequestParam Map<String, Object> map,@RequestParam(required =false,defaultValue="-1") int isAuth,@RequestParam(required=false,defaultValue="-1") int dr) {
		JsonBackData back = new JsonBackData();
		try {
				Pageable pageable = null;
				String pageNumber = (String) map.get("pageNumber");
				String pageSize = (String) map.get("pageSize");
				String sortField = (String) map.get("sortField");
				String sortType = (String) map.get("sortType");
				if (!StringUtils.isEmpty(pageNumber) && !StringUtils.isEmpty(pageSize)) {
					int iPageNumber = Integer.parseInt(pageNumber);
					if (!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortType)) {
						pageable = new PageRequest(iPageNumber <= 0 ? 0 : (iPageNumber - 1), Integer.parseInt(pageSize),
								Direction.fromStringOrNull(sortType), sortField);
					} else {
						pageable = new PageRequest(iPageNumber <= 0 ? 0 : (iPageNumber - 1), Integer.parseInt(pageSize));
					}
				}
				Page<UserVO> userVO = service.queryUserList(pageable,map);
				String backData = null;
				if(userVO != null){
					backData = JSON.toJSON(userVO).toString();
				}
				back.setBackData(backData);
				back.setSuccess(true);
				back.setBackMsg("查询用户信息列表成功！");
			
		}catch (Exception e) {
			logger.error("查询用户信息列表方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询用户信息列表失败,"+e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "delete")
	public JsonBackData delete(@RequestParam Map<String,Object> param) {
		JsonBackData back = new JsonBackData();
		try {	
			if(param.containsKey("phone")){
				String phone = (String) param.get("phone");
				service.deleteByPhone(phone);
				back.setSuccess(true);
				back.setBackMsg("删除用户信息成功！");	
			}else{
				back.setSuccess(false);
				back.setBackMsg("删除用户信息失败,参数缺少phone");
			}
				
		}catch (Exception e) {
			logger.error("删除用户信息方法：", e);
			back.setSuccess(false);
			back.setBackMsg("删除用户信息失败,"+e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "queryUser")
	@ResponseBody
	public JsonBackData queryUser(@RequestParam String phone) {
		JsonBackData back = new JsonBackData();
		try {
				UserVO userVO = service.queryUserByPhone(phone);
				String backData = null;
				if(userVO != null){
					backData = JSON.toJSON(userVO).toString();
				}
				back.setBackData(backData);
				back.setSuccess(true);
				back.setBackMsg("根据用户名查询用户信息成功！");
			
		}catch (Exception e) {
			logger.error("根据用户名查询用户信息方法：", e);
			back.setSuccess(false);
			back.setBackMsg("根据用户名查询用户信息失败,"+e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "login")
	@ResponseBody
	public JsonBackData login(@RequestParam String phone,@RequestParam String password) {
		JsonBackData back = new JsonBackData();
		try {
				UserVO vo =new UserVO();
				vo.setPhone(phone);
				vo.setPassword(password);
				UserVO userVO = service.login(vo);
				String backData = null;
				if(userVO != null){
					backData = JSON.toJSON(userVO).toString();
				}
				back.setBackData(backData);
				back.setSuccess(true);
				back.setBackMsg("登录成功！");
			
		}catch (Exception e) {
			logger.error("用户登录方法：", e);
			back.setSuccess(false);
			back.setBackMsg("登录失败,"+e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "saveSignAndPoints")
	@ResponseBody
	public JsonBackData saveSignAndPoints(@RequestParam Map<String,Object> param) {
		JsonBackData back = new JsonBackData();
		try {
				UserVO userVO = service.saveSignAndPoints(param);
				String backData = null;
				if(userVO != null){
					backData = JSON.toJSON(userVO).toString();
				}
				back.setBackData(backData);
				back.setSuccess(true);
				back.setBackMsg("保存积分/签到成功！");
			
		}catch (Exception e) {
			logger.error("保存积分/签到成功方法：", e);
			back.setSuccess(false);
			back.setBackMsg("保存积分/签到成功失败,"+e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "recomCodePoints")
	@ResponseBody
	public JsonBackData recomCodePoints(@RequestParam String recomCode,@RequestParam String currentUserId,@RequestParam int points) {
		JsonBackData back = new JsonBackData();
		try {
				UserVO userVO = service.recomCodePoints(recomCode,currentUserId,points);
				String backData = null;
				if(userVO != null){
					backData = JSON.toJSON(userVO).toString();
				}
				back.setBackData(backData);
				back.setSuccess(true);
				back.setBackMsg("保存推荐码积分/签到成功！");
			
		}catch (Exception e) {
			logger.error("保存推荐码积分/签到成功方法：", e);
			back.setSuccess(false);
			back.setBackMsg("保存推荐码积分/签到成功失败,"+e.getMessage());
		}
		return back;
	}
	@RequestMapping(value = "dailyActivity")
	@ResponseBody
	public JsonBackData dailyActivity(@RequestParam String day) {
		JsonBackData back = new JsonBackData();
		try {
				int count = service.dailyActivity(day);
				back.setBackData(JSON.toJSON(count).toString());
				back.setSuccess(true);
				back.setBackMsg("查询日活量成功！");
			
		}catch (Exception e) {
			logger.error("查询日活量成功方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询日活量失败,"+e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "dailyRegistCount")
	@ResponseBody
	public JsonBackData dailyRegistCount(@RequestParam String day) {
		JsonBackData back = new JsonBackData();
		try {
				int count = service.dailyRegistCount(day);
				back.setBackData(JSON.toJSON(count).toString());
				back.setSuccess(true);
				back.setBackMsg("查询日注册量成功！");
			
		}catch (Exception e) {
			logger.error("查询日注册量成功方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询日注册量失败,"+e.getMessage());
		}
		return back;
	}

	@RequestMapping(value = "weeklyGrowth")
	@ResponseBody
	public JsonBackData weeklyGrowth(@RequestParam String day) {
		JsonBackData back = new JsonBackData();
		try {
				List<WeeklyGrowthVO> count = service.weeklyGrowth(day);
				back.setBackData(JSON.toJSON(count).toString());
				back.setSuccess(true);
				back.setBackMsg("查询周注册增长量成功！");
			
		}catch (Exception e) {
			logger.error("查询周注册增长量方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询周注册增长量失败,"+e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "frozenUser")
	@ResponseBody
	public JsonBackData frozenUser(@RequestParam String id,@RequestParam(required=false,defaultValue="1") int type) {
		JsonBackData back = new JsonBackData();
		try {
				service.frozenUser(id,type);
				back.setSuccess(true);
				back.setBackMsg("冻结/解冻用户成功！");
			
		}catch (Exception e) {
			logger.error("冻结/解冻用户方法：", e);
			back.setSuccess(false);
			back.setBackMsg("冻结/解冻用户失败,"+e.getMessage());
		}
		return back;
	}

}
