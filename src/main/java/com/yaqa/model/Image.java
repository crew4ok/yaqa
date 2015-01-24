package com.yaqa.model;

import com.yaqa.dao.entity.ImageEntity;

public class Image {
    private final Long id;
    private final byte[] content;

    public static Image of(ImageEntity imageEntity) {
        return new Image(
                imageEntity.getId(),
                imageEntity.getContent()
        );
    }

    public Image(Long id, byte[] content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public byte[] getContent() {
        return content;
    }
}
