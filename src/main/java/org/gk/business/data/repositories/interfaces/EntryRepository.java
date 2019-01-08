package org.gk.business.data.repositories.interfaces;

import org.gk.business.data.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findTop7ByOrderByTimeDesc();
}
