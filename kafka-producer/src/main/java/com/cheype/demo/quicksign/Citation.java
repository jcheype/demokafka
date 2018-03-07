package com.cheype.demo.quicksign;

public class Citation {
    public final int authorId;
    public final int citation;

    public Citation(int authorId, int citation) {
        this.authorId = authorId;
        this.citation = citation;
    }

    public String getAuthor(){
        return "Auteur-"+ authorId;
    }

    public String getCitation(){
        return getAuthor() + "-" + citation;
    }
}
