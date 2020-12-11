package nju.oasis.api.service;

import nju.oasis.api.vo.ResponseVO;

public interface AuthorService {

    ResponseVO findById(String id);

    ResponseVO findRelationsById(long id,int minLevel,int maxLevel,int numOfEachLayer);

    ResponseVO findPredictionsById(long id,Double minDistance,Double maxDistance,Integer maxNum);
}
