package com.company;

/**
 * Created by pnosek on 2017-03-14.
 */
public class CrawlerException extends Exception{
    private String msg;

    public CrawlerException(String message) {
        this.msg = message;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
