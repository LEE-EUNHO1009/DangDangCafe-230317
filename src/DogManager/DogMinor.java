package DogManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingConstants;

public class DogMinor extends JDialog {
	private String file;
	private int type;
	private JLabel lblMinorImg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DogMinor dialog = new DogMinor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DogMinor() {
		setTitle("미성년자 동의서");
		setBounds(100, 100, 320, 438);
		getContentPane().setLayout(null);
		{
			JLabel lblMinorImg = new JLabel("<html><width=300 height=400></html>");
			lblMinorImg.setHorizontalAlignment(SwingConstants.CENTER);
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
			lblMinorImg.setBounds(0, 0, 300, 400);
			lblMinorImg.setToolTipText("더블클릭하여 미성년자 동의서 사진 경로를 입력하시오");
			lblMinorImg.setOpaque(true);
			lblMinorImg.setBackground(Color.YELLOW);
			getContentPane().add(lblMinorImg);
		}
	}

}
