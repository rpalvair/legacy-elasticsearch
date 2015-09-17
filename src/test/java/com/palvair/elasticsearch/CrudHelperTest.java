package com.palvair.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertNotNull;

/**
 * @author rpalvair
 */
@Log4j
@RunWith(JUnit4.class)
@Ignore
public class CrudHelperTest {

    private final IndexHelper indexHelper = new IndexHelper();
    private final CrudHelper crudHelper = new CrudHelper();

    private static final String index = "twitter";
    private static final String type = "tweet";

    @BeforeClass
    public static void beforeClass() {
        final CrudHelper crudHelper = new CrudHelper();
        final DeleteByQueryResponse getResponse = crudHelper.deleteByType(type);
    }

    @Test
    public void testGet() throws JsonProcessingException {
        final IndexResponse response = indexHelper.addToIndex(index, type);
        assertNotNull(response);
        final String id = response.getId();
        final GetResponse getResponse = crudHelper.get(index, type, id);
        final String document = getResponse.getSourceAsString();
        log.info("document = " + document);
    }

    @Test
    @Ignore
    public void testGetAll() throws JsonProcessingException {
        final SearchResponse getResponse = crudHelper.getAll("twitter", "tweet");
        final SearchHits hits = getResponse.getHits();
        Assert.assertNotNull(hits);
        for (SearchHit searchHit : hits.getHits()) {
            final String hit = searchHit.getSourceAsString();
            log.info("hit = " + hit);
        }
    }

    @Ignore
    @Test
    public void testDeleteByType() throws JsonProcessingException {
        final DeleteByQueryResponse getResponse = crudHelper.deleteByType("tweet_2");
        getResponse.status();

    }
}
