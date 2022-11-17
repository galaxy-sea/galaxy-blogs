package com.example.demoliteflow;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/11/15
 */
@Data
public class TopicTrie {
    private final String subTopic;
    private final Map<String, TopicTrie> subTopicParsers;
    private final List<String> methods;


    public TopicTrie() {
        this("");
    }

    public TopicTrie(String subTopic) {
        this.subTopic = subTopic;
        this.subTopicParsers = new TreeMap<>();
        this.methods = new ArrayList<>();
    }

    public List<String> findMethods(String topic) {
        String[] subTopics = topic.split("/");
        if (subTopics.length > 0) {
            return findMethods(subTopics, 0);
        }
        return Collections.emptyList();
    }

    private List<String> findMethods(String[] subTopics, int index) {
        String subTopic = subTopics[index];
        Map<String, TopicTrie> subTopicParsers = this.subTopicParsers;
        List<String> allMethods = new ArrayList<>();
        for (Map.Entry<String, TopicTrie> topicParserEntry : subTopicParsers.entrySet()) {
            String key = topicParserEntry.getKey();
            if ("#".equals(key)) {
                allMethods.addAll(topicParserEntry.getValue().getMethods());
            } else if ("+".equals(key) || subTopic.equals(key)) {
                if (subTopics.length == index + 1) {
                    allMethods.addAll(topicParserEntry.getValue().getMethods());
                } else {
                    allMethods.addAll(topicParserEntry.getValue().findMethods(subTopics, index + 1));
                }
            }
        }
        return allMethods;
    }


    public void add(String topic, String method) throws Exception {
        if (topic.contains("#") && !topic.endsWith("#")) {
            throw new Exception(topic + ": # 只能放在最后");
        }
        String[] subTopics = topic.split("/");
        if (subTopics.length > 0) {
            add(subTopics, 0, method);
        }
    }

    private void add(String[] subTopics, int index, String method) {
        String subTopic = subTopics[index];
        if (subTopic.startsWith("{") && subTopic.endsWith("}") && subTopic.length() > 2) {
            subTopic = "+";
        }
        TopicTrie topicTrie = this.subTopicParsers.computeIfAbsent(subTopic, TopicTrie::new);
        if (subTopics.length == index + 1) {
            topicTrie.getMethods().add(method);
        } else {
            topicTrie.add(subTopics, index + 1, method);
        }
    }
}
