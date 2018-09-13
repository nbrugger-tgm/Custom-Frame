package com.niton.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JRootPane;

import com.niton.frames.CustomFrame;
import com.niton.themes.base.Theme;

/**
 * This is the CustomRootPane Class
 *<br>It is responsible to paint the theme
 * @author Nils Brugger
 * @version 2018-08-10
 */
public class CustomRootPane extends JRootPane {
	private Theme t;
	public CustomRootPane(Theme t) {
		super();
		this.t = t;
	}

	/**
	 * <b>Type:</b> long<br> 
	 * <b>Description:</b><br>
	 */
	private static final long serialVersionUID = 2716373155118335582L;

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		t.paint((Graphics2D) g);
	}
}

