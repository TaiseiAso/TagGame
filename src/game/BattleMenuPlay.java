package game;

import java.awt.Point;

public class BattleMenuPlay extends PlayClass {
	/** バトルメニュー画面のパラメータを管理するクラスのオブジェクト */
	private BattleMenuParam battleMenuParam;
	/** バトル画面のパラメータを管理するクラスのオブジェクト */
	private BattleParam battleParam;

	/** コンストラクタ */
	public BattleMenuPlay(BattleMenuParam battleMenuParam, BattleParam battleParam, GameParam gameParam){
		super(gameParam);
		this.battleMenuParam = battleMenuParam;
		this.battleParam = battleParam;
	}

	public void play() {
		if(gameParam.isOperation()){
			battleMenuParam.setMouseOn(mouseLook(battleMenuParam.getObjectList()));

			Point point = gameParam.getMouseParam().getPoint();

			// ステージセレクトバーのスクロール処理
			if(gameParam.getMouseParam().isLeftClick()){
				if(battleMenuParam.isScrollable()){
					int scr = battleMenuParam.getScroll() + point.x - battleMenuParam.getMouseScroll();
					if(scr > 500){
						scr = 500;
					}else if(scr < -100*gameParam.getCreatedStages().size() + 320){
						scr = -100*gameParam.getCreatedStages().size() + 320;
					}
					battleMenuParam.setSelectScroll(scr);
				}
			}else{
				battleMenuParam.setScrollable(false);
			}

			if(gameParam.getMouseParam().isLeftClickMoment()){
				switch(battleMenuParam.getMouseOn()){
				case 1:	// スクロールバー
					int dis = point.x - battleMenuParam.getSelectScroll();
					if(dis >= 0){
						int pos = dis/100;
						int posJudge = dis%100;
						if(pos < gameParam.getCreatedStages().size() && posJudge <= 80){
							battleMenuParam.setObjectVisible(2, true);
							battleMenuParam.setSelectedStage(pos);
						}
					}
					battleMenuParam.setScroll(battleMenuParam.getSelectScroll());
					battleMenuParam.setMouseScroll(point.x);
					battleMenuParam.setScrollable(true);
					break;
				case 2:	// 戻る
					toGameMove(2, true, 0, false, true);
					break;
				case 3:	// バトルに移行する
					if(battleMenuParam.getSelectedStage() >= 0){
						battleParam.getStagePaint().setDrawedStage(gameParam.getCreatedStages().get(battleMenuParam.getSelectedStage()));
						battleParam.setCpuSum(battleMenuParam.getCpuSum());
						toGameMove(9, true, 1, true, true);
					}
					break;
				case 4:	// CPUの数を1減らす
					if(battleMenuParam.getCpuSum() > 1){
						battleMenuParam.setObjectVisible(4, true);
						battleMenuParam.setObjectVisible(battleMenuParam.getCpuSum()+5, false);
						battleMenuParam.minusCpuSum();
						if(battleMenuParam.getCpuSum() == 1){
							battleMenuParam.setObjectVisible(3, false);
						}
					}
					break;
				case 5:	// CPUの数を1増やす
					if(battleMenuParam.getCpuSum() < 7){
						battleMenuParam.setObjectVisible(3, true);
						battleMenuParam.setObjectVisible(battleMenuParam.getCpuSum()+6, true);
						battleMenuParam.addCpuSum();
						if(battleMenuParam.getCpuSum() == 7){
							battleMenuParam.setObjectVisible(4, false);
						}
					}
					break;
					default:
						break;
				}
			}
		}

		battleMenuParam.addFrame();
	}
}
