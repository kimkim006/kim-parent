package com.kim.schedule.mq.service;

//@Configuration
public class RabbitConfig {
	
//	public static final String TAPE_RELATES_BQ_QUEUE = "TAPE_RELATES_BQ_QUEUE";
//	public static final String TAPE_RELATES_MMT_QUEUE = "TAPE_RELATES_MMT_QUEUE";
//	public static final String TAPE_RELATES_ZL_QUEUE = "TAPE_RELATES_ZL_QUEUE";
// 
//    @Bean
//    public Queue bqTapeRelatesQueue() {
//        return new Queue(TAPE_RELATES_BQ_QUEUE);
//    }
//    
//    @Bean
//    public Queue mmtTapeRelatesQueue() {
//    	return new Queue(TAPE_RELATES_MMT_QUEUE);
//    }
//    
//    @Bean
//    public Queue zlTapeRelatesQueue() {
//    	return new Queue(TAPE_RELATES_ZL_QUEUE);
//    }
//    
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(new Jackson2JsonMessageConverter());
//        return template;
//    }
//
//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        return factory;
//    }
 
}
