package com.g6.nfp121.repositories;

import java.util.List;
import java.util.Set;

import com.g6.nfp121.entities.Contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query( "select o from Contact o where id in :ids" )
    Set<Contact> findByContactIds(@Param("ids") List<Long> contactIdList);
}
