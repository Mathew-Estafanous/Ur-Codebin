package com.urcodebin.backend.interfaces;

import com.urcodebin.backend.entity.UserAccount;

import java.util.Optional;

public interface UserAccountService {

    Optional<UserAccount> findByAccountId(Long id);

    UserAccount addUserAccount(UserAccount account);
}
