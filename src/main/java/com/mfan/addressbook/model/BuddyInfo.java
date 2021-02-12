package com.mfan.addressbook.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * This class represents BuddyInfo. A BuddyInfo includes name, phone number and, address.
 *
 * @author Michael Fan 101029934
 */
@Entity
public class BuddyInfo {
    @Id
    @GeneratedValue
    private long id; // primary key

    private String name;
    private String phoneNumber;
    private String address;

    @ManyToOne
    @JoinColumn(name = "addressBook_id")
    @JsonBackReference
    private AddressBook addressBook;

    /**
     * Creates a new BuddyInfo.
     *
     * @param name the name of the buddy
     * @param phoneNumber the phone number of the buddy
     * @param address the address of the buddy
     */
    public BuddyInfo(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    /**
     * Default constructor
     */
    public BuddyInfo() {
        this("", "", "");
    }

    /**
     * Returns the name of the buddy.
     * @return name of the buddy
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the buddy.
     *
     * @param name the name of the buddy
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the phone number of the buddy.
     *
     * @return phone number of the buddy
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the buddy.
     *
     * @param phoneNumber the phone number of the buddy
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the address of the buddy.
     *
     * @return address of the buddy
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the buddy.
     *
     * @param address the address of the buddy
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the id of the buddy
     *
     * @return id of the buddy
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the id of the buddy
     *
     * @param id the id the buddy
     */
    public void setId(long id) {
        this.id = id;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @Override
    public String toString() {
        return "BuddyInfo{" +
                "id=" + id +
                '}';
    }
}
