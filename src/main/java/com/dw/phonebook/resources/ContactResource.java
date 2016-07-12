package com.dw.phonebook.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.skife.jdbi.v2.DBI;

import com.dw.phonebook.dao.ContactDAO;
import com.dw.phonebook.representations.Contact;

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
        Contact contact = contactDao.getContactById(id);

        return Response
                .ok(contact)
                .build();
    }

    @POST
    public Response createContact(Contact contact) {
        // add code to create a new contact

        return Response
                .created(null)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {
        // delete the contact with given id

        return Response
                .noContent()
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(
            @PathParam("id") int id,
            Contact contact) {
        // update the contact with given id and info

        return Response
                .ok(new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone()))
                .build();
    }
}
