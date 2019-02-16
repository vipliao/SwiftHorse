package com.firm.swifthorse.complaint.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.firm.swifthorse.assessory.service.IAssessoryService;
import com.firm.swifthorse.assessory.vo.AssessoryVO;
import com.firm.swifthorse.base.service.impl.BaseServiceImpl;
import com.firm.swifthorse.complaint.entity.ComplaintEntity;
import com.firm.swifthorse.complaint.service.IComplaintService;
import com.firm.swifthorse.complaint.vo.ComplaintVO;

@Service
public class ComplaintServiceImpl extends BaseServiceImpl<ComplaintEntity, ComplaintVO> implements IComplaintService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IAssessoryService assessoryService;
	
	@Override
	public ComplaintVO queryOneComplaint(String id) throws Exception {
		if(id ==null || id.equals("")){
			throw new Exception("id不能为空！");
		}
		String sql = "select * from swift_complaint_info where 1=1 and id='"+id+"'";
		List<ComplaintVO> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>( ComplaintVO.class));
		if(list == null || list.size()<=0){
			throw new Exception("没有查询到数据！");
		}
		if(list != null && list.size()>1){
			throw new Exception("数据库中的数据不唯一！");
		}
		return queryAssessorys(list).get(0);
		
	}

	@Override
	public Page<ComplaintVO> queryComplaintList(Pageable pageable,Map<String, Object> map) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from swift_complaint_info where 1=1 ");
		if(map != null){
			if(map.containsKey("title")){//标题
				sb.append(" and title like '%"+map.get("title")+"%'");
			}
			if(map.containsKey("content")){//内容
				sb.append(" and content ='%"+map.get("content")+"%'");
			}
			if(map.containsKey("defendantId")){//被投诉对象
				sb.append(" and defendant_id ='"+map.get("defendantId")+"'");
			}
			if(map.containsKey("plaintiffId")){//投诉对象
				sb.append(" and plaintiff_id ='"+map.get("plaintiffId")+"'");
			}
			if(map.containsKey("dr")){//删除标志
				sb.append(" and dr ='"+map.get("dr")+"'");
			}else if(!map.containsKey("dr")){
				sb.append(" and dr =0");
			}
			
		}
		int total =  getTotalCount(sb.toString());
		if(pageable != null){
			sb.append(" limit " + pageable.getPageNumber() * pageable.getPageSize() + "," + pageable.getPageSize());
		}
		List<ComplaintVO> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<>( ComplaintVO.class));
		queryAssessorys(list);
		return new PageImpl(list, pageable, pageable != null ? total : (long) list.size());

	}
	
	private int getTotalCount(String sql) {
	      String totalSql = "select count(1) from (" + sql + ") t";
	      Integer total = (Integer)this.jdbcTemplate.queryForObject(totalSql, Integer.class);
	      return total.intValue();
	}
	
	@Override
	public Page<ComplaintVO> queryMyComplaintList(Pageable pageable,Map<String, Object> map) throws Exception {
		
		if(!map.containsKey("userId") || map.get("userId") == null || map.get("userId").equals("")){
			throw new Exception("userId不能为空");		
		}
		String userId=(String) map.get("userId");
		StringBuilder sb = new StringBuilder();
		sb.append("select * from swift_complaint_info where 1=1 ");
		if(map.containsKey("type")){
			int type=(int) map.get("type");
			if(type == 0){
				sb.append(" and plaintiff_id='"+userId+"'");
			}else if(type==1){
				sb.append(" and defendant_id='"+userId+"'");
			}else if(type==-1){
				sb.append(" and (defendant_id='"+userId+"' or defendant_id='"+userId+"')");
			}
		}else{
			sb.append(" and (defendant_id='"+userId+"' or defendant_id='"+userId+"')");
		}
		if(map.containsKey("dr")){
			int dr = (int) map.get("dr");
			if(dr==1){
				sb.append(" and dr =1");
			}else{
				sb.append("and dr=0");
			}
		}else{
			sb.append("and dr=0");
		}

		int total =  getTotalCount(sb.toString());
		if(pageable != null){
			sb.append(" limit " + pageable.getPageNumber() * pageable.getPageSize() + "," + pageable.getPageSize());
		}
		List<ComplaintVO> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<>( ComplaintVO.class));
		queryAssessorys(list);
		return new PageImpl(list, pageable, pageable != null ? total : (long) list.size());
	}
	
	@Transactional
	public ComplaintVO save(ComplaintVO vo, Class<ComplaintEntity> clazzE, Class<ComplaintVO> clazzV) throws Exception {
		ComplaintVO newVO = super.save(vo, clazzE, clazzV);
		if(newVO != null && newVO.getId()!=null && !newVO.getId().equals("")){
			List<ComplaintVO> list = new ArrayList<>();
			list.add(newVO);
			return queryAssessorys(list).get(0);
		}
		return null;
	}

	
	private List<ComplaintVO> queryAssessorys(List<ComplaintVO> vos) throws Exception{
		if(vos == null || vos.size()<=0){
			return null;
		}
		List<String> ids = new ArrayList<>();
		for(ComplaintVO vo:vos){
			ids.add(vo.getId());
		}
		List<AssessoryVO> assessorys = assessoryService.queryAssessorys(ids);
		if(assessorys !=null && assessorys.size()>0){
			for(AssessoryVO assessory:assessorys){
				List<AssessoryVO> a = new ArrayList<>();
				for(ComplaintVO b:vos){
					if(b.getId().equals(assessory.getBusinessId())){
						a.add(assessory);
					}
					b.setAssessorys(a);
					
				}
			}
			
		}
		return vos;
	}

}
