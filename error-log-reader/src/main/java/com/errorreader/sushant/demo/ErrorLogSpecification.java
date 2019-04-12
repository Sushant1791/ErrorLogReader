package com.errorreader.sushant.demo;

import java.sql.Timestamp;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class ErrorLogSpecification implements Specification<ErrorLog> {

	private static final long serialVersionUID = -1424143736060783389L;
	private SearchCriteria criteria;

	public ErrorLogSpecification(SearchCriteria searchCriteria) {
		this.criteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<ErrorLog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (criteria.getOperation().equalsIgnoreCase(">")) {
			if(criteria.getKey()!=null && (criteria.getKey().equalsIgnoreCase("createdTS"))) {
				return builder.equal(root.<Timestamp>get(criteria.getKey()), (Timestamp)criteria.getValue().get(0));	
			}
			return builder.equal(root.<String>get(criteria.getKey()), criteria.getValue().get(0).toString());
		} else if (criteria.getOperation().equalsIgnoreCase(":")) {
			if (root.get(criteria.getKey()).getJavaType() == String.class) {
				return builder.like(builder.lower(root.<String>get(criteria.getKey())), "%" + criteria.getValue().get(0).toString().toLowerCase() + "%");
			}
		}
		return null;
	}
}