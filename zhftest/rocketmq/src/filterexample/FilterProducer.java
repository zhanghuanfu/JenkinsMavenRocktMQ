package filterexample;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class FilterProducer {

	public static void main(String[] args) throws Exception{
		
		DefaultMQProducer producer = new DefaultMQProducer("Zhanghuanfu_group_filter");
		producer.setNamesrvAddr("127.0.0.1:9876");
		producer.setInstanceName("rmq-instance");  
        producer.setVipChannelEnabled(false);
		producer.start();

		for(int i = 0; i < 100; i ++) {
		
		Message msg = new Message("TopicTest",
		    "tag" + i, "Key" + i,
		    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
		);
		// Set some properties.
		msg.putUserProperty("a", String.valueOf(i));

		SendResult sendResult = producer.send(msg);
		System.out.println("Send Message : " + msg.getTopic() + "," + msg.getTags() + "," + msg.getKeys());
		Thread.sleep(100);
		}
		
		producer.shutdown();
	}
}
