package org.mx.modelo.comun;

public class MediaContent {

    String mediaType;
    String mediaValue;

    public MediaContent() {
    }

    public MediaContent(String mediaType, String mediaValue) {
        this.mediaType = mediaType;
        this.mediaValue = mediaValue;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaValue() {
        return mediaValue;
    }

    public void setMediaValue(String mediaValue) {
        this.mediaValue = mediaValue;
    }
}

