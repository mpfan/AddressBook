package com.mfan.addressbook.controller;

import com.mfan.addressbook.model.AddressBook;
import com.mfan.addressbook.model.BuddyInfo;
import com.mfan.addressbook.repository.AddressBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

/**
 * These methods return the template view.
 *
 * @author Michael Fan 101029934
 */
@Controller
public class AddressBookController {
    private AddressBookRepository addressBookRepository;

    public AddressBookController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    /**
     * Displays all buddyInfoes for an addressbook
     */
    @GetMapping("/addressbook/{id}")
    public String addressBookDetail(@PathVariable long id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id);

        model.addAttribute("addressBook", addressBook != null ? addressBook : new ArrayList<BuddyInfo>());

        return "addressbook";
    }

    /**
     * Lists all addressbooks
     */
    @GetMapping("/")
    public String addressBookList(Model model) {
        model.addAttribute("addressBooks", addressBookRepository.findAll());

        return "addressbook_list";
    }
}
