# Micronaut -> Kafka -> Spring Cloud Stream

A test project to work out issues with Micronaut and Spring Cloud Stream messaging interoperability

## Steps to reproduce

    $ ./gradlew docker
    $ docker-compose up
    $ curl -X POST http://localhost:8080/

## Micronaut side

```
@KafkaClient
public interface MyKafkaClient {

    @Topic("spring")
    void send(@KafkaKey String key,
              @Header("sender") String sender,
              @Body Book book);
}
```

Micronaut send the Kafka header 'sender' as a byte array so Spring side must create a String from byte[]

    Spring recieved message from 77,105,99,114,111,110,97,117,116

## Spring side

A message sent from Spring Cloud Stream to Kafka sends the header 'sender' as a quoted string "value".

```
return MessageBuilder.withPayload(book)
        .setHeader("sender", "spring")
        .build();
```

Spring send the header as "string" so Micronaut header value contains double quotes.

    Micronaut recieved message from "spring"
