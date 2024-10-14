package com.journal.journalApp.repository;


import com.journal.journalApp.model.JournalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JournalUserRepository extends JpaRepository<JournalUser, Long> {
    Optional<JournalUser> findByUsername(String username);
}
