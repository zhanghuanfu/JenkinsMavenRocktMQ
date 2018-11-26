package broadcastingexample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.impl.consumer.DefaultMQPushConsumerImpl;
import org.apache.rocketmq.client.impl.consumer.MQConsumerInner;
import org.apache.rocketmq.client.log.ClientLogger;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;

import io.netty.util.concurrent.GenericFutureListener;

public class BroadcastConsumer {
    public static void main(String[] args) throws Exception {
//    	for (int i = 0; i < 2; i ++) {
    		
    		final Logger log = ClientLogger.getLog();
    		DefaultMQPushConsumer consumer = null;
    		if (consumer == null) {
    			consumer = new DefaultMQPushConsumer("broadcast_consumergroup_shenm");
    		}
    		consumer.setNamesrvAddr("192.168.162.236:9876;192.168.162.235:9876");  
//    		consumer.setInstanceName("rmq-instance" + i);  
    		consumer.setVipChannelEnabled(false);
    		
//        int a = 7;
//        int b = 4;
//        System.out.println(a%b);
//        Map<Long, String> m = new HashMap<Long, String>();
//        String[] s = {"b", "a"};
//        s = new String[3];
//        s = {"b", "a"};
    		//дк
    		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    		//set to broadcast mode
    		consumer.setMessageModel(MessageModel.CLUSTERING);
//        consumer.subscribe("KNOCKINFO", "*");
    		consumer.subscribe("zhang0", "*");
//        consumer.subscribe("TopicTest", MessageSelector.byTag("Tag666"));
    		
    		consumer.registerMessageListener(new MessageListenerConcurrently() {
    			
    			@Override
    			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
    				
//            	
//            	if(msgs.get(0).getTags().equals("tag2")) {
//            		
//            		
//            		while(true) {
//            			try {
//							Thread.sleep(1000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//            		}
//            		
//            	}
    				
    				System.out.printf(" Receive New Messages: " + msgs.get(0).getTopic() + "," + 
    						msgs.get(0).getTags() + "," + msgs.get(0).getKeys() + "," + Thread.currentThread().getName() + "%n");
    				
    				log.info("[whereislog] topic: {} tag: {} updateConsumeOffsetToBroker {} {}",
    						msgs.get(0).getTopic(),
    						msgs.get(0).getTags(),
    						msgs.get(0).getKeys(),
    						Thread.currentThread().getName());
    				
    				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    			}
    		});
    		
    		consumer.start();
    		System.out.printf("Broadcast Consumer Started!%n");
//    		Thread.sleep(9000);
//    		consumer.shutdown();
//    		consumer.start();
//    		consumer = null;
//    		consumer.start();
    		
//    		UnSubscribeTest un = new UnSubscribeTest();
//    		un.SubscribeExchange(consumer);
    		
    	}
//    }
}
