package com.firm.swifthorse.deliver.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.firm.swifthorse.base.service.IBaseService;
import com.firm.swifthorse.deliver.entity.DeliverEntity;
import com.firm.swifthorse.deliver.vo.DeliverVO;

public interface IDeliverService extends IBaseService<DeliverEntity, DeliverVO>{

	Page<DeliverVO> queryList(Pageable pageable, Map<String, Object> map) throws Exception;

	void doCancle(String id) throws Exception;

	void updateState(String id, int state) throws Exception;

}
