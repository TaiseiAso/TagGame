package game;

import java.awt.Graphics;
import java.awt.geom.Point2D;

public abstract class StagePaint3D{
	/** 描画するステージ */
	protected Stage drawedStage;

	/** 描画の手助けをするクラスのオブジェクト */
	protected PolygonPaint polygonPaint;
	/** ブロックの描画クラスのオブジェクト */
	protected BlockPaint blockPaint;

	/** ブロックの大きさ */
	private static final float blockSize = 100.0f;
	/** カメラとスクリーンとの距離 */
	private static final float disToScreen = 300.0f;
	/** カメラとブロックの正面の描画面との距離 */
	protected float disToBlock;

	/**
	 * ブロックの正面部分をスクリーンに投影したときの幅の長さ
	 * disToBlockを変更するたびに更新する
	 */
	protected float blockWidthFront;
	/**
	 * ブロックの奥部分をスクリーンに投影したときの幅の長さ
	 * disToBlockを変更するたびに更新する
	 */
	protected float blockWidthBack;
	protected float blockWidthMiddle;

	/** カメラの座標 */
	protected Point2D.Float cameraPoint;

	/** コンストラクタ */
	public StagePaint3D(){
		this.polygonPaint = new PolygonPaint();
		this.blockPaint = new BlockPaint();
		setDrawedStage(new Stage());
		setCameraPoint(new Point2D.Float());
		setUp(1200, 20.0f, 15.0f);
	}

	/** ステージを描画する前にする準備 */
	public void setUp(int disToBlock, float cameraX, float cameraY){
		this.disToBlock = disToBlock;
		updateBlockWidth();
		getCameraPoint().x = cameraX;
		getCameraPoint().y = cameraY;
	}

	/** ブロックをスクリーンに投影したときの幅の長さを更新する */
	public void updateBlockWidth(){
		blockWidthFront = blockSize*disToScreen/disToBlock;
		blockWidthBack = blockSize*disToScreen/(disToBlock + 2.0f*blockSize);
		blockWidthMiddle = blockSize*disToScreen/(disToBlock + 1.0f*blockSize);
	}

	/** 3Dマップの描画(ブロック単位) */
	public abstract void paint3DBlock(Graphics g, int i, int j, boolean up, boolean left, boolean down, boolean right);

	/** 3Dマップの描画(マップ単位) */
	public void paint3D(Graphics g){
		int width = Stage.getWidth();
		int height = Stage.getHeight();

		int minX = Math.max(0, (int)(cameraPoint.getX() - 400/blockWidthBack));
		int maxX = Math.min(width - 1, (int)(cameraPoint.getX() + 400/blockWidthBack));
		int minY = Math.max(0, (int)(cameraPoint.getY() - 300/blockWidthBack));
		int maxY = Math.min(height - 1, (int)(cameraPoint.getY() + 300/blockWidthBack));

		for(int i = minX; i < (int)cameraPoint.getX(); i ++){
			for(int j = minY; j < (int)cameraPoint.getY(); j ++){
				paint3DBlock(g, i, j, false, false, true, true);
			}
		}
		for(int i = minX; i < (int)cameraPoint.getX(); i ++){
			for(int j = maxY; j > (int)cameraPoint.getY(); j --){
				paint3DBlock(g, i, j, true, false, false, true);
			}
		}
		for(int i = maxX; i > (int)cameraPoint.getX(); i --){
			for(int j = maxY; j > (int)cameraPoint.getY(); j --){
				paint3DBlock(g, i, j, true, true, false, false);
			}
		}
		for(int i = maxX; i > (int)cameraPoint.getX(); i --){
			for(int j = minY; j < (int)cameraPoint.getY(); j ++){
				paint3DBlock(g, i, j, false, true, true, false);
			}
		}
		for(int j = minY; j < (int)cameraPoint.getY(); j ++){
			paint3DBlock(g, (int)cameraPoint.getX(), j, false, false, true, false);
		}
		for(int i = minX; i < (int)cameraPoint.getX(); i ++){
			paint3DBlock(g, i, (int)cameraPoint.getY(), false, false, false, true);
		}
		for(int j = maxY; j > (int)cameraPoint.getY(); j --){
			paint3DBlock(g, (int)cameraPoint.getX(), j, true, false, false, false);
		}
		for(int i = maxX; i > (int)cameraPoint.getX(); i --){
			paint3DBlock(g, i, (int)cameraPoint.getY(), false, true, false, false);
		}
		paint3DBlock(g, (int)cameraPoint.getX(), (int)cameraPoint.getY(), false, false, false, false);
	}

	public Stage getDrawedStage() {
		return drawedStage;
	}

	public void setDrawedStage(Stage drawedStage) {
		this.drawedStage = drawedStage;
	}

	public Point2D.Float getCameraPoint() {
		return cameraPoint;
	}

	public void setCameraPoint(Point2D.Float cameraPoint) {
		this.cameraPoint = cameraPoint;
	}

	public void addCameraPoint(float dx, float dy){
		this.cameraPoint.x += dx;
		this.cameraPoint.y += dy;
	}

	public float getDisToBlock() {
		return disToBlock;
	}

	public void setDisToBlock(float disToBlock) {
		this.disToBlock = disToBlock;
		updateBlockWidth();
	}

	public float getBlockWidthFront() {
		return blockWidthFront;
	}

	public void setBlockWidthFront(float blockWidthFront) {
		this.blockWidthFront = blockWidthFront;
	}

	public float getBlockWidthBack() {
		return blockWidthBack;
	}

	public void setBlockWidthBack(float blockWidthBack) {
		this.blockWidthBack = blockWidthBack;
	}

	public float getBlockWidthMiddle() {
		return blockWidthMiddle;
	}

	public void setBlockWidthMiddle(float blockWidthMiddle) {
		this.blockWidthMiddle = blockWidthMiddle;
	}
}
