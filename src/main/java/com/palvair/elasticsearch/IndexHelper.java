package com.palvair.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import java.util.Date;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * @author rpalvair
 */
@Log4j
public class IndexHelper {

    private final Node node;
    private final Client client;

    public IndexHelper() {
        this.node = nodeBuilder().node();
        this.client = node.client();
    }

    public IndexResponse addToIndex(final String index, final String type) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final Tweet tweet = new Tweet("widdy", new Date(), "This is a tweet message");
        final String json = mapper.writeValueAsString(tweet);
        log.info("json = " + json);
        return client.prepareIndex(index, type).setSource(json).execute().actionGet();
    }
}
