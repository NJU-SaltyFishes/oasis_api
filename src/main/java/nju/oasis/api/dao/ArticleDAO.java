package nju.oasis.api.dao;

import nju.oasis.api.domain.ArticleES;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleDAO extends ElasticsearchRepository<ArticleES,String> {

    @Query
    List<ArticleES>keywordSearch(String keyword);
}
