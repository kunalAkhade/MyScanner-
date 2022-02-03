package com.example.myscanner;

public class model{
    String ExtractedText;
    String uri;

    public model(){}

    public model(String ExtractedText, String uri) {
        this.ExtractedText = ExtractedText;
        this.uri = uri;

    }


    public String getExtractedText() {
        return ExtractedText;
    }

    public void setExtractedText(String ExtractedText) {
        this.ExtractedText = ExtractedText;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
