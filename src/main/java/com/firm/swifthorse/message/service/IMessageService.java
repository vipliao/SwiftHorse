package com.firm.swifthorse.message.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.firm.swifthorse.base.service.IBaseService;
import com.firm.swifthorse.message.entity.MessageEntity;
import com.firm.swifthorse.message.vo.MessageVO;

public interface IMessageService extends IBaseService<MessageEntity,MessageVO>{


	Page<MessageVO> queryList(Pageable pageable, Map<String, Object> map)  throws Exception;

	void pushMessages(List<MessageVO> messages) throws Exception;

}
