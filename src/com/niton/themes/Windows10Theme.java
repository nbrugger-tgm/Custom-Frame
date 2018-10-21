package com.niton.themes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

import com.niton.listeners.Windows10HoverListener;
import com.niton.themes.base.ResizeableTheme;

/**
 * This is the Windows10Theme Class
 * @author Nils Brugger
 * @version 2018-09-10
 */
public class Windows10Theme extends ResizeableTheme {
	public Color borderColor = Color.WHITE;
	public boolean overMinimize = false,overMaximize = false,overClose = false;
	public int topSize = 29;
	public double buttonWidthRatio = 2;
	public int preferedIconSize = 32;
	public int buttonIconSize = 8;
	public int normalizeDisdance = 3;
	/**
	 * @see com.niton.themes.base.ResizeableTheme#getResizeRadius()
	 */
	@Override
	public int getResizeRadius() {
		return 5;
	}
	
	/**
	 * @see com.niton.themes.base.ResizeableTheme#addListeners()
	 */
	@Override
	public void addListeners() {
		super.addListeners();
		frame.addMouseMotionListener(new Windows10HoverListener(this));
	}

	/**
	 * @see com.niton.themes.base.ResizeableTheme#getDragingArea()
	 */
	@Override
	public Rectangle getDragingArea() {
		return new Rectangle(1, frame.isMaximized()? 0: 1, frame.getWidth()-(26*3), 21+(frame.isMaximized()? 1: 0));
	}

	/**
	 * @see com.niton.themes.base.Theme#getCloseArea()
	 */
	@Override
	public Rectangle getCloseArea() {
		boolean maximized = frame.isMaximized();
		int width = topSize -6;
		width *= buttonWidthRatio;
		if(maximized)
			width++;
		return new Rectangle((int) ((frame.getWidth()-1)-width*1), (maximized ? 0 :1), width,maximized ? topSize+1 : topSize-2);
	}

	/**
	 * @see com.niton.themes.base.Theme#getMaximizeArea()
	 */
	@Override
	public Rectangle getMaximizeArea() {
		boolean maximized = frame.isMaximized();
		int width = topSize -6;
		width *= buttonWidthRatio;
		return new Rectangle((int) ((frame.getWidth()-1)-width*2), (maximized ? 0 :1), width,maximized ? topSize+1 : topSize-2);
	}

	/**
	 * @see com.niton.themes.base.Theme#getMinimizeArea()
	 */
	@Override
	public Rectangle getMinimizeArea() {
		boolean maximized = frame.isMaximized();
		int width = topSize -6;
		width *= buttonWidthRatio;
		return new Rectangle((int) ((frame.getWidth()-1)-width*3), (maximized ? 0 :1), width,maximized ? topSize+1 : topSize-2);
	}

	/**
	 * @see com.niton.themes.base.Theme#getContentSize()
	 */
	@Override
	public Rectangle getContentSize() {
		return new Rectangle((frame.isMaximized() ? 0 :1), topSize, frame.getWidth()-(frame.isMaximized() ? 0 :2), frame.getHeight()-((frame.isMaximized() ? 0 :1) + topSize));
	}

	/**
	 * @see com.niton.themes.base.Theme#paint(java.awt.Graphics2D)
	 */
	@Override
	public void paint(Graphics2D g) {
		setupRendering(g);
		int col;
		if(getDarknes(borderColor) < (256/2))
			col = 255;
		else
			col = 0;
		drawHeadBar(g, col);
		
		drawMinimizeButton(col,g);
		
		drawMaximizeButton(col,g);
		
		drawCloseButton(col,g);
	}
	
	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-09-10
	 * @param col
	 * @param g
	 */
	private void drawCloseButton(int col, Graphics2D g) {
		Rectangle close = getCloseArea();
		if(overClose) {
			fillRectangle(close, g, new Color(230, 70, 70));
			g.setColor(Color.WHITE);
		}else {
			g.setColor(new Color(col, col, col));
		}
		int startX = close.x + ((close.width-buttonIconSize)/2);
		int startY = close.y + ((close.height-buttonIconSize)/2);
		int endX = startX + buttonIconSize;
		int endY = startY + buttonIconSize;
		g.drawLine(startX, startY, endX, endY);
		g.drawLine(startX, endY, endX, startY);
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-09-10
	 * @param col
	 * @param g
	 */
	private void drawMaximizeButton(int col, Graphics2D g) {
		Rectangle maximizeArea = getMaximizeArea();
		if(overMaximize) {
			fillRectangle(maximizeArea, g, new Color(70, 105, 210));
			g.setColor(Color.WHITE);
		}else {
			g.setColor(new Color(col, col, col));
		}
		int x = maximizeArea.x+((maximizeArea.width-buttonIconSize)/2);
		int y = (maximizeArea.height/2)-(buttonIconSize/2);
		g.drawRect(x, y, buttonIconSize, buttonIconSize);
		if(frame.isMaximized()) {
			g.drawLine(x+normalizeDisdance, y-normalizeDisdance, x+buttonIconSize+normalizeDisdance, y-normalizeDisdance);
			g.drawLine(x+buttonIconSize+normalizeDisdance, y-normalizeDisdance, x+buttonIconSize+normalizeDisdance, y-normalizeDisdance+buttonIconSize);
		}
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-09-10
	 * @param col
	 * @param g
	 */
	private void drawMinimizeButton(int col, Graphics2D g) {
		Rectangle minimizeArea = getMinimizeArea();
		if(overMinimize) {
			fillRectangle(minimizeArea, g, new Color(70, 105, 210));
			g.setColor(Color.WHITE);
		}else {
			g.setColor(new Color(col, col, col));
		}
		int x = minimizeArea.x+((minimizeArea.width-buttonIconSize)/2);
		g.drawLine(x, minimizeArea.height/2, x+buttonIconSize, minimizeArea.height/2);
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-09-10
	 * @param g 
	 */
	private void drawHeadBar(Graphics g,int col) {
		//base
		g.setColor(borderColor);
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		g.setColor(borderColor.darker());
		Rectangle r = getContentSize();
		g.drawRect(0, topSize-1, r.width+1, r.height);
		
		
		//icon
		Image icon = frame.getIconImages().stream().reduce(frame.getIconImages().get(0), (x, z) -> {
			return (x.getHeight(null) != preferedIconSize ? (z.getHeight(null) == preferedIconSize ? z : x) : x);
		});
		if (icon != null)
			g.drawImage(icon, 1 + (frame.isMaximized() ? 0 : 1), 2, topSize-4, topSize-2, null);
		

		int width = topSize -6;
		width *= buttonWidthRatio;
		
		//text
		String text = frame.getTitle();
		int avainable = (int) (frame.getWidth() - topSize - (width*3) - 5);
		Font titleFont = new Font("sans-serif", Font.PLAIN, (int) (topSize*0.50));
		FontMetrics metrics = g.getFontMetrics(titleFont);
		int w = metrics.stringWidth(text);
		if (w >= avainable)
			text += "...";
		while (w >= avainable) {
			text = text.substring(0, text.length() - 4);
			text += "...";
			w = metrics.stringWidth(text);
		}
		write(text, new Color(col, col, col), titleFont, topSize+((avainable-metrics.stringWidth(text))/2), 2,(Graphics2D) g);
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-09-10
	 * @param g
	 */
	private void setupRendering(Graphics2D g) {
//		g.setStroke(new BasicStroke(1.35f));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	}

	/**
	 * @see com.niton.themes.base.ResizeableTheme#currentlyResizeable()
	 */
	@Override
	public boolean currentlyResizeable() {
		return !frame.isMaximized();
	}
	
	
	
	private static double getDarknes(Color c) {
		double average = c.getGreen()+c.getBlue()+c.getRed();
		average /= 3;
		return average;
	}
}

