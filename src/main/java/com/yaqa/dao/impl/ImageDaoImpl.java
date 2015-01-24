package com.yaqa.dao.impl;

import com.yaqa.dao.ImageDao;
import com.yaqa.dao.entity.ImageEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDaoImpl extends GenericDaoImpl<ImageEntity> implements ImageDao {
    public ImageDaoImpl() {
        super(ImageEntity.class);
    }
}
