package com.journal.journalApp.service;

import com.journal.journalApp.model.JournalUser;
import com.journal.journalApp.repository.JournalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JournalUserService implements UserDetailsService {

    private final JournalUserRepository journalUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return journalUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    // Method to find a JournalUser directly by username
    public JournalUser findJournalUserByUsername(String username) {
        return journalUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    // Method to save a JournalUser
    public JournalUser saveUser(JournalUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return journalUserRepository.save(user);
    }
}
