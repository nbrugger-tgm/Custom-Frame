package com.niton.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.niton.themes.ResizeableTheme;

/**
 * This is the ResizeListener Class
 * @author Nils Brugger
 * @version 2018-08-09
 */
public class ResizeListener extends MouseAdapter {
	private ResizeableTheme theme;
	public ResizeListener(ResizeableTheme resizeableTheme) {
		theme = resizeableTheme;
	}

	
	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}
	
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}
	
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
}

		if (frame.getWidth() <= frame.getMinimumSize().getWidth()) {
			frame.setSize((int) frame.getMinimumSize().getWidth(), frame.getHeight());
			if (grabPosition == GrabPosition.LEFT || grabPosition == GrabPosition.BOTTOM_LEFT) {
				frame.setLocation(oldX, oldY);
			}
		}
		if (frame.getHeight() <= frame.getMinimumSize().getHeight())
			frame.setSize(frame.getWidth(), (int) frame.getMinimumSize().getHeight());
