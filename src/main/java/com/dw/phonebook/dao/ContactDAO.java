package com.dw.phonebook.dao;

import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.Bind;

import com.dw.phonebook.representations.Contact;
import com.dw.phonebook.mappers.ContactMapper;

public interface ContactDAO {

    @Mapper(ContactMapper.class)
    @SqlQuery("SELECT * FROM Contact WHERE id = 1")
    Contact getContactById(@Bind("id") int id);
}
