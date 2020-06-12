package info.lemuu.sql.connection;

import java.io.File;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import info.lemuu.sql.credentials.SQLCredentials;

public abstract class SQLConnection implements ISQLConnection {
	
	private final SQLCredentials sqlCredentials;
	private final String database;
	private final File directory;
	private final SQLType sqlType;

    private Connection connection;
    private Statement statement;

	public SQLConnection(SQLCredentials sqlCredentials, String database, File file, SQLType sqlType) {
		this.sqlCredentials = sqlCredentials;
		this.database = database;
		this.directory = file;
		this.sqlType = sqlType;
	}
	
	public SQLConnection(SQLCredentials sqlCredentials, String database, SQLType sqlType) {
		this(sqlCredentials, database, null, sqlType);
	}

	@Override
	public void open() {
		try {
			if (this.sqlType.equals(SQLType.MYSQL)) {
				this.connection = DriverManager.getConnection("jdbc:mysql://" + this.sqlCredentials.getHost() + ":" + this.sqlCredentials.getPort() +
																		"/" + this.database + "?"
																				+ "allowMultiQueries=true"
																				+ "&autoReconnect=true",
																		this.sqlCredentials.getUser(), this.sqlCredentials.getPassword());
				this.statement = connection.createStatement();
			}
			else if (this.sqlType.equals(SQLType.SQL)) {
				if (!this.directory.exists()) {
					this.directory.mkdir();
				}
				File file = new File(this.directory, this.database + ".db");
				
				try {
					Class.forName("org.sqlite.JDBC");
					this.connection = DriverManager.getConnection("jdbc:sqlite:" + file);
					this.statement = connection.createStatement();
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
					System.err.println("Not found class SQL.");
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Error at try open connection with database '" + this.database + "'");
		}
	}
	
	@Override
	public SQLCredentials getSQLCredentials() {
		return this.sqlCredentials;
	}

	@Override
	public void reconnect() throws SQLException {
		this.close();
		this.open();
	}

	@Override
	public void close() throws SQLException {
		if (!this.isOnline()) {
			return;
		}
		this.connection.close();
	}

	@Override
	public boolean isOffline() throws SQLException {
		return this.connection.isClosed();
	}

	@Override
	public boolean isOnline() throws SQLException {
		return this.connection != null && !this.isOffline() ;
	}

	@Override
	public PreparedStatement prepareStatement(String statement) throws SQLException {
		this.refresh();
		return this.connection.prepareStatement(statement);
	}
	
	public boolean testConnection() {
		try (PreparedStatement statement = this.prepareStatement("SELECT 1")) {
			statement.execute();
			return true;
		} catch (SQLException ex) {}
		return false;
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}
	@Override
	public Statement getStatement() {
		return this.statement;
	}
	
	@Override
	public void refresh() throws SQLException {
		if (this.isOnline()) {
			return;
		}
		this.open();
	}

}