package com.urcodebin.backend.repository;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.enumerators.PasteExpiration;
import com.urcodebin.enumerators.SyntaxtHighlight;
import org.junit.Assert;
import org.junit.Before;
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
public class CodePasteRepositoryTest {

    @Autowired
    private CodePasteRepository codePasteRepository;

    CodePaste codePasteToFind;

    @Before
    public void setup() {
        codePasteToFind = new CodePaste();
        codePasteToFind.setPasteExpiration(PasteExpiration.TENMINUTES);
        codePasteToFind.setPasteTitle("Test Paste Title");
        codePasteToFind.setSyntaxHighlighting(SyntaxtHighlight.JAVA);
        codePasteToFind.setSourceCode("THERE IS CODE HERE");
        codePasteRepository.save(codePasteToFind);
    }

    @Test
    public void repositoryProperlyFindsAValidCodePasteById() {
        Optional<CodePaste> foundPaste = codePasteRepository.findById(codePasteToFind.getPasteId());
        Assert.assertTrue(foundPaste.isPresent());
        Assert.assertEquals(codePasteToFind, foundPaste.get());
    }
}
