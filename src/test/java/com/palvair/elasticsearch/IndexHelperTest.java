package com.palvair.elasticsearch;

import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.index.IndexResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * @author rpalvair
 */
@Log4j
@RunWith(JUnit4.class)
public class IndexHelperTest {

    private final IndexHelper indexHelper = new IndexHelper();

    @Test
    public void testIndexCreation() throws IOException {
        final IndexResponse response = indexHelper.addToIndex("twitter", "tweet");
        assertNotNull(response);
        final String id = response.getId();
        log.info("id = " + id);
    }
}
