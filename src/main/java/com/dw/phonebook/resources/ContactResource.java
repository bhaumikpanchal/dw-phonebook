package com.dw.phonebook.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.skife.jdbi.v2.DBI;

import com.dw.phonebook.dao.ContactDAO;
import com.dw.phonebook.representations.Contact;

import java.net.URI;
import java.net.URISyntaxException;

@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final ContactDAO contactDao;

    public ContactResource(DBI jdbi) {
        contactDao = jdbi.onDemand(ContactDAO.class);
    }

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id) {
        //retrieve the contact with the given id
        Contact contact = contactDao.getContactById(id);

        return Response
                .ok(contact)
                .build();
    }

    @POST
    public Response createContact(Contact contact) throws URISyntaxException{
        //create a new contact
        int newContactId = contactDao.createContact(contact.getFirstName(), contact.getLastName(), contact.getPhone());

        return Response
                .created(new URI(String.valueOf(newContactId)))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {
        //delete the contact with a given id
        contactDao.deleteContact(id);

        return Response
                .noContent()
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(
            @PathParam("id") int id,
            Contact contact) {
        contactDao.updateContact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone());

        return Response
                .ok(new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone()))
                .build();
    }
}
