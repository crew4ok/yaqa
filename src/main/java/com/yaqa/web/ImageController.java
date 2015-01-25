package com.yaqa.web;

import com.yaqa.exception.InvalidImageException;
import com.yaqa.model.Image;
import com.yaqa.service.ImageService;
import com.yaqa.web.model.SaveImageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/image")
public class ImageController {
    private static final Logger log = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE
    })
    public byte[] getImageById(@PathVariable("id") Long imageId,
                               HttpServletResponse response) {
        final Image image = imageService.getById(imageId);
        response.setContentType(image.getContentType());
        return image.getContent().get();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SaveImageResponse saveImage(@RequestParam("image") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                final Image image = imageService.saveImage(imageFile.getBytes());

                return new SaveImageResponse(image.getId(), image.getContentType());
            } catch (IOException e) {
                log.error("Error while retrieving bytes from image", e);
                throw new InvalidImageException("error happened while retrieving bytes from image");
            }
        } else {
            throw new InvalidImageException("image is empty");
        }

    }
}
