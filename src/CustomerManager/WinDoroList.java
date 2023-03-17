package CustomerManager;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinDoroList extends JDialog {

	String retAddress;  // 주소를 저장하는 변수
	
	public String getAddress() { // 선택된 주소를 반환하는 함수
		return retAddress;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinDoroList dialog = new WinDoroList("세종대로");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JList list;

	/**
	 * Create the dialog.
	 */
	public WinDoroList() {
		setTitle("도로명 검색 결과");
		setBounds(100, 100, 434, 318);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				retAddress = list.getSelectedValue().toString();
				setVisible(false);
				dispose();
			}
		});
		list.setFont(new Font("굴림", Font.PLAIN, 15));
		scrollPane.setViewportView(list);

	}

	public WinDoroList(String doro) {
		// TODO Auto-generated constructor stub
		this();
		showDoroList(doro);
	}

	private void showDoroList(String doro) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");	
			
			//=============================================		
			String sql = "select * from addressTBL where road ='" + doro + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			Vector<String> vec = new Vector<>();
			while(rs.next()) {				
				vec.add(rs.getString("si") + " " + rs.getString("gu") + " " + rs.getString("dong"));				
			}			
			list.setListData(vec);
			
			stmt.close();
			rs.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		}			
	}

}
