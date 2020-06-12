package info.lemuu.sql.thread;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import info.lemuu.sql.connection.SQLConnection;

public class SQLThreadFallback implements Runnable {
	
	public final int MINUTES_CONNECTIONS_NO_RESPONSE = 10;
	
	private final SQLConnection sqlConnection;
	
	public SQLThreadFallback(SQLConnection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				this.sqlConnection.refresh();
				TimeUnit.MINUTES.sleep(this.MINUTES_CONNECTIONS_NO_RESPONSE);
			}
		} catch (SQLException | InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
}