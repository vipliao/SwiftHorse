package com.firm.swifthorse.subscribe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.firm.swifthorse.base.service.impl.BaseServiceImpl;
import com.firm.swifthorse.subscribe.entity.SubcribeEntity;
import com.firm.swifthorse.subscribe.service.ISubcribeService;
import com.firm.swifthorse.subscribe.vo.SubcribeVO;

@Service
public class SubcribeServiceImpl extends BaseServiceImpl<SubcribeEntity, SubcribeVO> implements ISubcribeService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Page<SubcribeVO> queryList(Pageable pageable,Map<String, Object> map) throws Exception {
		if(map == null || map.containsKey("userId") || map.get("userId")==null || map.get("userId").equals("")){
			throw new Exception("userId不能为空");
		}
		String userId=(String) map.get("userId");
		StringBuilder sql = new StringBuilder();
		sql.append("Select * from swift_subscribe where 1=1 and userId='"+userId+"'");
		if(map.containsKey("type")){
			int type =(int) map.get("type");
			if(type == 0){
				sql.append(" and type=0");
			}else if(type==1){
				sql.append(" and type =1");
			}
		}
		if(map.containsKey("businessType")){
			int businessType = (int) map.get("businessType");
			if(businessType == 0){
				sql.append(" and business_type=0");
			}else if(businessType == 1){
				sql.append(" and business_type=1");
			}
		}
		
		int total =  getTotalCount(sql.toString());
		if(pageable != null){
			sql.append(" limit " + pageable.getPageNumber() * pageable.getPageSize() + "," + pageable.getPageSize());
		}
		List<SubcribeVO> list = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<>(SubcribeVO.class));
		return new PageImpl(list, pageable, pageable != null ? total : (long) list.size());
	}
	
	private int getTotalCount(String sql) {
	      String totalSql = "select count(1) from (" + sql + ") t";
	      Integer total = (Integer)this.jdbcTemplate.queryForObject(totalSql, Integer.class);
	      return total.intValue();
	}

}
