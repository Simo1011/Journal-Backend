package com.journal.journalApp.repository;


import com.journal.journalApp.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    // Find all journal entries by a specific user
    List<JournalEntry> findByJournalUserId(Long journalUserId);

    // Search journal entries by tags
    List<JournalEntry> findByTagsContaining(String tag);
}