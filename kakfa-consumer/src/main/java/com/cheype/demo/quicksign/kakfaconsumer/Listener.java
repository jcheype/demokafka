package com.cheype.demo.quicksign.kakfaconsumer;

import org.springframework.kafka.annotation.KafkaListener;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Listener {
    private Map<String, LinkedList<String>> authors = new HashMap<>();

    Pattern p = Pattern.compile("Auteur-(\\d+)-.*");
    @KafkaListener(topics = "citations", groupId = "webservice")
    public void listen1(String foo) {
        Matcher matcher = p.matcher(foo);
        if(matcher.matches()){
            LinkedList<String> citations = authors.compute(matcher.group(1), (k,v)-> {
                LinkedList l = v==null?new LinkedList<>():v;
                if(l.size()>9){
                    l.removeFirst();
                }
                l.add(foo);
                return l;
            }
            );
//            System.out.println("citations = " + citations);
        }
       // System.out.println("foo = " + foo);
    }

    public List<String> getCitations(String author){
        List l = authors.get(author);
        return l==null?Collections.emptyList():l;
    }

}
