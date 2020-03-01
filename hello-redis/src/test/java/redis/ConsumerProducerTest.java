package redis;

import com.lovecyy.hello.redis.HelloRedisApplication;
import com.lovecyy.hello.redis.msg.queue.MessageConsumerService;
import com.lovecyy.hello.redis.msg.queue.MessageProducerService;
import com.lovecyy.hello.redis.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ys
 * @topic
 * @date 2020/3/1 13:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloRedisApplication.class)
public class ConsumerProducerTest {

    @Autowired
    private MessageProducerService messageProducerService;

    @Autowired
    private MessageConsumerService messageConsumerService;
    @Test
    public void test(){
      messageConsumerService.start();
      messageProducerService.sendMeassage(new User(1,"绽放撒"));
      messageProducerService.sendMeassage(new User(2,"绽"));
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        messageConsumerService.interrupt();
    }
}
