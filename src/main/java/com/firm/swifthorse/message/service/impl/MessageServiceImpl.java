package com.firm.swifthorse.message.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.firm.swifthorse.base.service.impl.BaseServiceImpl;
import com.firm.swifthorse.base.utils.PushMessageHelper;
import com.firm.swifthorse.message.entity.MessageEntity;
import com.firm.swifthorse.message.service.IMessageService;
import com.firm.swifthorse.message.vo.MessageVO;

@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageEntity, MessageVO> implements IMessageService{
	
	@Value("${jpush.appKey}")
	protected  String appKey;
	@Value("${jpush.mastersecret}")
	protected  String masterSecret;
	@Value("${jpush.group.pushkey}")
	protected  String groupPushKey;
	@Value("${jpush.group.mastersecret}")
	protected  String groupMasterSecret;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	@Override
	public Page<MessageVO> queryList(Pageable pageable,Map<String, Object> map) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("Select * from swift_message_info where 1=1");
		if(map.containsKey("isRead")){
			sql.append(" and is_read='"+map.get("isRead")+"'");
		}else {
			sql.append(" and is_read =0");
		}
		if(map.containsKey("receiverId")){
			sql.append(" and receiver_id='"+map.get("receiverId")+"'");
		}
		if(map.containsKey("senderId")){
			sql.append(" and sender_id='"+map.get("senderId")+"'");
		}
		int total =  getTotalCount(sql.toString());
		if(pageable != null){
			sql.append(" limit " + pageable.getPageNumber() * pageable.getPageSize() + "," + pageable.getPageSize());
		}
		List<MessageVO> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<MessageVO>(MessageVO.class));
		return new PageImpl(list, pageable, pageable != null ? total : (long) list.size());
	}
	private int getTotalCount(String sql) {
	      String totalSql = "select count(1) from (" + sql + ") t";
	      Integer total = (Integer)this.jdbcTemplate.queryForObject(totalSql, Integer.class);
	      return total.intValue();
	}
	@Override
	@Transactional
	public void pushMessages(List<MessageVO> messages) throws Exception{
		if(messages==null || messages.size()<=0){
			throw new Exception("未传入数据!");
		}
		for(MessageVO vo:messages){
			vo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		String insertSql = "INSERT INTO swift_message_info "
				+ " (id,receiver_id,sender_id,is_read,msg_title,msg_body,msg_dealurl,msg_type,msg_params,send_all,create_time,update_time)"
				+ " VALUES" + " (?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
			@Override
			public int getBatchSize() {
				return messages.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				MessageVO vo = messages.get(i);
				ps.setString(1,vo.getId() );	
				ps.setString(2, vo.getReceiverId());
				ps.setString(3, vo.getSenderId());
				ps.setInt(4, 0);
				ps.setString(5, vo.getMsgTitle());
				ps.setString(6, vo.getMsgBody());
				ps.setString(7, vo.getMsgDealurl());
				ps.setInt(8, vo.getMsgType());
				ps.setString(9, vo.getMsgParams());
				ps.setInt(10, vo.getSendAll());
				ps.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
				ps.setTimestamp(12, new Timestamp(System.currentTimeMillis()));

			}

		});
		for(MessageVO vo:messages){
			Map<String,String> extras = new HashMap<>();
			if(vo.getMsgParams() !=null && !vo.getMsgParams().equals("")){
				extras.put("msgParams", vo.getMsgParams());
			}
			extras.put("msgType", Integer.toString(vo.getMsgType()));
			extras.put("msgId",vo.getId());
			if(vo.getSendAll() ==1){//通知消息,推送所有用户
				PushMessageHelper.sendAllPush(appKey,masterSecret,vo.getMsgTitle(), vo.getMsgBody(), extras);
			}else{//指定的用户
				String[] receiverIds=vo.getReceiverId()!=null && !vo.getReceiverId().equals("")? vo.getReceiverId().split(","):null;
				if(receiverIds!=null  &&  receiverIds.length>0){
					PushMessageHelper.sendPush(appKey,masterSecret,vo.getMsgTitle(), vo.getMsgBody(), extras, receiverIds);
				}else{
					throw new Exception("未指定接收人!");
				}
			}
			
		}
		
	}

}
