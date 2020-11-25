package com.urcodebin.backend.repository;

import com.urcodebin.backend.entity.UserAccount;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void repositoryFindsCorrectAccountByUsername() {
        Optional<UserAccount> mathewAccount = userAccountRepository.findByUsernameEquals("Mathew");
        Optional<UserAccount> elijahAccount = userAccountRepository.findByUsernameEquals("Elijah");

        Assert.assertTrue(mathewAccount.isPresent());
        Assert.assertTrue(elijahAccount.isPresent());
        Assert.assertEquals(100, (long) mathewAccount.get().getId());
        Assert.assertEquals(101, (long) elijahAccount.get().getId());
    }

    @Test
    public void repositoryReturnsNoAccountWhenWrongUsernameIsPassed() {
        Optional<UserAccount> incorrectAccount = userAccountRepository.findByUsernameEquals("VERY INCORRECT USERNAME");
        Assert.assertFalse(incorrectAccount.isPresent());
    }
}
