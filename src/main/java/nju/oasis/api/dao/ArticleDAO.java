package nju.oasis.api.dao;

import nju.oasis.api.domain.Article;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleDAO extends ElasticsearchRepository<Article,String> {

    @Query
    List<Article>keywordSearch(String keyword);
}
