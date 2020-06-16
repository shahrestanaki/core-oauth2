package com.service.search;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class SearchSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    public SearchSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            if (root.get(criteria.getKey()).getJavaType() == Date.class) {
                return builder.greaterThan(
                        root.get(criteria.getKey()), (Date) criteria.getValue());
            } else {
                return builder.greaterThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            }
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            if (root.get(criteria.getKey()).getJavaType() == Date.class) {
                return builder.lessThan(
                        root.get(criteria.getKey()), (Date) criteria.getValue());
            } else {
                return builder.lessThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            }
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Boolean.class) {
                if (Boolean.parseBoolean(criteria.getValue().toString())) {
                    return builder.isTrue(root.get(criteria.getKey()));
                } else {
                    return builder.isFalse(root.get(criteria.getKey()));
                }
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
