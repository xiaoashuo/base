package redis;

import com.lovecyy.hello.redis.HelloRedisApplication;

import com.lovecyy.hello.redis.pojo.User;
import com.lovecyy.hello.redis.pubsub.Receiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ys
 * @topic 发布订阅模式测试
 * @date 2020/3/1 12:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloRedisApplication.class)
public class PubSubTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private Receiver receiver;
    @Test
    public void testPub() throws InterruptedException {


            stringRedisTemplate.convertAndSend("chat", "Hello from Redis!");


    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testPubPo() throws InterruptedException {

        User user = new User();
        user.setId(1);
        user.setName("aa");
        while (receiver.getCount()==0){
            redisTemplate.convertAndSend("topic",user );
        }


    }
}
