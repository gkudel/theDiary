package org.gk.business.data.specifications;

import org.gk.business.data.model.Entry;
import org.gk.business.data.model.Entry_;
import org.springframework.data.jpa.domain.Specification;


public final class EntrySpecification {

	public static Specification<Entry> hasTitle(String title) {
		return (Specification<Entry>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Entry_.title), "%" + title + "%");
	}
}
