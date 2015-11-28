package com.palvair.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.joda.time.LocalDate;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;


import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.node.NodeBuilder.*;

/**
 * Created by widdy on 28/11/2015.
 */
@Data
@Log4j
public class ViewService {

    private final String INDEX = "view";

    //private final Node node;

    private  Client client;

    public ViewService()  {
        //node = nodeBuilder().clusterName("elasticsearch_widdy").node();
        //client = node.client();
        /*Node node = nodeBuilder().settings(Settings.settingsBuilder().put("http.enabled", false))
                .client(true).clusterName("elasticsearch_widdy").node();
        client = node.client();*/

        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "elasticsearch_widdy").build();
        client =    new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        /*try {
            client = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getLocalHost(), 9300));
        } catch(Exception e) {
            log.error(e);
        }*/


        /*client = NodeBuilder.nodeBuilder()
                .client(true)
                .node()
                .client();*/
    }

    public IndexResponse insert(final View view) throws JsonProcessingException, UnknownHostException {

        //Settings seetings = ImmutableSettings.settingsBuilder().put("client.transport.ping_timeout", "30s").build();
        //Client client = new TransportClient(seetings).addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));

        //Node node = nodeBuilder().clusterName("elasticsearch_widdy").node();
       //node.client();


        /*Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch_widdy").build();
        Client client = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9200));*/




        final ObjectMapper mapper = new ObjectMapper();
        final String json = mapper.writeValueAsString(view);
        log.info("json = " + json);
        final LocalDate date = new LocalDate();
        log.debug("type = " + date.toString());
        final IndexResponse indexResponse = client.prepareIndex(INDEX, date.toString()).setSource(json).execute().actionGet();
        client.close();
        return indexResponse;
    }

    public SearchResponse getAll(final String index, final String type) {
        return client.prepareSearch(index)
                /*.setTypes(type)*/
                .setQuery(QueryBuilders.matchAllQuery())
                .setSize(100)
                .execute()
                .actionGet();
    }
}
