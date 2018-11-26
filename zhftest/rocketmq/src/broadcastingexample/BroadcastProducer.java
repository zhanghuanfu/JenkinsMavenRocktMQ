package broadcastingexample;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class BroadcastProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("broadcast_producergroup_1");
        producer.setNamesrvAddr("192.168.162.236:9876;192.168.162.235:9876");
        producer.setInstanceName("rmq-instance");
        producer.setVipChannelEnabled(false);
        producer.start();

        for (int i = 0; i < 100; i++){
//            Message msg = new Message("KNOCKINFO", "KnockTag", "OrderID188", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
//            for (int is = 0; is < 5; is ++) {
            	
            	Message msg = new Message("zhang0", "tag", "OrderID188", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            	
            	SendResult sendResult = producer.send(msg);
            	System.out.printf("%s%n", sendResult);
//            }
            Thread.sleep(100);
        	
        }
        producer.shutdown();
    }
}