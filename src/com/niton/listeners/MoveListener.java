package com.niton.listeners;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.niton.themes.ResizeableTheme;

/**
 * This is the MoveListener Class
 * @author Nils Brugger
 * @version 2018-08-09
 */
public class MoveListener extends MouseAdapter {
	private ResizeableTheme theme;
	private Point lastPos;
	private Dimension bevoreDrag;
	private boolean reresize = false;
	public MoveListener(ResizeableTheme theme) {
		super();
		this.theme = theme;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("I bimz click");
		Point relative = e.getPoint();
		if(
				theme.getDragingArea().contains(relative) &&
				!(
						theme.getCloseArea().contains(relative) ||
						theme.getMaximizeArea().contains(relative)||
						theme.getMinimizeArea().contains(relative)
				)
		) {
			Point absolute = e.getLocationOnScreen();
			if(!theme.getFrame().isMaximized()) {
				bevoreDrag = theme.getFrame().getSize();
			}else {
				reresize = true;
			}
			lastPos = absolute;
		}else {
			lastPos = null;
		}
	}
	public void mouseDragged(MouseEvent e) {
		System.out.println("I bimz drag");
		if(lastPos != null) {
			if(reresize){
				reresize = false;
				if(bevoreDrag != null)
				theme.getFrame().setSize(bevoreDrag);
				bevoreDrag = null;
			}
			Point now = e.getLocationOnScreen();
			int xdif = now.x - lastPos.x;
			int ydif = now.y - lastPos.y;
			Point windowPos = theme.getFrame().getLocation();
			windowPos.translate(xdif, ydif);
			theme.getFrame().setLocation(windowPos);
			lastPos = now;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		lastPos = null;
	}
}

