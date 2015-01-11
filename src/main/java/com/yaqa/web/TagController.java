package com.yaqa.web;

import com.yaqa.model.Tag;
import com.yaqa.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping("/")
    public List<Tag> getAllTags() {
        return tagService.getAll();
    }


}
