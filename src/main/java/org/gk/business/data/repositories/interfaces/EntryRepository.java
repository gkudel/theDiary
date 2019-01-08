package org.gk.business.data.repositories.interfaces;

import org.gk.business.data.model.Entry;

import java.util.List;

public interface EntryRepository {
    List<Entry> getEntries(long count);
}
