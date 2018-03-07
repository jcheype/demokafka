package com.cheype.demo.quicksign;


import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class CitationProducer implements  Runnable {
    private final static String TOPIC = "citations";
    private final static String BOOTSTRAP_SERVERS =
            "192.168.1.20:9092";

    private final static Random rand = new Random();

    private final static int MAX_AUTHORS=1000;
    private static final int MESSAGES_SECONDS = 200;

    private Map<Integer, Integer> authorCitations = new HashMap<>();

    private Producer<Integer, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,
                AuthorPartitioner.class.getName());
        return new KafkaProducer<>(props);
    }

    public void run() {
        final Producer<Integer, String> producer = createProducer();
        try {
            for (;;) {
                Citation citation = genCitation();
                final ProducerRecord<Integer, String> record =
                        new ProducerRecord<>(TOPIC, citation.authorId,
                                citation.getCitation());

                send(producer, record);
                Thread.sleep(1000/MESSAGES_SECONDS);
            }
        } catch(InterruptedException e){
            System.out.println("Closing...");
        }
        finally {
            producer.flush();
            producer.close();
        }
    }

    private void send(Producer<Integer, String> producer, ProducerRecord<Integer, String> record) {
            producer.send(record, (metadata, exception) -> {
                System.out.printf("sent record(key=%s value=%s) " +
                                "meta(partition=%d, offset=%d)\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset());
            });
    }

    private Citation genCitation() {
        int author = rand.nextInt(MAX_AUTHORS);
        int citation = authorCitations.merge(author, 1, (x,y)->x+y );
        return new Citation(author, citation);
    }
}
