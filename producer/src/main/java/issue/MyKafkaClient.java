package issue;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.messaging.annotation.Body;
import io.micronaut.messaging.annotation.Header;
import org.apache.kafka.clients.producer.ProducerConfig;

@KafkaClient
public interface MyKafkaClient {

    @Topic("spring")
    void send(@KafkaKey String key,
              @Header("sender") String sender,
              @Body Book book);
}
