package nju.oasis.api.service;

import nju.oasis.api.vo.ResponseVO;

public interface RecommendService {

    ResponseVO recommendDirection(String prefix);

    ResponseVO recommendPublication(String prefix);

    ResponseVO recommendReader(String direction,String publication,int limit);

    ResponseVO recommendArticle(String direction,String publication,Integer startPage, Integer limit);
}
