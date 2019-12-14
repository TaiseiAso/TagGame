package game;

import java.awt.Color;
import java.awt.Graphics;

public class StageMakePaint3D extends StagePaint3D{
	/** グリッドの種類 */
	private int grid;
	/** 描画予定のブロック */
	private int[][] nextDraw;

	/** コンストラクタ */
	public StageMakePaint3D(){
		super();
		setGrid(0);
		setNextDraw(new int[Stage.getHeight()][Stage.getWidth()]);
	}

	/** 3Dマップの描画(ブロック単位) */
	public void paint3DBlock(Graphics g, int i, int j, boolean up, boolean left, boolean down, boolean right){
		int[][] block = drawedStage.getBlock();
		int[][] obstacle = drawedStage.getObstacle();
		int[][] back = drawedStage.getBack();

		double blockX = i - cameraPoint.getX();
		double blockY = j - cameraPoint.getY();

		int backLeftX = 400 + (int)(blockWidthBack*blockX);
		int frontLeftX = 400 + (int)(blockWidthFront*blockX);
		int backRightX = 400 + (int)(blockWidthBack*(blockX + 1));
		int frontRightX = 400 + (int)(blockWidthFront*(blockX + 1));

		int backUpY = 300 + (int)(blockWidthBack*blockY);
		int frontUpY = 300 + (int)(blockWidthFront*blockY);
		int backDownY = 300 + (int)(blockWidthBack*(blockY + 1));
		int frontDownY = 300 + (int)(blockWidthFront*(blockY + 1));

		int frontWidth = frontRightX - frontLeftX;
		int frontHeight = frontDownY - frontUpY;
		int backWidth = backRightX - backLeftX;
		int backHeight = backDownY - backUpY;

		if(block[j][i] == 1){
			blockPaint.paintGrassBlockFront(g, drawedStage.getDistBlock()[j][i],
					frontLeftX,
					frontUpY,
					frontWidth, frontHeight);

			if(up && block[j - 1][i] != 1){
				blockPaint.paintGrassBlockSide(g, 1,
						backLeftX, backUpY,
						frontLeftX, frontUpY,
						frontRightX, frontUpY,
						backRightX, backUpY);
			}
			if(left && block[j][i - 1] != 1){
				blockPaint.paintGrassBlockSide(g, drawedStage.getDistBlock()[j][i] + 1,
						backLeftX, backUpY,
						backLeftX, backDownY,
						frontLeftX, frontDownY,
						frontLeftX, frontUpY);
			}
			if(down && block[j + 1][i] != 1){
				blockPaint.paintGrassBlockSide(g, 6,
						frontLeftX, frontDownY,
						backLeftX, backDownY,
						backRightX, backDownY,
						frontRightX, frontDownY);
			}
			if(right && block[j][i + 1] != 1){
				blockPaint.paintGrassBlockSide(g, drawedStage.getDistBlock()[j][i] + 1,
						frontRightX, frontUpY,
						frontRightX, frontDownY,
						backRightX, backDownY,
						backRightX, backUpY);
			}
		}else{
			if(back[j][i] == 1){
				blockPaint.paintBack(g,
						backLeftX, backUpY,
						backWidth, backHeight);
			}

			if(grid == 2){
				if(nextDraw[j][i] == 1){
					polygonPaint.fillRectangle(g, new Color(0, 0, 255), backLeftX, backUpY, backWidth, backHeight, 0.5f);
				}

				g.setColor(Color.GRAY);
				g.drawRect(backLeftX, backUpY, backWidth, backHeight);
			}

			if(block[j][i] == 2){
				if(block[j - 1][i] == 0){
					blockPaint.paintWaterBlockSide(g, 1,
							backLeftX, backUpY,
							frontLeftX, frontUpY,
							frontRightX, frontUpY,
							backRightX, backUpY);
				}
				if(block[j][i - 1] == 0){
					blockPaint.paintWaterBlockSide(g, drawedStage.getDistBlock()[j][i] + 1,
							backLeftX, backUpY,
							backLeftX, backDownY,
							frontLeftX, frontDownY,
							frontLeftX, frontUpY);
				}
				if(block[j + 1][i] == 0){
					blockPaint.paintWaterBlockSide(g, 6,
							frontLeftX, frontDownY,
							backLeftX, backDownY,
							backRightX, backDownY,
							frontRightX, frontDownY);
				}
				if(block[j][i + 1] == 0){
					blockPaint.paintWaterBlockSide(g, drawedStage.getDistBlock()[j][i] + 1,
							frontRightX, frontUpY,
							frontRightX, frontDownY,
							backRightX, backDownY,
							backRightX, backUpY);
				}
			}
			if(obstacle[j][i] == 1){
				blockPaint.paintObstacle(g,
						frontLeftX, frontUpY,
						frontWidth, frontHeight);
			}
			if(block[j][i] == 2){
				blockPaint.paintWaterBlockFront(g, drawedStage.getDistBlock()[j][i],
						frontLeftX, frontUpY,
						frontWidth, frontHeight);
			}
		}

		if(grid  ==  1){
			if(nextDraw[j][i] == 1){
				polygonPaint.fillRectangle(g, new Color(255, 0, 0), frontLeftX, frontUpY, frontWidth, frontHeight, 0.5f);
			}

			g.setColor(Color.GRAY);
			g.drawRect(frontLeftX, frontUpY, frontWidth, frontHeight);
		}
	}

	public int[][] getNextDraw() {
		return nextDraw;
	}

	public void setNextDraw(int[][] nextDraw) {
		this.nextDraw = nextDraw;
	}

	public int getGrid() {
		return grid;
	}

	public void setGrid(int grid) {
		this.grid = grid;
	}
}
