package DogManager;

import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import CustomerManager.WinCalendar;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Dog extends JPanel {
	private int type;
	private String file;
	private String originAttrs[] = new String[8];
	private JTextField tfDogName;
	private JTextField tfDogBirth;
	private JTextField tfDogWeight;
	private JTextField tfDogCoupon;
	private JTextField tfDogGuardianName;
	private JTextField tfDogNotandum;
	private JLabel lblDogCount;
	private JLabel taDogNotandum;
	private JLabel lblDogImage;
	private JLabel cbDog;
	private JComponent btnMinor;
	private JComponent btnCalendar;
	private JComponent btnDogCouponAdd;
	private JComponent btnSearchAddReset;
	private JComponent btnCharge;
	private JComponent btnDogGuardian;
	private JComponent btnDogAll;
	
	public Dog() {
		setLayout(null);
		
		JLabel lblDogImage = new JLabel("");
		lblDogImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
						lblDogImage.setIcon(pic);
					}
				}
			}
		});
		lblDogImage.setToolTipText("더블클릭하여 강아지 사진 경로를 입력하시오");
		lblDogImage.setOpaque(true);
		lblDogImage.setBackground(new Color(224, 255, 255));
		lblDogImage.setBounds(12, 10, 150, 200);
		add(lblDogImage);
		
		JLabel lblDogName = new JLabel("이름:");
		lblDogName.setBounds(174, 22, 64, 15);
		add(lblDogName);
		
		JLabel lblDog = new JLabel("견종:");
		lblDog.setBounds(174, 52, 57, 15);
		add(lblDog);
		
		JLabel lblDogBirth = new JLabel("생년월일:");
		lblDogBirth.setBounds(174, 115, 57, 15);
		add(lblDogBirth);
		
		JLabel lblDogCoupon = new JLabel("도장:");
		lblDogCoupon.setBounds(174, 176, 57, 15);
		add(lblDogCoupon);
		
		JLabel lblDogNotandum = new JLabel("주의사항:");
		lblDogNotandum.setBounds(12, 220, 57, 15);
		add(lblDogNotandum);
		
		tfDogName = new JTextField();
		tfDogName.setColumns(10);
		tfDogName.setBounds(242, 19, 116, 21);
		add(tfDogName);
		
		tfDogBirth = new JTextField();
		tfDogBirth.setColumns(10);
		tfDogBirth.setBounds(242, 112, 116, 21);
		add(tfDogBirth);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 240, 520, 325);
		add(scrollPane);
		
		JTextArea taDogNotandum = new JTextArea();
		scrollPane.setViewportView(taDogNotandum);
		
		tfDogNotandum = new JTextField();
		tfDogNotandum.setText("가족 | 신체사항 | 약 | 식사...");
		tfDogNotandum.setEnabled(false);
		scrollPane.setColumnHeaderView(tfDogNotandum);
		tfDogNotandum.setColumns(10);
		
		JButton btnDogAll = new JButton("통합");
		btnDogAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(type==1) { //멤버 추가 sql
					addDog();
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
		btnDogAll.setBounds(370, 172, 75, 49);
		add(btnDogAll);
		
		JButton btnDogCalendar = new JButton("달력보기");
		btnDogCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar winCalendar = new WinCalendar();
				winCalendar.setModal(true);
				winCalendar.setVisible(true);
				tfDogBirth.setText(winCalendar.getDate());
				
				Calendar cal = Calendar.getInstance();
				String strSolar = winCalendar.getDate();
				int year = cal.get(Calendar.YEAR);
				strSolar = year + strSolar.substring(4);
				tfDogBirth.setText(strSolar);
				
				showCountDown();
			}
		});
		btnDogCalendar.setBounds(370, 111, 116, 23);
		add(btnDogCalendar);
		
		JComboBox cbDog = new JComboBox();
		cbDog.setBounds(243, 48, 116, 23);
		add(cbDog);
		
		JLabel lblDogWeight = new JLabel("몸무게:");
		lblDogWeight.setBounds(174, 84, 43, 15);
		add(lblDogWeight);
		
		tfDogWeight = new JTextField();
		tfDogWeight.setColumns(10);
		tfDogWeight.setBounds(242, 81, 116, 21);
		add(tfDogWeight);
		
		JLabel lblDogKg = new JLabel("Kg");
		lblDogKg.setEnabled(false);
		lblDogKg.setBounds(364, 84, 43, 15);
		add(lblDogKg);
		
		JLabel lblDogCount = new JLabel("");
		lblDogCount.setToolTipText("생일 계산");
		lblDogCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblDogCount.setForeground(Color.RED);
		lblDogCount.setBounds(242, 143, 116, 15);
		add(lblDogCount);
		
		JButton btnDogCouponAdd = new JButton("Add");
		btnDogCouponAdd.setBounds(302, 172, 56, 23);
		add(btnDogCouponAdd);
		
		JButton btnDogCouponReset = new JButton("RE");
		btnDogCouponReset.setBounds(302, 195, 56, 23);
		add(btnDogCouponReset);
		
		tfDogCoupon = new JTextField();
		tfDogCoupon.setColumns(10);
		tfDogCoupon.setBounds(243, 173, 57, 21);
		add(tfDogCoupon);
		
		JLabel lblDogGuardianName = new JLabel("보호자명:");
		lblDogGuardianName.setBounds(370, 22, 57, 15);
		add(lblDogGuardianName);
		
		tfDogGuardianName = new JTextField();
		tfDogGuardianName.setColumns(10);
		tfDogGuardianName.setBounds(426, 19, 106, 21);
		add(tfDogGuardianName);
		
		JLabel lblCouponMax = new JLabel("");
		lblCouponMax.setToolTipText("10개 도장 되면 리셋");
		lblCouponMax.setForeground(Color.RED);
		lblCouponMax.setBounds(242, 199, 57, 15);
		add(lblCouponMax);
		
		JButton btnCharge = new JButton("결제");
		btnCharge.setBounds(445, 172, 75, 49);
		add(btnCharge);
		
		JButton btnDogGuardian = new JButton("보호자정보");
		btnDogGuardian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DogGuardian dogGuardian = new DogGuardian();
				dogGuardian.setModal(true);
				dogGuardian.setVisible(true);
				
				showMember(tfDogGuardianName.getText());
			}
		});
		btnDogGuardian.setBounds(370, 48, 162, 23);
		add(btnDogGuardian);
		
		JButton btnMinor = new JButton("미성년자 동의서");
		btnMinor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DogMinor dogMinor = new DogMinor();
				dogMinor.setModal(true);
				dogMinor.setVisible(true);
			}
		});
		btnMinor.setBounds(370, 144, 150, 23);
		add(btnMinor);
		
	}

	protected void modifyRecord() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "update dogTBL set guardian=?, class=?, weight=?, ";
			sql = sql +  "birth=?, coupon=?, notandum=?, image=? where name=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tfDogGuardianName.getText());
			pstmt.setString(2, cbDog.getText());	
			pstmt.setString(3, tfDogWeight.getText());
			pstmt.setString(4, tfDogBirth.getText());
			pstmt.setString(5, tfDogCoupon.getText());
			pstmt.setString(6, taDogNotandum.getText());
			pstmt.setString(7, file);
			pstmt.setString(8, tfDogName.getText());
			
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
		if(!originAttrs[0].equals(tfDogName.getText())) {
			originAttrs[0] = tfDogName.getText();
			return true;
		}else if(!originAttrs[1].equals(tfDogGuardianName.getText())) {
			originAttrs[1] = tfDogGuardianName.getText();
			return true;
		}else if(!originAttrs[2].equals(cbDog.getText())) {
			originAttrs[2] = cbDog.getText();
			return true;
		}else if(!originAttrs[3].equals(tfDogWeight.getText())) {
			originAttrs[3] = tfDogWeight.getText();
			return true;
		}else if(!originAttrs[4].equals(tfDogBirth.getText())) {
			originAttrs[4] = tfDogBirth.getText();
			return true;
		}else if(!originAttrs[5].equals(tfDogCoupon.getText())) {
			originAttrs[5] = tfDogCoupon.getText();
			return true;
		}else if(!originAttrs[6].equals(taDogNotandum.getText())) {
			originAttrs[6] = taDogNotandum.getText();
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
			String sql = "delete from dogTBL where id='" + tfDogName.getText() + "'";
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

	protected void showMember(String gID) {
		clearInfo();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select * from dogTBL where id='" + gID + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int idx=0;
			if(rs.next()) {
				for(idx=0 ; idx < originAttrs.length ; idx++)
					originAttrs[idx] = rs.getString(idx+1);
				
				//=====================================================
				tfDogName.setText(rs.getString(1));
				cbDog.setText(rs.getString(2));
				tfDogGuardianName.setText(rs.getString(3));
				tfDogGuardianName.setText(gID);
				tfDogWeight.setText(rs.getString(5));
				tfDogBirth.setText(rs.getString(6));
				tfDogCoupon.setText(rs.getString(7));
				taDogNotandum.setText(rs.getString(8));
				//if(rs.getString(7).equals("0")) {
				//	ckLunar.setSelected(false);
				//	tfBirthSolar.setText(tfBirthLunar.getText());
				//}else {
				//	ckLunar.setSelected(true);
				//}
				file = rs.getString(9);
				ImageIcon icon = new ImageIcon(file);
				Image image = icon.getImage();
				image = image.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
				ImageIcon pic = new ImageIcon(image);
				lblDogImage.setIcon(pic);
				
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

	private void clearInfo() {
		tfDogName.setText("");
		tfDogGuardianName.setText("");
		cbDog.setText("");
		tfDogWeight.setText("");
		tfDogBirth.setText("");
		tfDogCoupon.setText("");
		taDogNotandum.setText("");
		file = "";
		
		tfDogName.requestFocus();
		
	}

	protected void addDog() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "insert into dogTBL values(?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tfDogName.getText());
			pstmt.setString(2, tfDogGuardianName.getText());
			pstmt.setString(3, cbDog.getText());		
			pstmt.setString(4, tfDogWeight.getText());
			pstmt.setString(5, tfDogBirth.getText());
			pstmt.setString(6, tfDogCoupon.getText());
			pstmt.setString(7, taDogNotandum.getText());
			//if(ckLunar.isSelected())
			//	pstmt.setString(7, "1");  // 음력
			//else
			//	pstmt.setString(7, "0");  // 양력				
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

	public Dog(String[] temp) {
		this();		
		tfDogName.setText(temp[0]);
		tfDogGuardianName.setText(temp[1]);
		cbDog.setText(temp[2]);
		tfDogWeight.setText(temp[3]);
		tfDogBirth.setText(temp[4]);
		tfDogCoupon.setText(temp[5]);
		taDogNotandum.setText(temp[6]);
		//if(temp[6].equals("1"))
		//	ckLunar.setSelected(true);
		//else
		//	ckLunar.setSelected(false);
		
		file = temp[7];
		ImageIcon icon = new ImageIcon(file);
		Image image = icon.getImage();
		image = image.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
		ImageIcon pic = new ImageIcon(image);
		lblDogImage.setIcon(pic);
	}

	

	public Dog(int dType) {
		this();

		//type = sType;
		type = dType;
		
		if(type == 1) { // 추가
			btnDogGuardian.setVisible(false);
			btnDogAll.setToolTipText("댕댕이 추가");
		}else if(type==2) { //삭제
			btnMinor.setVisible(false);
			btnCalendar.setVisible(false);
			btnDogCouponAdd.setVisible(false);	
			btnSearchAddReset.setVisible(false);	
			btnCharge.setVisible(false);	
			lblDogImage.setToolTipText(null);
			btnDogAll.setToolTipText("댕댕이 탈퇴");			
		}else if(type==3){ //변경
			btnDogAll.setToolTipText("댕댕이 변경");
		}else {
			btnMinor.setVisible(false);
			btnCalendar.setVisible(false);
			btnDogCouponAdd.setVisible(false);	
			btnSearchAddReset.setVisible(false);	
			btnCharge.setVisible(false);	
			btnDogGuardian.setVisible(false);
			btnDogAll.setVisible(false);
		}
	}

	protected void showCountDown() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select datediff('" + tfDogBirth.getText() + "', now()) as CD";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				if(rs.getInt("CD")==0)
					lblDogCount.setText("생일 축하!!!");
				else
					lblDogCount.setText(rs.getInt("CD")+"일 남음");
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
}
