package com.palvair.elasticsearch;

import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * @author rpalvair
 */
@Log4j
public class CrudHelper {

    private final Node node;
    private final Client client;

    public CrudHelper() {
        this.node = nodeBuilder().node();
        this.client = node.client();
    }

    public GetResponse get(final String index, final String type, final String id) {
        return client.prepareGet(index, type, id).execute().actionGet();
    }

    /*public DeleteByQueryResponse deleteByType(final String type) {
        return client.prepareDeleteByQuery()
                .setQuery(termQuery("_type", type))
                .execute()
                .actionGet();
    }*/

    public SearchResponse getAll(final String index, final String type) {
        return client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery())
                .setSize(100)
                .execute()
                .actionGet();
    }

}
