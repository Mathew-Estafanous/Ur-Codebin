package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.UserAccount;
import com.urcodebin.backend.repository.UserAccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private UserAccountServiceImpl userAccountService;

    UserAccount userAccount;

    @Before
    public void setup() {
        userAccount = new UserAccount();
        userAccount.setId((long) 100);
        userAccount.setPassword("FakePassword");
        userAccount.setEmail("fakeEmail@fake.com");
        userAccount.setUsername("Fake Username");
    }

    @Test
    public void findWithValidIdReturnsCorrectAccount() {
        when(userAccountRepository.findById(userAccount.getId())).thenReturn(Optional.of(userAccount));
        Optional<UserAccount> foundAccount = userAccountService.findByAccountId(userAccount.getId());
        Assert.assertTrue(foundAccount.isPresent());
        Assert.assertEquals(foundAccount.get(), userAccount);
    }

    @Test
    public void findWithInvalidIdReturnsNoAccount() {
        when(userAccountRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Optional<UserAccount> invalidIdAccount = userAccountService.findByAccountId(99);
        Assert.assertFalse(invalidIdAccount.isPresent());    }

    @Test
    public void findWithUsernameReturnsCorrectAccount() {
        when(userAccountRepository.findByUsernameEquals(userAccount.getUsername())).thenReturn(Optional.of(userAccount));
        Optional<UserAccount> foundAccount = userAccountService.findByUsername(userAccount.getUsername());
        Assert.assertTrue(foundAccount.isPresent());
        Assert.assertEquals(foundAccount.get(), userAccount);
    }

    @Test
    public void findWithWrongUsernameReturnsNoAccount() {
        when(userAccountRepository.findByUsernameEquals(anyString())).thenReturn(Optional.empty());
        Optional<UserAccount> invalidUserAccount = userAccountService.findByUsername("InvalidUsername");
        Assert.assertFalse(invalidUserAccount.isPresent());
    }

    @Test
    public void whenUsernameIsTakenReturnTrue() {
        when(userAccountRepository.findByUsernameEquals(userAccount.getUsername())).thenReturn(Optional.of(userAccount));
        boolean isUsernameTaken = userAccountService.isUsernameTaken("Fake Username");
        Assert.assertTrue(isUsernameTaken);
    }
}
