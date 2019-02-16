package com.firm.swifthorse.complaint.controller;

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
import com.firm.swifthorse.complaint.entity.ComplaintEntity;
import com.firm.swifthorse.complaint.service.IComplaintService;
import com.firm.swifthorse.complaint.vo.ComplaintVO;

@Controller
@RequestMapping(value = "complaint")
public class Complaintcontroller {
	private static Logger logger = LoggerFactory.getLogger(Complaintcontroller.class);
	
	@Autowired
	private IComplaintService service;

	@RequestMapping(value = "save")
	@ResponseBody
	public JsonBackData save(@RequestBody ComplaintVO vo) {
		JsonBackData back = new JsonBackData();
		try {

			ComplaintVO reVO = service.save(vo, ComplaintEntity.class, ComplaintVO.class);
			String backData = null;
			if(reVO != null){
				backData = JSON.toJSON(reVO).toString();
			}
			back.setBackData(backData);
			back.setSuccess(true);
			back.setBackMsg("保存成功！");

		} catch (Exception e) {
			logger.error("投诉保存方法：", e);
			back.setSuccess(false);
			back.setBackMsg("保存失败," + e.getMessage());
		}
		return back;
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonBackData delete(@RequestParam String id) {
		JsonBackData back = new JsonBackData();
		try {

			service.delete(id);
			back.setSuccess(true);
			back.setBackMsg("删除成功！");

		} catch (Exception e) {
			logger.error("投诉删除方法：", e);
			back.setSuccess(false);
			back.setBackMsg("删除失败," + e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "queryOne")
	@ResponseBody
	public JsonBackData queryOneComplaint(@RequestParam String id) {
		JsonBackData back = new JsonBackData();
		try {

			ComplaintVO reVO = service.queryOneComplaint(id);
			String backData = null;
			if(reVO != null){
				backData = JSON.toJSON(reVO).toString();
			}
			back.setBackData(backData);
			back.setSuccess(true);
			back.setBackMsg("根据id查询一条投诉信息成功！");

		} catch (Exception e) {
			logger.error("根据id查询一条投诉信息方法：", e);
			back.setSuccess(false);
			back.setBackMsg("根据id查询一条投诉信息," + e.getMessage());
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
			Page<ComplaintVO> reVOs = service.queryComplaintList(pageable,map);
			String backData = null;
			if(reVOs != null){
				backData = JSON.toJSON(reVOs).toString();
			}
			back.setBackData(backData);
			back.setSuccess(true);
			back.setBackMsg("查询投诉列表成功！");

		} catch (Exception e) {
			logger.error("查询投诉列表方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询投诉列表失败," + e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "queryMyList")
	@ResponseBody
	public JsonBackData queryMyList(@RequestParam Map<String, Object> map) {
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
			Page<ComplaintVO> reVOs = service.queryMyComplaintList(pageable,map);
			String backData = null;
			if(reVOs != null){
				backData = JSON.toJSON(reVOs).toString();
			}
			back.setBackData(backData);
			back.setSuccess(true);
			back.setBackMsg("查询我的投诉列表成功！");

		} catch (Exception e) {
			logger.error("查询我的投诉列表方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询我的投诉列表失败," + e.getMessage());
		}
		return back;
	}

}
