package com.sievex.crawler.service;

import com.sievex.crawler.entity.StatusType;

import java.util.List;

public interface StatusTypeService {
    StatusType getStatusTypeByAlias(String alias);
    List<StatusType> getAllStatusTypes();
    StatusType saveStatusType(StatusType statusType);
    StatusType updateStatusType(StatusType statusType);
    boolean deleteStatusType(Long statusType);
}
