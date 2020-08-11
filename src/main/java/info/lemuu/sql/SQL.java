package info.lemuu.sql;

import info.lemuu.sql.actions.query.Query;
import info.lemuu.sql.actions.runnable.ISQLRunnable;
import info.lemuu.sql.actions.runnable.actions.ExecuteRunnable;
import info.lemuu.sql.actions.runnable.actions.InsertRunnable;
import info.lemuu.sql.actions.runnable.actions.UpdateRunnable;
import info.lemuu.sql.connection.SQLType;
import info.lemuu.sql.credentials.SQLCredentials;
import info.lemuu.sql.thread.SQLThread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SQL extends SQLThread {

	private final static List<SQL> sqls = new ArrayList<SQL>();
	
	public ExecutorService executor;
	
	public SQL(SQLCredentials sqlCredentials, String database, File file, SQLType sqlType) {
		super(sqlCredentials, database, file, sqlType);
		this.executor = Executors.newFixedThreadPool(3);
	}

	public SQL(SQLCredentials sqlCredentials, String database, SQLType sqlType) {
		super(sqlCredentials, database, sqlType);
		this.executor = Executors.newFixedThreadPool(3);
	}
	
	public void init() {
		this.open();
		this.thread.start();
		SQL.sqls.add(this);
	}
	
	@Override
	public void runUpdate(Query query, boolean asynchronously) {
		this.run(new UpdateRunnable(this, query), asynchronously);
	}
	
	@Override
	public long runInsert(Query query) {
		InsertRunnable runnable = new InsertRunnable(this, query);
		runnable.run();
		return runnable.getId();
	}

	@Override
	public void runExecute(Query query, boolean asynchronously) {
		this.run(new ExecuteRunnable(this, query), asynchronously);
	}
	
	private void run(ISQLRunnable runnable, boolean asynchronously) {
		if (asynchronously) {
			this.executor.submit(runnable);
		} else {
			runnable.run();
		}
	}
	
	public static List<SQL> getSQLs() {
		return sqls;
	}
	
}