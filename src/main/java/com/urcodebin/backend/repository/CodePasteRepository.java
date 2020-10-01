package com.urcodebin.backend.repository;

import com.urcodebin.backend.entity.CodePaste;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface CodePasteRepository extends JpaRepository<CodePaste, Integer> {

    Optional<CodePaste> findOneByPasteId(UUID pasteId);
}
