package com.firm.swifthorse.subscribe.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.firm.swifthorse.base.service.IBaseService;
import com.firm.swifthorse.subscribe.entity.SubcribeEntity;
import com.firm.swifthorse.subscribe.vo.SubcribeVO;

public interface ISubcribeService extends IBaseService<SubcribeEntity,SubcribeVO>{

	Page<SubcribeVO> queryList(Pageable pageable,Map<String, Object> map) throws Exception;

}
