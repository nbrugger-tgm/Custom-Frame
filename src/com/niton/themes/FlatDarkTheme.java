package com.niton.themes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import com.niton.frames.CustomFrame;
import com.niton.themes.base.ResizeableTheme;
import com.niton.themes.base.Theme;

/**
 * This is the FlatDarkTheme Class
 * 
 * @author Nils Brugger
 * @version 2018-08-09
 */
public class FlatDarkTheme extends ResizeableTheme {
	private Color border = new Color(66, 66, 66);
	private Color content = new Color(95, 95, 95);
	private Color borders = new Color(35, 35, 35);
	private Color headerText = new Color(175, 175, 175);
	private int borderSize = 6;
	private Font titleFont = new Font("Roboto Light", Font.PLAIN, 20);
	/**
	 * Creates an Instance of FlatDarkTheme.java
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-10
	 */
	public FlatDarkTheme() {
		titleFont = titleFont
				.deriveFont(Collections.singletonMap(TextAttribute.WEIGHT, TextAttribute.WEIGHT_EXTRA_LIGHT));
	}

	@Override
	public void paint(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintBase(g);
		paintButtons(g);
		paintTitle(g);
		paintTitleImage(g);
	}
	private void paintTitleImage(Graphics2D g) {
		Image icon = frame.getIconImages().stream().reduce(null, (x, z) -> {
			return x == null ? (z.getHeight(null) == 32 ? z : x) : x;
		});
		if (icon != null)
			g.drawImage(icon, 2 + (frame.isMaximized() ? 0 : borderSize), 2, 36, 36, null);
	}

	private void paintTitle(Graphics2D g) {
		String text = frame.getTitle();
		int avainable = frame.getWidth() - ((frame.isMaximized() ? 0 : borderSize) * 2) - 160;
		int w = g.getFontMetrics(titleFont).stringWidth(text);
		if (w >= avainable)
			text += "...";
		while (w >= avainable) {
			text = text.substring(0, text.length() - 9);
			text += "...";
			w = g.getFontMetrics(titleFont).stringWidth(text);
		}
		write(text, headerText, titleFont, (frame.isMaximized() ? 0 : borderSize) + 40 + ((avainable / 2) - (w / 2)), 7,
				g);
	}

	private void paintButtons(Graphics2D g) {
		Rectangle mini = getMinimizeArea();
		Rectangle close = getCloseArea();
		Rectangle toggle = getMaximizeArea();
		g.setColor(borders);
//		g.drawLine(mini.x, 0, mini.x, 40);
//		g.drawLine(close.x, 0, close.x, 40);
//		g.drawLine(close.x + close.width, 0, close.x + close.width, 40);
//		g.drawLine(toggle.x, 0, toggle.x, 40);
		
		
	}

	private void paintBase(Graphics2D g) {
		fillRectangle(new Rectangle(frame.getSize()), g, border);
		fillRectangle(getContentSize(), g, content);
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

