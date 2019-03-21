package com.kim.schedule.mq.service;

import org.springframework.stereotype.Component;

import com.kim.common.base.BaseService;

@Component
public class MqMessageService extends BaseService {
	
//	@Autowired
//	private InputDataParseService mqDataParseService;
//
//	@RabbitListener(queues = RabbitConfig.TAPE_RELATES_BQ_QUEUE)
//	public void onMessageBq(Message message) {
//		mqDataParseService.parse(message, SummaryEntity.SOURCE_BQ);
//	}
//	
//	@RabbitListener(queues = RabbitConfig.TAPE_RELATES_MMT_QUEUE)
//	public void onMessageMmt(Message message) {
//		mqDataParseService.parse(message, SummaryEntity.SOURCE_MMT);
//	}
//	
//	@RabbitListener(queues = RabbitConfig.TAPE_RELATES_ZL_QUEUE)
//	public void onMessageZl(Message message) {
//		mqDataParseService.parse(message, SummaryEntity.SOURCE_ZL);
//	}

}
