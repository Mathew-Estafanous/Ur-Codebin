package com.urcodebin.backend.entity;

import com.urcodebin.enumerators.PasteExpiration;
import com.urcodebin.enumerators.SyntaxtHighlight;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
public class CodePaste {

    @Id
    @GeneratedValue
    @Column(name = "paste_id")
    private UUID pasteId;

    @Column(name = "source_code")
    @NotNull
    private String sourceCode;

    @Column(name = "paste_title")
    private String pasteTitle;

    @Column(name = "syntax_highlighting")
    @NotNull
    @Enumerated(EnumType.STRING)
    private SyntaxtHighlight syntaxHighlighting;

    @Column(name = "paste_Expiration")
    @NotNull
    @Enumerated(EnumType.STRING)
    private PasteExpiration pasteExpiration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodePaste)) return false;
        CodePaste codePaste = (CodePaste) o;
        return pasteId == codePaste.pasteId &&
                sourceCode.equals(codePaste.sourceCode) &&
                Objects.equals(pasteTitle, codePaste.pasteTitle) &&
                syntaxHighlighting.equals(codePaste.syntaxHighlighting) &&
                pasteExpiration.equals(codePaste.pasteExpiration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pasteId, sourceCode, pasteTitle, syntaxHighlighting, pasteExpiration);
    }

    public UUID getPasteId() {
        return pasteId;
    }

    public void setPasteId(UUID pasteId) {
        this.pasteId = pasteId;
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

    public SyntaxtHighlight getSyntaxHighlighting() {
        return syntaxHighlighting;
    }

    public void setSyntaxHighlighting(SyntaxtHighlight syntaxHighlighting) {
        this.syntaxHighlighting = syntaxHighlighting;
    }

    public PasteExpiration getPasteExpiration() {
        return pasteExpiration;
    }

    public void setPasteExpiration(PasteExpiration pasteExpiration) {
        this.pasteExpiration = pasteExpiration;
    }
}
