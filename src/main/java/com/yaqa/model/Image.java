package com.yaqa.model;

import com.yaqa.dao.entity.ImageEntity;

import java.util.Optional;

public class Image {
    private final Long id;
    private final String contentType;
    private final Optional<byte[]> content;

    public static Image of(ImageEntity imageEntity) {
        return new Image(
                imageEntity.getId(),
                imageEntity.getContentType(),
                imageEntity.getContent()
        );
    }

    public static Image ofWithoutContent(ImageEntity imageEntity) {
        return new Image(
                imageEntity.getId(),
                imageEntity.getContentType()
        );
    }

    public Image(Long id, String contentType) {
        this.id = id;
        this.contentType = contentType;
        this.content = Optional.empty();
    }

    public Image(Long id, String contentType, byte[] content) {
        this.id = id;
        this.contentType = contentType;
        this.content = Optional.of(content);
    }

    public Long getId() {
        return id;
    }

    public Optional<byte[]> getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
