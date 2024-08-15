package com.bigevent.controller;


import com.bigevent.pojo.Article;
import com.bigevent.pojo.PageBean;
import com.bigevent.pojo.Result;
import com.bigevent.service.ArticleService;
import com.bigevent.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum, //当前页码
            Integer pageSize, //每页记录数
            @RequestParam(required = false) String categoryId, //分类id
            @RequestParam(required = false) String state //状态
    ){
        return Result.success(articleService.list(pageNum,pageSize,categoryId,state));
    }

    @GetMapping("/detail")
    public Result<Article> detail(Integer id){
        Article article = articleService.findById(id);
        return Result.success(article);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Article article){
        articleService.update(article);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        if (articleService.findById(id) == null){
            return Result.error("文章不存在");
        }
        articleService.delete(id);
        return Result.success();
    }



}
