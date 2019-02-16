package com.firm.swifthorse.subscribe.controller;

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
import com.firm.swifthorse.subscribe.entity.SubcribeEntity;
import com.firm.swifthorse.subscribe.service.ISubcribeService;
import com.firm.swifthorse.subscribe.vo.SubcribeVO;

@Controller
@RequestMapping(value="subscribe")
public class SubscribeController {
	private static Logger logger = LoggerFactory.getLogger(SubscribeController.class);
	
	@Autowired
	private ISubcribeService service;
	
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonBackData save(@RequestBody SubcribeVO vo) {
		JsonBackData back = new JsonBackData();
		try {

			SubcribeVO reVO = service.save(vo, SubcribeEntity.class, SubcribeVO.class);
			String backData = null;
			if(reVO != null){
				backData = JSON.toJSON(reVO).toString();
			}
			back.setBackData(backData);
			back.setSuccess(true);
			back.setBackMsg("保存成功！");

		} catch (Exception e) {
			logger.error("收藏/订阅保存方法：", e);
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
			logger.error("收藏/订阅删除方法：", e);
			back.setSuccess(false);
			back.setBackMsg("删除失败," + e.getMessage());
		}
		return back;
	}
	
	@RequestMapping(value = "queryList")
	@ResponseBody
	public JsonBackData queryList(@RequestParam Map<String, Object> map,@RequestParam String userId,@RequestParam(required=false,defaultValue="-1")int businessType,@RequestParam(required=false,defaultValue="-1") int type) {
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
			Page<SubcribeVO> reVOs = service.queryList(pageable,map);
			String backData = null;
			if(reVOs != null){
				backData = JSON.toJSON(reVOs).toString();
			}
			back.setBackData(backData);
			back.setSuccess(true);
			back.setBackMsg("查询收藏/订阅列表成功！");

		} catch (Exception e) {
			logger.error("查询收藏/订阅列表方法：", e);
			back.setSuccess(false);
			back.setBackMsg("查询收藏/订阅列表失败," + e.getMessage());
		}
		return back;
	}

}
