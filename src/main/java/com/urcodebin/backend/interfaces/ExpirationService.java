package com.urcodebin.backend.interfaces;

import com.urcodebin.backend.entity.CodePaste;
import java.util.List;

public interface ExpirationService {

    List<CodePaste> deleteExpiredCodePastes();
}
