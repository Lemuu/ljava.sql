package info.lemuu.sql.actions.query;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import org.apache.commons.lang3.ArrayUtils;

public class Query {

	private String query;
	private Object[] objects;

	public Query(String query, Object... objects) {
		this.query = query;
		this.objects = objects;
	}

	public String getQuery() {
		return query;
	}

	public Object[] getObjects() {
		return objects;
	}

	public void append(Query query) {
		append(query.getQuery(), query.getObjects());
	}

	public void append(String query, Object... objects) {
		this.query += query;
		this.objects = ArrayUtils.addAll(this.objects, objects);
	}

	public void applyObjects(PreparedStatement statement) throws SQLException {
		if (objects.length > 0) {
			for (int i = 0; i < objects.length; i++) {
				statement.setObject(i + 1, objects[i]);
			}
		}
	}

}
