package DogManager;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WinInputDialog extends JDialog {
	private JTextField tfUrl;
	private String sUrl;
	
	public String getImageUrl() {
		return sUrl;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinInputDialog dialog = new WinInputDialog();
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
	public WinInputDialog() {
		setResizable(false);
		setTitle("책 표지 URL(Uniform Resource Locator) 입력");
		setBounds(100, 100, 450, 188);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(WinInputDialog.class.getResource("/images/logo.png")));
		lblNewLabel.setBounds(12, 10, 120, 120);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("표지의 이미지 경로를 입력하시오.");
		lblNewLabel_1.setBounds(144, 39, 278, 15);
		getContentPane().add(lblNewLabel_1);
		
		tfUrl = new JTextField();
		tfUrl.setBounds(144, 64, 278, 21);
		getContentPane().add(tfUrl);
		tfUrl.setColumns(10);
		
		JButton btnAdd = new JButton("입력");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sUrl = tfUrl.getText();
				dispose();
			}
		});
		btnAdd.setBounds(144, 107, 97, 23);
		getContentPane().add(btnAdd);
		
		JButton btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		btnCancel.setBounds(325, 107, 97, 23);
		getContentPane().add(btnCancel);

	}

}
