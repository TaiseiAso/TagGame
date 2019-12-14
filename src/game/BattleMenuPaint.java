package game;

import java.awt.Color;
import java.awt.Graphics;

public class BattleMenuPaint extends PaintClass {
	/** バトルメニュー画面のパラメータを管理するクラスのオブジェクト */
	private BattleMenuParam battleMenuParam;

	/** コンストラクタ */
	public BattleMenuPaint(BattleMenuParam battleMenuParam, GameParam gameParam){
		super(gameParam);
		this.battleMenuParam = battleMenuParam;
	}

	public void paint(Graphics g) {
		paintObject(g, battleMenuParam);

		if(!gameParam.isGameMove()){
			StagePaint2D stagePaint = battleMenuParam.getStagePaint();

			// ステージ一覧の描画
			for(int i = 0; i < gameParam.getCreatedStages().size(); i ++){
				int stageLeft = 100*i + battleMenuParam.getSelectScroll();

				if(-83 <= stageLeft && stageLeft <= 803){
					Stage drawedStage = gameParam.getCreatedStages().get(i);

					g.setColor(Color.WHITE);
					g.drawString(drawedStage.getName(), stageLeft, 495);

					if(battleMenuParam.getSelectedStage() == i){
						g.setColor(Color.YELLOW);
						g.fillRect(stageLeft - 3, 497, 86, 66);
					}

					stagePaint.setDrawedStage(drawedStage);
					stagePaint.paint2DSimple(g, stageLeft, 500, 1, 1);
				}
			}
		}else{
			paintFade(g);
		}
	}
}
