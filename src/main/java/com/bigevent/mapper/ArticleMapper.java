package com.bigevent.mapper;

import com.bigevent.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {

    // 添加文章
//    @Insert("insert into article(title, content, cover_img, state, category_id, create_user, create_time, update_time)"+
//            "values(#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime})")
    void add(Article article);
}
