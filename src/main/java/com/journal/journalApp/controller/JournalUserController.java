package com.journal.journalApp.controller;

import com.journal.journalApp.model.JournalUser;
import com.journal.journalApp.service.JournalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/journalUsers")
public class JournalUserController {

    private final JournalUserService journalUserService;

    @Autowired
    public JournalUserController(JournalUserService journalUserService) {
        this.journalUserService = journalUserService;
    }

    // Endpoint for user registration
    @PostMapping("/register")
    public JournalUser registerUser(@RequestBody JournalUser user) {
        return journalUserService.saveUser(user);
    }
}
