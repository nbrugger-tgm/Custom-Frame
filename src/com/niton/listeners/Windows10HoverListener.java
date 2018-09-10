package com.niton.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.niton.themes.FlatDarkTheme;
import com.niton.themes.Windows10Theme;

/**
 * This is the CloseHoverListener Class
 * @author Nils Brugger
 * @version 2018-08-10
 */
public class Windows10HoverListener extends MouseAdapter {
	private Windows10Theme theme;

	public Windows10HoverListener(Windows10Theme theme) {
		super();
		this.theme = theme;
	}
	
	/**
	 * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		boolean change = false;
		boolean closeBev = theme.overClose;
		boolean maxiBev = theme.overMaximize;
		boolean miniBev = theme.overMinimize;
		
		theme.overClose = theme.getCloseArea().contains(e.getPoint());
		theme.overMaximize = theme.getMaximizeArea().contains(e.getPoint());
		theme.overMinimize = theme.getMinimizeArea().contains(e.getPoint());
		if(closeBev != theme.overClose || maxiBev != theme.overMaximize|| miniBev != theme.overMinimize)
			theme.getFrame().repaint();
	}
}

