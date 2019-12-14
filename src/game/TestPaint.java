package game;

import java.awt.Color;
import java.awt.Graphics;

public class TestPaint extends PaintClass{
	/** テストプレイ画面のパラメータを管理するクラスのオブジェクト */
	private TestParam testParam;

	/** コンストラクタ */
	public TestPaint(TestParam testParam, GameParam gameParam){
		super(gameParam);
		this.testParam = testParam;
	}

	public void paint(Graphics g) {
		int gameMoveHalfFrame = gameParam.getGameMoveMaxFrame()/2;
		if(!gameParam.isGameMove() ||
		(gameParam.getPostGameMode() == 8 && gameParam.getGameMoveFrame() >= gameMoveHalfFrame) ||
		(gameParam.getGameMode() == 8 && gameParam.getGameMoveFrame() < gameMoveHalfFrame)){

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 800, 600);

			StageGamePaint3D stagePaint = testParam.getStagePaint();
			stagePaint.paint3D(g);

			Player user = stagePaint.getUserFromPlayers();
			int stamina = user.getStamina();
			int maxStamina = user.getMaxStamina();
			Color c;
			if(stamina <= maxStamina*0.25){
				c = Color.RED;
			}else if(stamina <= maxStamina*0.5){
				c = Color.ORANGE;
			}else if(stamina <= maxStamina*0.8){
				c = Color.GREEN;
			}else{
				c = new Color(0, 180, 255);
			}
			polygonPaint.fillRectangle(g, c, 20, 550, (int)(user.getStamina()*0.3), 30, 0.5f);
			g.setColor(Color.BLACK);
			g.drawRect(20, 550, (int)(user.getMaxStamina()*0.3), 30);
		}

		paintObject(g, testParam);

		if(gameParam.isGameMove()){
			paintFade(g);
		}
	}
}
