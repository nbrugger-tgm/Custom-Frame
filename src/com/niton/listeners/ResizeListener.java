package com.niton.listeners;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Window.Type;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.niton.frames.CustomFrame;
import com.niton.themes.base.ResizeableTheme;

/**
 * This is the ResizeListener Class
 * 
 * @author Nils Brugger
 * @version 2018-08-09
 */
public class ResizeListener extends MouseAdapter {
	private ResizeableTheme theme;
	private Point lastPos;
	private Robot preventer;

	public ResizeListener(ResizeableTheme theme) {
		super();
		this.theme = theme;
		try {
			preventer = new Robot(theme.getFrame().getDisplay());
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point relative = e.getPoint();
		if (theme.getCloseArea().contains(relative) || theme.getMaximizeArea().contains(relative)
				|| theme.getMinimizeArea().contains(relative)) {
			return;
		}
		if (theme.currentlyResizeable()) {
			if (getGrabPosition(e.getLocationOnScreen(), theme.getFrame().getBounds(),
					theme.getResizeRadius()) != null) {
				lastPos = e.getLocationOnScreen();
				e.consume();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e))
			return;
		if (lastPos != null) {
			Point now = e.getLocationOnScreen();
			stayInArea(now, e);
			GrabPosition pos = getGrabPosition(lastPos, theme.getFrame().getBounds(), theme.getResizeRadius());
			if (pos != null)
				resize(pos, (int) (now.getX() - lastPos.getX()), (int) (now.getY() - lastPos.getY()));
			lastPos = e.getLocationOnScreen();
			e.consume();
			CustomFrame frame = theme.getFrame();
			int cursor = Cursor.DEFAULT_CURSOR;
			if (pos != null) {
				switch (pos) {
				case BOTTOM:
					cursor = Cursor.N_RESIZE_CURSOR;
					break;
				case BOTTOM_LEFT:
					cursor = Cursor.NE_RESIZE_CURSOR;
					break;
				case BOTTOM_RIGHT:
					cursor = Cursor.NW_RESIZE_CURSOR;
					break;
				case LEFT:
					cursor = Cursor.E_RESIZE_CURSOR;
					break;
				case RIGHT:
					cursor = Cursor.E_RESIZE_CURSOR;
					break;

				}
			}
			frame.setCursor(Cursor.getPredefinedCursor(cursor));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1)
			return;
		if (lastPos != null) {
			lastPos = null;
			e.consume();
			theme.getFrame().repaint();
		}
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!theme.currentlyResizeable())
			return;
		GrabPosition pos = getGrabPosition(e.getLocationOnScreen(), theme.getFrame().getBounds(),
				theme.getResizeRadius());
		CustomFrame frame = theme.getFrame();
		int cursor = Cursor.DEFAULT_CURSOR;
		if (pos != null) {
			switch (pos) {
			case BOTTOM:
				cursor = Cursor.N_RESIZE_CURSOR;
				break;
			case BOTTOM_LEFT:
				cursor = Cursor.NE_RESIZE_CURSOR;
				break;
			case BOTTOM_RIGHT:
				cursor = Cursor.NW_RESIZE_CURSOR;
				break;
			case LEFT:
				cursor = Cursor.E_RESIZE_CURSOR;
				break;
			case RIGHT:
				cursor = Cursor.E_RESIZE_CURSOR;
				break;

			}
		}
		frame.setCursor(Cursor.getPredefinedCursor(cursor));
	}
	/**
	 * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		theme.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @param point
	 * @param bounds
	 * @return
	 */
	private GrabPosition getGrabPosition(Point point, Rectangle bounds, int disdance) {
		GrabPosition pos = null;
		Point corner = bounds.getLocation();
		if (corner.distance(point) < disdance) {
			pos = GrabPosition.TOP_LEFT;
		}
		corner.translate((int) bounds.getWidth(), 0);
		if (corner.distance(point) < disdance) {
			pos = GrabPosition.TOP_RIGHT;
		}
		corner.translate(0, (int) bounds.getHeight());
		if (corner.distance(point) < disdance) {
			pos = GrabPosition.BOTTOM_RIGHT;
		}
		corner.translate((int) -bounds.getWidth(), 0);
		if (corner.distance(point) < disdance) {
			pos = GrabPosition.BOTTOM_LEFT;
		}
		if (pos == null) {
			if (point.getX() - bounds.getX() < disdance)
				pos = GrabPosition.LEFT;
			if (point.getY() - bounds.getY() < disdance)
				pos = GrabPosition.TOP;
			if ((bounds.getHeight() + bounds.getY()) - point.getY() < disdance)
				pos = GrabPosition.BOTTOM;
			if ((bounds.getWidth() + bounds.getX()) - point.getX() < disdance)
				pos = GrabPosition.RIGHT;
		}
		return pos;
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @param grabPosition
	 * @param d
	 * @param e
	 */
	private void resize(GrabPosition grabPosition, int x, int y) {
		CustomFrame frame = theme.getFrame();
		int oldW = frame.getWidth();
		int oldH = frame.getHeight();
		int oldX = frame.getX();
		int oldY = frame.getY();
		switch (grabPosition) {
		case BOTTOM:
			frame.setSize(oldW, oldH + y);
			break;
		case BOTTOM_LEFT:
			frame.setSize(oldW - x, oldH + y);
			frame.setLocation(oldX + x, oldY);
			break;
		case BOTTOM_RIGHT:
			frame.setSize(oldW + x, oldH + y);
			break;
		case LEFT:
			frame.setSize(oldW - x, oldH);
			frame.setLocation(oldX + x, oldY);
			break;
		case RIGHT:
			frame.setSize(oldW + x, oldH);
			break;
		case TOP:
			break;
		case TOP_LEFT:
			break;
		case TOP_RIGHT:
			break;
		}
		if (frame.getWidth() <= frame.getMinimumSize().getWidth()) {
			frame.setSize((int) frame.getMinimumSize().getWidth(), frame.getHeight());
			if (grabPosition == GrabPosition.LEFT || grabPosition == GrabPosition.BOTTOM_LEFT) {
				frame.setLocation(oldX, oldY);
			}
		}
		if (frame.getHeight() <= frame.getMinimumSize().getHeight())
			frame.setSize(frame.getWidth(), (int) frame.getMinimumSize().getHeight());
	}

	private void stayInArea(Point now, InputEvent e) {
		Rectangle allowed = theme.getFrame().getMaximizedSize();
		if (!allowed.contains(now)) {
			if (preventer != null) {
				if (allowed.contains(now.x, lastPos.y)) {
					preventer.mouseMove(now.x, lastPos.y);
					now.y = lastPos.y;
				} else if (allowed.contains(lastPos.x, now.y)) {
					preventer.mouseMove(lastPos.x, now.y);
					now.x = lastPos.x;
				} else {
					preventer.mouseMove(lastPos.x, lastPos.y);
					now.setLocation(lastPos.x, lastPos.y);
				}
			}
			e.consume();
		}
	}
}
