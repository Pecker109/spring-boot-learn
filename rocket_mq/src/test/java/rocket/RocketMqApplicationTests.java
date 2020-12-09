package rocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class RocketMqApplicationTests {
    @Resource
    RocketMQTemplate rocketMQTemplate;

    @Test
    public void send() {
        String springTopic = "private-quota-topic-test";
        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, "Hello!");
        System.out.println("发送状态 {}"+ sendResult.getSendStatus());
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", springTopic, sendResult);

    }

}
