package com.niton.themes;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.RoundRectangle2D;

import com.niton.frames.CustomFrame;
import com.niton.themes.base.ResizeableTheme;

/**
 * This is the OSXTheme Class
 * @author Nils Brugger
 * @version 2018-08-10
 */
public class OSXTheme extends ResizeableTheme {
	private Size size = Size.SMALL;
	public Font titleFont = new Font("arial", Font.PLAIN,13);
	/*
	 * @see com.niton.themes.base.Theme#setFrame(com.niton.frames.CustomFrame)
	 */
	@Override
	public void setFrame(CustomFrame frame) {
		super.setFrame(frame);
//		frame.setShape(new RoundRectangle2D.Double( frame.getX(),frame.getY(), frame.getWidth(), frame.getHeight(), 10, 10));
//		frame.setShape(new RoundRectangle2D.Double(0, 0, 100, 100, 10, 10));
		frame.invalidate();
	}
	
	/**
	 * @see com.niton.themes.base.ResizeableTheme#getResizeRadius()
	 */
	@Override
	public int getResizeRadius() {
		return 5;
	}

	/**
	 * @see com.niton.themes.base.ResizeableTheme#currentlyResizeable()
	 */
	@Override
	public boolean currentlyResizeable() {
		return !getFrame().isMaximized();
	}

	/**
	 * @see com.niton.themes.base.ResizeableTheme#getDragingArea()
	 */
	@Override
	public Rectangle getDragingArea() {
		return new Rectangle((int) ((4+3*22)*size.multiplyer), 0, (int) ((frame.getWidth()-(4+3*22))*size.multiplyer), (int) (22*size.multiplyer));
	}

	/**
	 * @see com.niton.themes.base.Theme#getCloseArea()
	 */
	@Override
	public Rectangle getCloseArea() {
		return new Rectangle((int) (8*size.multiplyer), (int) (5*size.multiplyer), (int) (12*size.multiplyer), (int) (12*size.multiplyer));
	}

	/**
	 * @see com.niton.themes.base.Theme#getMaximizeArea()
	 */
	@Override
	public Rectangle getMaximizeArea() {
		return new Rectangle((int) (48*size.multiplyer), (int) (5*size.multiplyer), (int) (12*size.multiplyer), (int) (12*size.multiplyer));
	}

	/**
	 * @see com.niton.themes.base.Theme#getMinimizeArea()
	 */
	@Override
	public Rectangle getMinimizeArea() {
		return new Rectangle((int) (28*size.multiplyer), (int) (5*size.multiplyer), (int) (12*size.multiplyer), (int) (12*size.multiplyer));
	}

	/**
	 * @see com.niton.themes.base.Theme#getContentSize()
	 */
	@Override
	public Rectangle getContentSize() {
		Rectangle r = frame.getBounds();
		r.y = (int) (23*size.multiplyer);
		r.height -= 23*size.multiplyer;
		r.x = 0;
		return r;
	}

	/**
	 * @see com.niton.themes.base.Theme#paint(java.awt.Graphics2D)
	 */
	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.decode("#FBFFFF"));
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint topGradient = new GradientPaint(0, 0,Color.decode("0xE9F6FF"), 0, 22, Color.decode("0xD2D2D2"));
		g.setPaint(topGradient);
		g.fillRect(0, 0, frame.getWidth(), getDragingArea().height);
		g.setColor(Color.decode("0x7E7E7E"));
		g.drawLine(0, (int)(22*size.multiplyer), frame.getWidth(), (int) (22*size.multiplyer));
		Rectangle puffer = getCloseArea();
		
		
		g.setColor(Color.decode("#F9625B"));
		g.fillArc(puffer.x, puffer.y, puffer.width, puffer.height, 0, 360);
		g.setColor(Color.decode("#AE5D5C"));
		g.drawArc(puffer.x, puffer.y, puffer.width, puffer.height, 0, 360);
		
		puffer = getMinimizeArea();
		g.setColor(Color.decode("#FBBC4A"));
		g.fillArc(puffer.x, puffer.y, puffer.width, puffer.height, 0, 360);
		g.setColor(Color.decode("#C6A45D"));
		g.drawArc(puffer.x, puffer.y, puffer.width, puffer.height, 0, 360);

		puffer = getMaximizeArea();
		g.setColor(Color.decode("#3BC858"));
		g.fillArc(puffer.x, puffer.y, puffer.width, puffer.height, 0, 360);
		g.setColor(Color.decode("#4C9754"));
		g.drawArc(puffer.x, puffer.y, puffer.width, puffer.height, 0, 360);
		
		g.setColor(Color.decode("#2B3F57"));
		g.drawRect(0, 0, frame.getWidth()-1, frame.getHeight()-1);
		
		String text = frame.getTitle();
		int avainable = (int) (frame.getWidth() - 76*(size.multiplyer));
		int w = g.getFontMetrics(titleFont).stringWidth(text);
		if (w >= avainable)
			text += "...";
		while (w >= avainable) {
			text = text.substring(0, text.length() - 4);
			text += "...";
			w = g.getFontMetrics(titleFont).stringWidth(text);
		}
		write(text, Color.decode("#4C4D51"), titleFont, (int) ((60+8)*(size.multiplyer) + ((avainable / 2) - (w / 2))), (int) (4*size.multiplyer),g);
	}
	
	/**
	 * @param size the size to set
	 */
	public void setSize(Size size) {
		this.size = size;
		titleFont = new Font("arial", Font.PLAIN, (int) (13*size.multiplyer));
	}
	public static enum Size{
		SMALL(1),
		NORMAL(1.2),
		BIG(2);
		public final double multiplyer;

		private Size(double multiplyer) {
			this.multiplyer = multiplyer;
		}
	}
}

