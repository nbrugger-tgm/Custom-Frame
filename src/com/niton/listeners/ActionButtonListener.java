package com.niton.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import com.niton.frames.CustomFrame;
import com.niton.themes.Theme;

import sun.text.normalizer.CharTrie.FriendAgent;

/**
 * This is the ActionButtonListener Class
 * @author Nils Brugger
 * @version 2018-08-09
 */
public class ActionButtonListener extends MouseAdapter {
	private CustomFrame frame;
	public ActionButtonListener(CustomFrame frame) {
		super();
		this.frame = frame;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		Theme t = frame.getTheme();
		if(t.getCloseArea().contains(e.getPoint())) {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}else if(t.getMaximizeArea().contains(e.getPoint())) {
			if(!frame.isMaximized())
				frame.maximize();
			else
				frame.undoResize();
		}else if(t.getMinimizeArea().contains(e.getPoint())) {
			frame.iconify();
		}
	}
}

