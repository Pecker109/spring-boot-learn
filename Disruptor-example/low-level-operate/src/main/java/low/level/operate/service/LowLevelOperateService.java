package low.level.operate.service;

/**
 * @author Pecker
 * @since 2025/6/11 11:26
 */
public interface LowLevelOperateService {
    /**
     * 消费者数量
     */
    int CONSUMER_NUM = 3;

    /**
     * 环形缓冲区大小
     */
    int BUFFER_SIZE = 16;

    /**
     * 发布一个事件
     *
     * @param value value
     */
    void publish(String value);
}
