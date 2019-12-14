package game;

import java.awt.Color;
import java.awt.Graphics;

public class StageMenuPaint extends PaintClass{
	/** ステージメニュー画面のパラメータを管理するクラスのオブジェクト */
	private StageMenuParam stageMenuParam;

	/** コンストラクタ */
	public StageMenuPaint(StageMenuParam stageMenuParam, GameParam gameParam){
		super(gameParam);
		this.stageMenuParam = stageMenuParam;
	}

	public void paint(Graphics g) {
		paintObject(g, stageMenuParam);

		if(!gameParam.isGameMove()){
			// 選択中のステージの描画
			StagePaint2D stagePaint = stageMenuParam.getStagePaint();
			if(stageMenuParam.getSelectedStage() >= 0){
				stagePaint.setDrawedStage(gameParam.getCreatedStages().get(stageMenuParam.getSelectedStage()));
				stagePaint.paint2D(g, 196, 116, 5, 5);
			}

			// ステージ一覧の描画
			for(int i = 0; i < gameParam.getCreatedStages().size(); i ++){
				int stageLeft = 100*i + stageMenuParam.getSelectScroll();

				if(-83 <= stageLeft && stageLeft <= 803){
					Stage drawedStage = gameParam.getCreatedStages().get(i);

					g.setColor(Color.WHITE);
					g.drawString(drawedStage.getName(), stageLeft, 495);

					if(stageMenuParam.getSelectedStage() == i){
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
