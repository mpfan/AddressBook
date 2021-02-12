package com.mfan.addressbook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an AddressBook. The AddressBook has list of BuddInfo.
 *
 * @author Michael Fan 101029934
 */
@Entity
public class AddressBook {
    @Id
    @GeneratedValue
    @Column(name = "addressBook_id")
    private long id; // primary key

    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.PERSIST)
    private List<BuddyInfo> list;

    /**
     * Creates a new AddressBook.
     */
    public AddressBook() {
        list = new ArrayList<BuddyInfo>();
    }

    /**
     * Adds a buddy to the list.
     *
     * @param buddyInfo the buddy to be added to the list
     */
    public void add(BuddyInfo buddyInfo) {
        list.add(buddyInfo);
    }

    /**
     * Removes a buddy from the list.
     *
     * @param id the id of the buddy to be remove
     */
    public void remove(long id) {
        for(BuddyInfo buddyInfo : list) {
            if(id == buddyInfo.getId()) {
                list.remove(buddyInfo);
                break;
            }
        }
    }

    /**
     * Returns the id of the address book
     *
     * @return id of the address book
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the id of the address book
     *
     * @param id the id the address book
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the list of buddies
     *
     * @return the list of buddies
     */
    public List<BuddyInfo> getList() {
        return this.list;
    }

    /**
     * Sets the list of buddies
     *
     * @param list this list of buddies
     */
    public void setList(List<BuddyInfo> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "AddressBook{" +
                "id=" + id +
                '}';
    }
}
