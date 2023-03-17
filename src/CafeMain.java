import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import CustomerManager.WinMemberAdd;
import CustomerManager.WinMemberAllShow;
import CustomerManager.WinMemberModify;
import CustomerManager.WinMemberRemove;
import DogManager.WinDogAllShow;
import DogManager.WinDogUpdate;
import DogManager.WinComboSearch;
import DogManager.WinDogDelete;
import DogManager.WinDogInsert;

public class CafeMain extends JDialog {
	private static JTable table;
	private static JLabel lblISBN;
	private JTextField tfSearch;
	private final int typeAdd = 1;
	private final int typeRemove = 2;
	private final int typeModify = 3;
	private final int typeSelect = 4;
	private final int DogAdd = 5;
	private final int DogRemove = 6;
	private final int DogModify = 7;
	private final int DogSelect = 8;
	private JProgressBar progressBar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CafeMain dialog = new CafeMain();
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
	public CafeMain() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				showTable();
			}
			public void windowLostFocus(WindowEvent e) {
			}
		});
		setTitle("댕댕이 카페(소형, 중형)");
		setBounds(100, 100, 833, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnuFile = new JMenu("파일(F)");
		mnuFile.setMnemonic('F');
		menuBar.add(mnuFile);
		
		JMenuItem mnuExit = new JMenuItem("종료(X)");
		mnuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_DOWN_MASK));
		mnuExit.setMnemonic('X');
		mnuFile.add(mnuExit);
		
		JMenu mnuBook = new JMenu("멍뭉이(D)");
		mnuBook.setMnemonic('D');
		menuBar.add(mnuBook);
		
		JMenuItem mnuDogAdd = new JMenuItem("멍뭉이 추가(A)...");
		mnuDogAdd.setMnemonic('A');
		mnuDogAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinDogInsert winDogInsert = new WinDogInsert(DogAdd);
				winDogInsert.setModal(true);
				winDogInsert.setVisible(true);
			}
		});
		mnuDogAdd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		mnuBook.add(mnuDogAdd);
		
		JMenuItem mnuDogRemove = new JMenuItem("멍뭉이 삭제...");
		mnuDogRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1) {
					WinComboSearch winComboSearch = new WinComboSearch(0); // 0:삭제 1:변경
					winComboSearch.setModal(true);
					winComboSearch.setVisible(true);
				}else {
					WinDogDelete winDogDelete = 
							new WinDogDelete(table.getValueAt(table.getSelectedRow(), 0).toString());
					winDogDelete.setModal(true);
					winDogDelete.setVisible(true);
				}				
			}
		});
		mnuBook.add(mnuDogRemove);
		
		JMenuItem mnuDogModify = new JMenuItem("멍뭉이 정보 변경...");
		mnuDogModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1) {
					WinComboSearch winComboSearch = new WinComboSearch(1); // 0:삭제 1:변경
					winComboSearch.setModal(true);
					winComboSearch.setVisible(true);
				}else {
					WinDogUpdate winDogUpdate = 
							new WinDogUpdate(table.getValueAt(table.getSelectedRow(), 0).toString());
					winDogUpdate.setModal(true);
					winDogUpdate.setVisible(true);
				}
			}
		});
		mnuBook.add(mnuDogModify);
		
		mnuBook.addSeparator();
		
		JMenuItem mnuDogDetailShow = new JMenuItem("멍뭉이 상세보기...");
		mnuDogDetailShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinDogAllShow winDogAllShow = new WinDogAllShow();
				//winDogAllShow.setModal(true);
				winDogAllShow.setVisible(true);
			}
		});
		mnuBook.add(mnuDogDetailShow);
		
		JMenu mnuCustomer = new JMenu("일반 고객(M)");
		mnuCustomer.setMnemonic('M');
		menuBar.add(mnuCustomer);
		
		JMenuItem mnuCustomerAdd = new JMenuItem("고객 추가...");
		mnuCustomerAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberAdd winMemberAdd = new WinMemberAdd(typeAdd);
				winMemberAdd.setModal(true);
				winMemberAdd.setVisible(true);
			}
		});
		mnuCustomer.add(mnuCustomerAdd);
		
		JMenuItem mnuCustomerRemove = new JMenuItem("고객 삭제...");
		mnuCustomerRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberRemove winMemberRemove = new WinMemberRemove(typeRemove);
				winMemberRemove.setModal(true);
				winMemberRemove.setVisible(true);
			}
		});
		mnuCustomer.add(mnuCustomerRemove);
		
		JMenuItem mnuCustomerModify = new JMenuItem("고객 정보 변경...");
		mnuCustomerModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberModify winMemberModify = new WinMemberModify(typeModify);
				winMemberModify.setModal(true);
				winMemberModify.setVisible(true);				
			}
		});
		mnuCustomer.add(mnuCustomerModify);
		
		mnuCustomer.addSeparator();
		
		JMenuItem mnuCustomerDetailShow = new JMenuItem("고객 전체 보기...");
		mnuCustomer.add(mnuCustomerDetailShow);
		
		JMenu mnuCharge = new JMenu("결제(C)");
		mnuCharge.setMnemonic('C');
		menuBar.add(mnuCharge);
		
		JMenuItem mnuCharge2 = new JMenuItem("결제");
		mnuCharge.add(mnuCharge2);
		
		JMenu mnuHelp = new JMenu("도움말(H)");
		mnuHelp.setMnemonic('H');
		menuBar.add(mnuHelp);
		
		JMenuItem mnuHelp2 = new JMenuItem("Help...");
		mnuHelp2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinHelp winHelp = new WinHelp();
				winHelp.setModal(true);
				winHelp.setVisible(true);
			}
		});
		mnuHelp2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mnuHelp2.setMnemonic('p');
		mnuHelp.add(mnuHelp2);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnDogAdd = new JButton("");
		btnDogAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinDogInsert winDogInsert = new WinDogInsert(DogAdd);
				winDogInsert.setModal(true);
				winDogInsert.setVisible(true);
			}
		});
		btnDogAdd.setIcon(new ImageIcon(CafeMain.class.getResource("/image/add.png")));
		toolBar.add(btnDogAdd);
		
		JButton btnDogRemove = new JButton("");
		btnDogRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1) {
					WinComboSearch winComboSearch = new WinComboSearch(0); // 0:삭제 1:변경
					winComboSearch.setModal(true);
					winComboSearch.setVisible(true);
				}else {
					int rowIndex[] = table.getSelectedRows(); // 선택된 레코드들을 저장하는 배열				
					
					if(rowIndex.length > 1) {
						for(int i=0;i<rowIndex.length;i++) {
							WinDogDelete winDogDelete = 
									new WinDogDelete(table.getValueAt(rowIndex[i], 0).toString());
							winDogDelete.setModal(true);
							winDogDelete.setVisible(true);
						}
					}else {				
						WinDogDelete winDogDelete = 
								new WinDogDelete(table.getValueAt(table.getSelectedRow(), 0).toString());
						winDogDelete.setModal(true);
						winDogDelete.setVisible(true);
					}
				}
			}
		});
		btnDogRemove.setIcon(new ImageIcon(CafeMain.class.getResource("/image/remove.png")));
		toolBar.add(btnDogRemove);
		
		JButton btnDogModify = new JButton("");
		btnDogModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1) {
					WinComboSearch winComboSearch = new WinComboSearch(1); // 0:삭제 1:변경
					winComboSearch.setModal(true);
					winComboSearch.setVisible(true);
				}else {
					WinDogUpdate winDogUpdate = 
							new WinDogUpdate(table.getValueAt(table.getSelectedRow(), 0).toString());
					winDogUpdate.setModal(true);
					winDogUpdate.setVisible(true);
				}
			}
		});
		btnDogModify.setIcon(new ImageIcon(CafeMain.class.getResource("/image/update.png")));
		toolBar.add(btnDogModify);
		
		JButton btnDogSelect = new JButton("");
		btnDogSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinDogAllShow winDogAllShow = new WinDogAllShow();
				//winDogAllShow.setModal(true);
				winDogAllShow.setVisible(true);
			}
		});
		btnDogSelect.setIcon(new ImageIcon(CafeMain.class.getResource("/image/search.png")));
		toolBar.add(btnDogSelect);
		
		toolBar.addSeparator();
		
		JComboBox cbSearch = new JComboBox();
		cbSearch.setModel(new DefaultComboBoxModel(new String[] {"강아지이름", "보호자명", "일반고객이름", "전화번호", "견종"}));
		toolBar.add(cbSearch);
		
		tfSearch = new JTextField();
		tfSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					showSearchResult(cbSearch.getSelectedItem().toString(), tfSearch.getText());
				}
			}
		});
		tfSearch.setText("단어입력");
		tfSearch.setFont(new Font("굴림", Font.BOLD, 20));
		toolBar.add(tfSearch);
		tfSearch.setColumns(10);
		
		toolBar.addSeparator();
		
		JButton btnMemberAdd = new JButton("");
		btnMemberAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberAdd winMemberAdd = new WinMemberAdd(typeAdd);
				winMemberAdd.setModal(true);
				winMemberAdd.setVisible(true);
			}
		});
		btnMemberAdd.setIcon(new ImageIcon(CafeMain.class.getResource("/image/memberAdd.png")));
		toolBar.add(btnMemberAdd);
		
		JButton btnMemberRemove = new JButton("");
		btnMemberRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberRemove winMemberRemove = new WinMemberRemove(typeRemove);
				winMemberRemove.setModal(true);
				winMemberRemove.setVisible(true);
			}
		});
		btnMemberRemove.setIcon(new ImageIcon(CafeMain.class.getResource("/image/memberRemove.png")));
		toolBar.add(btnMemberRemove);
		
		JButton btnMemberModify = new JButton("");
		btnMemberModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberModify winMemberModify = new WinMemberModify(typeModify);
				winMemberModify.setModal(true);
				winMemberModify.setVisible(true);
			}
		});
		btnMemberModify.setIcon(new ImageIcon(CafeMain.class.getResource("/image/memberUpdate.png")));
		toolBar.add(btnMemberModify);
		
		JButton btnMemberSelect = new JButton("");
		btnMemberSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberAllShow winMemberAllShow = new WinMemberAllShow();
				winMemberAllShow.setModal(true);
				winMemberAllShow.setVisible(true);
			}
		});
		btnMemberSelect.setIcon(new ImageIcon(CafeMain.class.getResource("/image/memberSearch.png")));
		toolBar.add(btnMemberSelect);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		String columnNames[] = {"ISBN","제목","저자","출판사","출판일","가격","수량"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}			
		};
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		panel.add(progressBar);
		
		lblISBN = new JLabel("현재 선택한 ISBN:");
		panel.add(lblISBN);
		
		
		//showTable();
	}

	protected void insertList(String sID, String sISBN) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "insert into listTBL values(null,'";
			sql = sql + sID + "','" + sISBN + "', curDate())";
			Statement stmt = con.createStatement();
			System.out.println(sql);
			
			stmt.executeUpdate(sql);
			
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}

	protected void showSearchResult(String sItem, String txtSearch) {
		// TODO Auto-generated method stub
		String sql="";
		int choice=0;
		if(sItem.equals("제목")) {
			sql = "select * from bookTBL where title like '%" + txtSearch + "%'";
			choice=0;
		}else if(sItem.equals("저자")) {
			sql = "select * from bookTBL where author like '%" + txtSearch + "%'";
			choice=0;
		}else if(sItem.equals("출판사")) {
			sql = "select * from bookTBL where publisher like '%" + txtSearch + "%'";
			choice=0;
		}else if(sItem.equals("회원이름")) {
			sql = "select * from memberTBL where name like '%" + txtSearch + "%'";
			choice=1;
		}else if(sItem.equals("전화번호")) {
			sql = "select * from memberTBL where mobile like '%" + txtSearch + "%'";
			choice=1;
		}		
		if(sItem.equals("제목") || sItem.equals("저자") || sItem.equals("출판사")) {
			String columnNames1[] = {"ISBN","제목","저자","출판사","출판일","가격","수량"};		
			DefaultTableModel dtm = new DefaultTableModel(columnNames1, 0);
			table.setModel(dtm);
		}else {
			String columnNames2[] = {"ID","회원명","전화번호","주소"};
			DefaultTableModel dtm = new DefaultTableModel(columnNames2,0);
			table.setModel(dtm);			
		}
		showResultRecords(sql, choice);
		
	}

	private void showResultRecords(String sql, int choice) {
		// TODO Auto-generated method stub
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				
			}

			@Override
			protected Void doInBackground() throws Exception {
				// TODO Auto-generated method stub
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = 
							DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
					
					//=============================================		
					String sql2 = sql;					
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sql2);
					int total = 0;
					while(rs.next())
						total++;
					
					rs = stmt.executeQuery(sql);
					int count = 0;
					
					DefaultTableModel dtm = (DefaultTableModel)table.getModel();
					dtm.setRowCount(0);
					
					while(rs.next()) {
						count++;
						Vector <String> vec = new Vector<>();
						if(choice==0) {
							for(int i=1;i<=9;i++) {
								if(i!=6 && i!=8)
									vec.add(rs.getString(i));
							}
						}else {
							for(int i=1;i<=5;i++) {
								if(i!=2)
									vec.add(rs.getString(i));
							}
						}
						dtm.addRow(vec);
						Thread.sleep(10);
						progressBar.setValue(count*100/total);
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
				
				return null;
			} 
		};
		worker.execute();		
	}

	public CafeMain(DefaultTableModel dtm) {
		// TODO Auto-generated constructor stub
		this();
		table.setModel(dtm);
	}

	private void showTable() {	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "select * from bookTBL";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			String columnNames1[] = {"ISBN","제목","저자","출판사","출판일","가격","수량"};		
			DefaultTableModel dtm = new DefaultTableModel(columnNames1, 0);
			table.setModel(dtm);
			
			while(rs.next()) {
				Vector <String> vec = new Vector<>();
				for(int i=1;i<=9;i++) {
					if(i!=6 && i!=8)
						vec.add(rs.getString(i));
				}
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
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					JTable source = (JTable)e.getSource();
					int row = source.rowAtPoint(e.getPoint());
					int col = source.columnAtPoint(e.getPoint());
					if(!source.isRowSelected(row))
						source.changeSelection(row, col, false, false);
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					WinDogUpdate winBookUpdate = 
							new WinDogUpdate(table.getValueAt(table.getSelectedRow(), 0).toString());
					winBookUpdate.setModal(true);
					winBookUpdate.setVisible(true);
				}else {
					lblISBN.setText("현재 선택한 제목: " + table.getValueAt(table.getSelectedRow(), 1).toString() );
				}
			}
		});
	}
}
