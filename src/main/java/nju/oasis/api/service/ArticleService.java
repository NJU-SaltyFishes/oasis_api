package nju.oasis.api.service;

import nju.oasis.api.vo.ResponseVO;

import java.util.List;

public interface ArticleService {

    ResponseVO search(String allField, String content, Integer startPage, Integer limit, Integer startYear, Integer endYear);

    List<String> keywordSearch(String keyword);

    ResponseVO findById(String id);
}
