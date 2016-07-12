package com.dw.phonebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class PhonebookConfiguration extends Configuration {
    @JsonProperty
    private String message;

    @JsonProperty
    private int messageRepititions;

    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    public String getMessage() {
        return message;
    }

    public int getMessageRepititions() {
        return messageRepititions;
    }

    public DataSourceFactory getDataSourceFactory() { return database; }
}
