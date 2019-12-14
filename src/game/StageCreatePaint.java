package game;

import java.awt.Color;
import java.awt.Graphics;

public class StageCreatePaint extends PaintClass{
	/** ステージメニュー画面のパラメータを管理するクラスのオブジェクト */
	private StageCreateParam stageCreateParam;

	/** コンストラクタ */
	public StageCreatePaint(StageCreateParam stageCreateParam, GameParam gameParam){
		super(gameParam);
		this.stageCreateParam = stageCreateParam;
	}

	public void paint(Graphics g) {
		int gameMoveHalfFrame = gameParam.getGameMoveMaxFrame()/2;
		if(!gameParam.isGameMove() ||
		(gameParam.getPostGameMode() == 7 && gameParam.getGameMoveFrame() >= gameMoveHalfFrame) ||
		(gameParam.getGameMode() == 7 && gameParam.getGameMoveFrame() < gameMoveHalfFrame)){

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 800, 600);

			StagePaint3D stagePaint = stageCreateParam.getStagePaint();
			stagePaint.paint3D(g);
		}

		paintObject(g, stageCreateParam);

		if(!gameParam.isGameMove()){
			g.drawString(stageCreateParam.getDrawingStage().getName(), 600, 40);
			polygonPaint.fillRectangle(g, Color.YELLOW, 705, 85+80*stageCreateParam.getSelectBlock(), 70, 70, 0.3f);
			polygonPaint.fillRectangle(g, Color.YELLOW, 15, 115+60*stageCreateParam.getSelectDrawWay(), 52, 52, 0.3f);
		}

		if(stageCreateParam.isEditName()){
			polygonPaint.fillRectangle(g, Color.BLACK, 0, 0, 800, 600, 0.5f);
		}

		if(gameParam.isGameMove()){
			paintFade(g);
		}
	}
}
