package com.dw.phonebook;

import com.dw.phonebook.resources.ContactResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<PhonebookConfiguration> {
    private static final Logger LOGGER= LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(Bootstrap<PhonebookConfiguration> b) {}

    @Override
    public void run(PhonebookConfiguration c, Environment e) throws Exception {
        LOGGER.info("Method App#run() called");

        for(int i=0; i < c.getMessageRepititions(); i++) {
            System.out.println(c.getMessage());
        }

        e.jersey().register(new ContactResource());
    }

    public static void main( String[] args ) throws Exception {
        new App().run(args);
    }
}
