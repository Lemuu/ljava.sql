package info.lemuu.sql.actions.table;

import java.util.List;
import info.lemuu.sql.SQL;
import java.util.ArrayList;
import java.sql.SQLException;

public class Table implements ITable {
	
	private final String name;
	private final SQL sql;
	private boolean idPrimary;
	private final List<String> query;
	
	public Table(String name, SQL sql) {
		this.name = name;
		this.sql = sql;
		this.query = new ArrayList<String>();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS `" + this.name + "` (");
		if (this.isIdPrimary()) {
			sb.append("id BIGINT(20) AUTO_INCREMENT PRIMARY KEY, ");
		}
		this.query.forEach(query -> sb.append(query));
		
		return sb.toString();
	}

	@Override
	public SQL getSQL() {
		return this.sql;
	}

	@Override
	public boolean isIdPrimary() {
		return this.idPrimary;
	}

	@Override
	public Table setIdPrimary(boolean idPrimary) {
		this.idPrimary = idPrimary;
		return this;
	}

	@Override
	public void append(String query) {
		this.query.add(query + ", ");
	}

	@Override
	public void createTable() throws SQLException {
		String query = this.getQuery();
		query = query.substring(0, query.length() - 2) + ");";
		
		this.sql.getStatement().execute(query);
	}

}