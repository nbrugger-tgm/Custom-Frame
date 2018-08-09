package com.niton.themes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.niton.themes.base.ResizeableTheme;
import com.niton.themes.base.Theme;

/**
 * This is the FlatDarkTheme Class
 * @author Nils Brugger
 * @version 2018-08-09
 */
public class FlatDarkTheme extends ResizeableTheme {
	private Color border = new Color(66, 66, 66);
	private Color content = new Color(95, 95, 95);
	private Color borders = new Color(35, 35, 35);
	private Color headerText = new Color(175, 175, 175);
	private int borderSize = 6;

	@Override
	public void paint(Graphics2D g) {
//		g.setColor(border);
//		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
//		g.setColor(content);
//		g.fillRect((frame.isMaximized()?0:borderSize), 40, getFrame().getWidth()-(frame.isMaximized()?0:borderSize*2), getFrame().getHeight()-40-(frame.isMaximized()?0:borderSize));
//		g.setStroke(new BasicStroke(2));
//		g.setColor(borders);
//		g.drawRect(getFrame().getWidth()-120-(frame.isMaximized()?0:borderSize), 0, 40, 40);
//		g.drawRect(getFrame().getWidth()-80-(frame.isMaximized()?0:borderSize), 0, 40, 40);
//		g.drawRect(getFrame().getWidth()-40-(frame.isMaximized()?0:borderSize), 0, 40, 40);
		
		
		fillRectangle(new Rectangle(frame.getSize()), g, border);
		fillRectangle(getContentSize(), g, content);
		paintRectangle(getMinimizeArea(), g, 2, borders);
		paintRectangle(getMaximizeArea(), g, 2, borders);
		paintRectangle(getCloseArea(), g, 2, borders);
		
//		fillRectangle(frame.getBounds(), g, border);
//		fillRectangle(getContentSize(), g, content);
//		paintRectangle(getMinimizeArea(), g, 2, borders);
//		paintRectangle(getMaximizeArea(), g, 2, borders);
//		paintRectangle(getCloseArea(), g, 2, borders);
		
	}
	
	@Override
	public Rectangle getMinimizeArea() {
		return new Rectangle(getFrame().getWidth()-120-(frame.isMaximized()?0:borderSize), 0, 40, 40);
	}
	
	@Override
	public Rectangle getMaximizeArea() {
		return new Rectangle(getFrame().getWidth()-80-(frame.isMaximized()?0:borderSize), 0, 40, 40);
	}
	
	@Override
	public Rectangle getContentSize() {
		return new Rectangle((frame.isMaximized()?0:borderSize), 40, getFrame().getWidth()-(frame.isMaximized()?0:borderSize*2), getFrame().getHeight()-40-(frame.isMaximized()?0:borderSize));
	}
	
	@Override
	public Rectangle getCloseArea() {
		return new Rectangle(getFrame().getWidth()-40-(frame.isMaximized()?0:borderSize), 0, 40, 40);
	}
	
	@Override
	public int getResizeRadius() {
		return borderSize;
	}
	
	@Override
	public Rectangle getDragingArea() {
		return new Rectangle(0, 0, getFrame().getWidth()-120, 40);
	}
	
	@Override
	public boolean currentlyResizeable() {
		return !frame.isMaximized();
	}
}

