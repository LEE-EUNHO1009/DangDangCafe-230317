package CustomerManager;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class WinMemberModify extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinMemberModify dialog = new WinMemberModify(3);
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
	 * @param typeModify 
	 */
	public WinMemberModify(int typeModify) {
		setTitle("회원 변경창");
		setBounds(100, 100, 638, 288);
				
		Member member = new Member(typeModify);
		getContentPane().add(member, BorderLayout.CENTER);
	}

}
