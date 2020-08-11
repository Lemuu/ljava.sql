package info.lemuu.sql.actions.runnable.actions;

import info.lemuu.sql.SQL;
import info.lemuu.sql.actions.query.Query;
import info.lemuu.sql.actions.runnable.ISQLRunnable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExecuteRunnable implements ISQLRunnable {

	private final SQL sql;
	private final Query query;

	public ExecuteRunnable(SQL sql, Query query) {
		this.sql = sql;
		this.query = query;
	}

	@Override
	public void run() {
		try (PreparedStatement statement = this.sql.prepareStatement(this.query.getQuery())) {
			this.query.applyObjects(statement);

			statement.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Error at execute query '" + this.query.getQuery() + "'");
		}
	}

	@Override
	public SQL getSQL() {
		return this.sql;
	}

	@Override
	public Query getQuery() {
		return this.query;
	}

}