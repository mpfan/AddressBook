package com.mfan.addressbook.repository;

import com.mfan.addressbook.model.BuddyInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for BuddyInfo
 *
 * @author Michael Fan 101029934
 */
public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Long> {
    List<BuddyInfo> findByName(String name);

    List<BuddyInfo> findByAddress(String address);

    List<BuddyInfo> findByPhoneNumber(String phoneNumber);

    BuddyInfo findById(long id);
}
