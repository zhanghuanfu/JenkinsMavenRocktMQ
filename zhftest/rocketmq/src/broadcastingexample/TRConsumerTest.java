package broadcastingexample;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.log.ClientLogger;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;

public class TRConsumerTest {
    public static void main(String[] args) throws Exception {

    	final Logger log = ClientLogger.getLog();
		DefaultMQPushConsumer consumer = null;
		if(consumer == null) {
			consumer = new DefaultMQPushConsumer("broadcast_consumergroup_zhf_TRtest");
		}
		consumer.setNamesrvAddr("192.168.162.236:9876;192.168.162.235:9876");  
		consumer.setInstanceName("rmq-instance");  
		consumer.setVipChannelEnabled(false);
		
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		consumer.setMessageModel(MessageModel.CLUSTERING);
		consumer.subscribe("TR", "*");
		
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

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
    }
}
