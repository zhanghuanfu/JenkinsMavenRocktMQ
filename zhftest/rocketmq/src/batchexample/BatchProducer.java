package batchexample;

import java.util.ArrayList;
import java.util.List;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

public class BatchProducer {

	public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("Zhanghuanfu_group_batch");
        producer.setNamesrvAddr("192.168.162.236:9876");  
        producer.setInstanceName("rmq-instance");  
        producer.setVipChannelEnabled(false);
        
        producer.start();
        
        String topic = "BatchTest";
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "TagA", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message(topic, "TagA", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message(topic, "TagA", "OrderID003", "Hello world 2".getBytes()));
        
//        try {
//            producer.send(messages);
//        } catch (Exception e) {
//            e.printStackTrace();
//            //handle the error
//        }
        
        ListSplitter splitter = new ListSplitter(messages);
        while (splitter.hasNext()) {
           try {
               List<Message>  listItem = splitter.next();
               producer.send(listItem);
           } catch (Exception e) {
               e.printStackTrace();
               //handle the error
           }
        }
        
        producer.shutdown();
    }
}
