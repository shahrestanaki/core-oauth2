package com.repository;

import com.service.search.SearchCriteria;
import com.service.search.SearchCriteriaList;
import com.service.search.SearchSpecification;
import com.view.SimplePageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;


@NoRepositoryBean
public interface GeneralRepository<T, PK extends Serializable> extends JpaRepository<T, PK>, JpaSpecificationExecutor<T> {
    Logger logger = LoggerFactory.getLogger(GeneralRepository.class);
    //Page<T> findAll(Specification<T> var1, Pageable var2);

    default SimplePageResponse<T> findAllCriteria(SearchCriteriaList search) {
        try {
            Sort sort = Sort.unsorted();
            if (search.getSort() != null) {
                Sort.Direction direction;
                if (search.getSort().contains("desc")) {
                    direction = Sort.Direction.DESC;
                    search.setSort(search.getSort().replace("desc", ""));
                } else {
                    direction = Sort.Direction.ASC;
                    search.setSort(search.getSort().replace("asc", ""));
                }
                sort = new Sort(direction, search.getSort().trim());
            }
            Pageable pageable = PageRequest.of(search.getPage(), search.getSize(), sort);
            SimplePageResponse<T> temp = new SimplePageResponse<>();
            Specification<T> combinedSpecs = creatFilter(search.getSearch());

            Page results = findAll(combinedSpecs, pageable);
            List<T> educationFieldViews = results.getContent();
            temp.setContent(educationFieldViews);
            temp.setCount(results.getTotalElements());
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }


    default Specification<T> creatFilter(HashSet<SearchCriteria> search) {
        final Specification<T>[] combinedSpecs = new Specification[]{null};
        search.stream().filter(item -> item.getValue() != null).forEach(item -> {
            if (item.getKey().contains(".")) {
                combinedSpecs[0] = Specification.where(
                        (root, query, builder) -> {
                            String[] relation = item.getKey().split("\\.");
                            //final Join<T, B> join = root.join(item.getKey().split("\\.")[0]);
                            if (relation.length == 2) {
                                return builder.or(
                                        builder.like(root.join(relation[0]).get(relation[1]), item.getValue().toString())
                                );
                            } else {
                                return builder.or(
                                        builder.like(root.join(relation[0]).join(relation[1]).get(relation[2]), item.getValue().toString())
                                );
                            }
                        });
            } else {
                SearchSpecification newItem = new SearchSpecification(new SearchCriteria(item.getKey(), item.getOperation(), item.getValue()));
                combinedSpecs[0] = combinedSpecs[0] == null ? Specification.where(newItem) : combinedSpecs[0].and(newItem);
            }
        });
        return combinedSpecs[0];
    }
}
