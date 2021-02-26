package com.mfan.addressbook.controller;

import com.mfan.addressbook.model.AddressBook;
import com.mfan.addressbook.model.BuddyInfo;
import com.mfan.addressbook.repository.AddressBookRepository;
import com.mfan.addressbook.repository.BuddyInfoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

/**
 * These methods return the template view.
 *
 * @author Michael Fan 101029934
 */
@Controller
public class AddressBookController {
    private AddressBookRepository addressBookRepository;
    private BuddyInfoRepository buddyInfoRepository;

    public AddressBookController(AddressBookRepository addressBookRepository, BuddyInfoRepository buddyInfoRepository) {
        this.addressBookRepository = addressBookRepository;
        this.buddyInfoRepository = buddyInfoRepository;
    }

    /**
     * Displays all buddyInfoes for an addressbook
     */
    @GetMapping("/addressbook/list/{id}")
    public String addressBookDetail(@PathVariable long id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id);

        model.addAttribute("addressBook", addressBook != null ? addressBook : new ArrayList<BuddyInfo>());
        model.addAttribute("buddyInfo", new BuddyInfo());

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

    /**
     * Creates an AddressBook and redirects the user to /addressbook/list/{id}.
     * After creation the user is redirected to /addressbook/list/{id} where id
     * is the id of the newly created addressbook.
     */
    @PostMapping("/addressbook/create/addressbook")
    public RedirectView newAddressBook() {
        AddressBook addressBook = new AddressBook();
        AddressBook newAddressBook = addressBookRepository.save(addressBook);

        return new RedirectView("/addressbook/list/" + newAddressBook.getId());
    }

    /**
     * Creates a BudddyInfo and add it to the current AddressBook that the user
     * is viewing.
     */
    @PostMapping("/addressbook/{id}/create/addBuddy")
    public RedirectView newBuddy(@PathVariable long id, @ModelAttribute BuddyInfo buddyInfo) {
        AddressBook addressBook = addressBookRepository.findById(id);

        if(addressBook == null) return null;

        buddyInfo.setAddressBook(addressBook);
        buddyInfoRepository.save(buddyInfo);
        addressBook.add(buddyInfo);

        return new RedirectView("/addressbook/list/" + addressBook.getId());
    }

    /**
     * Deletes a BuddyInfo from the urrent AddressBook that the user is viewing.
     */
    @PostMapping("/addressbook/{addressBookId}/delete/{buddyId}")
    public RedirectView deleteBuddy(@PathVariable long addressBookId, @PathVariable long buddyId) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId);
        BuddyInfo buddyInfo = buddyInfoRepository.findById(buddyId);

        if(addressBook == null || buddyInfo == null)  return null;

        addressBook.remove(buddyInfo.getId());
        buddyInfoRepository.delete(buddyInfo);

        return new RedirectView("/addressbook/list/" + addressBook.getId());
    }
}
