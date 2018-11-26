package brokermodel.synchronous;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class ReliableSynchronous {

	public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("zhanghuanfu_producer_synchronous");
        producer.setNamesrvAddr("192.168.162.236:9876");  
        producer.setInstanceName("rmq-instance");  
        producer.setVipChannelEnabled(false);   // 必须设为false否则连接broker10909端口
        
        //Launch the instance.
        producer.start();
        System.out.println("开始发送数据");
        
        
        for (int i = 0; i < 100; i++) {
        	
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest" /* Topic */,
                "TagA" /* Tag */,
                ("Hello RocketMQ " +
                    i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
