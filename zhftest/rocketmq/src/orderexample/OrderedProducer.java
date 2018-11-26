package orderexample;

import java.util.List;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;


public class OrderedProducer {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
    	DefaultMQProducer producer = new DefaultMQProducer("zhanghuanfu_order_producer");
        producer.setNamesrvAddr("192.168.162.235:9876;192.168.162.236:9876");  
        producer.setInstanceName("rmq-instance");  
        producer.setVipChannelEnabled(false);   // 必须设为false否则连接broker10909端口
        
        //Launch the instance.
        producer.start();
        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 4; i < 19; i++) {
            int orderId = i % 10;
            //Create a message instance, specifying topic, tag and message body.
//            Message msg = new Message("ZhanghuanfuTopicLocal", tags[i % tags.length], "KEY" + i,
//                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            
            
            Message msg = new Message("what1", String.valueOf(i), "KEY" + i,
                  ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                Integer id = (Integer) arg;
                int index = id % mqs.size();
                return mqs.get(index);
            }
            }, orderId);
            
//            System.out.println(producer.getProducerGroup());
            System.out.println("Send Message:" + msg.getTopic() + "," + msg.getTags() + "," + msg.getKeys());
            System.out.printf("%s%n", sendResult);
//            Thread.sleep(1000);
        }
        
//        List<MessageQueue> queue = producer.fetchPublishMessageQueues("ZhanghuanfuTopicTest2");
//        for(MessageQueue s : queue) {
//        	System.out.println(s.getBrokerName());
//        	System.out.println(s.getQueueId());
//        	System.out.println(s.getTopic());
//        }
        
        //server shutdown
        producer.shutdown();
    }
}
