package com.cloud.hello.rabbit.header;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 22:03
 */
@Component
public class ApiCreditSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void creditBank(Map<String,Object> head,String msg){
        System.out.println("credit.bank send message"+msg);
        amqpTemplate.convertAndSend("creditBankExchange","credit.bank",getMessage(head,msg));
    }

    public void creditFinance(Map<String,Object> head,String msg){
        System.out.println("credit.finance send message"+msg);
        amqpTemplate.convertAndSend("creditFinanceExchange","credit.finance",getMessage(head,msg));
    }
    private Message getMessage(Map<String, Object> head, Object msg){
        MessageProperties messageProperties = new MessageProperties();
        for (Map.Entry<String, Object> entry : head.entrySet()) {
            messageProperties.setHeader(entry.getKey(), entry.getValue());
        }
        MessageConverter messageConverter = new SimpleMessageConverter();
        return messageConverter.toMessage(msg, messageProperties);
    }
}
