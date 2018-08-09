package com.niton.themes;

import java.awt.Point;
import java.awt.Rectangle;

import com.niton.listeners.MoveListener;
import com.niton.listeners.ResizeListener;

/**
 * This is the ResizeableTheme Class
 * @author Nils Brugger
 * @version 2018-08-09
 */
public abstract class ResizeableTheme extends Theme {
	private Point grabPosition;
	
	/**
	 * If you overwrite this method it is very important to call the super method!<br>
	 * please add this code at the beginning of your method.<br>
	 * <pre>
	 * super.addListeners();
	 * </pre>
	 * @see com.niton.themes.Theme#addListeners()
	 */
	@Override
	public void addListeners() {
		frame.addMouseAdapter(new ResizeListener(this));
		frame.addMouseAdapter(new MoveListener(this));
	}
	/**
	 * @return the grabPosition
	 */
	public Point getGrabPosition() {
		return grabPosition;
	}

	/**
	 * @param grabPosition the grabPosition to set
	 */
	public void setGrabPosition(Point grabPosition) {
		this.grabPosition = grabPosition;
	}
	
	/**
	 * <b>Description :</b><br>
	 * Here you can define how big the disdance to the border can be that it is resizeable
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @return
	 */
	public abstract int getResizeRadius();
	
	/**
	 * <b>Description :</b><br>
	 * Write here if the frame should be resizeable at the moment.<br>
	 * this often retruns false if the window is fullscreen.
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @return true if it is resizeable at moment
	 */
	public abstract boolean currentlyResizeable();
	/**
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @return the area of the frame where the user can grab the frame and move it
	 */
	public abstract Rectangle getDragingArea();
}

