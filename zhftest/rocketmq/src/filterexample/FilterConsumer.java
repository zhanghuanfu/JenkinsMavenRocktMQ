package filterexample;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

public class FilterConsumer {

	public static void main(String[] args) {
		
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("Zhanghuanfu_group_filter");
		consumer.setNamesrvAddr("127.0.0.1:9876");
		consumer.setInstanceName("rmq-instance");  
        consumer.setVipChannelEnabled(false);   // 必须设为false否则连接broker10909端口
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		
		// only subsribe messages have property a, also a >=0 and a <= 3
		try {
//			consumer.subscribe("TopicTest", "tag99");
			consumer.subscribe("TopicTest", MessageSelector.bySql("a = 50"));
		} catch (MQClientException e) {
			e.printStackTrace();
		}

		consumer.registerMessageListener(new MessageListenerConcurrently() {
		    @Override
		    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		    	System.out.println("Receive New Messages:" + msgs.get(0).getTopic() + "," + msgs.get(0).getTags() + "," + msgs.get(0).getKeys() + "," + Thread.currentThread().getName() + "%n");
		        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		    }
		});
		
		try {
			consumer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
		System.out.println("FilterConsumer Started!!!");
		
	}
}
