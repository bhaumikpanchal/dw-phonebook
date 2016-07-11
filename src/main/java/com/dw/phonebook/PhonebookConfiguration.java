package com.dw.phonebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class PhonebookConfiguration extends Configuration {
    @JsonProperty
    private String message;

    @JsonProperty
    private int messageRepititions;

    public String getMessage() {
        return message;
    }

    public int getMessageRepititions() {
        return messageRepititions;
    }
}
