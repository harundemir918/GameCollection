import java.sql.*;
import javax.swing.*;

public class sqliteConnection {
	Connection bagla = null;
	public static Connection dbConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:X:\\Programlama\\Çalýþmalarým\\Java\\Yaz Dönemi\\Serbest\\Eclipse Workplace\\GameCollection\\db\\game.db");
			return conn;
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
