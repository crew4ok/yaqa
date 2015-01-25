package com.yaqa.web.model;

import org.springframework.http.MediaType;

public class SaveImageRequest {
    private final MediaType mediaType;

    public SaveImageRequest(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
