package com.palvair.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.util.Date;

/**
 * @author rpalvair
 */
@Log4j
public class IndexHelper {


    public IndexHelper() {

    }

    public IndexResponse addToIndex(final String index, final String type) throws JsonProcessingException {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "elasticsearch_widdy").build();
        Client client =    new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        final ObjectMapper mapper = new ObjectMapper();
        final Tweet tweet = new Tweet("widdy", new Date(), "This is a tweet message");
        final String json = mapper.writeValueAsString(tweet);
        log.info("json = " + json);
        final IndexResponse indexResponse = client.prepareIndex(index, type).setSource(json).execute().actionGet();
        client.close();
        return indexResponse;
    }

}
