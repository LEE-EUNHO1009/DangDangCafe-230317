package DogManager;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinResultSearch extends JDialog {
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinResultSearch dialog = new WinResultSearch(null,0);
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
	public WinResultSearch(DefaultTableModel dtm, int type) {
		setTitle("검색 결과");
		setBounds(100, 100, 450, 300);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(dtm);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				int row = table.getSelectedRow();
				if(row != -1) {
					int ret = JOptionPane.showConfirmDialog(null, "선택한 책으로 이동합니다");
					if(ret == JOptionPane.OK_OPTION) {
						dispose();
						if(type==0) {
							WinDogDelete winBookDelete = new WinDogDelete(table.getValueAt(row, 0).toString());
							winBookDelete.setModal(true);
							winBookDelete.setVisible(true);
						}else {
							WinDogUpdate winBookUpdate = new WinDogUpdate(table.getValueAt(row, 0).toString());
							winBookUpdate.setModal(true);
							winBookUpdate.setVisible(true);
						}							
					}
				}
			}
		});
		scrollPane.setViewportView(table);

	}

}
