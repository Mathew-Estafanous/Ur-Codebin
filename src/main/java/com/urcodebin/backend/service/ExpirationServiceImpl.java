package com.urcodebin.backend.service;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.backend.repository.CodePasteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service("ExpirationService")
public class ExpirationServiceImpl implements ExpirationService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final CodePasteRepository codePasteRepository;

    @Autowired
    public ExpirationServiceImpl(CodePasteRepository codePasteRepository) {
        this.codePasteRepository = codePasteRepository;
    }

    @Override
    @Scheduled(cron = "0 */5 * * * *")
    public List<CodePaste> deleteExpiredCodePastes() {
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        List<CodePaste> expiredPastes = codePasteRepository.findByPasteExpirationDateLessThan(currentTime);
        LOGGER.info("Deleting {} expired CodePastes", expiredPastes.size());
        codePasteRepository.deleteAll(expiredPastes);
        return expiredPastes;
    }
}
