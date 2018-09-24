package issue;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class ResponseListener {

    private final Logger log = LoggerFactory.getLogger(ResponseListener.class);

    @Topic("micronaut")
    public void onMessage(@Header("sender") String sender, Book book) {
        log.debug("---> Micronaut recieved message from {}: {}", sender, book);
    }
}
