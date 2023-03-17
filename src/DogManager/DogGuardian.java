package DogManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DogGuardian extends JDialog {
	private JTextField tfDogGuardianName;
	private JTextField tfDogGuardianPhone;
	private JTextField tfDogGuardianPhone2;
	private JTextField tfDogGuardianAddress;
	private String file;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DogGuardian dialog = new DogGuardian();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DogGuardian() {
		setBounds(100, 100, 643, 196);
		getContentPane().setLayout(null);
		{
			JLabel lblMinorImg = new JLabel("");
			lblMinorImg.addMouseListener(new MouseAdapter() {
				

				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2) {
						//열기 대화상자를 열어 사진을 선택한다.						
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
							lblMinorImg.setIcon(pic);
							
						}
					}
				}
			});
			lblMinorImg.setBounds(12, 10, 200, 127);
			lblMinorImg.setToolTipText("더블클릭하여 미성년자 동의서 신분증 사진 경로를 입력하시오");
			lblMinorImg.setOpaque(true);
			lblMinorImg.setHorizontalAlignment(SwingConstants.CENTER);
			lblMinorImg.setBackground(new Color(192, 192, 192));
			getContentPane().add(lblMinorImg);
		}
		
		JLabel lblDogGuardianName = new JLabel("보호자명:");
		lblDogGuardianName.setBounds(224, 13, 57, 15);
		getContentPane().add(lblDogGuardianName);
		
		tfDogGuardianName = new JTextField();
		tfDogGuardianName.setColumns(10);
		tfDogGuardianName.setBounds(348, 10, 154, 21);
		getContentPane().add(tfDogGuardianName);
		
		JLabel lblDogGuardianPhone = new JLabel("보호자 연락처:");
		lblDogGuardianPhone.setBounds(224, 48, 93, 15);
		getContentPane().add(lblDogGuardianPhone);
		
		tfDogGuardianPhone = new JTextField();
		tfDogGuardianPhone.setColumns(10);
		tfDogGuardianPhone.setBounds(348, 45, 154, 21);
		getContentPane().add(tfDogGuardianPhone);
		
		JLabel lblDogGuardianPhone2 = new JLabel("비상 연락처(본인 외):");
		lblDogGuardianPhone2.setBounds(224, 83, 123, 15);
		getContentPane().add(lblDogGuardianPhone2);
		
		tfDogGuardianPhone2 = new JTextField();
		tfDogGuardianPhone2.setColumns(10);
		tfDogGuardianPhone2.setBounds(348, 80, 154, 21);
		getContentPane().add(tfDogGuardianPhone2);
		
		JLabel lblDogGuardianAddress = new JLabel("주소:");
		lblDogGuardianAddress.setBounds(224, 118, 123, 15);
		getContentPane().add(lblDogGuardianAddress);
		
		tfDogGuardianAddress = new JTextField();
		tfDogGuardianAddress.setColumns(10);
		tfDogGuardianAddress.setBounds(348, 115, 267, 21);
		getContentPane().add(tfDogGuardianAddress);
	}
}
