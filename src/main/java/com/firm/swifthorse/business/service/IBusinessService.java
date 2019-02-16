package com.firm.swifthorse.business.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.firm.swifthorse.base.service.IBaseService;
import com.firm.swifthorse.business.entity.BusinessEntity;
import com.firm.swifthorse.business.vo.BusinessVO;

public interface IBusinessService extends IBaseService<BusinessEntity,BusinessVO>{

	Page<BusinessVO> queryBusinessList(Pageable pageable, Map<String, Object> map) throws Exception;

	Page<BusinessVO> queryMyBusinessList(Pageable pageable, Map<String, Object> map) throws Exception;
	
	BusinessVO queryOneBusiness(String id) throws Exception;

	void saveGoodTags(String id,String goodTags) throws Exception;


}
