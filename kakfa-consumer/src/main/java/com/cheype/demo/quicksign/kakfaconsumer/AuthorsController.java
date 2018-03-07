package com.cheype.demo.quicksign.kakfaconsumer;

import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class AuthorsController {

    private final Listener listener;

    @Autowired
    public AuthorsController(Listener listener) {
        this.listener = listener;
    }

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/authors/{id}")
    List<String> authors(@PathVariable("id") String author) {
        return listener.getCitations(author);
    }

}
