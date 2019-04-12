package com.errorreader.sushant.demo;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long>, JpaSpecificationExecutor<ErrorLog> {

	public static final String SOURCE_COUNT_QUERY = 
			"SELECT SOURCE, COUNT(SOURCE) AS STATS FROM"
			+ "(SELECT * FROM ERROR_LOG "
			+ "ORDER BY ID DESC "
			+ "LIMIT :lowerlimit , :upperlimit) "
			+ "AS M GROUP BY SOURCE ORDER BY SOURCE";

	@QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
	Page<ErrorLog> findTop20ByOrderByIdDesc(Pageable pageable);
	
	@Query(value = SOURCE_COUNT_QUERY, nativeQuery = true)
	List<Object[]> getSourceCount(@Param("lowerlimit") Integer lowerLimit, @Param("upperlimit") Integer upperLimit);
	
}
