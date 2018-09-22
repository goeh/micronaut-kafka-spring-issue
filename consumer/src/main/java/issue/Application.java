package issue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@EnableBinding(Processor.class)
public class Application {

    private final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Message<Book> listener(MessageHeaders headers, Message<Book> message) {
        String sender = getHeader(headers, "sender");
        Book book = message.getPayload();

        log.debug("Spring recieved message from {}: {}", sender, book);

        return MessageBuilder.withPayload(book)
                .setHeader("sender", "spring")
                .build();
    }

    private String getHeader(MessageHeaders headers, String name) {
        byte[] header = headers.get(name, byte[].class);
        if (header != null) {
            return new String(header);
        }
        return null;
    }

    public static class Book {
        private String title;
        private String author;

        private Book() {
        }

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        @Override
        public String toString() {
            return title + " by " + author;
        }
    }
}
