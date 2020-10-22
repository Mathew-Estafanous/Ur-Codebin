package com.urcodebin.backend.entity;

import com.urcodebin.enumerators.SyntaxHighlight;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "code_paste")
@SecondaryTable(name = "source_table",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "source_id", referencedColumnName = "paste_id"))
public class CodePaste {

    @Id
    @Column(name = "paste_id", unique = true, nullable = false, length = 16)
    private final UUID pasteId = UUID.randomUUID();

    @NotNull
    @Lob
    @Column(name = "source_code", table = "source_table")
    private String sourceCode;

    @Column(name = "paste_title")
    private String pasteTitle;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "syntax_highlighting")
    private SyntaxHighlight syntaxHighlighting;

    @NotNull
    @Column(name = "paste_Expiration")
    private LocalDateTime pasteExpirationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodePaste)) return false;
        CodePaste codePaste = (CodePaste) o;
        return pasteId.equals(codePaste.pasteId) &&
                sourceCode.equals(codePaste.sourceCode) &&
                Objects.equals(pasteTitle, codePaste.pasteTitle) &&
                syntaxHighlighting.equals(codePaste.syntaxHighlighting) &&
                pasteExpirationDate.equals(codePaste.pasteExpirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pasteId, sourceCode, pasteTitle, syntaxHighlighting, pasteExpirationDate);
    }

    public UUID getPasteId() {
        return pasteId;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getPasteTitle() {
        return pasteTitle;
    }

    public void setPasteTitle(String pasteTitle) {
        this.pasteTitle = pasteTitle;
    }

    public SyntaxHighlight getSyntaxHighlighting() {
        return syntaxHighlighting;
    }

    public void setSyntaxHighlighting(SyntaxHighlight syntaxHighlighting) {
        this.syntaxHighlighting = syntaxHighlighting;
    }

    public LocalDateTime getPasteExpiration() {
        return pasteExpirationDate;
    }

    public void setPasteExpiration(LocalDateTime pasteExpirationDate) {
        this.pasteExpirationDate = pasteExpirationDate;
    }
}
