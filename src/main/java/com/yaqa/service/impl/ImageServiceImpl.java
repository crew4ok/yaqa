package com.yaqa.service.impl;

import com.yaqa.dao.ImageDao;
import com.yaqa.dao.entity.ImageEntity;
import com.yaqa.exception.InvalidImageFormatException;
import com.yaqa.model.Image;
import com.yaqa.service.ImageService;
import com.yaqa.util.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageDao imageDao;

    @Override
    @Transactional
    public Image saveImage(byte[] imageContent) {
        final String contentType = determineImageFormat(imageContent);
        final ImageEntity imageEntity = new ImageEntity(imageContent, contentType);

        imageDao.save(imageEntity);

        return Image.ofWithoutContent(imageEntity);
    }

    @Override
    public Image getById(Long imageId) {
        ParamUtils.assertPositive(imageId, "imageId");

        return Image.of(imageDao.getById(imageId));
    }

    private String determineImageFormat(byte[] imageContent) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageContent);
             ImageInputStream iis = ImageIO.createImageInputStream(bis)) {

            final Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
            if (!imageReaders.hasNext()) {
                throw new InvalidImageFormatException();
            }

            String format = imageReaders.next().getFormatName();
            if (format == null) {
                throw new InvalidImageFormatException();
            }

            format = format.toLowerCase();
            switch (format) {
                case "png":
                    return MediaType.IMAGE_PNG_VALUE;

                case "jpg":
                case "jpeg":
                    return MediaType.IMAGE_JPEG_VALUE;

                default:
                    throw new InvalidImageFormatException();
            }

        } catch (IOException e) {
            throw new InvalidImageFormatException();
        }
    }
}
