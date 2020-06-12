package info.lemuu.sql.actions.runnable;

import info.lemuu.sql.SQL;
import info.lemuu.sql.actions.query.Query;

public interface ISQLRunnable extends Runnable {

	public SQL getSQL();
	
	public Query getQuery();
	
}