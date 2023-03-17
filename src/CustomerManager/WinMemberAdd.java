package CustomerManager;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class WinMemberAdd extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinMemberAdd dialog = new WinMemberAdd(1);
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
	 * @param typeAdd 
	 */
	public WinMemberAdd(int typeAdd) {
		setTitle("회원 가입창");
		setBounds(100, 100, 638, 288);
				
		Member member = new Member(typeAdd);
		getContentPane().add(member, BorderLayout.CENTER);
	}

}
