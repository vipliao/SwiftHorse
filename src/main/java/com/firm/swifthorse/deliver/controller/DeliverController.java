package com.firm.swifthorse.deliver.controller;

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
import com.firm.swifthorse.deliver.entity.DeliverEntity;
import com.firm.swifthorse.deliver.service.IDeliverService;
import com.firm.swifthorse.deliver.vo.DeliverVO;

@Controller
@RequestMapping("deliver")
public class DeliverController {
private static Logger logger = LoggerFactory.getLogger(DeliverController.class);
	
	@Autowired
	private IDeliverService service;
	
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonBackData save(@RequestBody DeliverVO vo) {
		JsonBackData back = new JsonBackData();
		try {
			DeliverVO reVO = service.save(vo, DeliverEntity.class, DeliverVO.class);
			String backData = null;
			if(reVO != null){
				backData = JSON.toJSON(reVO).toString();
			}
			back.setBackData(backData);
			back.setSuccess(true);
			back.setBackMsg("投递信息保存成功！");

		} catch (Exception e) {
			logger.error("投递信息保存方法：", e);
			back.setSuccess(false);
			back.setBackMsg("投递信息保存失败," + e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "qureyOne")
	@ResponseBody
	public JsonBackData qureyOne(@RequestParam String id) {
		JsonBackData back = new JsonBackData();
		try {
				DeliverVO reVO = service.findVOById(id, DeliverVO.class);
				String backData = null;
				if(reVO != null){
					backData = JSON.toJSON(reVO).toString();
				}
				back.setBackData(backData);
				back.setSuccess(true);
				back.setBackMsg("查询投递信息成功！");
			
		}catch (Exception e) {
			logger.error("查询投递信息方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询投递信息失败,"+e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "queryList")
	@ResponseBody
	public JsonBackData queryList(@RequestParam Map<String, Object> map) {
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
				Page<DeliverVO> reVO = service.queryList(pageable,map);
				String backData = null;
				if(reVO != null){
					backData = JSON.toJSON(reVO).toString();
				}
				back.setBackData(backData);
				back.setSuccess(true);
				back.setBackMsg("查询投递信息列表成功！");
			
		}catch (Exception e) {
			logger.error("查询投递信息列表方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询投递信息列表失败,"+e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "doCancle")
	@ResponseBody
	public JsonBackData delete(@RequestParam String id) {
		JsonBackData back = new JsonBackData();
		try {	
			service.doCancle(id);
			back.setSuccess(true);
			back.setBackMsg("取消投递信息成功！");			
		}catch (Exception e) {
			logger.error("取消投递信息方法：", e);
			back.setSuccess(false);
			back.setBackMsg("取消投递信息失败,"+e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "updateState")
	@ResponseBody
	public JsonBackData updateState(@RequestParam String id,@RequestParam int state) {
		JsonBackData back = new JsonBackData();
		try {	
			service.updateState(id,state);
			back.setSuccess(true);
			back.setBackMsg("更新投递信息状态成功！");			
		}catch (Exception e) {
			logger.error("更新投递信息状态方法：", e);
			back.setSuccess(false);
			back.setBackMsg("更新投递信息状态失败,"+e.getMessage());
		}
		return back;
	}
	
}
