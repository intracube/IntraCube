package org.intracube.api.input;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.*;

import org.intracube.api.elements.ClientElements;


public class InputResponder implements KeyListener, MouseListener, MouseMotionListener, ClientElements {

	private static boolean isPressed = false;
	private static boolean isReleased = false;
	//private static boolean isTyped = false;
	private static String keyP = "", keyR = ""; //, keyT ="";
	private static KeyEvent kPE=null, kRE=null; //, kTE =null;

	//private static boolean mouseClick = false;
	private static boolean mouseEnter = false;
	private static boolean mouseExit = false;
	private static boolean mousePress = false;
	private static boolean mouseRelease = false;
	private static int mouseButtonClicked = 0, mouseButtonPressed = 0, mouseButtonReleased = 0;
	private static MouseEvent mCE=null, mEnE=null, mExE=null, mPE=null, mRE=null;


	@Override
	public void keyPressed(KeyEvent e) {
		kPE = e;
		keyP = String.valueOf(e.getKeyChar());
		isPressed = true;
		
		
		kRE = null;
		keyR = "";
		isReleased = false;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		kRE = e;
		keyR = String.valueOf(e.getKeyChar());
		isReleased = true;
		
		kPE = null;
		keyP = "";
		isPressed = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	///// gets

	public boolean isPressed(){
		return isPressed;
	}

	public boolean isReleased(){
		return isReleased;
	}

	/*public boolean isTyped(){
		
	}*/

	public String getKeyPressed(){
		return keyP;
	}

	public String getKeyReleased(){
		return keyR;
	}

	/*public String getKeyTyped(){

	}*/

	public KeyEvent getKeyPressEvent(){
		return kPE;
	}

	public KeyEvent getKeyReleaseEvent(){
		return kRE;
	}

	/*public KeyEvent getKeyTypedEvent(){
	
	}*/

	/////////////////// Mouse


	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseEnter = true;
		mEnE = e;

		mouseExit = false;
		mExE = null;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseExit = true;
		mExE = e;
		
		mouseEnter = false;
		mEnE = null;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePress = true;
		mouseButtonPressed = e.getButton();
		mPE = e;
		

		mouseRelease = false;
		mouseButtonReleased = -1;
		mRE = null;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseRelease = true;
		mouseButtonReleased = e.getButton();
		mRE = e;
		
		mousePress = false;
		mouseButtonPressed = -1;
		mPE = null;
	}

	////// gets

	/*public boolean isClicked(){
		
	}*/

	public boolean isMouseEnter(){
		return mouseEnter;
	}

	public boolean isMouseExit(){
		return mouseExit;
	}

	public boolean isMousePress(){
		return mousePress;
	}

	public boolean isMouseRelease(){
		return mouseRelease;
	}

	public int getClickedType(){
		return mouseButtonClicked;
	}

	public int getPressedType(){
		return mouseButtonPressed;
	}

	public int getReleasedType(){
		return mouseButtonReleased;
	}

	public MouseEvent getMouseClickEvent(){
		return mCE;
	}

	public MouseEvent getMouseEnterEvent(){
		return mEnE;
	}

	public MouseEvent getMouseExitEvent(){
		return mExE;
	}

	public MouseEvent getMousePressEvent(){
		return mPE;
	}

	public MouseEvent getMouseReleaseEvent(){
		return mRE;
	}

	// Mouse motion
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// Other
	
	public Point getAbsMousePos(){
		return new Point(MouseInfo.getPointerInfo().getLocation().x-client.getMainPanel().getLocationOnScreen().x, 
				MouseInfo.getPointerInfo().getLocation().y-client.getMainPanel().getLocationOnScreen().y);
	}
	
	public Point getMousePos(){
		return new Point(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
	}
}
