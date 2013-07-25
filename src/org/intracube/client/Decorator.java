package org.intracube.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.intracube.api.input.InputResponder;

/**
 * 
 * @author Credits to "http://java-swing-tips.blogspot.com/2010/05/custom-decorated-titlebar-jframe.html" for framework; much of this class borrowed from that site
 *
 */
public class Decorator extends JPanel {
	private static final long serialVersionUID = 7407337335689472513L;
	private static final int W = 4;
	private JLabel left, right, top, bottom, topleft, topright, bottomleft, bottomright;
	//private Dimension normalSize = new Dimension(150,150);
	private JPanel resizePanel  = new JPanel(new BorderLayout()) {

		private static final long serialVersionUID = -8482725577803909298L;
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g.create();
			int w = getWidth();
			int h = getHeight();
			g2.setPaint(Color.orange);
			g2.fillRect(0,0,w,h);
			g2.setPaint(Color.black);
			g2.drawRect(0,0,w-1,h-1);

			g2.drawLine(0,2,2,0);
			g2.drawLine(w-3,0,w-1,2);

			g2.clearRect(0,0,2,1);
			g2.clearRect(0,0,1,2);
			g2.clearRect(w-2,0,2,1);
			g2.clearRect(w-1,0,1,2);

			g2.dispose();
			super.paintComponent(g);
		}
		
	};
	private JPanel contentPanel = new JPanel(new BorderLayout());
	private int state = 0;

	private static JFrame frame;
	private static JLabel lblTitle;

	public void setTitle(String title){
		lblTitle.setText(title);
	}
	
	public JFrame makeFrame(String str, JMenuBar bar) {
		Decorator.frame = new JFrame(str) {
			private static final long serialVersionUID = 2774965472101310379L;
			@Override
			public Container getContentPane() {
				return contentPanel;
			}
		};

		frame.setMinimumSize(new Dimension(150,150));
		frame.setUndecorated(true);
		lblTitle = new JLabel(str);

		JButton button = getButton(new ImageIcon(getClass().getResource("/resources/title_close.png")).getImage());
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getToolkit().getSystemEventQueue().postEvent(
						new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});

		JButton iconify = getButton(new ImageIcon(getClass().getResource("/resources/title_mini.png")).getImage());//new JButton(" ");

		iconify.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(state | Frame.ICONIFIED);
				frame.requestFocus();
			}
		});

		JButton max = getButton(new ImageIcon(getClass().getResource("/resources/title_max.png")).getImage());
		max.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				if (frame.getExtendedState() == Frame.MAXIMIZED_BOTH){
					frame.setExtendedState(Frame.NORMAL);
					frame.requestFocus();
				}else{
					frame.setExtendedState(state | Frame.MAXIMIZED_BOTH);
					frame.requestFocus();
				}
			}
		});

		JPanel title = new JPanel(new BorderLayout());
		DragWindowListener dwl = new DragWindowListener();
		title.addMouseListener(dwl);
		title.addMouseMotionListener(dwl);
		title.setOpaque(false);
		title.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));

		Box titleBox = Box.createHorizontalBox();
		titleBox.add(lblTitle);
		titleBox.add(Box.createHorizontalGlue());
		titleBox.add(iconify);
		titleBox.add(max);
		titleBox.add(button);

		title.add(titleBox, BorderLayout.NORTH);
		title.add(bar, BorderLayout.SOUTH);

		ResizeWindowListener rwl = new ResizeWindowListener();
		for(JLabel l:java.util.Arrays.asList(
				left         = new JLabel(), right        = new JLabel(),
				top          = new JLabel(), bottom       = new JLabel(),
				topleft      = new JLabel(), topright     = new JLabel(),
				bottomleft   = new JLabel(), bottomright  = new JLabel())) {
			l.addMouseListener(rwl);
			l.addMouseMotionListener(rwl);
		}

		Dimension d = new Dimension(W, 0);
		left.setPreferredSize(d);
		left.setMinimumSize(d);
		right.setPreferredSize(d);
		right.setMinimumSize(d);

		d = new Dimension(0, W);
		top.setPreferredSize(d);
		top.setMinimumSize(d);
		bottom.setPreferredSize(d);
		bottom.setMinimumSize(d);

		d = new Dimension(W, W);
		topleft.setPreferredSize(d);
		topleft.setMinimumSize(d);
		topright.setPreferredSize(d);
		topright.setMinimumSize(d);
		bottomleft.setPreferredSize(d);
		bottomleft.setMinimumSize(d);
		bottomright.setPreferredSize(d);
		bottomright.setMinimumSize(d);

		left.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		right.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
		top.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
		bottom.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
		topleft.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
		topright.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
		bottomleft.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
		bottomright.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));

		JPanel titlePanel = new JPanel(new BorderLayout(0,0));
		titlePanel.add(top, BorderLayout.NORTH);
		titlePanel.add(title,BorderLayout.CENTER);

		JPanel northPanel = new JPanel(new BorderLayout(0,0));
		northPanel.add(topleft,       BorderLayout.WEST);
		northPanel.add(titlePanel,    BorderLayout.CENTER);
		northPanel.add(topright,      BorderLayout.EAST);

		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(bottomleft,    BorderLayout.WEST);
		southPanel.add(bottom,        BorderLayout.CENTER);
		southPanel.add(bottomright,   BorderLayout.EAST);

		resizePanel.add(left,         BorderLayout.WEST);
		resizePanel.add(right,        BorderLayout.EAST);
		resizePanel.add(northPanel,   BorderLayout.NORTH);
		resizePanel.add(southPanel,   BorderLayout.SOUTH);
		resizePanel.add(contentPanel, BorderLayout.CENTER);

		titlePanel.setOpaque(false);
		northPanel.setOpaque(false);
		southPanel.setOpaque(false);

		contentPanel.setOpaque(false);
		resizePanel.setOpaque(false);
		frame.setContentPane(resizePanel);

		frame.setFocusable(true);
		
		frame.addKeyListener(new InputResponder());
		frame.addMouseListener(new InputResponder());

		return frame;
	}

	private JButton getButton(Image img){
		JButton button = new JButton(" ");

		button.setIcon(new ImageIcon(img));
		button.setOpaque(true);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setBackground(Color.ORANGE);

		return button;
	}

	private class ResizeWindowListener extends MouseAdapter {
		private Rectangle startSide = null;

		@Override 
		public void mousePressed(MouseEvent e) {
			startSide = frame.getBounds();
		}
		@Override 
		public void mouseDragged(MouseEvent e) {
			if(startSide==null) return;
			Component c = e.getComponent();
			if(c==topleft) {
				startSide.y += e.getY();
				startSide.height -= e.getY();
				startSide.x += e.getX();
				startSide.width -= e.getX();
			}else if(c==top) {
				startSide.y += e.getY();
				startSide.height -= e.getY();
			}else if(c==topright) {
				startSide.y += e.getY();
				startSide.height -= e.getY();
				startSide.width += e.getX();
			}else if(c==left) {
				startSide.x += e.getX();
				startSide.width -= e.getX();
			}else if(c==right) {
				startSide.width += e.getX();
			}else if(c==bottomleft) {
				startSide.height += e.getY();
				startSide.x += e.getX();
				startSide.width -= e.getX();
			}else if(c==bottom) {
				startSide.height += e.getY();
			}else if(c==bottomright) {
				startSide.height += e.getY();
				startSide.width += e.getX();
			}
			frame.setBounds(startSide);
		}
	}

	private class DragWindowListener extends MouseAdapter {
		private MouseEvent start;
		private Window window;
		@Override 
		public void mousePressed(MouseEvent me) {
			start = me;
		}
		@Override 
		public void mouseDragged(MouseEvent me) {
			if(window==null) {
				window = SwingUtilities.windowForComponent(me.getComponent());
			}
			Point eventLocationOnScreen = me.getLocationOnScreen();
			window.setLocation(eventLocationOnScreen.x - start.getX(), eventLocationOnScreen.y - start.getY());
		}
	}
}

