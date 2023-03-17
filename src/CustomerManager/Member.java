package CustomerManager;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSpinner;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.im.InputContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Vector;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Member extends JPanel {
	private JTextField tfID;
	private JTextField tfName;
	private JTextField tfMobile;
	private JTextField tfAddress;
	private JTextField tfBirthSolar;
	private JPasswordField passwordPW1;
	private JPasswordField passwordPW2;
	private JTextField tfBirthLunar;
	private JButton btnSearchAddress;
	private JButton btnDup;
	private JButton btnCalendar;
	private int type;
	private JLabel lblImage;
	private JLabel lblCountDown;
	private JCheckBox ckLunar;
	private String file;
	private JButton btnIdSearch;
	private String originAttrs[] = new String[8];
	private JButton btnAll;
	
	public Member() {
		setLayout(null);
		
		lblImage = new JLabel("");
		lblImage.addMouseListener(new MouseAdapter() {
			

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					if(type % 2 == 1 ) {  // 삭제/조회가 아니라면 열기 대화상자를 열어 사진을 선택한다.						
						JFileChooser chooser = new JFileChooser();
						FileNameExtensionFilter filter = 
								new FileNameExtensionFilter("그림파일", "png", "gif", "jpg");
						chooser.setFileFilter(filter);
						
						int ret = chooser.showOpenDialog(null);
						if(ret == JFileChooser.APPROVE_OPTION) {
							file = chooser.getSelectedFile().getPath();
							ImageIcon icon = new ImageIcon(file);
							Image image = icon.getImage();
							image = image.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
							ImageIcon pic = new ImageIcon(image);
							lblImage.setIcon(pic);
						}
					}
				}
			}
		});
		lblImage.setToolTipText("더블클릭하여 사진을 선택하시오");
		lblImage.setOpaque(true);
		lblImage.setBackground(Color.YELLOW);
		lblImage.setBounds(12, 10, 150, 180);
		add(lblImage);
		
		JLabel lblId = new JLabel("아이디:");
		lblId.setBounds(174, 22, 57, 15);
		add(lblId);
		
		JLabel lblPw = new JLabel("비밀번호:");
		lblPw.setBounds(174, 52, 57, 15);
		add(lblPw);
		
		JLabel lblName = new JLabel("이름:");
		lblName.setBounds(174, 82, 57, 15);
		add(lblName);
		
		JLabel lblMobile = new JLabel("전화번호:");
		lblMobile.setBounds(174, 112, 57, 15);
		add(lblMobile);
		
		JLabel lblAddress = new JLabel("주소:");
		lblAddress.setBounds(174, 142, 57, 15);
		add(lblAddress);
		
		JLabel lblBirth = new JLabel("생일:");
		lblBirth.setBounds(174, 172, 57, 15);
		add(lblBirth);
		
		tfID = new JTextField();
		tfID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				 try{
                   InputContext inCtx = tfID.getInputContext(); // comp는 text component
                   Character.Subset[] subset = null;
                   inCtx.setCharacterSubsets( subset );
                  }catch(Exception ee) { }
			}
		});
		tfID.setColumns(10);
		tfID.setBounds(242, 19, 116, 21);
		add(tfID);
		
		tfName = new JTextField();
		tfName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				try{
                   InputContext inCtx2 = tfName.getInputContext(); // comp는 text component
                   Character.Subset[] subset2 = { Character.UnicodeBlock.HANGUL_SYLLABLES  };
                   inCtx2.setCharacterSubsets( subset2 );
                  }catch(Exception ee) { ee.printStackTrace(); }
			}
		});
		tfName.setColumns(10);
		tfName.setBounds(242, 79, 116, 21);
		add(tfName);
		
		tfMobile = new JTextField();
		tfMobile.setColumns(10);
		tfMobile.setBounds(242, 109, 116, 21);
		add(tfMobile);
		
		tfAddress = new JTextField();
		tfAddress.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				try{
                   InputContext inCtx2 = tfAddress.getInputContext(); // comp는 text component
                   Character.Subset[] subset2 = { Character.UnicodeBlock.HANGUL_SYLLABLES  };
                   inCtx2.setCharacterSubsets( subset2 );
                  }catch(Exception ee) { ee.printStackTrace(); }
			}
		});
		tfAddress.setColumns(10);
		tfAddress.setBounds(242, 139, 271, 21);
		add(tfAddress);
		
		tfBirthSolar = new JTextField();
		tfBirthSolar.setBackground(new Color(255, 255, 0));
		tfBirthSolar.setHorizontalAlignment(SwingConstants.RIGHT);
		tfBirthSolar.setColumns(10);
		tfBirthSolar.setBounds(481, 169, 116, 21);
		add(tfBirthSolar);
		
		passwordPW1 = new JPasswordField();
		passwordPW1.setBounds(243, 49, 115, 21);
		add(passwordPW1);
		
		btnSearchAddress = new JButton("...");
		btnSearchAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String doro = JOptionPane.showInputDialog("도로명 입력:");
				WinDoroList winDoroList = new WinDoroList(doro);
				winDoroList.setModal(true);
				winDoroList.setVisible(true);
				tfAddress.setText(winDoroList.getAddress());
				tfAddress.requestFocus();  // 커서를 주는 이유는 상세 주소를 입력하기 위해서
			}
		});
		btnSearchAddress.setBounds(517, 138, 43, 23);
		add(btnSearchAddress);
		
		btnDup = new JButton("중복확인");
		btnDup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isDup())
					passwordPW1.requestFocus();
				else {
					JOptionPane.showMessageDialog(null, "아이디가 존재합니다", "중복오류", JOptionPane.ERROR_MESSAGE);
					tfID.setText("");
					tfID.requestFocus();
				}
			}
		});
		btnDup.setBounds(370, 18, 93, 23);
		add(btnDup);
		
		passwordPW2 = new JPasswordField();
		passwordPW2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(new String(passwordPW1.getPassword()).equals(new String(passwordPW2.getPassword()))) {
						tfName.setEnabled(true);
						tfName.requestFocus();
					}						
				}
			}
		});
		passwordPW2.setBounds(461, 49, 115, 21);
		add(passwordPW2);
		
		JLabel lblConfirm = new JLabel("비밀번호확인:");
		lblConfirm.setBounds(376, 52, 87, 15);
		add(lblConfirm);
		
		btnCalendar = new JButton("...");
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar winCalendar = new WinCalendar();
				winCalendar.setModal(true);
				winCalendar.setVisible(true);
				tfBirthLunar.setText(winCalendar.getDate());
				
				Calendar cal = Calendar.getInstance();
				String strSolar = winCalendar.getDate();
				int year = cal.get(Calendar.YEAR);
				strSolar = year + strSolar.substring(4);
				tfBirthSolar.setText(strSolar);
				
				showCountDown();				
			}
		});
		btnCalendar.setBounds(370, 168, 43, 23);
		add(btnCalendar);
		
		ckLunar = new JCheckBox("음력");
		ckLunar.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				
				if(ckLunar.isSelected()) { // 음력=>양력
					String strLunar = tfBirthLunar.getText();
					strLunar = strLunar.replaceAll("-", "");					
					strLunar = year + strLunar.substring(4, 6) + strLunar.substring(6);
					SolarLunar sl = new SolarLunar();
					String strSolar = sl.fromLunar(strLunar);
					strSolar = year + "-" + strSolar.substring(4, 6) + "-" + strSolar.substring(6);
					tfBirthSolar.setText(strSolar);
					
				}else { // 양력=>음력					
					String strSolar = tfBirthLunar.getText();
					strSolar = year + strSolar.substring(4);
					tfBirthSolar.setText(strSolar);
				}				
				showCountDown();
			}
		});
		ckLunar.setBounds(421, 168, 57, 23);
		add(ckLunar);
		
		tfBirthLunar = new JTextField();
		tfBirthLunar.setHorizontalAlignment(SwingConstants.RIGHT);
		tfBirthLunar.setColumns(10);
		tfBirthLunar.setBounds(243, 169, 116, 21);
		add(tfBirthLunar);
		
		lblCountDown = new JLabel("");
		lblCountDown.setForeground(new Color(255, 0, 0));
		lblCountDown.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountDown.setFont(new Font("굴림", Font.BOLD, 20));
		lblCountDown.setBounds(481, 93, 116, 34);
		add(lblCountDown);
		
		btnIdSearch = new JButton("ID찾기");
		btnIdSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMember(tfID.getText());
			}
		});
		btnIdSearch.setBounds(479, 18, 93, 23);
		add(btnIdSearch);
		
		btnAll = new JButton("통합");
		btnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(type==1) { //멤버 추가 sql
					addMember();
					clearInfo();
				}else if(type==2) { // 멤버 삭제 sql
					if(JOptionPane.showConfirmDialog(null, "정말 삭제할까요?") == JOptionPane.YES_OPTION) {
						removeRecord();
						clearInfo();
					}
				}else if(type==3) { // 멤버 변경 sql
					if(isModify()) {
						modifyRecord();
						System.out.println("변경O");
					}
					else
						System.out.println("변경X");
				}else {
					;
				}
			}
		});
		btnAll.setBounds(370, 78, 93, 49);
		add(btnAll);
	}
	protected void modifyRecord() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "update memberTBL set password=?, name=?, mobile=?, ";
			sql = sql +  "address=?, birth=?, lunar=?, image=? where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(passwordPW1.getPassword()));
			pstmt.setString(2, tfName.getText());
			pstmt.setString(3, tfMobile.getText());
			pstmt.setString(4, tfAddress.getText());
			pstmt.setString(5, tfBirthLunar.getText());
			if(ckLunar.isSelected())
				pstmt.setString(6, "1");
			else
				pstmt.setString(6, "0");
			pstmt.setString(7, file);
			pstmt.setString(8, tfID.getText());
			
			pstmt.executeUpdate();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 
		
	}
	protected boolean isModify() {
		if(!originAttrs[0].equals(tfID.getText())) {
			originAttrs[0] = tfID.getText();
			return true;
		}else if(!originAttrs[1].equals(String.valueOf(passwordPW1.getPassword()))) {
			originAttrs[1] = String.valueOf(passwordPW1.getPassword());
			return true;
		}else if(!originAttrs[2].equals(tfName.getText())) {
			originAttrs[2] = tfName.getText();
			return true;
		}else if(!originAttrs[3].equals(tfMobile.getText())) {
			originAttrs[3] = tfMobile.getText();
			return true;
		}else if(!originAttrs[4].equals(tfAddress.getText())) {
			originAttrs[4] = tfAddress.getText();
			return true;
		}else if(!originAttrs[5].equals(tfBirthLunar.getText())) {
			originAttrs[5] = tfBirthLunar.getText();
			return true;
		}else if(!originAttrs[6].equals(ckLunar.isSelected() ? "1" : "0")) {
			originAttrs[6] = ckLunar.isSelected() ? "1" : "0";
			return true;
		}else if(!originAttrs[7].equals(file))
			return true;
		else
			return false;		
	}
	protected void removeRecord() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "delete from memberTBL where id='" + tfID.getText() + "'";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 		
	}
	protected void showMember(String sID) {
		clearInfo();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select * from memberTBL where id='" + sID + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int idx=0;
			if(rs.next()) {
				for(idx=0 ; idx < originAttrs.length ; idx++)
					originAttrs[idx] = rs.getString(idx+1);
				
				//=====================================================
				tfID.setText(sID);
				passwordPW1.setText(rs.getString(2));
				passwordPW2.setText(rs.getString(2));
				tfName.setText(rs.getString(3));
				tfMobile.setText(rs.getString(4));
				tfAddress.setText(rs.getString(5));
				tfBirthLunar.setText(rs.getString(6));
				if(rs.getString(7).equals("0")) {
					ckLunar.setSelected(false);
					tfBirthSolar.setText(tfBirthLunar.getText());
				}else {
					ckLunar.setSelected(true);
				}
				file = rs.getString(8);
				ImageIcon icon = new ImageIcon(file);
				Image image = icon.getImage();
				image = image.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
				ImageIcon pic = new ImageIcon(image);
				lblImage.setIcon(pic);
				
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
	protected boolean isDup() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select * from memberTBL where id='" + tfID.getText() + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
				return true;			
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 
		return false;
	}
	protected void clearInfo() {
		// TODO Auto-generated method stub
		tfID.setText("");
		passwordPW1.setText("");
		passwordPW2.setText("");
		tfName.setText("");
		tfMobile.setText("");
		tfAddress.setText("");
		tfBirthLunar.setText("");
		tfBirthSolar.setText("");
		lblCountDown.setText("");
		ckLunar.setSelected(false);
		file = "";
		
		tfID.requestFocus();
	}
	protected void addMember() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "insert into memberTBL values(?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tfID.getText());
			pstmt.setString(2, String.valueOf(passwordPW1.getPassword()));
			pstmt.setString(3, tfName.getText());
			pstmt.setString(4, tfMobile.getText());
			pstmt.setString(5, tfAddress.getText());
			pstmt.setString(6, tfBirthLunar.getText());
			if(ckLunar.isSelected())
				pstmt.setString(7, "1");  // 음력
			else
				pstmt.setString(7, "0");  // 양력				
			pstmt.setString(8, file);
			pstmt.executeUpdate();
			pstmt.close();
			
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 
		
	}
	protected void showCountDown() {		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select datediff('" + tfBirthSolar.getText() + "', now()) as CD";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				if(rs.getInt("CD")==0)
					lblCountDown.setText("생일 축하!!!");
				else
					lblCountDown.setText(rs.getInt("CD")+"일 남음");
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
	public Member(int sType) {
		// TODO Auto-generated constructor stub
		this();

		type = sType;
		
		if(type == 1) { // 추가
			btnIdSearch.setVisible(false);
			btnAll.setText("회원 추가");
		}else if(type==2) { //삭제
			btnDup.setVisible(false);
			btnCalendar.setVisible(false);
			btnSearchAddress.setVisible(false);	
			lblImage.setToolTipText(null);
			btnAll.setText("회원 탈퇴");			
		}else if(type==3){ //변경
			btnAll.setText("회원 변경");
		}else {
			btnDup.setVisible(false);
			btnCalendar.setVisible(false);
			btnSearchAddress.setVisible(false);
			btnIdSearch.setVisible(false);
			btnAll.setVisible(false);
		}
	}
	
	public Member(String[] temp) {
		this(4);		
		tfID.setText(temp[0]);
		passwordPW1.setText(temp[1]);
		passwordPW2.setText(temp[1]);
		tfName.setText(temp[2]);
		tfMobile.setText(temp[3]);
		tfAddress.setText(temp[4]);
		tfBirthLunar.setText(temp[5]);
		if(temp[6].equals("1"))
			ckLunar.setSelected(true);
		else
			ckLunar.setSelected(false);
		
		file = temp[7];
		ImageIcon icon = new ImageIcon(file);
		Image image = icon.getImage();
		image = image.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
		ImageIcon pic = new ImageIcon(image);
		lblImage.setIcon(pic);
	}
}
