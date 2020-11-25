package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.UserAccount;
import com.urcodebin.backend.interfaces.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountDetailsService implements UserDetailsService {

    private final UserAccountService userDetailsService;

    @Autowired
    public AccountDetailsService(@Qualifier("AccountService") UserAccountService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> foundUser = userDetailsService.findByUsername(username);

        if(foundUser.isPresent()) {
            UserAccount user = foundUser.get();
            return User.withUsername(username)
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("User cannot be found");
        }
    }
}
