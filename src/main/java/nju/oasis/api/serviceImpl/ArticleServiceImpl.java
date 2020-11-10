package nju.oasis.api.serviceImpl;

import nju.oasis.api.config.Model;
import nju.oasis.api.dao.ArticleDAO;
import nju.oasis.api.domain.ArticleES;
import nju.oasis.api.domain.AuthorES;
import nju.oasis.api.service.ArticleService;

import nju.oasis.api.vo.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
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
        if (allField == null || allField.length() == 0 || startPage < 0 || limit <= 0
                || !(Model.SEARCH_ARTICLE.equals(content) || Model.SEARCH_PEOPLE.equals(content))) {
            return ResponseVO.output(ResultCode.PARAM_ERROR, null);
        }
        startYear = (startYear == null ? 1951 : startYear);
        endYear = (endYear == null ? Calendar.getInstance().getWeekYear() : endYear);
        if (startYear > endYear) {
            return ResponseVO.output(ResultCode.PARAM_ERROR, null);
        }
        Map<String, Object> result = null;
        switch (content){
            case Model.SEARCH_ARTICLE:
                Map<String, Object> articleInfoMap = searchArticle(allField, startPage,limit,startYear, endYear);
                if (articleInfoMap != null) {
                    result = packArticleInfo(articleInfoMap, content);
                }
                break;
            case Model.SEARCH_PEOPLE:
                Map<String,Object> authorInfoMap = searchAuthor(allField, startPage,limit);
                if(authorInfoMap != null) {
                    result = packAuthorInfo(authorInfoMap,content);
                }
                break;
        }
        return ResponseVO.output(ResultCode.SUCCESS, result);
    }

    public Map<String, Object> searchArticle(String keyword, Integer startPage, Integer limit, Integer startYear, Integer endYear) {

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(new RangeQueryBuilder("date").gte(startYear).lte(endYear).format("yyyy"))
                        .should(QueryBuilders.matchQuery("name", keyword))
                        .should(QueryBuilders.matchQuery("abstract", keyword))
                        .should(QueryBuilders.matchQuery("publication.name", keyword))
                        .should(QueryBuilders.matchQuery("authors.name", keyword))
                        .minimumShouldMatch(1))
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .withPageable(PageRequest.of(startPage, limit))
                .build();
        SearchHits<ArticleES> searchHits = elasticsearchRestTemplate.search(searchQuery, ArticleES.class);

        Map<String, Object> map = new HashMap<>();
        map.put("count",  searchHits.getTotalHits());
        if (searchHits.getTotalHits() <= 0){
            logger.warn("[searchArticle] keyword = " + keyword + ", total_hits = " + searchHits.getTotalHits());
            return map;
        }
        List<ArticleES> articles = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        map.put("article_list", articles);
        return map;
    }

    public  Map<String, Object> packArticleInfo(Map<String,Object> articleInfoMap, String content) {
        Map<String,Object> result = new HashMap<>();
        result.put("content", content);
        result.put("count", 0);
        result.put("info", null);
        if (articleInfoMap.get("count") == null || articleInfoMap.get("article_list") == null){
            return result;
        }
        result.put("count", articleInfoMap.get("count"));
        List<ArticleES> articles = (List<ArticleES>)articleInfoMap.get("article_list");
        List<SearchArticleVO> searchArticleList = new ArrayList<>();
        for (ArticleES article: articles){
            if (article == null){
                continue;
            }
            searchArticleList.add(new SearchArticleVO(article));
        }
        result.put("info", searchArticleList);
        return result;
    }

    public Map<String, Object> searchAuthor(String keyword, Integer startPage, Integer limit){
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("name", keyword)))
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .withPageable(PageRequest.of(startPage, limit))
                .build();
        SearchHits<AuthorES> searchHits = elasticsearchRestTemplate.search(searchQuery, AuthorES.class);
        Map<String, Object> map = new HashMap<>();
        map.put("count",  searchHits.getTotalHits());
        if (searchHits.getTotalHits() <= 0){
            logger.warn("[searchAuthor] keyword = " + keyword + ", total_hits = " + searchHits.getTotalHits());
            return map;
        }
        List<AuthorES> authors = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        map.put("author_list", authors);
        return map;
    }

    public  Map<String, Object> packAuthorInfo(Map<String,Object> authorInfoMap, String content) {
        Map<String,Object> result = new HashMap<>();
        result.put("content", content);
        result.put("count", 0);
        result.put("info", null);
        if (authorInfoMap.get("count") == null || authorInfoMap.get("author_list") == null){
            return result;
        }
        result.put("count", authorInfoMap.get("count"));
        List<AuthorES> authors = (List<AuthorES>)authorInfoMap.get("author_list");
        List<SearchAuthorVO> searchAuthorList = new ArrayList<>();
        for (AuthorES author: authors){
            if (author == null){
                continue;
            }
            searchAuthorList.add(new SearchAuthorVO(author));
        }
        result.put("info", searchAuthorList);
        return result;
    }

    @Override
    public List<String> keywordSearch(String keyword){
        return null;
    }

    public ResponseVO findById(String id){
        Optional<ArticleES> articleOptional = articleDAO.findById(id);
        if(articleOptional.isPresent()){
            ArticleES articleES = articleOptional.get();
            return ResponseVO.output(ResultCode.SUCCESS,new ArticleESVO(articleES));
        }
        else{
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
    }

}
