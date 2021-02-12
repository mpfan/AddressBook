package com.mfan.addressbook.repository;

import com.mfan.addressbook.model.AddressBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for AddressBook
 *
 * @author Michael Fan 101029934
 */
public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {
    AddressBook findById(long id);
}
