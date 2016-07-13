package com.dw.phonebook;

import com.dw.phonebook.dao.UserDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.skife.jdbi.v2.DBI;

public class PhonebookAuthentication implements Authenticator<BasicCredentials, Boolean> {
    private final UserDAO userDao;

    public PhonebookAuthentication(DBI jdbi) {
        userDao = jdbi.onDemand(UserDAO.class);
    }

    public Optional<Boolean> authenticate(BasicCredentials c) throws AuthenticationException {
        boolean validUser = (userDao.getUser(c.getUsername(), c.getPassword()) == 1);

        if(validUser) {
            return Optional.of(true);
        }
        return Optional.absent();
    }
}
