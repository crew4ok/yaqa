package com.yaqa.dao.impl;

import com.yaqa.dao.ImageDao;
import com.yaqa.dao.entity.ImageEntity;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ImageDaoImpl extends GenericDaoImpl<ImageEntity> implements ImageDao {
    public ImageDaoImpl() {
        super(ImageEntity.class);
    }

    @Override
    public List<ImageEntity> getByIds(List<Long> imageIds) {
        if (imageIds == null || imageIds.isEmpty()) {
            return Collections.emptyList();
        }

        return em.createQuery("select i " +
                        " from ImageEntity i " +
                        " where i.id in :imageIds ",
                ImageEntity.class)
                .setParameter("imageIds", imageIds)
                .getResultList();
    }
}
