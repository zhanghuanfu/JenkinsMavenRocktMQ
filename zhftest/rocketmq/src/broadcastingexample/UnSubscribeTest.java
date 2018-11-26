package broadcastingexample;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;

public class UnSubscribeTest {

	public void SubscribeExchange(DefaultMQPushConsumer consumer) throws MQClientException, InterruptedException {
		
		for (int i = 0; i < 999999; i ++) {
			
//			Thread.sleep(10000);
			
			int temp = i;
			consumer.unsubscribe("zhang" + i);
			consumer.subscribe("zhang" + (temp + 1), "*");
			
			System.out.println("Test SubscribeExchange" + i);
		}
		
	}

}
