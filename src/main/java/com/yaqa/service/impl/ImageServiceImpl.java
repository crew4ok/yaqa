package com.yaqa.service.impl;

import com.yaqa.dao.ImageDao;
import com.yaqa.dao.entity.ImageEntity;
import com.yaqa.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {
    private static final Base64.Decoder base64Decoder = Base64.getDecoder();
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();

    @Autowired
    private ImageDao imageDao;

    @Override
    @Transactional
    public ImageEntity saveImage(String encodedImage) {
        final byte[] decodedImage = base64Decoder.decode(encodedImage);
        final ImageEntity imageEntity = new ImageEntity(decodedImage);

        imageDao.save(imageEntity);

        return imageEntity;
    }

    @Override
    public byte[] getContentById(Long imageId) {
        return imageDao.getById(imageId).getContent();
    }
}
