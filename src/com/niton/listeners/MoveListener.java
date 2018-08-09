package com.niton.listeners;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Window.Type;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.annotation.processing.ProcessingEnvironment;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.niton.themes.base.ResizeableTheme;

/**
 * This is the MoveListener Class
 * 
 * @author Nils Brugger
 * @version 2018-08-09
 */
public class MoveListener extends MouseAdapter {
	private ResizeableTheme theme;
	private Point lastPos;
	private Dimension bevoreDrag;
	private boolean reresize = false;
	private JFrame preview;
	private Robot preventer;

	public MoveListener(ResizeableTheme theme) {
		super();
		this.theme = theme;
		preview = new JFrame();
		preview.setUndecorated(true);
		preview.setOpacity(0.3f);
		preview.setBackground(Color.WHITE);
		preview.setType(Type.UTILITY);
		preview.setSize(0, 0);
		preview.setVisible(true);
		preview.setAlwaysOnTop(true);
		preview.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		try {
			preventer = new Robot(theme.getFrame().getDisplay());
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (e.getButton() != MouseEvent.BUTTON1)
			return;
		Point relative = e.getPoint();
		if (theme.getDragingArea().contains(relative) && !(theme.getCloseArea().contains(relative)
				|| theme.getMaximizeArea().contains(relative) || theme.getMinimizeArea().contains(relative))) {
			Point absolute = e.getLocationOnScreen();
			reresize = theme.getFrame().isMaximized();
			lastPos = absolute;
			e.consume();
		} else {
			lastPos = null;
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e))
			return;
		if (lastPos != null) {
			Point now = e.getLocationOnScreen();
			if (reresize) {
				reresize = false;
				if (bevoreDrag != null)
					theme.getFrame().setSize(bevoreDrag);
				theme.getFrame().setLocation(now.x - (theme.getFrame().getWidth() / 2), (int) now.getY());
				bevoreDrag = null;
			}
			stayInArea(now, e);
			moveWindow(now);
			borderCheck(now, false);
			lastPos = now;
			bevoreDrag = theme.getFrame().getSize();
			e.consume();
		}
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @param now
	 */
	private void borderCheck(Point now, boolean release) {
		Rectangle allowed = theme.getFrame().getMaximizedSize();
		GrabPosition position = GrabPosition.BOTTOM;

		Point bot = allowed.getLocation();
		bot.translate(0, (int) allowed.getHeight());
		if (bot.distance(now) < 5)
			position = GrabPosition.BOTTOM_LEFT;
		else if (allowed.getLocation().distance(now) < 5)
			position = GrabPosition.TOP_LEFT;

		Point topR = allowed.getLocation();
		topR.translate((int) allowed.getWidth(), 0);
		if (topR.distance(now) < 5)
			position = GrabPosition.TOP_RIGHT;

		topR.translate(0, (int) allowed.getHeight());
		if (topR.distance(now) < 5)
			position = GrabPosition.BOTTOM_RIGHT;

		if (position == GrabPosition.BOTTOM) {
			if (now.getX() - allowed.getX() < 5)
				position = GrabPosition.LEFT;
			if (now.getY() - allowed.getY() < 5)
				position = GrabPosition.TOP;
			if ((allowed.getX() + allowed.getWidth()) - now.getX() < 5)
				position = GrabPosition.RIGHT;
		}
		if (position != GrabPosition.BOTTOM) {
			if (release) {
				fit(position, allowed);
				preview.setSize(0, 0);
			} else {
				showPreview(position, allowed);
			}
		} else {
			preview.setSize(0, 0);
		}
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @param position
	 * @param allowed
	 */
	private void fit(GrabPosition position, Rectangle allowed) {
		switch (position) {
		case BOTTOM_LEFT:
			theme.getFrame().setLocation(0, (int) (allowed.getHeight() / 2));
			theme.getFrame().setSize((int) allowed.getWidth() / 2, (int) (allowed.getHeight() / 2));
			break;
		case BOTTOM_RIGHT:
			theme.getFrame().setSize((int) allowed.getWidth() / 2, (int) (allowed.getHeight() / 2));
			theme.getFrame().setLocation((int) allowed.getWidth() / 2, (int) (allowed.getHeight() / 2));
			break;
		case LEFT:
			theme.getFrame().setLocation(0, 0);
			theme.getFrame().setSize((int) (allowed.getWidth() / 2), (int) allowed.getHeight());
			break;
		case RIGHT:
			theme.getFrame().setLocation((int) (allowed.getWidth() / 2), 0);
			theme.getFrame().setSize((int) (allowed.getWidth() / 2), (int) allowed.getHeight());
			break;
		case TOP:
			theme.getFrame().setBounds(allowed);
			break;
		case TOP_LEFT:
			theme.getFrame().setLocation(0, 0);
			theme.getFrame().setSize((int) allowed.getWidth() / 2, (int) (allowed.getHeight() / 2));
			break;
		case TOP_RIGHT:
			theme.getFrame().setLocation((int) (allowed.getWidth() / 2), 0);
			theme.getFrame().setSize((int) allowed.getWidth() / 2, (int) (allowed.getHeight() / 2));
			break;
		default:
			return;
		}
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2018-08-09
	 * @param position
	 */
	private void showPreview(GrabPosition position, Rectangle allowed) {
		switch (position) {
		case BOTTOM_LEFT:
			preview.setLocation(0, (int) (allowed.getHeight() / 2));
			preview.setSize((int) allowed.getWidth() / 2, (int) (allowed.getHeight() / 2));
			break;
		case BOTTOM_RIGHT:
			preview.setSize((int) allowed.getWidth() / 2, (int) (allowed.getHeight() / 2));
			preview.setLocation((int) allowed.getWidth() / 2, (int) (allowed.getHeight() / 2));
			break;
		case LEFT:
			preview.setLocation(0, 0);
			preview.setSize((int) (allowed.getWidth() / 2), (int) allowed.getHeight());
			break;
		case RIGHT:
			preview.setLocation((int) (allowed.getWidth() / 2), 0);
			preview.setSize((int) (allowed.getWidth() / 2), (int) allowed.getHeight());
			break;
		case TOP:
			preview.setBounds(allowed);
			break;
		case TOP_LEFT:
			preview.setLocation(0, 0);
			preview.setSize((int) allowed.getWidth() / 2, (int) (allowed.getHeight() / 2));
			break;
		case TOP_RIGHT:
			preview.setLocation((int) (allowed.getWidth() / 2), 0);
			preview.setSize((int) allowed.getWidth() / 2, (int) (allowed.getHeight() / 2));
			break;
		default:
			return;
		}
	}

	private void moveWindow(Point now) {
		int xdif = now.x - lastPos.x;
		int ydif = now.y - lastPos.y;
		Point windowPos = theme.getFrame().getLocation();
		windowPos.translate(xdif, ydif);
		theme.getFrame().setLocation(windowPos);
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

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1)
			return;
		borderCheck(e.getLocationOnScreen(), true);
		if(lastPos != null)
			e.consume();
		lastPos = null;
	}
}
