package info.lemuu.sql.actions.runnable.actions;

import java.sql.ResultSet;
import java.sql.Statement;
import info.lemuu.sql.SQL;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import info.lemuu.sql.actions.query.Query;
import info.lemuu.sql.actions.runnable.ISQLRunnable;

public class InsertRunnable implements ISQLRunnable {
	
	private final SQL sql;
	private final Query query;
	private int id;

	public InsertRunnable(SQL sql, Query query) {
		this.sql = sql;
		this.query = query;
		this.id = -1;
	}

	@Override
	public void run() {
		try (PreparedStatement statement = this.sql.getConnection().prepareStatement(this.query.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
			this.query.applyObjects(statement);
			statement.executeUpdate();

			try (ResultSet result = statement.getGeneratedKeys()) {
				if (result.first()) {
					this.id = result.getInt(1);
				}
			}
		} catch (SQLException ex) {
			System.err.println("Error at execute query '" + this.query.getQuery() + "'");
		}
	}

	@Override
	public Query getQuery() {
		return this.query;
	}

	public SQL getSQL() {
		return this.sql;
	}

	public int getId() {
		return id;
	}

}