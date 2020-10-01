package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.CodePaste;

import java.util.Optional;
import java.util.UUID;

public interface IPasteService {

    void createNewPaste(CodePaste codePaste);

    Optional<CodePaste> findByPasteId(UUID pasteId);
}
