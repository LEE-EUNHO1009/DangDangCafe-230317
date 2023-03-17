package DogManager;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class WinDogDelete extends JDialog {
	private JTextField tfISBN;
	private JTextField tfTitle;
	private JTextField tfAuthor;
	private JTextField tfPublisher;
	private JTextField tfPubDate;
	private JTextField tfPrice;

	private String sUrl;
	private JTextArea taDescription;
	private JSpinner spinnerAmount;
	private JLabel lblImage;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinDogDelete dialog = new WinDogDelete("1");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public WinDogDelete() {
		setResizable(false);
		setModal(true);
		setTitle("도서 삭제창");
		setBounds(100, 100, 560, 614);
		getContentPane().setLayout(null);
		
		String imgUrl = "https://shopping-phinf.pstatic.net/main_3246352/32463527641.20230207163644.jpg";
		sUrl = imgUrl;
		imgUrl = "<html><img src='" + imgUrl + "' width=150 height=200></html>";
		lblImage = new JLabel(imgUrl);
		
		
		lblImage.setToolTipText("더블클릭하여 그림 경로를 입력하시오");
		lblImage.setBounds(12, 10, 150, 200);
		getContentPane().add(lblImage);
		
		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setBounds(174, 22, 57, 15);
		getContentPane().add(lblISBN);
		
		JLabel lblTitle = new JLabel("책제목:");
		lblTitle.setBounds(174, 52, 57, 15);
		getContentPane().add(lblTitle);
		
		JLabel lblAuthor = new JLabel("저자:");
		lblAuthor.setBounds(174, 82, 57, 15);
		getContentPane().add(lblAuthor);
		
		JLabel lblPublisher = new JLabel("출판사:");
		lblPublisher.setBounds(174, 112, 57, 15);
		getContentPane().add(lblPublisher);
		
		JLabel lblPubDate = new JLabel("출판일:");
		lblPubDate.setBounds(174, 142, 57, 15);
		getContentPane().add(lblPubDate);
		
		JLabel lblPrice = new JLabel("가격:");
		lblPrice.setBounds(174, 172, 57, 15);
		getContentPane().add(lblPrice);
		
		JLabel lblAmount = new JLabel("수량:");
		lblAmount.setBounds(174, 202, 57, 15);
		getContentPane().add(lblAmount);
		
		JLabel lblDescription = new JLabel("책 소개:");
		lblDescription.setBounds(12, 220, 57, 15);
		getContentPane().add(lblDescription);
		
		tfISBN = new JTextField();
		tfISBN.setBounds(242, 19, 116, 21);
		getContentPane().add(tfISBN);
		tfISBN.setColumns(10);
		
		tfTitle = new JTextField();
		tfTitle.setColumns(10);
		tfTitle.setBounds(242, 49, 290, 21);
		getContentPane().add(tfTitle);
		
		tfAuthor = new JTextField();
		tfAuthor.setColumns(10);
		tfAuthor.setBounds(242, 79, 166, 21);
		getContentPane().add(tfAuthor);
		
		tfPublisher = new JTextField();
		tfPublisher.setColumns(10);
		tfPublisher.setBounds(242, 109, 116, 21);
		getContentPane().add(tfPublisher);
		
		tfPubDate = new JTextField();
		tfPubDate.setColumns(10);
		tfPubDate.setBounds(242, 139, 116, 21);
		getContentPane().add(tfPubDate);
		
		tfPrice = new JTextField();
		tfPrice.setColumns(10);
		tfPrice.setBounds(242, 169, 116, 21);
		getContentPane().add(tfPrice);
		
		spinnerAmount = new JSpinner();
		spinnerAmount.setBounds(242, 199, 77, 22);
		getContentPane().add(spinnerAmount);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 240, 520, 325);
		getContentPane().add(scrollPane);
		
		taDescription = new JTextArea();
		taDescription.setLineWrap(true);
		scrollPane.setViewportView(taDescription);
		
		JButton btnRemove = new JButton("삭제");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeBook();
				dispose();
			}
		});
		btnRemove.setBounds(370, 172, 162, 49);
		getContentPane().add(btnRemove);
		
		JComboBox cbPublisher = new JComboBox();
		cbPublisher.setBounds(370, 108, 116, 23);
		getContentPane().add(cbPublisher);

	}

	public WinDogDelete(String sISBN) {
		this();
		tfISBN.setText(sISBN);
		tfISBN.setEnabled(false); // 동적으로 run time
		showRecord(sISBN);
	}

	private void showRecord(String sISBN) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select * from bookTBL where isbn='" + sISBN + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				tfTitle.setText(rs.getString(2));
				tfAuthor.setText(rs.getString(3));
				tfPublisher.setText(rs.getString(4));
				tfPubDate.setText(rs.getString(5));				
				tfPrice.setText(rs.getString(7));				
				taDescription.setText(rs.getString(8));
				spinnerAmount.setValue(Integer.parseInt(rs.getString(9)));
				
				String imgUrl = rs.getString(6);
				imgUrl = "<html><img src='" + imgUrl + "' width=150 height=200></html>";
				lblImage.setText(imgUrl);
			}
			rs.close();
			stmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 	
		
	}

	protected void removeBook() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "delete from bookTBL where isbn='";
			sql = sql + tfISBN.getText() + "'";
			Statement stmt = con.createStatement();
			if(stmt.executeUpdate(sql) > 0 )
				;
			else
				JOptionPane.showMessageDialog(null, "삭제 오류");
			
			stmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 		
	}
}
