package com.firm.swifthorse.business.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

import com.alibaba.fastjson.JSON;
import com.firm.swifthorse.assessory.service.IAssessoryService;
import com.firm.swifthorse.assessory.vo.AssessoryVO;
import com.firm.swifthorse.base.service.impl.BaseServiceImpl;
import com.firm.swifthorse.business.entity.BusinessEntity;
import com.firm.swifthorse.business.service.IBusinessService;
import com.firm.swifthorse.business.vo.BusinessMessagePamasVO;
import com.firm.swifthorse.business.vo.BusinessVO;
import com.firm.swifthorse.message.service.IMessageService;
import com.firm.swifthorse.message.vo.MessageVO;

@Service
public class BusinessServiceImpl extends BaseServiceImpl<BusinessEntity, BusinessVO> implements IBusinessService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IAssessoryService assessoryService;
	@Autowired
	private IMessageService messageService;
	
	@Override
	public Page<BusinessVO> queryBusinessList(Pageable pageable,Map<String, Object> map) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from swift_business_info where 1=1 ");
		if(map != null){
			if(map.containsKey("companyOwn")){//所属行业
				sb.append(" and company_own ='"+map.get("companyOwn")+"'");
			}
			if(map.containsKey("postType")){//从业类型
				sb.append(" and post_type ='"+map.get("postType")+"'");
			}
			if(map.containsKey("workType")){//工种
				sb.append(" and work_type ='"+map.get("workType")+"'");
			}
			if(map.containsKey("education")){//受教育程度
				sb.append(" and education ='"+map.get("education")+"'");
			}
			if(map.containsKey("position")){//职位
				sb.append(" and position ='"+map.get("position")+"'");
			}
			if(map.containsKey("personOriginPlace")){//籍贯
				sb.append(" and person_origin_place ='"+map.get("personOriginPlace")+"'");
			}
			if(map.containsKey("personNation")){//民族
				sb.append(" and person_nation ='"+map.get("personNation")+"'");
			}
			if(map.containsKey("personAgeGroup")){//年龄
				sb.append(" and person_age_group ='"+map.get("personAgeGroup")+"'");
			}
			if(map.containsKey("tag")){//标签
				sb.append(" and tag like '%"+map.get("tag")+"%'");
			}
			if(map.containsKey("workTimeType")){//工作时长类型：全职等
				sb.append(" and work_time_type ='"+map.get("workTimeType")+"'");
			}
			if(map.containsKey("publishType")){//发布类型等
				sb.append(" and publish_type  ='"+map.get("publishType")+"'");
			}
			if(map.containsKey("workPlace")){//工作地点
				sb.append(" and work_place like '"+map.get("workPlace")+"%'");
			}
			if(map.containsKey("workArea")){//工作区域
				sb.append(" and work_area  ='"+map.get("workArea")+"'");
			}
			if(map.containsKey("isGood")){//是否优质记录
				sb.append(" and is_good  ='"+map.get("isGood")+"'");
			}
		}
		int total =  getTotalCount(sb.toString());
		if(pageable != null){
			sb.append(" limit " + pageable.getPageNumber() * pageable.getPageSize() + "," + pageable.getPageSize());
		}
		List<BusinessVO> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<>( BusinessVO.class));
		queryAssessorys(list);
		return new PageImpl(list, pageable, pageable != null ? total : (long) list.size());
		
	}
		
	private int getTotalCount(String sql) {
	      String totalSql = "select count(1) from (" + sql + ") t";
	      Integer total = (Integer)this.jdbcTemplate.queryForObject(totalSql, Integer.class);
	      return total.intValue();
	}

	@Override
	public Page<BusinessVO> queryMyBusinessList(Pageable pageable, Map<String, Object> map)throws Exception {
		if(!map.containsKey("userId") || map.get("userId")==null ||map.get("userId").equals("")){
			throw new Exception("userId不能为空");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select * from swift_business_info where 1=1 and user_id='"+map.get("userId")+"'");
		if(map.containsKey("publishType")){
			sb.append(" and publish_type ="+map.get("publishType"));
		}
		if(map.containsKey("isEnd")){
			String isEndStr= (String) map.get("isEnd");
			int isEnd = Integer.parseInt(isEndStr);
			if(isEnd==1){
				String now = DateFormat.getDateInstance().format(new Date())+" 00:00:00";
				sb.append(" and end_date <='"+now+"'");
			}
			if(isEnd==0){
				String now = DateFormat.getDateInstance().format(new Date())+" 23:59:59";
				sb.append(" and end_date >'"+now+"'");
			}
		}	
		int total =  getTotalCount(sb.toString());
		if(pageable != null){
			sb.append(" limit " + pageable.getPageNumber() * pageable.getPageSize() + "," + pageable.getPageSize());
		}
		List<BusinessVO> list = jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper<>( BusinessVO.class));
		queryAssessorys(list);
		return new PageImpl(list, pageable, pageable != null ? total : (long) list.size());

	}
	
	@Transactional
	public BusinessVO save(BusinessVO vo, Class<BusinessEntity> clazzE, Class<BusinessVO> clazzV) throws Exception {
		BusinessVO business = super.save(vo, clazzE, clazzV);
		if(business != null && business.getId()!=null && !business.getId().equals("")){
			//发送消息:工作地点+工种+受教育学历+是否有纹身
			List<MessageVO> messages =new ArrayList<>();
			StringBuilder sql= new StringBuilder();
			String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			sql.append( "select user_id userId,company_name companyName,id id, title title,publish_type publishType,is_user_outh isUserOuth,tag tag from swift_business_info where 1=1"
						+" and work_type="+vo.getWorkType()
						+" and work_place='"+vo.getWorkPlace()+"'"
	//					+" and education <= "+vo.getEducation()
						+" and DATE_FORMAT(end_date,'%Y-%m-%d') >= '"+now+"'"
					);
			if(vo.getPersonTatoo()==1){
				sql.append("and person_tatoo <> 0");
			}
			if(vo.getPublishType()==0){//发布招人：查找投递表+用户表+供人记录，发送推荐供人和推荐用户的消息
				sql.append("  and publish_type=1");
				sql.append(" union all ");
				sql.append("select id userId,user_name companyName,'' title,id id,2 publishType,is_auth isUserOuth,'' tag from swift_user_info where 1=1"
							+" and work_type="+vo.getWorkType()
//							+" and education <= "+vo.getEducation()
							+" and dr=0"
							+" and id in (select user_id from swift_deliver where 1=1 and state <> 21 and TIMESTAMPDIFF(DAY,update_time,'"+now+"') <= 15)"
					);
			
			}else{//发布供人:查找招人记录，发送推荐招人消息
				sql.append(" and publish_type=0");
			}
			List<BusinessVO> businesss=jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<>( BusinessVO.class));
			if(businesss!=null && businesss.size()>0){
				if(vo.getPublishType()==0){
					MessageVO message = new MessageVO();
					message.setReceiverId(vo.getUserId());
					message.setMsgBody("伯乐遇良驹，人才在此，快戳这里>>");
					message.setMsgTitle("千里马人才推荐");
					message.setMsgType(2);
					message.setSenderId("system");				
					List<BusinessVO> busTypes = new ArrayList<>();//招人/供人表数据
					Map<String,BusinessVO> userTypes = new HashMap<>();//用户表数据
					for(int i=0;i< businesss.size();i++){
						if(businesss.get(i).getPublishType()==1){
							busTypes.add(businesss.get(i));
						}else if(businesss.get(i).getPublishType()==2 && !userTypes.containsKey(businesss.get(i).getUserId())){
							userTypes.put(businesss.get(i).getUserId(),businesss.get(i));
						}
					}
				
					List<BusinessMessagePamasVO> busMessageParams = new ArrayList<>();
					if(busTypes != null && busTypes.size()>0){
						for(BusinessVO busType:busTypes){
							BusinessMessagePamasVO busMessageParam = new BusinessMessagePamasVO();
							if(busType.getTitle() !=null && !busType.getTitle().equals("")){
								if(busType.getCompanyName()!=null && !busType.getCompanyName().equals("")){
									busMessageParam.setTitle("["+busType.getCompanyName()+"]"+busType.getTitle());
								}else{
									busMessageParam.setTitle(busType.getTitle());
								}
							}
							if(busType.getTag()!=null && !busType.getTag().equals("")){
								busMessageParam.setContent(busType.getTag());
							}else{
								busMessageParam.setContent("查看供人详情...");
							}
							busMessageParam.setType(21);
							busMessageParam.setId(busType.getId());
							busMessageParams.add(busMessageParam);							
						}
						
					}
					if(userTypes != null && userTypes.size()>0){						
						for (Map.Entry<String, BusinessVO> entry : userTypes.entrySet()) {  
							BusinessMessagePamasVO busMessageParam = new BusinessMessagePamasVO();
							if(entry.getValue().getCompanyName()!=null && !entry.getValue().getCompanyName().equals("")){
								busMessageParam.setTitle(entry.getValue().getCompanyName()+"正在求职");
							}else{
								busMessageParam.setTitle("千里马精心推荐");
							}
							busMessageParam.setContent("查看人才详情...");
							busMessageParam.setId(entry.getKey());
							busMessageParam.setType(22);
							busMessageParams.add(busMessageParam);
						}
												
					}
					
					message.setMsgParams(JSON.toJSON(busMessageParams).toString());
					messages.add(message);
				}else if(vo.getPublishType()==1){
					MessageVO message = new MessageVO();
					message.setMsgBody("好职位不容错过哟，赶快查看吧→");
					message.setMsgTitle("千里马职位推荐");
					message.setMsgType(1);
					message.setReceiverId(vo.getUserId());
					message.setSenderId("system");
					List<BusinessMessagePamasVO> busMessageParams = new ArrayList<>();
					List<BusinessVO> newBusinesss = new ArrayList<>();
					if(businesss.size()>10){
						newBusinesss = businesss.subList(0, 10);
					}else{
						newBusinesss = businesss;
					}
					for(BusinessVO bus:newBusinesss){
						BusinessMessagePamasVO busMessageParam = new BusinessMessagePamasVO();
						busMessageParam.setId(bus.getId());
						if(bus.getTitle() !=null && !bus.getTitle().equals("")){
							if(bus.getCompanyName()!=null && !bus.getCompanyName().equals("")){
								busMessageParam.setTitle("["+bus.getCompanyName()+"]"+bus.getTitle());
							}else{
								busMessageParam.setTitle(bus.getTitle());
							}
						}
						if(bus.getTag()!=null && !bus.getTag().equals("")){
							busMessageParam.setContent(bus.getTag());
						}else{
							busMessageParam.setContent("查看招人详情...");
						}
						busMessageParam.setType(20);
						busMessageParams.add(busMessageParam);
					}
					message.setMsgParams(JSON.toJSON(busMessageParams).toString());
					messages.add(message);
				}
				
			}
			messageService.pushMessages(messages);
			//查询附件
			if (vo.getAssessorys() != null && vo.getAssessorys().size() > 0) {
				List<String> delAIds= new ArrayList<>();
				List<String> addAIds = new ArrayList<>();
				for(AssessoryVO avo:vo.getAssessorys()){
					if(avo.getDr()==1){
						delAIds.add(avo.getId());
					}else if(avo.getBusinessId() !=null && !avo.getBusinessId().equals("")){
						addAIds.add(avo.getId());
					}
				}
				if(delAIds !=null && delAIds.size()>0){
					String idList = delAIds.toString().replaceAll(" ", "").replaceAll("\\,", "\\'\\,\\'")
							.replaceAll("\\[", "\\('").replaceAll("\\]", "\\')");
					String deleteSql ="delete from invest_assessory where id in "+idList;
					jdbcTemplate.update(deleteSql);
				}
				if(addAIds !=null && addAIds.size()>0){
					String idList = addAIds.toString().replaceAll(" ", "").replaceAll("\\,", "\\'\\,\\'")
							.replaceAll("\\[", "\\('").replaceAll("\\]", "\\')");
					String updateSql ="update  invest_assessory set business_id='"+business.getId()+"' where id in "+idList;
					jdbcTemplate.update(updateSql);
				}
			}
			List<BusinessVO> list = new ArrayList<>();
			list.add(business);
			return queryAssessorys(list).get(0);
		}
		
		return null;
	}
	
	public  BusinessVO queryOneBusiness(String id) throws Exception{
		if(id ==null || id.equals("")){
			throw new Exception("id不能为空！");
		}
		String sql = "select * from swift_business_info where 1=1 and id='"+id+"'";
		List<BusinessVO> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>( BusinessVO.class));
		if(list == null || list.size()<=0){
			throw new Exception("没有查询到数据！");
		}
		if(list != null && list.size()>1){
			throw new Exception("数据库中的数据不唯一！");
		}
		return queryAssessorys(list).get(0);
	}
	
	private List<BusinessVO> queryAssessorys(List<BusinessVO> business) throws Exception{
		if(business == null || business.size()<=0){
			return null;
		}
		List<String> businessIds = new ArrayList<>();
		for(BusinessVO vo:business){
			businessIds.add(vo.getId());
		}
		HashSet<String> h = new HashSet<>();
		h.addAll(businessIds);
		businessIds.clear();
		businessIds.addAll(h);
		List<AssessoryVO> assessorys = assessoryService.queryAssessorys(businessIds);
		if(assessorys !=null && assessorys.size()>0){
			for(AssessoryVO assessory:assessorys){
				List<AssessoryVO> a = new ArrayList<>();
				for(BusinessVO b:business){
					if(b.getId().equals(assessory.getBusinessId())){
						a.add(assessory);
					}
					b.setAssessorys(a);
					
				}
			}
			
		}
		return business;
	}

	@Transactional
	@Override
	public void saveGoodTags(String id,String goodTags) throws Exception {
		String sql="update swift_business_info set is_good=1,good_tags='"+goodTags+"' where 1=1 and id='"+id+"'";
		jdbcTemplate.update(sql);
	}


}
