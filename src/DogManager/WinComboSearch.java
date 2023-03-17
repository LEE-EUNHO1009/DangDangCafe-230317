package DogManager;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class WinComboSearch extends JDialog {
	private JTextField tfSearchWord;
	private int type;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinComboSearch dialog = new WinComboSearch();
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
	public WinComboSearch() {
		
		setBounds(100, 100, 263, 70);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JComboBox cbSearchColumn = new JComboBox();
		cbSearchColumn.setModel(new DefaultComboBoxModel(new String[] {"이름", "견종", "보호자"}));
		panel.add(cbSearchColumn);
		
		tfSearchWord = new JTextField();
		tfSearchWord.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					// 전달할 내용을 추출
					dispose();
					
					DefaultTableModel dtm;
					dtm = extractRecords(cbSearchColumn.getSelectedItem().toString(),tfSearchWord.getText());
					
					WinResultSearch winResultSearch = new WinResultSearch(dtm, type);
					winResultSearch.setModal(true);
					winResultSearch.setVisible(true);
					
				}
			}
		});
		panel.add(tfSearchWord);
		tfSearchWord.setColumns(10);

	}

	public WinComboSearch(int type) {
		this();
		if(type==0)
			setTitle("콤보에서 선택하여 검색(삭제용)");
		else
			setTitle("콤보에서 선택하여 검색(변경용)");
		this.type = type;
	}

	protected DefaultTableModel extractRecords(String searchColumn, String searchWord) {
		String colmns[] = {"ISBN","제목","저자","출판사"};
		DefaultTableModel dtm = new DefaultTableModel(colmns, 0);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select isbn, title, author, publisher from bookTBL ";
			sql = sql + "where " + searchColumn + "='" + searchWord + "'"; 
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Vector<String> vec = new Vector<>();	
				for(int i=1;i<=colmns.length;i++)
					vec.add(rs.getString(i));
				dtm.addRow(vec);
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
		return dtm;
	}

}
