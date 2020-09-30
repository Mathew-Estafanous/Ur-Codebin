package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.backend.repository.CodePasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int getPastesCounts() {
        return (int) codePasteRepository.count();
    }
}
