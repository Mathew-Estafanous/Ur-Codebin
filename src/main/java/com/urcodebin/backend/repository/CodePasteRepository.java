package com.urcodebin.backend.repository;

import com.urcodebin.backend.entity.CodePaste;
import com.urcodebin.enumerators.PasteVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Repository
public interface CodePasteRepository extends JpaRepository<CodePaste, UUID> {

    List<CodePaste> findByPasteTitleContainsAndPasteVisibilityIs(String pasteTitle, PasteVisibility visibility);

    List<CodePaste> findByPasteExpirationDateLessThan(LocalDateTime time);

    List<CodePaste> findByPasteVisibilityIs(PasteVisibility visibility);
}
