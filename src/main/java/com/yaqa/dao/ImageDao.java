package com.yaqa.dao;

import com.yaqa.dao.entity.ImageEntity;

import java.util.List;

public interface ImageDao extends GenericDao<ImageEntity> {

    List<ImageEntity> getByIds(List<Long> imageIds);

}
