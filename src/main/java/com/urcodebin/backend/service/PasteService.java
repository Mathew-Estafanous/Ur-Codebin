package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.CodePaste;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PasteService {

    CodePaste createNewPaste(CodePaste codePaste);

    Optional<CodePaste> findByPasteId(UUID pasteId);

    List<CodePaste> findAllPublicPastes();

    List<CodePaste> findAllPublicPastesWithTitle(String title);
}
