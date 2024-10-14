package com.journal.journalApp.controller;

import com.journal.journalApp.model.JournalEntry;
import com.journal.journalApp.model.JournalUser;
import com.journal.journalApp.service.JournalEntryService;
import com.journal.journalApp.service.JournalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/journal/entries")
@RequiredArgsConstructor
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    private final JournalUserService journalUserService;
    // Get all journal entries
    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntries() {
        List<JournalEntry> entries = journalEntryService.getAllEntries();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    // Get journal entries by user
    @GetMapping("/user/{journalUserId}")
    public ResponseEntity<List<JournalEntry>> getEntriesByUser(@PathVariable Long journalUserId) {
        List<JournalEntry> entries = journalEntryService.getEntriesByUser(journalUserId);
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    // Create a new journal entry
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        // Get the currently authenticated user (cast the principal to JournalUser)
        JournalUser journalUser = (JournalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Associate the journal entry with the authenticated user
        journalEntry.setJournalUser(journalUser);

        // Save the journal entry
        JournalEntry createdEntry = journalEntryService.createEntry(journalEntry);
        return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
    }
    // Update a journal entry
    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> updateEntry(
            @PathVariable Long id,
            @RequestBody JournalEntry updatedEntry) throws AccessDeniedException {

        // Get the currently authenticated user
        JournalUser journalUser = (JournalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Update the journal entry if the entry belongs to the authenticated user
        JournalEntry updated = journalEntryService.updateEntry(id, updatedEntry, journalUser.getId());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Delete a journal entry
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        journalEntryService.deleteEntry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Search journal entries by tag
    @GetMapping("/search")
    public ResponseEntity<List<JournalEntry>> searchEntriesByTag(@RequestParam String tag) {
        List<JournalEntry> entries = journalEntryService.searchEntriesByTag(tag);
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }
}