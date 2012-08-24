package org.intracube.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;



/**
 *
 * @author Aaron McClure
 */
public class Loader extends javax.swing.JFrame {
	private static final long serialVersionUID = 8633534139918629002L;

	@Override
	public void setVisible(boolean b){
		if (b){
			if (!isVisible() && panelMain == null){
				initComponents();
			}
			super.setVisible(true);
		}else{
			super.setVisible(false);
		}
	}

	private void initComponents() {
		
		panelMain = new JPanel(new FlowLayout());
		lblStatus = new JLabel();
		pB = new JProgressBar();
		lblPic = new JLabel();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setBackground(new Color(0, 0, 0));
		setResizable(false);
		
		
		lblStatus.setText("Loading client...");
		lblStatus.setForeground(Color.cyan);

		lblPic.setIcon(new ImageIcon(picLogo));
		
		pB.setPreferredSize(new Dimension(300,20));
		pB.setString("IntraCube");
		pB.setStringPainted(true);
				
		panelMain.setBackground(Color.black);
				
		panelMain.add(lblPic);
		panelMain.add(pB);
		panelMain.add(lblStatus);
		
		this.setUndecorated(true);
		this.setSize(300,150);
		this.add(panelMain);

		Toolkit tKit = Toolkit.getDefaultToolkit();
		Dimension wndSize = tKit.getScreenSize();
		int x = (wndSize.width - 352) / 2;
		int y = (wndSize.height - 97) / 2;
		setLocation(x,y);

		setTitle("Start up");
		startLoading();
	}


	private void startLoading(){
		pB.setBackground(Color.DARK_GRAY);
		pB.setForeground(Color.green);
		pB.setIndeterminate(true);
	}

	private static JLabel lblStatus, lblPic;
	private JPanel panelMain;
	private JProgressBar pB;
	private Image picLogo = new ImageIcon(getClass().getResource("/resources/IntraCubeLogoTitleClient.png")).getImage();
	
}

