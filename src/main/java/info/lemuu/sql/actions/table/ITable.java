package info.lemuu.sql.actions.table;

import info.lemuu.sql.SQL;
import java.sql.SQLException;

public interface ITable {
	
	String getName();
	
	String getQuery();
	
	SQL getSQL();
	
	boolean isIdPrimary();
	
	Table setIdPrimary(boolean idPrimary);
	
	void append(String query);
	
	void createTable() throws SQLException;

}