package com.niton.themes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import com.niton.frames.CustomFrame;
import com.niton.listeners.CloseHoverListener;
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
	private int borderSize = 7;
	private int iconLineSize = 3;
	private int buttonPadding = 10;
	private Font titleFont = new Font("Roboto Light", Font.PLAIN, 20);
	private boolean overexit = false;
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
	/**
	 * @see com.niton.themes.base.ResizeableTheme#addListeners()
	 */
	@Override
	public void addListeners() {
		super.addListeners();
		frame.addMouseAdapter(new CloseHoverListener(this));
	}
	@Override
	public boolean currentlyResizeable() {
		return !frame.isMaximized();
	}

	/**
	 * @return the border
	 */
	public Color getBorder() {
		return border;
	}
	/**
	 * @return the borders
	 */
	public Color getBorders() {
		return borders;
	}

	/**
	 * @return the borderSize
	 */
	public int getBorderSize() {
		return borderSize;
	}

	/**
	 * @return the buttonPadding
	 */
	public int getButtonPadding() {
		return buttonPadding;
	}

	@Override
	public Rectangle getCloseArea() {
		return new Rectangle(getFrame().getWidth() - 40 - (frame.isMaximized() ? 0 : borderSize), 0, 40, 40);
	}
	/**
	 * @return the content
	 */
	public Color getContent() {
		return content;
	}
	@Override
	public Rectangle getContentSize() {
		return new Rectangle((frame.isMaximized() ? 0 : borderSize), 40,
				getFrame().getWidth() - (frame.isMaximized() ? 0 : borderSize * 2),
				getFrame().getHeight() - 40 - (frame.isMaximized() ? 0 : borderSize));
	}
	@Override
	public Rectangle getDragingArea() {
		return new Rectangle(0, 0, getFrame().getWidth() - 120, 40);
	}
	/**
	 * @return the headerText
	 */
	public Color getHeaderText() {
		return headerText;
	}
	/**
	 * @return the iconLineSize
	 */
	public int getIconLineSize() {
		return iconLineSize;
	}
	@Override
	public Rectangle getMaximizeArea() {
		return new Rectangle(getFrame().getWidth() - 80 - (frame.isMaximized() ? 0 : borderSize), 0, 40, 40);
	}
	@Override
	public Rectangle getMinimizeArea() {
		return new Rectangle(getFrame().getWidth() - 120 - (frame.isMaximized() ? 0 : borderSize), 0, 40, 40);
	}
	@Override
	public int getResizeRadius() {
		return borderSize;
	}
	/**
	 * @return the titleFont
	 */
	public Font getTitleFont() {
		return titleFont;
	}
	public boolean isOverexit() {
		return overexit;
	}
	@Override
	public void paint(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintBase(g);
		paintButtons(g);
		paintTitle(g);
		paintTitleImage(g);
	}
	private void paintBase(Graphics2D g) {
		fillRectangle(new Rectangle(frame.getSize()), g, border);
		fillRectangle(getContentSize(), g, content);
	}
	private void paintButtons(Graphics2D g) {
		Rectangle mini = getMinimizeArea();
		Rectangle close = getCloseArea();
		Rectangle toggle = getMaximizeArea();
		g.setColor(borders);
		
		g.setStroke(new BasicStroke(iconLineSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 12));
		g.drawLine(mini.x+buttonPadding, 40-buttonPadding, mini.x+mini.width-buttonPadding, 40-buttonPadding);

		g.drawLine(toggle.x+toggle.width-buttonPadding, buttonPadding, toggle.x+buttonPadding+borderSize, 40-buttonPadding-borderSize);
		g.drawLine(toggle.x+toggle.width-buttonPadding, buttonPadding, toggle.x+buttonPadding, 40-buttonPadding);
		if(frame.isMaximized()) {
			g.drawLine(toggle.x+buttonPadding, 20, toggle.x+buttonPadding, 40-buttonPadding);
			g.drawLine(toggle.x+buttonPadding, 40-buttonPadding, toggle.x+buttonPadding+(toggle.width-(2*buttonPadding))/2, 40-buttonPadding);
		}else {
			g.drawLine(toggle.x+buttonPadding+(toggle.width-(2*buttonPadding))/2, buttonPadding, toggle.x+40-(buttonPadding), buttonPadding);
			g.drawLine(toggle.x+toggle.width-buttonPadding, buttonPadding, toggle.x+toggle.width-buttonPadding, (toggle.height/2));
		}
		if(overexit) {
			fillRectangle(close, g, Color.RED);
		}
		g.drawLine(close.x+buttonPadding, buttonPadding, close.x+close.width-buttonPadding, 40-buttonPadding);
		g.drawLine(close.x+close.width-buttonPadding, buttonPadding, close.x+buttonPadding, 40-buttonPadding);
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
	private void paintTitleImage(Graphics2D g) {
		Image icon = frame.getIconImages().stream().reduce(null, (x, z) -> {
			return x == null ? (z.getHeight(null) == 32 ? z : x) : x;
		});
		if (icon != null)
			g.drawImage(icon, 2 + (frame.isMaximized() ? 0 : borderSize), 2, 36, 36, null);
	}
	/**
	 * @param border the border to set
	 */
	public void setBorder(Color border) {
		this.border = border;
	}
	
	
	/**
	 * @param borders the borders to set
	 */
	public void setBorders(Color borders) {
		this.borders = borders;
	}
	

	/**
	 * @param borderSize the borderSize to set
	 */
	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}

	/**
	 * @param buttonPadding the buttonPadding to set
	 */
	public void setButtonPadding(int buttonPadding) {
		this.buttonPadding = buttonPadding;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(Color content) {
		this.content = content;
	}

	/**
	 * @param headerText the headerText to set
	 */
	public void setHeaderText(Color headerText) {
		this.headerText = headerText;
	}

	/**
	 * @param iconLineSize the iconLineSize to set
	 */
	public void setIconLineSize(int iconLineSize) {
		this.iconLineSize = iconLineSize;
	}

	public void setOverexit(boolean overexit) {
		this.overexit = overexit;
	}

	/**
	 * @param titleFont the titleFont to set
	 */
	public void setTitleFont(Font titleFont) {
		this.titleFont = titleFont;
	}
}
