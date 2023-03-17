package DogManager;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WinDogInsert extends JDialog {
	private JTextField tfDogName;
	private JTextField tfDogBirth;

	private String sUrl;
	private JTextArea taDogNotandum;
	private JTextField tfDogWeight;
	private JTextField textField;
	private JTextField tfDogCoupon;
	private JTextField tfDogGuardianName;
	private JLabel lblDogCount;
	private String file;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinDogInsert dialog = new WinDogInsert(5);
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
	public WinDogInsert(int DogAdd) {
		setTitle("댕댕이 추가");
		setBounds(100, 100, 560, 617);
		getContentPane().setLayout(null);
		
		Dog dog = new Dog(DogAdd);
		dog.setBounds(0, 0, 544, 578);
		getContentPane().add(dog);
	}
}
