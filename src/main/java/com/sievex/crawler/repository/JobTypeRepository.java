package com.sievex.crawler.repository;

import com.sievex.crawler.entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTypeRepository extends JpaRepository<JobType, Long> {
    JobType findByAlias(String alias);
}
