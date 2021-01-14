package rocket;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author Pecker
 * @Description Producer端发送同步消息
 * <p>
 * 消息发送者的固定步骤
 * 1.创建消息生产者producer，并制定生产者组名
 * 2.指定NameServer地址
 * 3.启动producer
 * 4.创建消息对象，指定主题Topic、Tag和消息体
 * 5.发送消息
 * 6.关闭生产者producer
 * @since 2020-11-25
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        doSend2();
    }


    public static void doSend1() {
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

    public static void doSend2() {
        try {
            // 1.创建消息生产者producer，并制定生产者组名
            DefaultMQProducer producer = new DefaultMQProducer("producer-test");
            // 2.指定NameServer地址
            producer.setNamesrvAddr("10.20.141.213:9876");
            producer.setSendMsgTimeout(30000);

            // 3.启动Producer实例
            producer.start();
            for (int i = 0; i < 1; i++) {
                // 4.创建消息对象，指定主题Topic、Tag和消息体
                Message msg = new Message("private-quota-topic-test",
                        "",
                        ("entityId=2_ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 5.发送消息到一个Broker
                SendResult sendResult = producer.send(msg);
                // 6.通过sendResult返回消息是否成功送达
                System.out.printf("%s%n", sendResult);
            }
            // 7.如果不再发送消息，关闭Producer实例。
            producer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
