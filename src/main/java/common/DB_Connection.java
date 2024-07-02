package common;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
	
	public static void main(String[] args) {
		DB_Connection obj_DB_Connection = new DB_Connection();
		
		System.out.println(obj_DB_Connection.get_connection());
	}

	public Connection get_connection() {
		
		Connection connection = null;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/targent?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&user=root&password=1234$");
			
			System.out.println("Connection reussi");
			
		} catch ( ClassNotFoundException | SQLException e) {
			System.out.println("Erreur lors de la connexion : " + e.getMessage());
		}
		return connection;
	}
	
}

