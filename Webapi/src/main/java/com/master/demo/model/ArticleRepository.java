package com.master.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository
        extends JpaRepository<ArticleEntity, Integer>
{
}
