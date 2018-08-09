package com.niton.themes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Icon;

import com.niton.frames.CustomFrame;

/**
 * This is the Theme Class
 * @author Nils Brugger
 * @version 2018-08-08
 */
public abstract class Theme{
	protected CustomFrame frame;
	/**
	 * <b>Description :</b><br>
	 * You may add any listeners you need to the frame in here
	 * @author Nils Brugger
	 * @version 2018-08-09
	 */
	public abstract void addListeners();
	/** 
	 * @author Nils Brugger
	 * @version 2018-08-08
	 * @return the area in which the user needs to click to trigger the action on close
	 */
	public abstract Rectangle getCloseArea();
	/**
	 * @author Nils Brugger
	 * @version 2018-08-08
	 * @return the area in which the user needs to click to iconify the Window
	 */
	public abstract Rectangle getMaximizeArea();	
	/**
	 * @author Nils Brugger
	 * @version 2018-08-08
	 * @return the area in which the user needs to click to make the frame
	 */
	public abstract Rectangle getMinimizeArea();
	
	/**
	 * <b>Description :</b><br>
	 * This method should provide the area in which the content of the window will be.<br>
	 * it <b>HAS TO BE</b> <i>smaller</i> than the parameter because the outer areas are the border for your theme<br>
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @return the area for the content
	 */
	public abstract Rectangle getContentSize();
	
	/**
	 * <b>Description :</b><br>
	 * This method needs to paint the background of the window and the border.<br>
	 * The painting of the sub components doesn't needs to be done.
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @param g the graphics to paint on
	 */
	public abstract void paint(Graphics2D g);

	
	/**
	 * @return the frame
	 */
	public CustomFrame getFrame() {
		return frame;
	}
	/**
	 * @param frame the frame to set
	 */
	public void setFrame(CustomFrame frame) {
		this.frame = frame;
	}
}

