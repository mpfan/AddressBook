package com.mfan.addressbook;

import com.mfan.addressbook.controller.AddressBookController;
import com.mfan.addressbook.controller.AddressBookRestController;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for checking if the controllers have been created.
 *
 * @author Michael Fan 101029934
 */

@SpringBootTest
class AddressbookApplicationTests {
	@Autowired
	private AddressBookController controller;

	@Autowired
	private AddressBookRestController restController;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(restController).isNotNull();
	}

}
