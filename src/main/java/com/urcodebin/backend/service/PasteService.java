package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.backend.repository.CodePasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("PasteService")
public class PasteService implements IPasteService {

    private final CodePasteRepository codePasteRepository;

    @Autowired
    public PasteService(CodePasteRepository codePasteRepository) {
        this.codePasteRepository = codePasteRepository;
    }

    @Override
    public void createNewPaste(CodePaste codePaste) {
        codePasteRepository.save(codePaste);
    }

    @Override
    public Optional<CodePaste> findByPasteId(UUID pasteId) {
        return codePasteRepository.findOneByPasteId(pasteId);
    }
}
