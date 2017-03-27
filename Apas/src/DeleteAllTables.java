

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JList;

public class DeleteAllTables {

	private JList<String> lst_tables;
	private static Connection connection = null;
	public static final String CONNECTIONDRIVER = "com.ibm.as400.access.AS400JDBCDriver";
	private static final String CONNECTIONSTRING = "jdbc:as400://";
	private static String library = "REPDBF";
	static ArrayList<String> tableList = new ArrayList<String>();

	public static void main(String[] args) {
		try {
			tableList = getTableList();
			for (int i = 0; i < tableList.size(); i++) {

				System.out.println(tableList.get(i));
				String dropTable = "drop table " + tableList.get(i);
				Connection con = createConnection();
				if (con != null) 
				{
					Statement stat;
					stat = con.createStatement();
					stat.execute(dropTable);
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Connection createConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName(CONNECTIONDRIVER);
				Properties pro = new Properties();
				pro.put("libraries", library);
				pro.put("naming", "system");
				pro.put("date format", "iso");
				pro.put("prompt", "false");
				pro.put("user", "CARE");
				pro.put("password", "care22");
				connection = DriverManager.getConnection(CONNECTIONSTRING
						+ "172.17.66.11", pro);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static ArrayList<String> getTableList() {
		ArrayList<String> tableList = new ArrayList<String>();
		try {
			Connection con = createConnection();
			if (con != null) {
				Statement stat = con.createStatement();
				ResultSet res = stat
						.executeQuery("select TABLE_NAME from systables where table_schema = '"
								+ library
								+ "' and table_type = 'T' and system_table = 'N' and table_name not in ('AUDTAK')");
				while (res.next())
					tableList.add(res.getNString("TABLE_NAME"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableList;
	}
}
