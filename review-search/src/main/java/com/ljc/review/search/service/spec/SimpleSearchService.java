package com.ljc.review.search.service.spec;

import com.ljc.review.search.entity.common.BaseResult;
import com.ljc.review.search.entity.param.QueryEntity;

public interface SimpleSearchService {
    BaseResult intelliQuote(QueryEntity queryEntity);
}
