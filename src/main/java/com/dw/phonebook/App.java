package com.dw.phonebook;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<Configuration> {
    private static final Logger LOGGER= LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(Bootstrap<Configuration> b) {}

    @Override
    public void run(Configuration c, Environment e) throws Exception {
        LOGGER.info("Method App#run() called");

        System.out.println("Hello, World!");
    }

    public static void main( String[] args ) throws Exception {
        new App().run(args);
    }
}
