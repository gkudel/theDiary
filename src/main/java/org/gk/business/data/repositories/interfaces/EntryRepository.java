package org.gk.business.data.repositories.interfaces;

import org.gk.business.data.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long>, QuerydslPredicateExecutor<Entry>   {
    List<Entry> findTop7ByOrderByTimeDesc();
}
