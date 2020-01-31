package com.master.demo.controllers;

import com.master.demo.model.ArticleEntity;
import com.master.demo.model.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    ArticleRepository repo;
    @RequestMapping(path = "/Article")
    @GetMapping(path = "/hi")
    public String Hello()
    {
        return "Hello friends !";
    }

    @GetMapping(path = "/article")
    public List<ArticleEntity> getArticle()
    {
        return repo.findAll();
    }

}
