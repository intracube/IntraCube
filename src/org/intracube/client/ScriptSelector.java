package org.intracube.client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.JPopupMenu.Separator;

import org.intracube.api.elements.ClientElements;
import org.intracube.api.elements.Priority;
import org.intracube.config.Account;
import org.intracube.config.ClassImport;
import org.intracube.config.ManRead;

/*
 * scriptselector.java
 *
 * Created on Jan 16, 2012, 10:41:56 AM
 */

/**
 *
 * @author Aaron McClure
 */
@SuppressWarnings("serial")
public class ScriptSelector extends javax.swing.JDialog implements ClientElements {

	public ScriptSelector() throws ClassNotFoundException, IOException{
		ArrayList<Class<?>> classes = new ClassImport().getClasses();
		String[] items = new String[classes.size()];
		int numScripts = 0;
		String status = new Account().getGroup();
		for (int i=0; i<classes.size(); i++){
			try{
				items[i] = new String(classes.get(i).getName() + " ► " + new ManRead().getAnnotation(classes.get(i)).description()); // in future versions get name from manifest
				numScripts++;
				if (!status.equals("diamond") && !status.equals("admin") && !status.equals("mod")){
					if (!status.equals("vip") && numScripts==5){
						JOptionPane.showMessageDialog(null, "Only first 5 scripts are shown. If you would like to store more, upgrade to VIP or Diamond at www.intracube.org");
						break;
					}else if (status.equals("vip") && numScripts==25){
						JOptionPane.showMessageDialog(null, "Only first 25 scripts are shown. If you would like to store unlimited scripts, upgrade to Diamond at www.intracube.org");
						break;
					}
				}
			}catch (Exception ex){
				
			}
		}
		initComponents(items);
	}

	private void initComponents(String[] nms) {
		jPanel1 = new JPanel();
		jScrollPane1 = new JScrollPane();
		lstScripts = new JList<Object>(nms);
		btnRun = new JButton();
		btnCancel = new JButton();
		jMenuBar1 = new JMenuBar();
		menuFile = new JMenu();
		mItemImport = new JMenuItem();
		jSeparator1 = new Separator();
		mItemExit = new JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Script Selector");

		jPanel1.setBackground(new java.awt.Color(0, 102, 102));

		lstScripts.setBackground(new java.awt.Color(204, 204, 204));
		lstScripts.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jScrollPane1.setViewportView(lstScripts);

		btnRun.setBackground(new java.awt.Color(204, 255, 204));
		btnRun.setText("Run");
		btnRun.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnRunActionPerformed(evt);
			}
		});

		btnCancel.setBackground(new java.awt.Color(204, 255, 204));
		btnCancel.setText("Cancel");
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
										.addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap())
		);
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap())
		);

		menuFile.setText("File");

		mItemImport.setText("Import");
		mItemImport.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mItemImportActionPerformed(evt);
			}
		});
		menuFile.add(mItemImport);
		menuFile.add(jSeparator1);

		mItemExit.setText("Exit");
		mItemExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mItemExitActionPerformed(evt);
			}
		});
		menuFile.add(mItemExit);

		jMenuBar1.add(menuFile);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
		);

		Toolkit tKit = Toolkit.getDefaultToolkit();
		Dimension wndSize = tKit.getScreenSize();
		int x = (wndSize.width - 750) / 3;
		int y = (wndSize.height - 700) / 3;
		setLocation(x,y);
		setResizable(false);
		pack();
	}

	private void mItemImportActionPerformed(java.awt.event.ActionEvent evt) {
		String turl = JOptionPane.showInputDialog("Enter pastebin link.");

		if (turl.equals("") || !turl.toLowerCase().contains("pastebin.com")){ JOptionPane.showMessageDialog(null, "Invalid entry. Only " +
		"'Pastebin' links are accepted."); return;}

		if (!turl.contains("raw.php?i=")){
			turl = new StringBuffer(turl).insert(turl.indexOf(".com/")+5, "raw.php?i=").toString();
		}

		StringBuilder f = new StringBuilder();
		try {
			String text;
			BufferedReader x = new BufferedReader(new InputStreamReader(new URL(turl).openStream()));
			while (((text) = x.readLine()) != null) {
				f.append(text + "\n");
			}
			x.close();
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null, "Malformed URL");
		} catch (IOException e) {
			log.show("IOException.", Priority.SEVERE);
		}

		while (true){
			String clsName = JOptionPane.showInputDialog("Enter class name.");
			if (!clsName.equals("")){
				File txtFile;
				if (clsName.endsWith(".java")){
					txtFile = new File(new ClassImport().getScriptSourceDirectory(), clsName);
				}else if (clsName.endsWith(".txt")){
					txtFile = new File(new ClassImport().getScriptSourceDirectory(), clsName.substring(0,clsName.length()-4)); // test
				}else{
					txtFile = new File(new ClassImport().getScriptSourceDirectory(), clsName + ".java");
				}
				try {
					if (!txtFile.exists()){
						boolean success = txtFile.createNewFile();
						PrintWriter writer = new PrintWriter(txtFile);
						writer.write(f.toString());
						writer.close();
						if (success){
							Runtime.getRuntime().exec("cmd /c start Compile.bat", null, new File(new ClassImport().getScriptSourceDirectory().getAbsolutePath().substring(0, 
									new ClassImport().getScriptSourceDirectory().getAbsolutePath().indexOf("IntraCube Scripts")+17)));
							this.dispose();
							JOptionPane.showMessageDialog(null, "Script added. If your command prompt recieved an error, visit: www.intracube.org/faq");
							new ScriptSelector().setVisible(true);

						}else{
							JOptionPane.showMessageDialog(null, "Failed to add script.");
						}
					}else{
						JOptionPane.showMessageDialog(null, "File already exists.");
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error. " + e.getLocalizedMessage());
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Error. " + e.getLocalizedMessage());
					e.printStackTrace();
				}
				break;
			}else{
				if (JOptionPane.showConfirmDialog(null, "Invalid class name. Script cannot be imported without a class name.  Try again?") == JOptionPane.NO_OPTION){
					JOptionPane.showMessageDialog(null, "Script failed to import.");
					break;
				}
			}
		}
	}

	private void mItemExitActionPerformed(java.awt.event.ActionEvent evt) {
		this.dispose();
	}

	private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {
		Hashtable<String, Class<?>> table = new ClassImport().getHashtable();

		if (lstScripts.getSelectedIndex() != -1){
			this.dispose();
			new MainDriver().startScript(table.get(lstScripts.getSelectedValue().toString().substring(0, lstScripts.getSelectedValue().toString().indexOf(" ► "))));
		}else{
			JOptionPane.showMessageDialog(null, "No script selected.", "No selection made.", JOptionPane.ERROR_MESSAGE, null);
		}
	}

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
		this.dispose();
	}

	private JButton btnCancel;
	private JButton btnRun;
	private JMenuBar jMenuBar1;
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	private Separator jSeparator1;
	private JList<Object> lstScripts;
	private JMenuItem mItemExit;
	private JMenuItem mItemImport;
	private JMenu menuFile;
}

