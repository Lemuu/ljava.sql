package info.lemuu.sql.connection;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import info.lemuu.sql.actions.query.Query;
import info.lemuu.sql.credentials.SQLCredentials;

public interface ISQLConnection {
	
	void open();
	
	void reconnect() throws SQLException;
	
	void close() throws SQLException;
	
	boolean isOffline() throws SQLException;
	
	boolean isOnline() throws SQLException;
	
	void refresh() throws SQLException;
	
	PreparedStatement prepareStatement(String statement) throws SQLException;
	
	Connection getConnection();
	
	Statement getStatement();
	
	SQLCredentials getSQLCredentials();
	
	void runUpdate(Query query, boolean asynchronously);

	long runInsert(Query query);

	void runExecute(Query query, boolean asynchronously);

}