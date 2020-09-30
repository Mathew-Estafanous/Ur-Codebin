package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.CodePaste;

public interface IPasteService {

    void createNewPaste(CodePaste codePaste);

    int getPastesCounts();
}
