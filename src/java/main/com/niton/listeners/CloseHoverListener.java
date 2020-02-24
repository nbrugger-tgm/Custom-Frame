package com.niton.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.niton.themes.FlatDarkTheme;

/**
 * This is the CloseHoverListener Class
 * @author Nils Brugger
 * @version 2018-08-10
 */
public class CloseHoverListener extends MouseAdapter {
	FlatDarkTheme theme;

	public CloseHoverListener(FlatDarkTheme theme) {
		super();
		this.theme = theme;
	}
	
	/**
	 * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		boolean bev = theme.isOverexit();
		boolean now = theme.getCloseArea().contains(e.getPoint());
		theme.setOverexit(now);
		if(bev ^ now) {
			theme.getFrame().repaint();
		}
	}
}

