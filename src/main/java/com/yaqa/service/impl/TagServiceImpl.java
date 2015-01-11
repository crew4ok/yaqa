package com.yaqa.service.impl;

import com.yaqa.dao.TagDao;
import com.yaqa.model.Tag;
import com.yaqa.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public List<Tag> getAll() {
        return tagDao.getAll()
                .stream()
                .map(Tag::of)
                .collect(Collectors.toList());
    }
}
