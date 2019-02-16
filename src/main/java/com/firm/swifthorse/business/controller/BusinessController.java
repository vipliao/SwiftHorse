package com.firm.swifthorse.business.controller;

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
import com.firm.swifthorse.business.entity.BusinessEntity;
import com.firm.swifthorse.business.service.IBusinessService;
import com.firm.swifthorse.business.vo.BusinessVO;

@Controller
@RequestMapping(value = "business")
public class BusinessController {

	private static Logger logger = LoggerFactory.getLogger(BusinessController.class);

	@Autowired
	private IBusinessService service;

	@RequestMapping(value = "save")
	@ResponseBody
	public JsonBackData save(@RequestBody BusinessVO vo) {
		JsonBackData back = new JsonBackData();
		try {

			BusinessVO reVO = service.save(vo, BusinessEntity.class, BusinessVO.class);
			String backData = null;
			if(reVO != null){
				backData = JSON.toJSON(reVO).toString();
			}
			back.setBackData(backData);
			back.setSuccess(true);
			back.setBackMsg("保存成功！");

		} catch (Exception e) {
			logger.error("招人/供人保存方法：", e);
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
			logger.error("招人/供人删除方法：", e);
			back.setSuccess(false);
			back.setBackMsg("删除失败," + e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "queryOneBusiness")
	@ResponseBody
	public JsonBackData queryOneBusiness(@RequestParam String id) {
		JsonBackData back = new JsonBackData();
		try {

			BusinessVO reVO = service.queryOneBusiness(id);
			String backData = null;
			if(reVO != null){
				backData = JSON.toJSON(reVO).toString();
			}
			back.setBackData(backData);
			back.setSuccess(true);
			back.setBackMsg("查询招人供/供人详细信息成功！");

		} catch (Exception e) {
			logger.error("查询招人供/供人详细信息方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询招人供/供人详细信息," + e.getMessage());
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
			Page<BusinessVO> reVOs = service.queryBusinessList(pageable,map);
			String backData = null;
			if(reVOs != null){
				backData = JSON.toJSON(reVOs).toString();
			}
			back.setBackData(backData);
			back.setSuccess(true);
			back.setBackMsg("查询招人/供人列表成功！");

		} catch (Exception e) {
			logger.error("查询招人/供人列表方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询招人/供人列表失败," + e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "saveGoodTags")
	@ResponseBody
	public JsonBackData saveGoodTags(@RequestParam String id,@RequestParam String goodTags) {
		JsonBackData back = new JsonBackData();
		try {
			service.saveGoodTags(id,goodTags);
			back.setSuccess(true);
			back.setBackMsg("保存优秀标签成功！");

		} catch (Exception e) {
			logger.error("保存优秀标签方法：", e);
			back.setSuccess(false);
			back.setBackMsg("保存优秀标签失败," + e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "queryMyList")
	@ResponseBody
	public JsonBackData queryMyList(@RequestParam Map<String,Object> map) {
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
			Page<BusinessVO> reVOs = service.queryMyBusinessList(pageable,map);
			String backData = null;
			if(reVOs != null){
				backData = JSON.toJSON(reVOs).toString();
			}
			back.setBackData(backData);
			back.setSuccess(true);
			back.setBackMsg("查询我的招人/供人列表成功！");

		} catch (Exception e) {
			logger.error("查询我的招人/供人列表方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询我的招人/供人列表失败," + e.getMessage());
		}
		return back;
	}

}
