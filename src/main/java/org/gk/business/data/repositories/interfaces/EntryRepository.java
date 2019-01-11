package org.gk.business.data.repositories.interfaces;

import org.gk.business.data.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long>, JpaSpecificationExecutor  {
    List<Entry> findTop7ByOrderByTimeDesc();
}
