package com.palvair.elasticsearch;

import lombok.Data;

import java.util.Date;

/**
 * @author rpalvair
 */
@Data
public class Tweet {

    private final String user;

    private final Date postDate;

    private final String message;

    public Tweet(final String user, final Date postDate, final String message) {
        this.user = user;
        this.postDate = postDate;
        this.message = message;
    }
}
