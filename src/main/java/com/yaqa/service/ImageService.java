package com.yaqa.service;

import com.yaqa.model.Image;

public interface ImageService {

    Image saveImage(byte[] imageContent);

    Image getById(Long imageId);
}
