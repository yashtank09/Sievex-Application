package com.sievex.crawler.repository;

import com.sievex.crawler.entity.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusTypeRepository extends JpaRepository<StatusType, Long> {
    StatusType findByName(String name);
    StatusType findByAlias(String alias);
}
