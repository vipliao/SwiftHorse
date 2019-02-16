package com.firm.swifthorse.deliver.service.impl;

import java.util.ArrayList;
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

import com.firm.swifthorse.base.service.impl.BaseServiceImpl;
import com.firm.swifthorse.business.vo.BusinessVO;
import com.firm.swifthorse.deliver.entity.DeliverEntity;
import com.firm.swifthorse.deliver.service.IDeliverService;
import com.firm.swifthorse.deliver.vo.DeliverVO;
import com.firm.swifthorse.message.service.IMessageService;
import com.firm.swifthorse.message.vo.MessageVO;
import com.firm.swifthorse.user.vo.UserVO;

@Service
public class DeliverServiceImpl extends BaseServiceImpl<DeliverEntity, DeliverVO> implements IDeliverService{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IMessageService messageService;

	@Override
	public Page<DeliverVO> queryList(Pageable pageable,Map<String, Object> map) throws Exception {
		if(map==null){
			throw new Exception("传入参数为空");
		}
		StringBuilder sql =new StringBuilder( "select * from swift_deliver where 1=1");
		if(map.containsKey("isRead")){
			sql.append(" and is_read='"+map.get("isRead")+"'");
		}else{
			sql.append(" and is_read='1'");
		}
		if(map.containsKey("dr")){
			sql.append(" and dr='0'");
		}
		if(map.containsKey("businessId")){
			sql.append(" and business_id='"+map.get("businessId")+"'");
		}
		if(map.containsKey("userId")){
			sql.append(" and user_id='"+map.get("userId")+"'");
		}
		int total =  getTotalCount(sql.toString());
		if(pageable != null){
			sql.append(" limit " + pageable.getPageNumber() * pageable.getPageSize() + "," + pageable.getPageSize());
		}
		List<DeliverVO> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(DeliverVO.class));	
		queryUserAndBusinessInfo(list);
		return new PageImpl(list, pageable, pageable != null ? total : (long) list.size());
	}
	private int getTotalCount(String sql) {
	      String totalSql = "select count(1) from (" + sql + ") t";
	      Integer total = (Integer)this.jdbcTemplate.queryForObject(totalSql, Integer.class);
	      return total.intValue();
	}

	@Override
	@Transactional
	public void doCancle(String id) throws Exception {
		if(id == null || id.equals("")){
			throw new Exception("传入id不能为空");
		}
		String sql ="update swift_deliver set dr=1 where 1=1 and  id='"+id+"'";
		jdbcTemplate.update(sql);
		
	}
	
	
	List<DeliverVO> queryUserAndBusinessInfo(List<DeliverVO> list) throws Exception{
		if(list ==null || list.size()<=0){
			return null;
		}
		List<String> userIds = new ArrayList<>();
		List<String> businessIds = new ArrayList<>();
		for(DeliverVO vo :list){
			userIds.add(vo.getUserId());
			businessIds.add(vo.getBusinessId());
		}
		
		List<UserVO> users =new ArrayList<>();
		List<BusinessVO> businesss = new ArrayList<>();
		if(userIds !=null && userIds.size()>0){
			 HashSet<String> h  =   new HashSet<String>(userIds);
			 userIds.clear();
			 userIds.addAll(h);
			 String userIdStr = userIds.toString().replaceAll(" ", "").replaceAll("\\,", "\\'\\,\\'").replaceAll("\\[", "\\('").replaceAll("\\]", "\\')");
			 String sql = "select * from swift_user_info where 1=1 and id in "+userIdStr;
			 users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserVO>(UserVO.class));
			 
		}
		if(businessIds !=null && businessIds.size()>0){
			 HashSet<String> h  =   new HashSet<String>(businessIds);
			 businessIds.clear();
			 businessIds.addAll(h);
			 String businessIdStr = businessIds.toString().replaceAll(" ", "").replaceAll("\\,", "\\'\\,\\'").replaceAll("\\[", "\\('").replaceAll("\\]", "\\')");
			 String sql = "select * from swift_business_info where 1=1 and id in "+businessIdStr;
			 businesss = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BusinessVO.class)); 
		}
		for(DeliverVO vo :list){
			if(users !=null && users.size()>0){
				for(UserVO user:users){
					if(vo.getUserId().equals(user.getId())){
						if(vo.getUser() == null ){
							vo.setUser(user);
						}
					}
				}
				if(businesss !=null && businesss.size()>0){
					for(BusinessVO business:businesss){
						if(vo.getBusinessId().equals(business.getId())){
							if(vo.getBusiness()==null){
								vo.setBusiness(business);
							}
						}
					}
				}
				
			}
		}
		
		return list;
		
	}
	@Override
	public DeliverVO save(DeliverVO vo, Class<DeliverEntity> clazzE, Class<DeliverVO> clazzV) throws Exception {
		if(vo.getUserId()==null || vo.getUserId().equals("")){
			throw new Exception("投递人不能为空!");
		}
		if(vo.getBusinessId()==null ||vo.getBusinessId().equals("")){
			throw new Exception("投递人不能为空!");
		}
		DeliverVO reVO = super.save(vo, clazzE, clazzV);
		String msgParams = "user_id=" + vo.getUserId() + ",business_id=" + vo.getBusinessId() + ",deliver_id="+ reVO.getId();
		pushMessages(vo.getBusinessId(), msgParams, 0);			
		List<DeliverVO> reVOs = new ArrayList<>();
		reVOs.add(reVO);
		List<DeliverVO>  list = queryUserAndBusinessInfo(reVOs);
		return list.get(0);
	}

	@Override
	@Transactional
	public void updateState(String id, int state) throws Exception {
		String querySql ="select user_id userId,id id,state state from swift_deliver where 1=1 and id='"+id+"'";
		List<DeliverVO> list = jdbcTemplate.query(querySql, new BeanPropertyRowMapper<>(DeliverVO.class));
		if(list !=null && list.size()>0){
			if(list.get(0).getState() != state){
				pushMessages(list.get(0).getUserId(),"deliver_id="+list.get(0).getId(),state);
			}
		}
		String sql="update swift_deliver set state ="+state+" where 1=1 and id='"+id+"'";
		jdbcTemplate.update(sql);
	}
	
	void pushMessages(String msgReceiverId,String msgParams,int deliverState) throws Exception{
		MessageVO messageVO = new MessageVO();
		messageVO.setMsgTitle("千里马投递直通车");
		messageVO.setSenderId("system");
		if(deliverState==0){//投递，给发布招人的推送消息
			messageVO.setMsgBody("有人投递了您发布的招人信息,赶紧看看吧->");
			String sql = "select * from swift_business_info where 1=1 and id='"+msgReceiverId+"'";
			List<BusinessVO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BusinessVO.class));
			if(list !=null && list.size()>0){
				messageVO.setReceiverId(list.get(0).getUserId());
			}
			
		}else{//投递进度更新，推送查看进度消息
			messageVO.setMsgBody("您投递的招人信息进度更新啦,别错过噢>>");
			messageVO.setReceiverId(msgReceiverId);
		}
		messageVO.setMsgType(4);
		messageVO.setMsgParams(msgParams);
		if(messageVO.getReceiverId() !=null && !messageVO.getReceiverId().equals("")){
			List<MessageVO> messages = new ArrayList<>();
			messages.add(messageVO);
			messageService.pushMessages(messages);
		}
	}

}
