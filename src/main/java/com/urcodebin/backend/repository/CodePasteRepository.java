package com.urcodebin.backend.repository;

import com.urcodebin.backend.entity.CodePaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface CodePasteRepository extends JpaRepository<CodePaste, UUID> {

    List<CodePaste> findByPasteTitleContains(String pasteTitle);
}
