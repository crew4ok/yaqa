package com.yaqa.web.model;

public class SaveImageResponse {
    private final Long imageId;
    private final String contentType;

    public SaveImageResponse(Long imageId, String contentType) {
        this.imageId = imageId;
        this.contentType = contentType;
    }

    public Long getImageId() {
        return imageId;
    }

    public String getContentType() {
        return contentType;
    }
}
