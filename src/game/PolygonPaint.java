package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PolygonPaint {
	/** 透明度を適切な値に補正する */
	private float correctTransparency(float transparency){
		if(transparency > 1.0f){
			transparency = 1.0f;
		}else if(transparency < 0.0f){
			transparency = 0.0f;
		}
		return transparency;
	}

	/** 透明度をつけて単色描画(4点の座標を指定) */
	public void fillRectangle(Graphics g, Color c, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, float transparency){
		Graphics2D g2 = (Graphics2D)g;
		transparency = correctTransparency(transparency);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
		g2.setColor(c);
		int[] xPoints = {x1, x2, x3, x4};
		int[] yPoints = {y1, y2, y3, y4};
		g2.fillPolygon(xPoints, yPoints, 4);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}

	/** 透明度をつけて単色描画(左上の座標と縦と横の長さを指定) */
	public void fillRectangle(Graphics g, Color c, int x, int y, int w, int h, float transparency){
		Graphics2D g2 = (Graphics2D)g;
		transparency = correctTransparency(transparency);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
		g2.setColor(c);
		g2.fillRect(x, y, w, h);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}
}
