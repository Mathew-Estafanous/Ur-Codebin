package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.UserAccount;
import com.urcodebin.backend.interfaces.UserAccountService;
import com.urcodebin.backend.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("AccountService")
public class UserAccountServiceImpl implements UserAccountService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public Optional<UserAccount> findByAccountId(Long id) {
        LOGGER.info("Finding UserAccount by Account Id | ID: {}", id);
        Optional<UserAccount> foundAccount = userAccountRepository.findById(id);
        if(!foundAccount.isPresent())
            LOGGER.warn("No UserAccount found with {} as id", id);
        return foundAccount;
    }

    @Override
    public UserAccount addUserAccount(UserAccount account) {
        LOGGER.info("Adding new user account to database | ID: {}", account.getId());
        return userAccountRepository.save(account);
    }

    @Override
    public void deleteUserByAccountId(Long id) {
        LOGGER.info("Deleting user account from database | ID: {}", id);
        userAccountRepository.deleteById(id);
    }
}
