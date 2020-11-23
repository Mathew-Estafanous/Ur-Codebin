package com.urcodebin.backend.interfaces;

import com.urcodebin.backend.entity.UserAccount;

import java.util.Optional;

public interface UserAccountService {

    Optional<UserAccount> findByAccountId(long id);

    Optional<UserAccount> findByUsername(String username);

    boolean isUsernameTaken(String username);

    void addUserAccount(UserAccount account);

    void deleteUserByAccountId(long id);
}
