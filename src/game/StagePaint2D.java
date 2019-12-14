package game;

import java.awt.Color;
import java.awt.Graphics;

public class StagePaint2D {
	/** 描画するステージ */
	protected Stage drawedStage;

	/** ブロックを描画するクラスのオブジェクト */
	private BlockPaint blockPaint;

	/** コンストラクタ */
	public StagePaint2D(){
		setDrawedStage(new Stage());
		this.blockPaint = new BlockPaint();
	}

	/** 2Dマップの描画 */
	public void paint2D(Graphics g, int x, int y, int w, int h){
		int width = Stage.getWidth();
		int height = Stage.getHeight();
		int[][] block = drawedStage.getBlock();
		int[][] obstacle = drawedStage.getObstacle();
		int[][] back = drawedStage.getBack();

		for(int i = 0; i < height; i ++){
			for(int j = 0; j < width; j ++){
				if(obstacle[i][j] == 1){
					g.setColor(new Color(162, 162, 162));
					g.fillRect(x+w*j, y+h*i, w, h);
				}else if(block[i][j] == 1){
					blockPaint.paintGrassBlockFront(g, drawedStage.getDistBlock()[i][j], x+w*j, y+h*i, w, h);
				}else if(block[i][j] == 2){
					g.setColor(new Color(0, 152, 255));
					g.fillRect(x+w*j, y+h*i, w, h);
				}else if(back[i][j] == 1){
					g.setColor(new Color(46, 170, 87));
					g.fillRect(x+w*j, y+h*i, w, h);
				}else{
					g.setColor(new Color(255, 255, 255));
					g.fillRect(x+w*j, y+h*i, w, h);
				}
			}
		}
	}

	/** 単色の2Dマップを描画 */
	public void paint2DSimple(Graphics g, int x, int y, int w, int h){
		int width = Stage.getWidth();
		int height = Stage.getHeight();
		int[][] block = drawedStage.getBlock();
		int[][] obstacle = drawedStage.getObstacle();
		int[][] back = drawedStage.getBack();

		for(int i = 0; i < height; i ++){
			for(int j = 0; j < width; j ++){
				if(obstacle[i][j] == 1){
					g.setColor(new Color(162, 162, 162));
				}else if(block[i][j] == 1){
					if(i != 0 && block[i - 1][j] != 1){
						g.setColor(new Color(51, 102, 51));
					}else{
						if(i%2 == 0){
							g.setColor(new Color(153, 102, 0));
						}else{
							g.setColor(new Color(153, 81, 0));
						}
					}
				}else if(block[i][j] == 2){
					g.setColor(new Color(0, 102, 255));
				}else if(back[i][j] == 1){
					if((i + j)%2 == 0){
						g.setColor(new Color(46, 170, 87));
					}else{
						g.setColor(new Color(34, 139, 34));
					}
				}else{
					g.setColor(new Color(255, 255, 255));
				}
				g.fillRect(x+w*j, y+h*i, w, h);
			}
		}
	}

	public void setDrawedStage(Stage drawedStage) {
		this.drawedStage = drawedStage;
	}
}
