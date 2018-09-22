package issue;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;

@Controller("/")
public class HelloController {

    private final MyKafkaClient kafkaClient;

    public HelloController(MyKafkaClient kafkaClient) {
        this.kafkaClient = kafkaClient;
    }

    @Post
    @Produces(MediaType.TEXT_PLAIN)
    public String send() {
        kafkaClient.send(String.valueOf(System.currentTimeMillis()), "Micronaut", new Book("Candy Is Magic", "Jami Curl"));
        return "Sent message to Kafka topic 'spring'\n";
    }
}
