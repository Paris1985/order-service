package com.example.order;


;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class OrderService {
    @Value("${topic.name}")
    private String topicName;

    @Autowired
    private  KafkaTemplate<String, Order> kafkaTemplate;

    public Order saveSchedule(Order schedule) {


        ListenableFuture<SendResult<String, Order>> future =
                kafkaTemplate.send(topicName, schedule);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Order> result) {
                System.out.println("Sent message=[" + result.getProducerRecord().value() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {

            }
        });

        return schedule;
    }


}
