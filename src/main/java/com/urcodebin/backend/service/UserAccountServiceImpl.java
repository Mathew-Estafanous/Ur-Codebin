package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.UserAccount;
import com.urcodebin.backend.interfaces.UserAccountService;
import com.urcodebin.backend.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("AccountService")
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public Optional<UserAccount> findByAccountId(Long id) {
        return userAccountRepository.findById(id);
    }

    @Override
    public UserAccount addUserAccount(UserAccount account) {
        return userAccountRepository.save(account);
    }
}
