import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class gameTrackDB extends JFrame {
	Connection connection = null;
	private JPanel contentPane;
	private JTable dataTable;
	private JScrollPane scrollPane;
	private JLabel idLabel;
	private JLabel serialNoLabel;
	private JLabel nameLabel;
	private JLabel platformLabel;
	private JLabel dateLabel;
	private JTextField idBox;
	private JTextField serialNoBox;
	private JTextField nameBox;
	private JTextField platformBox;
	private JTextField dateBox;
	private JButton newRecord;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gameTrackDB frame = new gameTrackDB();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	
	public void fillGameList() {
		try {
			String query = "SELECT id, SerialNo, Name, Platform, Date FROM Game";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "Warning", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void RefreshTable( ) {
		try {
			String query = "SELECT id, SerialNo, Name, Platform, Date FROM Game";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			dataTable.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public gameTrackDB() {
		connection = sqliteConnection.dbConnection();
		setTitle("Game Collection");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton LoadGameData = new JButton("Load Game Data");
		LoadGameData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "SELECT * FROM Game";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					dataTable.setModel(DbUtils.resultSetToTableModel(rs));
					fillGameList();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		LoadGameData.setFont(new Font("Tahoma", Font.PLAIN, 15));
		LoadGameData.setBounds(10, 13, 172, 25);
		contentPane.add(LoadGameData);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 53, 808, 384);
		contentPane.add(scrollPane);
		
		dataTable = new JTable();
		scrollPane.setViewportView(dataTable);
		
		idLabel = new JLabel("ID Number");
		idLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		idLabel.setBounds(10, 456, 140, 35);
		contentPane.add(idLabel);
		
		serialNoLabel = new JLabel("Serial Number");
		serialNoLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		serialNoLabel.setBounds(10, 504, 140, 35);
		contentPane.add(serialNoLabel);
		
		nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		nameLabel.setBounds(10, 552, 140, 35);
		contentPane.add(nameLabel);
		
		platformLabel = new JLabel("Platform");
		platformLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		platformLabel.setBounds(10, 600, 140, 35);
		contentPane.add(platformLabel);
		
		dateLabel = new JLabel("Date");
		dateLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		dateLabel.setBounds(10, 648, 140, 35);
		contentPane.add(dateLabel);
		
		idBox = new JTextField();
		idBox.setBounds(162, 456, 140, 35);
		contentPane.add(idBox);
		idBox.setColumns(10);
		
		serialNoBox = new JTextField();
		serialNoBox.setColumns(10);
		serialNoBox.setBounds(162, 504, 140, 35);
		contentPane.add(serialNoBox);
		
		nameBox = new JTextField();
		nameBox.setColumns(10);
		nameBox.setBounds(162, 552, 140, 35);
		contentPane.add(nameBox);
		
		platformBox = new JTextField();
		platformBox.setColumns(10);
		platformBox.setBounds(162, 600, 140, 35);
		contentPane.add(platformBox);
		
		dateBox = new JTextField();
		dateBox.setColumns(10);
		dateBox.setBounds(162, 648, 140, 35);
		contentPane.add(dateBox);
		
		newRecord = new JButton("Add Record");
		newRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "INSERT INTO Game (id, SerialNo, Name, Platform, Date) VALUES (?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					
					String serialBox = serialNoBox.getText();
					pst.setString(1, idBox.getText());
					pst.setString(2, serialBox);
					pst.setString(3, nameBox.getText());
					pst.setString(4, platformBox.getText());
					pst.setString(5, dateBox.getText());
					
					pst.execute();
					
					idBox.setText(null);
					serialNoBox.setText(null);
					nameBox.setText(null);
					platformBox.setText(null);
					dateBox.setText(null);
					
					
					RefreshTable();
					JOptionPane.showMessageDialog(null, "Data Saved Successfully.");
					pst.close();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		newRecord.setBounds(314, 456, 140, 35);
		contentPane.add(newRecord);
		
		JButton updateRecord = new JButton("Update Record");
		updateRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String updatedQuery = "UPDATE Game SET id = '" + idBox.getText() + "', SerialNo = '" + serialNoBox.getText() + "', Name = '" + nameBox.getText() + "', Platform = '" + platformBox.getText() + "', Date = '" + dateBox.getText() + "' WHERE id = '" + idBox.getText() + "' OR Name = '" + nameBox.getText() + "'";
					PreparedStatement updatedPst = connection.prepareStatement(updatedQuery);
					updatedPst.execute();
					
					idBox.setText(null);
					serialNoBox.setText(null);
					nameBox.setText(null);
					platformBox.setText(null);
					dateBox.setText(null);
					
					RefreshTable();
					JOptionPane.showMessageDialog(null, "Data Updated Successfully.");
					updatedPst.close();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		updateRecord.setBounds(466, 458, 140, 35);
		contentPane.add(updateRecord);
		
		JButton deleteRecord = new JButton("Delete Record");
		deleteRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int action = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Warning", JOptionPane.WARNING_MESSAGE);
					if (action == 0) {
						String deletingRecord = "DELETE FROM Game WHERE id = '" + idBox.getText() + "'";
						PreparedStatement deletingPst = connection.prepareStatement(deletingRecord);
						deletingPst.execute();
						
						RefreshTable();
						JOptionPane.showMessageDialog(null, "Record Deleted.");
						
						deletingPst.close();
					}					
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		deleteRecord.setBounds(618, 458, 140, 35);
		contentPane.add(deleteRecord);
		
		JTextPane txtpnAbc = new JTextPane();
		txtpnAbc.setText("Basic Guide: \n1. To load the data from database, click Load Game Data on top left corner.\n"
				+ "2. To add a new record, enter the data that you want to add, then click Add Record.\n"
				+ "3. To update a record that you have on database, enter all the data correctly then click Update Record, otherwise it might not update all the data.\n"
				+ "4. To delete a record from database, just enter id number then click Delete Record.");
		txtpnAbc.setBounds(314, 504, 444, 179);
		contentPane.add(txtpnAbc);
	}
}
