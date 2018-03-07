package com.cheype.demo.quicksign.kakfaconsumer;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;

public class RebalanceListener implements ConsumerRebalanceListener {

    private Collection<TopicPartition> assigned;

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> collection) {
        System.out.println("RevokedCollection = " + collection);
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> collection) {
        this.assigned = collection;
    }

    public Collection<TopicPartition> getAssigned() {
        return assigned;
    }
}
