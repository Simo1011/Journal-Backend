package com.journal.journalApp.service;

import com.journal.journalApp.model.JournalEntry;
import com.journal.journalApp.repository.JournalEntryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;

    // Retrieve all journal entries
    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    // Retrieve all journal entries for a specific user
    public List<JournalEntry> getEntriesByUser(Long journalUserId) {
        return journalEntryRepository.findByJournalUserId(journalUserId);
    }

    // Create a new journal entry
    public JournalEntry createEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    // Method to update a journal entry
    public JournalEntry updateEntry(Long id, JournalEntry updatedEntry, Long userId) throws AccessDeniedException {
        JournalEntry existingEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Journal entry not found"));

        // Check if the authenticated user is the owner of the entry
        if (!existingEntry.getJournalUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to update this entry");
        }

        // Update the fields
        existingEntry.setTitle(updatedEntry.getTitle());
        existingEntry.setContent(updatedEntry.getContent());
        existingEntry.setTags(updatedEntry.getTags());
        existingEntry.setUpdatedAt(LocalDateTime.now());

        // Save the updated entry
        return journalEntryRepository.save(existingEntry);
    }
    // Delete a journal entry by ID
    public void deleteEntry(Long id) {
        journalEntryRepository.deleteById(id);
    }

    // Search journal entries by tag
    public List<JournalEntry> searchEntriesByTag(String tag) {
        return journalEntryRepository.findByTagsContaining(tag);
    }
}