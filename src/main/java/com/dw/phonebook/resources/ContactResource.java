package com.dw.phonebook.resources;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.auth.Auth;
import org.skife.jdbi.v2.DBI;

import com.dw.phonebook.dao.ContactDAO;
import com.dw.phonebook.representations.Contact;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final ContactDAO contactDao;
    private final Validator validator;

    public ContactResource(DBI jdbi, Validator validator) {
        contactDao = jdbi.onDemand(ContactDAO.class);
        this.validator = validator;
    }

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id, @Auth Boolean isAuthenticated) {
        //retrieve the contact with the given id
        Contact contact = contactDao.getContactById(id);

        return Response
                .ok(contact)
                .build();
    }

    @POST
    public Response createContact(Contact contact, @Auth Boolean isAuthenticated) throws URISyntaxException{

        //validates the contact's data
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);

        //Are there any constraint violations
        if(violations.size() > 0) {
            //Validation errors occurred
            ArrayList<String> validationMessages = new ArrayList<>();

            for (ConstraintViolation<Contact> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        } else {
            // OK, no validation errors
            //create a new contact
            int newContactId = contactDao.createContact(contact.getFirstName(), contact.getLastName(), contact.getPhone());

            return Response
                    .created(new URI(String.valueOf(newContactId)))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id, @Auth boolean isAuthenticated) {
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
            Contact contact,
            @Auth Boolean isAuthenticated) {

        //validate the updated data
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);

        //Are there any constraint violations?
        if(violations.size() > 0) {
            //Validation errors occurred
            ArrayList<String> validationMessages = new ArrayList<>();

            for(ConstraintViolation<Contact> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ":" + violation.getMessage());
            }

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        } else {
            // No errors
            //update the contact with provided id
            contactDao.updateContact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone());

            return Response
                    .ok(new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone()))
                    .build();
        }

    }
}
