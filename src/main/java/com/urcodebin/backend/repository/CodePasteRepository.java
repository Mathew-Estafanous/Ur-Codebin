package com.urcodebin.backend.repository;

import com.urcodebin.backend.entity.CodePaste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodePasteRepository extends JpaRepository<CodePaste, Integer> {

}
