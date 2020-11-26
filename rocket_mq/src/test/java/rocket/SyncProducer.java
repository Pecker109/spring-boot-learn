package rocket;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author Pecker
 * @Description Producer端发送同步消息
 * @since 2020-11-25
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        send2();
    }


    public static void send1() {
        try {
            String group = "myconsumer2";
            DefaultMQProducer producer = new DefaultMQProducer(group);
            producer.setNamesrvAddr("10.20.141.213:9876");
            producer.start();
            Message message = new Message();

            //Message message = new Message("ddos_message_topic", "tagA", "key", "这是一条测试信息延迟10秒".getBytes());
            message.setTopic("ddos_message_topic");

            message.setBody("hello".getBytes());
            //message.setDelayTimeLevel(1);
            //message.setKeys("addSite");
            SendResult sendResult = producer.send(message, 10000);
            System.out.println(sendResult.toString());
            producer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void send2() {
        try {
            // 实例化消息生产者Producer
            DefaultMQProducer producer = new DefaultMQProducer("producer-test");
            // 设置NameServer的地址
            producer.setNamesrvAddr("10.20.141.213:9876");
            producer.setSendMsgTimeout(30000);

            // 启动Producer实例
            producer.start();
            for (int i = 0; i < 1; i++) {
                // 创建消息，并指定Topic，Tag和消息体
                Message msg = new Message("private-quota-topic-test",
                        "",
                        ("entityId=2_ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 发送消息到一个Broker
                SendResult sendResult = producer.send(msg);
                // 通过sendResult返回消息是否成功送达
                System.out.printf("%s%n", sendResult);
            }
            // 如果不再发送消息，关闭Producer实例。
            producer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
