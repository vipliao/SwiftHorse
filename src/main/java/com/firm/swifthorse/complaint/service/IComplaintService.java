package com.firm.swifthorse.complaint.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.firm.swifthorse.base.service.IBaseService;
import com.firm.swifthorse.complaint.entity.ComplaintEntity;
import com.firm.swifthorse.complaint.vo.ComplaintVO;

public interface IComplaintService extends IBaseService<ComplaintEntity,ComplaintVO>{

	ComplaintVO queryOneComplaint(String id) throws Exception;

	Page<ComplaintVO> queryComplaintList(Pageable pageable,Map<String, Object> map) throws Exception;

	Page<ComplaintVO> queryMyComplaintList(Pageable pageable,Map<String, Object> map) throws Exception;

}
