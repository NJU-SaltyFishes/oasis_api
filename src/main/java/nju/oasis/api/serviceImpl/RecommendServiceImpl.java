package nju.oasis.api.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import nju.oasis.api.domain.AuthorES;
import nju.oasis.api.service.RecommendService;
import nju.oasis.api.vo.ResponseVO;
import nju.oasis.api.vo.ResultCode;
import nju.oasis.api.vo.SearchAuthorVO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public ResponseVO recommendDirection(String prefix){
        if(prefix==null||prefix.length()<1){
            log.error("[recommendDirection] recommendDirection must exist but was null or length < 1!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        ResponseVO responseVO = restTemplate.getForObject("http://OASIS-SERV/recommend/direction?prefix="+prefix,
                ResponseVO.class);
        if (responseVO.getStatus() != 0) {
            log.warn("[recommendDirection] recommendDirection: " + prefix + " wrong!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        return responseVO;
    }

    @Override
    public ResponseVO recommendPublication(String prefix){
        if(prefix==null||prefix.length()<1){
            log.error("[recommendPublication] recommendPublication must exist but was null or length < 1!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        ResponseVO responseVO = restTemplate.getForObject("http://OASIS-SERV/recommend/publication?prefix="+prefix,
                ResponseVO.class);
        if (responseVO.getStatus() != 0) {
            log.warn("[recommendPublication] recommendPublication: " + prefix + " wrong!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        return responseVO;
    }

    @Override
    public ResponseVO recommendReader(String direction,String publication,int limit){
        boolean directionFlag = direction==null||direction.length()==0;
        boolean publicationFlag = publication==null||publication.length()==0;
        if(directionFlag&&publicationFlag){
            log.warn("[recommendReader] recommendReader: both direction and publication are empty!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC)).withSort(SortBuilders.fieldSort("influence").order(SortOrder.DESC));
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(!directionFlag){
            boolQueryBuilder. should(QueryBuilders.matchQuery("direction.name", direction))
                    .should(QueryBuilders.matchQuery("directionHistory.name", direction)).minimumShouldMatch(1);
        }
        if(!publicationFlag){
            boolQueryBuilder.must(QueryBuilders.matchQuery("authorArticle.publication.name", publication));
        }
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder).withPageable(PageRequest.of(0, limit));
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        SearchHits<AuthorES> searchHits = elasticsearchRestTemplate.search(searchQuery, AuthorES.class);
        Map<String, Object> map = new HashMap<>();
        if (searchHits.getTotalHits() < 10){
            log.warn("[recommendReader] direction = " + direction + ", publication = "+publication+", total_hits = " + searchHits.getTotalHits());
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        List<AuthorES> authors = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        List<SearchAuthorVO> searchAuthorList = new ArrayList<>();
        authors.forEach(authorES -> {
            searchAuthorList.add(new SearchAuthorVO(authorES));
        });
        map.put("authors", searchAuthorList);
        return ResponseVO.output(ResultCode.SUCCESS,map);
    }
}
