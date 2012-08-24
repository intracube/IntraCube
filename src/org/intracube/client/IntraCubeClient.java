package org.intracube.client;


import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JPopupMenu.Separator;
import javax.swing.border.TitledBorder;

import org.intracube.api.elements.ClientElements;
import org.intracube.api.elements.Priority;
import org.intracube.config.ManRead;


/**
 * 
 * @author Aaron McClure
 */

public class IntraCubeClient implements ClientElements, WindowListener{

	public JPanel getMainPanel(){
		return panelMain;
	}

	public JList getLog(){
		return txtLog;
	}

	public JLabel getLMsg(){
		if (new Throwable().getStackTrace()[2].getClassName().contains("org.IntraCube.Client")){
			return lblMsg;
		}else{
			return null;
		}
	}

	public void setLMsgTxt(String text){
		if (new Throwable().getStackTrace()[2].getClassName().toLowerCase().contains("org.intracube.client")){
			lblMsg.setText(text);
			lblMsg.setSize(frameMaster.getPreferredSize());
		}
	}

	public DefaultListModel getListModel(){
		return IntraCubeClient.lstModel;
	}

	public IntraCubeClient() {
		if (frameMaster == null){
			initComponents();
		}
	}


	private void initComponents() {

		menuMain = new JMenuBar();
		frameMaster = new Decorator().makeFrame("IntraCube v " + version, menuMain);
		panelMain = new GameCanvas();
		lblMsg = new JLabel();
		panelLog = new JPanel();
		jScrollPane1 = new JScrollPane();
		txtLog = new JList();

		menuFile = new JMenu();
		mItemRun = new JMenuItem();
		mItemStop = new JMenuItem();
		jSeparator1 = new Separator();
		mItemSS = new JMenuItem();
		jSeparator2 = new Separator();
		mItemExit = new JMenuItem();
		menuView = new JMenu();
		mItemLogc = new JCheckBoxMenuItem();
		menuHelp = new JMenu();
		mItemSite = new JMenuItem();
		mItemProject = new JMenuItem();
		mItemAbout = new JMenuItem();
		jSeparator3 = new Separator();
		mItemSDecription = new JMenuItem();
		glass = new JPanel();

		menuMain.setBackground(Color.orange);//(51,255,204));
		TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "IntraCube v " + version); // needed?
		menuMain.setBorder(titledBorder);
		menuHelp.getPopupMenu().setLightWeightPopupEnabled(false);
		menuView.getPopupMenu().setLightWeightPopupEnabled(false);
		menuFile.getPopupMenu().setLightWeightPopupEnabled(false);

		lstModel = new DefaultListModel();
		lstModel.addElement("Welcome to IntraCube!");
		txtLog = new JList(lstModel);

		frameMaster.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frameMaster.setBackground(new Color(0, 0, 0));

		panelMain.setBackground(new Color(0, 0, 0));
		panelMain.setFont(new Font("Times New Roman", 0, 12));

		lblMsg.setFont(new Font("Tahoma", 0, 14));
		lblMsg.setForeground(new Color(0, 255, 255));
		lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblMsg.setSize(frameMaster.getPreferredSize());

		javax.swing.GroupLayout thisLayout = new GroupLayout(panelMain);
		panelMain.setLayout(thisLayout);

		panelLog.setBackground(new Color(204, 204, 204));

		txtLog.setBackground(new Color(204, 204, 204));
		txtLog.setSize(panelLog.getWidth(), panelLog.getHeight());
		txtLog.setBackground(Color.LIGHT_GRAY);
		txtLog.setFont(new Font("Monospaced",Font.BOLD,14)); 

		jScrollPane1.setViewportView(txtLog);

		GroupLayout panelLogLayout = new GroupLayout(panelLog);
		panelLog.setLayout(panelLogLayout);
		panelLogLayout.setHorizontalGroup(
				panelLogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
		);
		panelLogLayout.setVerticalGroup(
				panelLogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
		);

		menuMain.setBorder(BorderFactory.createEtchedBorder());
		menuMain.setBorderPainted(false);
		menuMain.setDoubleBuffered(true);
		menuFile.setText("File");

		mItemRun.setText("Run Script");
		mItemRun.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					mItemRunActionPerformed(evt);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		menuFile.add(mItemRun);

		mItemStop.setText("Stop Script");
		mItemStop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					mItemStopActionPerformed(evt);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		menuFile.add(mItemStop);

		menuFile.add(jSeparator1);

		mItemSS.setText("Screenshot");
		mItemSS.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mItemSSActionPerformed(evt);
			}
		});
		menuFile.add(mItemSS);
		menuFile.add(jSeparator2);

		mItemExit.setText("Exit");
		mItemExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mItemExitActionPerformed(evt);
			}
		});
		menuFile.add(mItemExit);

		menuMain.add(menuFile);

		menuView.setText("View");

		mItemLogc.setSelected(true);
		mItemLogc.setText("Show Log");
		mItemLogc.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				mItemLogcStateChanged(evt);
			}
		});
		menuView.add(mItemLogc);

		menuMain.add(menuView);

		menuHelp.setText("Help");

		mItemSite.setText("Site");
		mItemSite.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mItemSiteActionPerformed(evt);
			}
		});
		menuHelp.add(mItemSite);

		mItemProject.setText("Project");
		mItemProject.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mItemProjectActionPerformed(evt);
			}
		});
		menuHelp.add(mItemProject);

		mItemAbout.setText("About");
		mItemAbout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mItemAboutActionPerformed(evt);
			}
		});
		menuHelp.add(mItemAbout);
		menuHelp.add(jSeparator3);

		mItemSDecription.setText("Script Description");
		mItemSDecription.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mItemSDecriptionActionPerformed(evt);
			}
		});
		menuHelp.add(mItemSDecription);

		menuMain.add(menuHelp);

		frameMaster.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				glass.setSize(frameMaster.getSize());
				updateGlass();
			}
		});


		GroupLayout layout = new GroupLayout(frameMaster.getContentPane());
		frameMaster.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(panelLog, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(panelMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addComponent(panelMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panelLog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);

		panelLog.getAccessibleContext().setAccessibleName("panelLog");

		Toolkit tKit = Toolkit.getDefaultToolkit();
		Dimension wndSize = tKit.getScreenSize();
		int x = (wndSize.width - 750) / 2;
		int y = (wndSize.height - 700) / 2;
		frameMaster.setLocation(x,y);

		systTimer = new Timer();
		TimerTask task = new STM(systTimer);
		systTimer.scheduleAtFixedRate(task, 1, 1);

		frameMaster.pack();

		frameMaster.setSize(750,700);
		frameMaster.addWindowListener(this);

		setUpGlass();

		try {
			String[] feed = getFeed();
			for (int i=0; i<feed.length; i++){
				if (feed[i] == null || feed[i].equalsIgnoreCase("null")) break; // if no message is desired, type null in pastebin
				log.show(feed[i]);
			}
			if (!isUpdated()){
				log.show("Client version outdated. Please upgrade to the newest version at", Priority.SEVERE);
				log.show("http://www.intracube.org/", Priority.SEVERE);
			}
		} catch (NumberFormatException e1) {
			log.show("Unable to read version number.  Try again later.", Priority.SEVERE);
			e1.printStackTrace();
		} catch (java.net.UnknownHostException e3){
			log.show("Unknown host: Unable to contact server. Check internet connection.", Priority.SEVERE);
			e3.printStackTrace();
		} catch (IOException e2) {
			log.show("IOException.", Priority.SEVERE);
			e2.printStackTrace();
		}
		System.out.println("3.5");
	}

	private static class STM extends TimerTask {
		private Timer timer;
		public STM(Timer timer){
			this.timer = timer;
		}
		public void run() {
			try{
				if (new MainDriver().isRunning() && mItemStop != null){
					mItemStop.setEnabled(true);
					mItemRun.setEnabled(false);
					if (glass != null && glass.isVisible()){
						glass.setVisible(false);
					}
					new Decorator().setTitle("IntraCube v " + version + " - " + new ManRead().getScriptName());
				}else{

					if (!glass.isVisible()){
						glass.setVisible(true);
						frameMaster.getContentPane().invalidate();
						frameMaster.getContentPane().repaint();
					}

					mItemRun.setEnabled(true);
					mItemStop.setEnabled(false);
					new Decorator().setTitle("IntraCube v " + version);
				}

				if (client.getLog() != null && client.getLog().isVisible() && new MainDriver().isRunning()){
					client.getLog().ensureIndexIsVisible(client.getLog().getLastVisibleIndex()+1); 
				}
			}catch (Exception ex){
				log.show("Error thrown during system execution.", Priority.SEVERE);
				log.show("Client state: Unstable. Recommended to restart client.", Priority.SEVERE);

				timer.cancel();
				timer = null;

				log.show("Type: " + ex.getMessage(), Priority.SEVERE);
				log.show("Trace: " + ex.getStackTrace()[0], Priority.SEVERE);
			}
		}
	}

	private void setUpGlass(){
		picLabel = new JLabel(new ImageIcon(picLogo));
		glass.setLayout(null);
		panelMain.add(glass, BorderLayout.CENTER);
		glass.setBackground(Color.black);
		lblMsg.setText("Ready.");
		glass.add(lblMsg);
		glass.add(picLabel);
		updateGlass();
		glass.setVisible(true);
		if (picLabel == null){
			System.out.println("CALLED");
		}

	}
	private JLabel picLabel = null;
	private Image picLogo = new ImageIcon(getClass().getResource("/resources/IntraCubeLogoTitleClient.png")).getImage();

	private void updateGlass(){
		if (glass!= null && picLabel != null && lblMsg != null){
			glass.setSize(frameMaster.getSize());

			picLabel.setSize(picLogo.getWidth(null), picLogo.getHeight(null));
			picLabel.setLocation(((frameMaster.getWidth() - picLabel.getWidth()) / 2), ((frameMaster.getHeight() - picLabel.getHeight()) / 2)-125);

			lblMsg.setLocation(((frameMaster.getWidth() - lblMsg.getWidth()) / 2), ((frameMaster.getHeight() - lblMsg.getHeight()) / 2)-75);
			lblMsg.setSize(frameMaster.getPreferredSize());
		}
	}

	private void mItemRunActionPerformed(java.awt.event.ActionEvent evt) throws ClassNotFoundException, IOException {   
		ScriptSelector frame = new ScriptSelector();
		frame.setVisible(true);
	}     

	@SuppressWarnings("deprecation")
	private void mItemStopActionPerformed(java.awt.event.ActionEvent evt) throws ClassNotFoundException, IOException {
		try{
			Set<Thread> tS = Thread.getAllStackTraces().keySet();
			Thread[] tA= tS.toArray(new Thread[tS.size()]);
			// timer.setName("System Timer")
			for (int i=0; i< tA.length; i++){
				if (tA[i].getName().startsWith("Timer") && !tA[i].getName().equals("Timer-0")){ // need check on system crashes
					tA[i].stop(); // temporary work around
				}
			}
		}catch (Exception ex){
			log.show("Unable to terminate secondary threads.", Priority.SEVERE);
			log.show("Restart client.", Priority.SEVERE);
			log.show("Trace: " + ex.getMessage(), Priority.SEVERE);
		}
		new MainDriver().stop();
	}  

	private void mItemSSActionPerformed(java.awt.event.ActionEvent evt) {    
		try {	
			Rectangle screenRectangle = new Rectangle(panelMain.getSize());
			screenRectangle.setLocation(new Point(frameMaster.getLocation().x+6, frameMaster.getLocation().y +(menuMain.getSize().height)*2+6));
			Robot robot = new Robot();

			JOptionPane.showMessageDialog(null, "Picture taken.", "Picture", JOptionPane.INFORMATION_MESSAGE);
			BufferedImage image = robot.createScreenCapture(screenRectangle);
			JFileChooser chooser = new JFileChooser();
			chooser.showSaveDialog(new JFrame());

			File outputFile = new File(chooser.getSelectedFile().getPath());
			ImageIO.write(image, "png", outputFile);

			log.show("Image saved to " + chooser.getSelectedFile().getPath());
		} catch (Exception ex){
			JOptionPane.showMessageDialog(null, "Error.", "Error.", JOptionPane.ERROR_MESSAGE);
		}
	}                                       

	private void mItemExitActionPerformed(java.awt.event.ActionEvent evt) {
		if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Closing Client", JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
			frameMaster.dispose();
			System.exit(0);
		}
	}

	private void mItemLogcStateChanged(javax.swing.event.ChangeEvent evt) {
		if (mItemLogc.getState()){
			panelLog.setVisible(true);
		}else{
			panelLog.setVisible(false);
		}
		updateGlass();
	}

	private void mItemSiteActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			navigateToWeb(new URI("http://www.IntraCube.org"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void mItemProjectActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			navigateToWeb(new URI( "http://www.github.com/IntraCube/source")); // edit
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void mItemAboutActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			navigateToWeb(new URI( "http://www.IntraCube.org/about")); // edit
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void navigateToWeb(URI uri){
		if(Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				log.show("Unable to navigate to " + uri.getPath(), Priority.SEVERE);
			}
		}
	}

	private void mItemSDecriptionActionPerformed(java.awt.event.ActionEvent evt) {
		ManRead reader = new ManRead();	
		if (new MainDriver().isRunning()){
			String authors = "";
			for (int i=0; i<reader.getAuthors().length; i++){
				if (reader.getAuthors().length == 1){
					authors = reader.getAuthors()[i];
					break;
				}else{
					if (i == reader.getAuthors().length-1){
						authors += reader.getAuthors()[i];
					}else{
						authors += reader.getAuthors()[i] + ", ";
					}
				}
			}
			JOptionPane.showMessageDialog(null, "Name: " + reader.getScriptName() + "\nAuthor: " + authors + "\nVersion: " +
					reader.getVersion() + "\nDescription: " + reader.getDescription() + "\nWebsite: " + 
					reader.getWebsite(), "Script Description", JOptionPane.INFORMATION_MESSAGE); 
		}else{
			JOptionPane.showMessageDialog(null, "No script is running.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if (new MainDriver().isRunning()){
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?") == JOptionPane.YES_OPTION){
				frameMaster.dispose();
				System.exit(0);
			}
		} else{
			frameMaster.dispose();
			System.exit(0);
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}


	public static void main(String args[]) {		
		loader.setVisible(true);
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new IntraCubeClient();
				frameMaster.setFocusable(true);
				loader.setVisible(false);
				frameMaster.setVisible(true);	
				frameMaster.setIconImage(new ImageIcon(getClass().getResource("/resources/IntraCubeTaskbar.png")).getImage()); 
				createMainDirectory();
			}
		});
	}

	private static void createMainDirectory() {
		try {
			System.out.println(getPathDir());
			if (!(new File(getPathDir()).exists())) {
				String intraDirMainPath = getPathDir();

				File dir = new File(intraDirMainPath);
				boolean success = (dir.mkdir());
				new File(dir.getPath() + File.separator + "Scripts").mkdir(); 
				new File(dir.getPath() + File.separator + "Scripts" + File.separator + "Jars").mkdir();
				new File(dir.getPath() + File.separator + "Scripts" + File.separator + "Sources").mkdir();
				new File(dir.getPath() + File.separator + "Screenshots").mkdir(); 
				new File(dir.getPath() + File.separator + "Settings").mkdir(); 

				Class<?> encCls = new Object(){}.getClass().getEnclosingClass();

				String fileName="Compile";
				File compFile = new File(dir,fileName+".bat");

				try{
					if(!compFile.exists()){
						InputStream is = encCls.getResourceAsStream("/resources/Compile.txt");
						Scanner scanner = new Scanner(is);
						String line = "";
						while (scanner.hasNextLine()) {
							line += scanner.nextLine() + "\n";
						}
						FileOutputStream out = new FileOutputStream(compFile);
						new PrintStream(out).print(line);

						scanner.close();
						out.close();		
						System.out.println(compFile.toString());
						compFile.createNewFile();
					}
				}catch (Exception ex){
					JOptionPane.showMessageDialog(null, ex.toString());
				}
				fileName="FindJDK";
				File fJDKFile=new File(dir,fileName+".bat");

				if(!fJDKFile.exists()){
					InputStream is = encCls.getResourceAsStream("/resources/FindJDK.txt");
					Scanner scanner = new Scanner(is);
					String line = "";
					while (scanner.hasNextLine()) {
						line += scanner.nextLine() + "\n";
						System.out.println(line);
					}
					FileOutputStream out = new FileOutputStream(fJDKFile);
					new PrintStream(out).print(line);

					scanner.close();
					out.close();	

					fJDKFile.createNewFile();
				}

				fileName="path";
				File pathFile =new File(dir + File.separator + "Settings",fileName+".txt");
				if(!pathFile.exists()){
					String location = IntraCubeClient.class.getProtectionDomain().getCodeSource().getLocation().getPath();
					System.out.println(location);

					FileOutputStream out = new FileOutputStream(pathFile);
					new PrintStream(out).print(location);
					pathFile.createNewFile();

					out.close();
				}

				if (success) {
					log.show("Created IntraCube Directory");
				}else{
					log.show("Unable to create IntraCube directory");
				}
			}
		} catch (Exception e) {
		}
	}
	public static String getPathDir() {
		return getDocumentsDirectory() + File.separator + "IntraCube Scripts";
	}
	public static String getDocumentsDirectory() {
		String os = System.getProperty("os.name");
		if (os.toLowerCase().contains("mac")) {
			return System.getProperty("user.home") + File.separator + "Documents";
		}

		if (os.toLowerCase().contains("windows")) {
			return System.getProperty("user.home") + File.separator + "My Documents";
		}

		if (os.toLowerCase().contains("linux")) {
			return System.getProperty("user.home");
		} else {
			return System.getProperty("user.home");
		}

	}

	private String updateUrl = "http://pastebin.com/raw.php?i=6RvnMv5s";
	private String getVersion() throws IOException{
		String text;
		BufferedReader x = new BufferedReader(new InputStreamReader(new URL(updateUrl).openStream()));
		text = x.readLine();
		x.close();
		return text;
	}

	private boolean isUpdated() throws NumberFormatException, IOException{
		return (Double.parseDouble(getVersion()) == version);
	}

	private String[] getFeed() throws MalformedURLException, IOException{
		String[] text = new String[15]; // will only show first 15 lines
		BufferedReader x = new BufferedReader(new InputStreamReader(new URL("http://pastebin.com/raw.php?i=m2DLLxxW").openStream()));
		Scanner scan = new Scanner(x);
		int tempCount = 0;
		while (scan.hasNext()){
			text[tempCount] = scan.nextLine();
			tempCount++;
		}
		return text;
	}



	private static JFrame frameMaster;

	private JMenu menuHelp;
	private JMenu menuView;
	private JScrollPane jScrollPane1;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;
	private JSeparator jSeparator3;
	private static JLabel lblMsg;
	private JMenuItem mItemAbout;
	private JMenuItem mItemExit;
	private JCheckBoxMenuItem mItemLogc;
	private JMenuItem mItemProject;
	private static JMenuItem mItemRun;
	private static JMenuItem mItemStop;
	private JMenuItem mItemSDecription;
	private JMenuItem mItemSS;
	private JMenuItem mItemSite;
	private JMenu menuFile;
	private JMenuBar menuMain;
	private static JPanel panelLog;
	private static JPanel panelMain;
	private static JList txtLog;

	private static DefaultListModel lstModel;
	private static JPanel glass;
	private Timer systTimer;

	private static double version = 1.0;
	private final static Loader loader = new Loader();
}

