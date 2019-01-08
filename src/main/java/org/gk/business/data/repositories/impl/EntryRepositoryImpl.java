package org.gk.business.data.repositories.impl;

import org.gk.business.data.model.Entry;
import org.gk.business.data.repositories.interfaces.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class EntryRepositoryImpl implements EntryRepository {
	private static final String SQL_SLECT_NEWS = "SELECT ID, TITLE, TEXT, TIME FROM ENTRIES WHERE ROWNUM() < :rowNumber ORDER BY TIME DESC";

	@Autowired
	NamedParameterJdbcOperations jdbcOperations;

	@Override
	public List<Entry> getEntries(long count) {
		return jdbcOperations.query(SQL_SLECT_NEWS, new HashMap<String, Object>(){{
			put("rowNumber", count);
		}}, this::mapRow);
	}

	public Entry mapRow(ResultSet resultSet, int i) throws SQLException {
		return new Entry(
				resultSet.getLong("ID"),
				resultSet.getString("TITLE"),
				resultSet.getString("TEXT"),
				resultSet.getDate("TIME")
		);
	}
}
