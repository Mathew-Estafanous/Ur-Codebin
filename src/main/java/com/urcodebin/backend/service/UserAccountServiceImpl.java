package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.UserAccount;
import com.urcodebin.backend.interfaces.UserAccountService;
import com.urcodebin.backend.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("AccountService")
public class UserAccountServiceImpl implements UserAccountService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final UserAccountRepository userAccountRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserAccount> findByAccountId(long id) {
        LOGGER.info("Finding UserAccount by Account Id | ID: {}", id);
        Optional<UserAccount> foundAccount = userAccountRepository.findById(id);
        if(!foundAccount.isPresent())
            LOGGER.warn("No UserAccount found with {} as id", id);
        return foundAccount;
    }

    @Override
    public void addUserAccount(UserAccount account) {
        UserAccount user = createNewEncryptedUser(account);
        LOGGER.info("Adding new user account to database");
        userAccountRepository.save(user);
    }

    @Override
    public void deleteUserByAccountId(long id) {
        LOGGER.info("Deleting user account from database | ID: {}", id);
        userAccountRepository.deleteById(id);
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        return userAccountRepository.findByUsernameEquals(username);
    }

    private UserAccount createNewEncryptedUser(UserAccount account) {
        UserAccount user = new UserAccount();
        user.setUsername(account.getUsername());
        user.setEmail(account.getEmail());
        user.setCodePaste(account.getCodePastes());
        user.setPassword(passwordEncoder.encode(account.getPassword()));
        return user;
    }

}
