package nju.oasis.api.serviceImpl;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import nju.oasis.api.config.Model;
import nju.oasis.api.dao.ArticleDAO;
import nju.oasis.api.domain.ArticleES;
import nju.oasis.api.domain.AuthorES;
import nju.oasis.api.service.ArticleService;

import nju.oasis.api.service.ArticleServiceForBl;
import nju.oasis.api.vo.*;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService, ArticleServiceForBl {

    @Autowired
    ArticleDAO articleDAO;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;



    @Override
    public ResponseVO search(String allField, String content, Integer startPage, Integer limit, Integer startYear, Integer endYear) {
        System.out.println(allField);
        if (allField == null || allField.length() == 0 || startPage < 0 || limit <= 0
                || !(Model.SEARCH_ARTICLE.equals(content) || Model.SEARCH_PEOPLE.equals(content))||
                allField.startsWith(",")||allField.startsWith("&")) {
            return ResponseVO.output(ResultCode.PARAM_ERROR, null);
        }

        startYear = (startYear == null ? 1951 : startYear);
        Integer currentYear = Calendar.getInstance().getWeekYear();
        endYear = (endYear == null || endYear > currentYear ? currentYear : endYear);
        if (startYear > endYear) {
            return ResponseVO.output(ResultCode.PARAM_ERROR, null);
        }

        // es的限制
        if (limit * startPage > Model.MAX_ES_QUERY_COUNT) {
            log.warn("[search] size must be less than or equal to: [10000] but was " + limit*(startPage+1));
            return ResponseVO.output(ResultCode.SUCCESS, null);
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
        Matcher m = Model.PATTERN.matcher(keyword);

        NativeSearchQueryBuilder nativeSearchQueryBuilder =
                new NativeSearchQueryBuilder().withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .withPageable(PageRequest.of(startPage, limit));

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(new RangeQueryBuilder("date")
                .gte(startYear).lte(endYear).format("yyyy"));
        if(!m.find()) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("name", keyword).boost(3))
                    .should(QueryBuilders.matchQuery("pdf_link", keyword).boost(2))
                    .should(QueryBuilders.matchQuery("abstract", keyword))
                    .should(QueryBuilders.matchQuery("publication.name", keyword))
                    .should(QueryBuilders.matchQuery("authors.name", keyword))
                    .should(QueryBuilders.matchQuery("authors.affiliationName", keyword))
                    .should(QueryBuilders.matchQuery("directions.name", keyword))
                    .minimumShouldMatch(1);
        }
        else{
            if(m.group(Model.ARTICLE_POS)!=null){
                boolQueryBuilder.must(QueryBuilders.matchQuery("name", m.group(Model.ARTICLE_POS)));
            }
            if(m.group(Model.AUTHOR_POS)!=null){
                boolQueryBuilder.must(QueryBuilders.matchQuery("authors.name", m.group(Model.AUTHOR_POS)));
            }
            if(m.group(Model.AFFILIATION_POS)!=null){
                boolQueryBuilder.must(QueryBuilders.matchQuery("authors.affiliationName", m.group(Model.AFFILIATION_POS)));
            }
            if(m.group(Model.DIRECTION_POS)!=null){
                boolQueryBuilder. must(QueryBuilders.matchQuery("directions.name", m.group(Model.DIRECTION_POS)));
            }
        }
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder).withQuery(boolQueryBuilder);
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        SearchHits<ArticleES> searchHits = elasticsearchRestTemplate.search(searchQuery, ArticleES.class);

        Map<String, Object> map = new HashMap<>();
        map.put("count",  searchHits.getTotalHits());
        if (searchHits.getTotalHits() <= 0){
            log.warn("[searchArticle] keyword = " + keyword + ", total_hits = " + searchHits.getTotalHits());
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
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .withPageable(PageRequest.of(startPage, limit));
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        Matcher m = Model.PATTERN.matcher(keyword);

        if(!m.find()) {
            boolQueryBuilder
                    .should(QueryBuilders.matchQuery("name", keyword).boost(5))
                    .should(QueryBuilders.matchQuery("affiliation.name", keyword))
                    .should(QueryBuilders.matchQuery("affiliationHistory.name", keyword))
                    .should(QueryBuilders.matchQuery("direction.name", keyword))
                    .should(QueryBuilders.matchQuery("directionHistory.name", keyword))
                    .should(QueryBuilders.matchQuery("authorArticle.name", keyword));
        }
        else{
            if(m.group(Model.ARTICLE_POS)!=null){
                boolQueryBuilder.must(QueryBuilders.matchQuery("authorArticle.name", m.group(Model.ARTICLE_POS)));
            }
            if(m.group(Model.AUTHOR_POS)!=null){
                boolQueryBuilder.must(QueryBuilders.matchQuery("name", m.group(Model.AUTHOR_POS)));
            }
            if(m.group(Model.AFFILIATION_POS)!=null){
                boolQueryBuilder.should(QueryBuilders.matchQuery("affiliation.name", m.group(Model.AFFILIATION_POS))
                ).should(QueryBuilders.matchQuery("affiliationHistory.name", m.group(Model.AFFILIATION_POS)));
            }
            if(m.group(Model.DIRECTION_POS)!=null) {
                boolQueryBuilder. should(QueryBuilders.matchQuery("direction.name", m.group(Model.DIRECTION_POS)))
                .should(QueryBuilders.matchQuery("directionHistory.name", m.group(Model.DIRECTION_POS)));
            }
        }
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        SearchHits<AuthorES> searchHits = elasticsearchRestTemplate.search(searchQuery, AuthorES.class);
        Map<String, Object> map = new HashMap<>();
        map.put("count",  searchHits.getTotalHits());
        if (searchHits.getTotalHits() <= 0){
            log.warn("[searchAuthor] keyword = " + keyword + ", total_hits = " + searchHits.getTotalHits());
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
        if(id == null){
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        Optional<ArticleES> articleOptional = articleDAO.findById(id);
        if(!articleOptional.isPresent()){
            log.warn("[findById] articleId = " + id + " is not present!" );
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        ArticleES articleES = articleOptional.get();
        ArticleESVO result = new ArticleESVO(articleES);

        List<Map<String, String>> references = new ArrayList<>();
        for (String refUrl: articleES.getReferences()){
            String param = (articleES.getSource() == 1? "as_q" : "q");
            String referenceTitle = getReferenceTitleByQueryUrl(refUrl, param);
            if (referenceTitle == null ||"".equals(referenceTitle)){
                continue;
            }
            Map<String, String> map = new HashMap<>();
            map.put("reference", referenceTitle);
            map.put("ref_url", refUrl);
            references.add(map);
        }
        result.setReferences(references);
        return ResponseVO.output(ResultCode.SUCCESS, result);
    }

    public String getReferenceTitleByQueryUrl(String refUrl, String param) {
        try {
            URL url = new URL(URLDecoder.decode(refUrl, "UTF-8" ));
            if (url.getQuery() == null) {
                return null;
            }
            Map<String, String> paramMap = new HashMap<>();
            for (String keyValue: url.getQuery().trim().split("&")){
                if (keyValue == null || keyValue.trim().length() == 0 || !keyValue.contains("=")){
                    continue;
                }
                keyValue = keyValue.trim();
                String[] keyValueList = keyValue.split("=");
                if (keyValueList.length < 2|| keyValueList[0].trim().length() == 0){
                    continue;
                }
                paramMap.put(keyValueList[0], keyValueList[1].replace('\n', ' '));
            }
            if (paramMap.get(param) == null){
                return null;
            }
            return paramMap.get(param);
        }catch (MalformedURLException | UnsupportedEncodingException ex){
            log.warn("[ArticleServiceImpl-getReferenceTitleByQueryUrl] get url param failed, err: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

}
