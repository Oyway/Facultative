package ua.svinkov.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import ua.svinkov.model.entity.Topic;

public class TopicMapper implements ObjectMapper<Topic> {

    @Override
    public Topic extractFromResultSet(ResultSet rs) throws SQLException {
        return Topic.builder().topicId(rs.getInt("topicid")).topic(rs.getString("topic")).build();
    }

    public Topic makeUnique(Map<Integer, Topic> cache,
    		Topic topic) {
        cache.putIfAbsent(topic.getTopicId(), topic);
        return cache.get(topic.getTopicId());
    }
}
