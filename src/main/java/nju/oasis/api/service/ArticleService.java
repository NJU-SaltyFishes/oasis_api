package nju.oasis.api.service;

import nju.oasis.api.vo.ResponseVO;

import java.util.List;

public interface ArticleService {

    List<String> keywordSearch(String keyword);

    ResponseVO findById(String id);
}
