package game;

import java.awt.Color;
import java.awt.Graphics;

public class BlockPaint {
	/** 草ブロックの正面部分の描画 */
	public void paintGrassBlockFront(Graphics g, int no, int x, int y, int w, int h){
		if(no == 1){	// 一番上の草ブロック
			g.setColor(new Color(153, 102, 0));
			g.fillRect(x, y, w, h/2);

			g.setColor(new Color(153, 81, 0));
			g.fillRect(x, y+h/2, w, h - h/2);

			g.setColor(new Color(51, 102, 51));
			int[] xPoints = {x, x+w/4, x+w/2, x+3*w/4, x+w, x+w, x};
			int[] yPoints = {y+h/6, y+h/2, y+h/6, y+h/2, y+h/6, y, y};
			g.fillPolygon(xPoints, yPoints, 7);
		}else if(no == 2){	// 真ん中の草ブロック
			g.setColor(new Color(153, 102, 0));
			g.fillRect(x, y, w, h/2);

			g.setColor(new Color(153, 81, 0));
			g.fillRect(x, y+h/2, w, h - h/2);
		}else if(no == 3){	// 一番下の草ブロック
			g.setColor(new Color(153, 102, 0));
			g.fillRect(x, y, w, h/2);

			g.setColor(new Color(153, 81, 0));
			g.fillRect(x, y+h/2, w, h - h/2);

			g.setColor(new Color(51, 102, 51));
			int[] xPoints = {x, x+w/2, x+w, x+w, x};
			int[] yPoints = {y+5*h/6, y+11*h/12, y+5*h/6, y+h, y+h};
			g.fillPolygon(xPoints, yPoints, 5);
		}else if(no == 4){	// 一番上かつ一番下の草ブロック
			g.setColor(new Color(153, 102, 0));
			g.fillRect(x, y, w, h/2);

			g.setColor(new Color(153, 81, 0));
			g.fillRect(x, y+h/2, w, h - h/2);

			g.setColor(new Color(51, 102, 51));
			int[] xPoints1 = {x, x+w/4, x+w/2, x+3*w/4, x+w, x+w, x};
			int[] yPoints1 = {y+h/6, y+h/2, y+h/6, y+h/2, y+h/6, y, y};
			g.fillPolygon(xPoints1, yPoints1, 7);

			g.setColor(new Color(51, 102, 51));
			int[] xPoints2 = {x, x+w/2, x+w, x+w, x};
			int[] yPoints2 = {y+5*h/6, y+11*h/12, y+5*h/6, y+h, y+h};
			g.fillPolygon(xPoints2, yPoints2, 5);
		}
	}

	/** 草ブロックの側面部分の描画 */
	public void paintGrassBlockSide(Graphics g, int no, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
		if(no == 1){	// ブロックの上
			g.setColor(new Color(51, 132, 51));
			int[] xPoints = {x1, x2, x3, x4};
			int[] yPoints = {y1, y2, y3, y4};
			g.fillPolygon(xPoints, yPoints, 4);

		}else if(no == 2){	// 一番上のブロックの側面
			int dy1 = y1 + (y2 - y1)/6;
			int dy2 = y4 + (y3 - y4)/6;

			int dy3 = y1 + (y2 - y1)/2;
			int dy4 = y4 + (y3 - y4)/2;

			g.setColor(new Color(51, 72, 51));
			int[] xPoints1 = {x1, x2, x3, x4};
			int[] yPoints1 = {y1, dy1, dy2, y4};
			g.fillPolygon(xPoints1, yPoints1, 4);

			g.setColor(new Color(133, 82, 0));
			int[] xPoints2 = {x1, x2, x3, x4};
			int[] yPoints2 = {dy1, dy3, dy4, dy2};
			g.fillPolygon(xPoints2, yPoints2, 4);

			g.setColor(new Color(133, 61, 0));
			int[] xPoints3 = {x1, x2, x3, x4};
			int[] yPoints3 = {dy3, y2, y3, dy4};
			g.fillPolygon(xPoints3, yPoints3, 4);

		}else if(no == 3){	// 真ん中のブロックの側面
			int dy1 = y1 + (y2 - y1)/2;
			int dy2 = y4 + (y3 - y4)/2;

			g.setColor(new Color(133, 82, 0));
			int[] xPoints1 = {x1, x2, x3, x4};
			int[] yPoints1 = {y1, dy1, dy2, y4};
			g.fillPolygon(xPoints1, yPoints1, 4);

			g.setColor(new Color(133, 61, 0));
			int[] xPoints2 = {x1, x2, x3, x4};
			int[] yPoints2 = {dy1, y2, y3, dy2};
			g.fillPolygon(xPoints2, yPoints2, 4);

		}else if(no == 4){	// 一番下のブロックの側面
			int dy1 = y1 + (y2 - y1)/2;
			int dy2 = y4 + (y3 - y4)/2;

			int dy3 = y1 + 5*(y2 - y1)/6;
			int dy4 = y4 + 5*(y3 - y4)/6;

			g.setColor(new Color(133, 82, 0));
			int[] xPoints1 = {x1, x2, x3, x4};
			int[] yPoints1 = {y1, dy1, dy2, y4};
			g.fillPolygon(xPoints1, yPoints1, 4);

			g.setColor(new Color(133, 61, 0));
			int[] xPoints2 = {x1, x2, x3, x4};
			int[] yPoints2 = {dy1, dy3, dy4, dy2};
			g.fillPolygon(xPoints2, yPoints2, 4);

			g.setColor(new Color(51, 72, 51));
			int[] xPoints3 = {x1, x2, x3, x4};
			int[] yPoints3 = {dy3, y2, y3, dy4};
			g.fillPolygon(xPoints3, yPoints3, 4);

		}else if(no == 5){	// 一番上かつ一番下のブロックの側面
			int dy1 = y1 + (y2 - y1)/6;
			int dy2 = y4 + (y3 - y4)/6;

			int dy3 = y1 + (y2 - y1)/2;
			int dy4 = y4 + (y3 - y4)/2;

			int dy5 = y1 + 5*(y2 - y1)/6;
			int dy6 = y4 + 5*(y3 - y4)/6;

			g.setColor(new Color(51, 72, 51));
			int[] xPoints1 = {x1, x2, x3, x4};
			int[] yPoints1 = {y1, dy1, dy2, y4};
			g.fillPolygon(xPoints1, yPoints1, 4);

			g.setColor(new Color(133, 82, 0));
			int[] xPoints2 = {x1, x2, x3, x4};
			int[] yPoints2 = {dy1, dy3, dy4, dy2};
			g.fillPolygon(xPoints2, yPoints2, 4);

			g.setColor(new Color(133, 61, 0));
			int[] xPoints3 = {x1, x2, x3, x4};
			int[] yPoints3 = {dy3, dy5, dy6, dy4};
			g.fillPolygon(xPoints3, yPoints3, 4);

			g.setColor(new Color(51, 72, 51));
			int[] xPoints4 = {x1, x2, x3, x4};
			int[] yPoints4 = {dy5, y2, y3, dy6};
			g.fillPolygon(xPoints4, yPoints4, 4);

		}else if(no == 6){	// ブロックの下
			g.setColor(new Color(51, 72, 51));
			int[] xPoints = {x1, x2, x3, x4};
			int[] yPoints = {y1, y2, y3, y4};
			g.fillPolygon(xPoints, yPoints, 4);
		}
	}

	/** 水中ブロックの正面部分の描画 */
	public void paintWaterBlockFront(Graphics g, int no, int x, int y, int w, int h){
		if(no == 1){	// 一番上の水中ブロック
			g.setColor(new Color(0, 202, 255));
			g.fillRect(x, y, w, h/8);
			g.fillRect(x, y+h/4, w, h/8);
			g.setColor(new Color(0, 152, 255));
			g.fillRect(x, y+h/2, w, h/8);
			g.fillRect(x, y+3*h/4, w, h/8);

		}else if(no == 2){	// 真ん中の水中ブロック
			g.setColor(new Color(0, 152, 255));
			g.fillRect(x, y, w, h/8);
			g.fillRect(x, y+h/4, w, h/8);
			g.fillRect(x, y+h/2, w, h/8);
			g.fillRect(x, y+3*h/4, w, h/8);

		}else if(no == 3){	// 一番下のブロック
			g.setColor(new Color(0, 152, 255));
			g.fillRect(x, y, w, h/8);
			g.fillRect(x, y+h/4, w, h/8);
			g.setColor(new Color(0, 102, 255));
			g.fillRect(x, y+h/2, w, h/8);
			g.fillRect(x, y+3*h/4, w, h/8);

		}else if(no == 4){	// 一番上かつ一番下のブロック
			g.setColor(new Color(0, 202, 255));
			g.fillRect(x, y, w, h/8);
			g.fillRect(x, y+h/4, w, h/8);
			g.setColor(new Color(0, 102, 255));
			g.fillRect(x, y+h/2, w, h/8);
			g.fillRect(x, y+3*h/4, w, h/8);
		}
	}

	/** 水中ブロックの側面部分の描画 */
	public void paintWaterBlockSide(Graphics g, int no, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
		if(no == 1){	// ブロックの上
			g.setColor(new Color(50, 255, 255));
			int[] xPoints = {x1, x2, x3, x4};
			int[] yPoints = {y1, y2, y3, y4};
			g.fillPolygon(xPoints, yPoints, 4);

		}else if(no == 2){	// 一番上の水中ブロック
			int dy1 = y1 + (y2 - y1)/2;
			int dy2 = y4 + (y3 - y4)/2;

			g.setColor(new Color(0, 235, 235));
			int[] xPoints1 = {x1, x2, x3, x4};
			int[] yPoints1 = {y1, dy1, dy2, y4};
			g.fillPolygon(xPoints1, yPoints1, 4);

			g.setColor(new Color(0, 162, 235));
			int[] xPoints2 = {x1, x2, x3, x4};
			int[] yPoints2 = {dy1, y2, y3, dy2};
			g.fillPolygon(xPoints2, yPoints2, 4);

		}else if(no == 3){	// 真ん中の水中ブロック
			g.setColor(new Color(0, 162, 235));
			int[] xPoints = {x1, x2, x3, x4};
			int[] yPoints = {y1, y2, y3, y4};
			g.fillPolygon(xPoints, yPoints, 4);

		}else if(no == 4){	// 一番下のブロック
			int dy1 = y1 + (y2 - y1)/2;
			int dy2 = y4 + (y3 - y4)/2;

			g.setColor(new Color(0, 162, 235));
			int[] xPoints1 = {x1, x2, x3, x4};
			int[] yPoints1 = {y1, dy1, dy2, y4};
			g.fillPolygon(xPoints1, yPoints1, 4);

			g.setColor(new Color(0, 132, 235));
			int[] xPoints2 = {x1, x2, x3, x4};
			int[] yPoints2 = {dy1, y2, y3, dy2};
			g.fillPolygon(xPoints2, yPoints2, 4);

		}else if(no == 5){	// 一番上かつ一番下のブロック
			int dy1 = y1 + (y2 - y1)/2;
			int dy2 = y4 + (y3 - y4)/2;

			g.setColor(new Color(0, 235, 235));
			int[] xPoints1 = {x1, x2, x3, x4};
			int[] yPoints1 = {y1, dy1, dy2, y4};
			g.fillPolygon(xPoints1, yPoints1, 4);

			g.setColor(new Color(0, 132, 235));
			int[] xPoints2 = {x1, x2, x3, x4};
			int[] yPoints2 = {dy1, y2, y3, dy2};
			g.fillPolygon(xPoints2, yPoints2, 4);

		}else if(no == 6){	// ブロックの下
			g.setColor(new Color(0, 115, 255));
			int[] xPoints = {x1, x2, x3, x4};
			int[] yPoints = {y1, y2, y3, y4};
			g.fillPolygon(xPoints, yPoints, 4);
		}
	}

	/** 障害物ブロックの描画 */
	public void paintObstacle(Graphics g, int x, int y, int w, int h){
		g.setColor(new Color(162, 162, 162));
		int[] xPoints1 = {x, x, x + w, x + w};
		int[] yPoints1 = {y, y + h, y + h, y};
		g.fillPolygon(xPoints1, yPoints1, 4);
	}

	/** 背景ブロックの描画 */
	public void paintBack(Graphics g, int x, int y, int w, int h){
		g.setColor(new Color(46, 170, 87));
		int[] xPoints1 = {x, x, x + w, x + w};
		int[] yPoints1 = {y, y + h, y + h, y};
		g.fillPolygon(xPoints1, yPoints1, 4);
	}
}
