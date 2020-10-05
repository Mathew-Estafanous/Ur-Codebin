package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.backend.repository.CodePasteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("PasteService")
public class PasteServiceImpl implements PasteService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final CodePasteRepository codePasteRepository;

    @Autowired
    public PasteServiceImpl(CodePasteRepository codePasteRepository) {
        this.codePasteRepository = codePasteRepository;
    }

    @Override
    public CodePaste createNewPaste(CodePaste codePaste) {
        LOGGER.info("Adding new paste to database | UUID: " + codePaste.getPasteId().toString());
        return codePasteRepository.save(codePaste);
    }

    @Override
    public Optional<CodePaste> findByPasteId(UUID pasteId) {
        LOGGER.info("Finding CodePaste by pasteId | UUID: " + pasteId.toString());
        final Optional<CodePaste> foundCodePaste = codePasteRepository.findById(pasteId);
        if(!foundCodePaste.isPresent())
            LOGGER.warn("No CodePaste found with pasteId");
        return foundCodePaste;
    }

    @Override
    public List<CodePaste> findAllPublicPastes() {
        LOGGER.info("Finding all public CodePastes in database");
        return codePasteRepository.findAll();
    }
}
