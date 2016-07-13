package com.dw.phonebook;

import com.dw.phonebook.resources.ClientResource;
import com.dw.phonebook.resources.ContactResource;
import io.dropwizard.Application;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.jersey.api.client.Client;

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

        //Create a DBI factory and build a JDBI instance
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(e, c.getDataSourceFactory(), "mysql");

        //Add resource to the environment
        e.jersey().register(new ContactResource(jdbi, e.getValidator()));

        //Build client and add the resource to the environment
        final Client client = new JerseyClientBuilder(e).build("REST Client");
        e.jersey().register(new ClientResource(client));

        //Register Authenticator with the environment
        e.jersey().register(new BasicAuthProvider<Boolean>(new PhonebookAuthentication(), "Web Service Realm"));
    }

    public static void main( String[] args ) throws Exception {
        new App().run(args);
    }
}
