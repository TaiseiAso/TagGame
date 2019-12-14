package game;

import java.awt.Graphics;

public class ResultPaint extends PaintClass{
	/** リザルト画面のパラメータを管理するクラスのオブジェクト */
	private ResultParam resultParam;

	/** コンストラクタ */
	public ResultPaint(ResultParam resultParam, GameParam gameParam){
		super(gameParam);
		this.resultParam = resultParam;
	}

	public void paint(Graphics g) {
		if(!gameParam.isGameMove()){
			ResultData resultData = gameParam.getResultData();
			paintNumber(g, resultData.getRating(), 0, 520, 175);
			int winCount = resultData.getWinCount();
			int battleCount = resultData.getBattleCount();
			paintNumber(g, winCount, 0, 520, 275);
			paintNumber(g, battleCount, 0, 520, 375);
			int winLevel;
			if(battleCount == 0){
				winLevel =  0;
			}else{
				winLevel = (100*winCount)/battleCount;
			}
			paintNumber(g, winLevel, 1, 520, 475);
		}

		paintObject(g, resultParam);
	}
}
