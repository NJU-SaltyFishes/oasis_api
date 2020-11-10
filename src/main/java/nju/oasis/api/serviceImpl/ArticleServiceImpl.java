package nju.oasis.api.serviceImpl;

import nju.oasis.api.config.Model;
import nju.oasis.api.dao.ArticleDAO;
import nju.oasis.api.domain.Article;
import nju.oasis.api.service.ArticleService;

import nju.oasis.api.vo.*;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service

public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDAO articleDAO;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    private static final Logger logger = LoggerFactory.getLogger("log4j.properties");

    @Override
    public ResponseVO search(String allField, String content, Integer startPage, Integer limit, Integer startYear, Integer endYear) {
        if (allField == null || allField.length() == 0 || startPage < 0 || limit <= 0) {
            return ResponseVO.output(ResultCode.SUCCESS, null);
        }
        startYear = (startYear == null ? 1951 : startYear);
        endYear = (endYear == null ? Calendar.getInstance().getWeekYear() : endYear);
        if (startYear > endYear) {
            return ResponseVO.output(ResultCode.SUCCESS, null);
        }

        Map<String, Object> Result = null;
        switch (content){
            case Model.SEARCH_ARTICLE:
                Map<String, Object> articleInfoMap = searchArticle(allField, startPage,limit,startYear, endYear);
                Result = packArticleInfo(articleInfoMap, content);
                break;
            case Model.SEARCH_PEOPLE:
                searchAuthor(allField, startPage,limit);
                break;
            default:
                return ResponseVO.output(ResultCode.SUCCESS,null);
        }

        return ResponseVO.output(ResultCode.SUCCESS, Result);
    }

    public  Map<String, Object> packArticleInfo(Map<String,Object> articleInfoMap, String content) {
        if (articleInfoMap == null) {
            return null;
        }
        Map<String,Object> Result = new HashMap<>();
        Result.put("content", content);
        Result.put("count", 0);
        Result.put("info", null);
        if (articleInfoMap.get("count") == null || articleInfoMap.get("article_list") == null){
            return Result;
        }
        Result.put("count", articleInfoMap.get("count"));
        List<Article> articles = (List<Article>)articleInfoMap.get("article_list");
        List<SearchArticleVO> searchArticleList = new ArrayList<>();
        for (Article article:articles){
            searchArticleList.add(new SearchArticleVO(article));
        }
        Result.put("info", searchArticleList);
        return Result;
    }

    public Map<String, Object> searchArticle(String allField, Integer startPage, Integer limit, Integer startYear, Integer endYear) {

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(new RangeQueryBuilder("date").gte(startYear).lte(endYear).format("yyyy"))
                        .should(QueryBuilders.matchQuery("name", allField))
                        .should(QueryBuilders.matchQuery("abstract", allField))
                        .should(QueryBuilders.matchQuery("publication.name", allField))
                        .should(QueryBuilders.matchQuery("authors.name", allField))
                        .minimumShouldMatch(1))
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .withPageable(PageRequest.of(startPage, limit))
                .build();
        SearchHits<Article> searchHits = elasticsearchRestTemplate.search(searchQuery, Article.class);

        Map<String, Object> map = new HashMap<>();
        map.put("count",  searchHits.getTotalHits());
        if (searchHits.getTotalHits() <= 0){
            return map;
        }
        List<Article> articles = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        map.put("article_list", articles);
        return map;
    }
    public Map<String, Object> searchAuthor(String allField, Integer startPage, Integer limit){
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.boolQuery()
//                        .must(QueryBuilders.matchQuery("name", allField)))
//                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
//                .withPageable(PageRequest.of(startPage, limit))
//                .build();
//        SearchHits<Article> searchHits = elasticsearchRestTemplate.search(searchQuery, Author.class);
        return null;
    }

    @Override
    public List<String> keywordSearch(String keyword){
        return null;
    }

    public ResponseVO findById(String id){
        Optional<Article> articleOptional = articleDAO.findById(id);
        if(articleOptional.isPresent()){
            Article article = articleOptional.get();
            return ResponseVO.output(ResultCode.SUCCESS,new ArticleVO(article));
        }
        else{
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
    }

}
