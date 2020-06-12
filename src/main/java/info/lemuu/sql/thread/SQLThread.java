package info.lemuu.sql.thread;

import java.io.File;
import info.lemuu.sql.connection.SQLType;
import info.lemuu.sql.connection.SQLConnection;
import info.lemuu.sql.credentials.SQLCredentials;

public abstract class SQLThread extends SQLConnection {
	
	protected Thread thread;

	public SQLThread(SQLCredentials sqlCredentials, String database, File file, SQLType sqlType) {
		super(sqlCredentials, database, file, sqlType);
		this.thread = new Thread(new SQLThreadFallback(this), "Thread SQL fallback");
	}

	public SQLThread(SQLCredentials sqlCredentials, String database, SQLType sqlType) {
		super(sqlCredentials, database, sqlType);
		this.thread = new Thread(new SQLThreadFallback(this), "Thread SQL fallback");
	}
	
}