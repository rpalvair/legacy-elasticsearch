package com.palvair.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.UnknownHostException;

/**
 * Created by widdy on 28/11/2015.
 */
@Log4j
@RunWith(JUnit4.class)
public class ViewServiceTest {

    private ViewService viewService = new ViewService();

    @Test
    public void insert() throws JsonProcessingException, UnknownHostException {
        final View view = new View();
        view.setPage("index.php");
        final IndexResponse indexResponse = viewService.insert(view);
        Assert.assertTrue(indexResponse.isCreated());
    }

    @Test
    //@Ignore
    public void testGetAll() throws JsonProcessingException {
        final SearchResponse getResponse = viewService.getAll("view", "tweet");
        final SearchHits hits = getResponse.getHits();
        Assert.assertNotNull(hits);
        log.info("size = " + hits.getHits().length);
        for (SearchHit searchHit : hits.getHits()) {
            final String hit = searchHit.getSourceAsString();
            log.info("hit = " + hit);
        }
    }
}
