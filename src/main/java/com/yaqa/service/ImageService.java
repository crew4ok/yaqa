package com.yaqa.service;

import com.yaqa.dao.entity.ImageEntity;

public interface ImageService {

    /**
     * Saves an image from base64-encoded string.
     * @param encodedImage - base64-encoded image.
     * @return id of the saved image.
     */
    ImageEntity saveImage(String encodedImage);

    byte[] getContentById(Long imageId);
}
