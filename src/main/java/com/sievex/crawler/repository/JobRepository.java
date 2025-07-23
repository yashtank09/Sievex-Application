package com.sievex.crawler.repository;

import com.sievex.crawler.entity.Jobs;
import com.sievex.crawler.entity.StatusType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Jobs, Long> {
    @EntityGraph(attributePaths = {"jobTypeId", "site", "status"})
    List<Jobs> findTop5ByStatusAliasOrderByPriorityAscCreatedAtAsc(String statusAlias);

    @Query("SELECT j FROM Jobs j WHERE j.status = :status")
    List<Jobs> findPendingJobs(@Param("status") StatusType status);

}
