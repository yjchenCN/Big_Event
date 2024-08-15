package com.bigevent.service;

import com.bigevent.pojo.Article;
import com.bigevent.pojo.PageBean;

public interface ArticleService {
    //添加文章
    void add(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state);

    //根据id查询文章
    Article findById(Integer id);

    //修改文章
    void update(Article article);

    //删除文章
    void delete(Integer id);
}
