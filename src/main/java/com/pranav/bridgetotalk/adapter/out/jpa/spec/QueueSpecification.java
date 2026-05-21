package com.pranav.bridgetotalk.adapter.out.jpa.spec;

import com.pranav.bridgetotalk.adapter.in.web.dto.queue.QueueFilter;
import com.pranav.bridgetotalk.adapter.out.jpa.entity.QueueJpaEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.UUID;

public class QueueSpecification {

    public static Specification<QueueJpaEntity> withOptionalFiltersByCompany(QueueFilter filter, UUID companyId) {

        // Implements the toPredicate method of the Specification interface
        return (root, query, criteriaBuilder) -> {

            var predicates = new ArrayList<Predicate>();


            if (filter.queryOptions().includeInactive()) {
                predicates.add(criteriaBuilder.isNotNull(root.get("deletedAt")));
            } else {
                predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));
            }

            // By company
            predicates.add(criteriaBuilder.equal(root.get("companyId"), companyId));

            filter.name()
                    .filter(s -> !s.isBlank())
                    .ifPresent(name -> {
                        var namePattern = "%" + name.toLowerCase() + "%";
                        predicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                namePattern
                        ));
                    });


            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
