package openmessagingexample;

import java.nio.charset.Charset;

import io.openmessaging.Message;
import io.openmessaging.MessagingAccessPoint;
import io.openmessaging.MessagingAccessPointFactory;
import io.openmessaging.Producer;
import io.openmessaging.Promise;
import io.openmessaging.PromiseListener;
import io.openmessaging.SendResult;

public class OMSProducer {

	public static void main(String[] args) throws Exception {
        final MessagingAccessPoint messagingAccessPoint = MessagingAccessPointFactory
            .getMessagingAccessPoint("openmessaging:rocketmq://192.168.162.236:9876,192.168.162.236:9876/namespace");

        final Producer producer = messagingAccessPoint.createProducer();

        messagingAccessPoint.startup();
        System.out.printf("MessagingAccessPoint startup OK%n");

        producer.startup();
        System.out.printf("Producer startup OK%n");

        {for (int i = 0; i < 999999999; i ++) {
        	
        	Message message = producer.createBytesMessageToTopic("OMS_HELLO_TOPIC_sync", "OMS_HELLO_BODY_sync".getBytes(Charset.forName("UTF-8")));
        	SendResult sendResult = producer.send(message);
        	System.out.printf("Send sync message OK, msgId: %s%n", sendResult.messageId());
        }
        }

//        {
//            final Promise<SendResult> result = producer.sendAsync(producer.createBytesMessageToTopic("OMS_HELLO_TOPIC_async", "OMS_HELLO_BODY_async".getBytes(Charset.forName("UTF-8"))));
//            result.addListener(new PromiseListener<SendResult>() {
//                @Override
//                public void operationCompleted(Promise<SendResult> promise) {
//                    System.out.printf("Send async message OK, msgId: %s%n", promise.get().messageId());
//                }
//
//                @Override
//                public void operationFailed(Promise<SendResult> promise) {
//                    System.out.printf("Send async message Failed, error: %s%n", promise.getThrowable().getMessage());
//                }
//            });
//        }
//
//        {
//            producer.sendOneway(producer.createBytesMessageToTopic("OMS_HELLO_TOPIC_oneway", "OMS_HELLO_BODY_oneway".getBytes(Charset.forName("UTF-8"))));
//            System.out.printf("Send oneway message OK%n");
//        }

        producer.shutdown();
        messagingAccessPoint.shutdown();
    }
}
