import java.awt.EventQueue;
import javax.swing.JFrame;
import java.sql.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frmGameCollection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmGameCollection.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection = null;
	private JTextField username;
	private JPasswordField password;
	private ImageIcon img;
	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		connection = sqliteConnection.dbConnection();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGameCollection = new JFrame();
		frmGameCollection.setTitle("Game Collection");
		frmGameCollection.setBounds(100, 100, 660, 550);
		frmGameCollection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGameCollection.getContentPane().setLayout(null);
		
		username = new JTextField();
		username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		username.setBounds(312, 314, 150, 50);
		frmGameCollection.getContentPane().add(username);
		username.setColumns(10);
		
		JLabel usernameLabel = new JLabel("User Name:");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usernameLabel.setBounds(150, 311, 150, 50);
		frmGameCollection.getContentPane().add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordLabel.setBounds(150, 377, 150, 50);
		frmGameCollection.getContentPane().add(passwordLabel);
		
		
		ImageIcon ok = new ImageIcon(getClass().getResource("ok.png"));
		JButton login = new JButton("Login", ok);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String sorgu = "SELECT * FROM Users WHERE username = ? AND password = ?";
					PreparedStatement state = connection.prepareStatement(sorgu);
					state.setString(1, username.getText());
					state.setString(2, password.getText());
					
					ResultSet rs = state.executeQuery();
					int sayac = 0;
					while (rs.next()) {
						sayac++;
					}
					if (sayac == 1) {
						JOptionPane.showMessageDialog(null, "Login Successful.");
						frmGameCollection.dispose();
						gameTrackDB gameDB = new gameTrackDB();
						gameDB.setVisible(true);
					}
					else if (sayac > 1) {
						JOptionPane.showMessageDialog(null, "There are two of the same users.");
					}
					else {
						JOptionPane.showMessageDialog(null, "Username or password is incorrect. Please try again.");
					}
					
					rs.close();
					state.close();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		login.setFont(new Font("Tahoma", Font.PLAIN, 20));
		login.setBounds(250, 440, 130, 50);
		frmGameCollection.getContentPane().add(login);
		
		JLabel mainTitle = new JLabel("Game Collection Main Window");
		mainTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		mainTitle.setBounds(149, 13, 313, 30);
		frmGameCollection.getContentPane().add(mainTitle);
		
		password = new JPasswordField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		password.setBounds(312, 377, 150, 50);
		frmGameCollection.getContentPane().add(password);
		
		img = new ImageIcon(getClass().getResource("login.png"));
		JLabel iconLabel = new JLabel(img);
		iconLabel.setBounds(150, 56, 313, 245);
		frmGameCollection.getContentPane().add(iconLabel);
	}
}
