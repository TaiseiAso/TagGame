package game;

import java.awt.Point;

public class StageMenuPlay extends PlayClass{
	/** ステージメニュー画面のパラメータを管理するクラスのオブジェクト */
	private StageMenuParam stageMenuParam;
	/** ステージ作成画面のパラメータを管理するクラスのオブジェクト */
	private StageCreateParam stageCreateParam;

	/** コンストラクタ */
	public StageMenuPlay(StageMenuParam stageMenuParam, StageCreateParam stageCreateParam, GameParam gameParam){
		super(gameParam);
		this.stageMenuParam = stageMenuParam;
		this.stageCreateParam = stageCreateParam;
	}

	public void play() {
		if(gameParam.isOperation()){
			stageMenuParam.setMouseOn(mouseLook(stageMenuParam.getObjectList()));

			Point point = gameParam.getMouseParam().getPoint();

			// ステージセレクトバーのスクロール処理
			if(gameParam.getMouseParam().isLeftClick()){
				if(stageMenuParam.isScrollable()){
					int scr = stageMenuParam.getScroll() + point.x - stageMenuParam.getMouseScroll();
					if(scr > 500){
						scr = 500;
					}else if(scr < -100*gameParam.getCreatedStages().size() + 320){
						scr = -100*gameParam.getCreatedStages().size() + 320;
					}
					stageMenuParam.setSelectScroll(scr);
				}
			}else{
				stageMenuParam.setScrollable(false);
			}

			if(gameParam.getMouseParam().isLeftClickMoment()){
				switch(stageMenuParam.getMouseOn()){
				case 1:	// ステージの新規作成
					stageCreateParam.setDrawingStage(new Stage());
					stageCreateParam.setSaved(false);
					stageCreateParam.setNewStageMake(true);
					toGameMove(7, true, 1, true, true);
					break;

				case 2:	// ステージの編集
					if(stageMenuParam.getSelectedStage() >= 0){
						stageCreateParam.setDrawingStage(gameParam.getCreatedStages().get(stageMenuParam.getSelectedStage()).clone());
						stageCreateParam.setSaved(true);
						stageCreateParam.setNewStageMake(false);
						stageCreateParam.setEditStageNo(stageMenuParam.getSelectedStage());
						stageCreateParam.setObjectVisible(15, false);
						toGameMove(7, true, 1, true, true);
					}
					break;

				case 3:	// 複製
					if(stageMenuParam.getSelectedStage() >= 0){
						gameParam.getCreatedStages().add(
							gameParam.getCreatedStages().get(
								stageMenuParam.getSelectedStage()
							).clone()
						);
						// 選択中のステージを、今複製したステージに変更する
						stageMenuParam.setSelectedStage(gameParam.getCreatedStages().size() - 1);
						// ステージセレクトのスクロールバーの位置を調節する
						int scr1 = -100*gameParam.getCreatedStages().size() + 800;
						if(stageMenuParam.getSelectScroll() > scr1){
							stageMenuParam.setSelectScroll(scr1);
						}
						gameParam.getDataIO().saveStage("createStages.txt", gameParam.getCreatedStages(), true);
					}
					break;

				case 4:	// 削除
					if(stageMenuParam.getSelectedStage() >= 0){
						gameParam.getCreatedStages().remove(stageMenuParam.getSelectedStage());
						// ステージの選択状態を解除する
						stageMenuParam.setSelectedStage(-1);
						// ステージセレクトのスクロールバーの位置を調節する
						int scr2 = -100*gameParam.getCreatedStages().size() + 320;
						if(stageMenuParam.getSelectScroll() < scr2){
							stageMenuParam.setSelectScroll(scr2);
						}
						gameParam.getDataIO().saveStage("createStages.txt", gameParam.getCreatedStages(), true);
					}
					break;

				case 5:	// スクロールバー
					int dis = point.x - stageMenuParam.getSelectScroll();
					if(dis >= 0){
						int pos = dis/100;
						int posJudge = dis%100;
						if(pos < gameParam.getCreatedStages().size() && posJudge <= 80){
							stageMenuParam.setSelectedStage(pos);
						}
					}
					stageMenuParam.setScroll(stageMenuParam.getSelectScroll());
					stageMenuParam.setMouseScroll(point.x);
					stageMenuParam.setScrollable(true);
					break;

				case 6:	// 戻る
					toGameMove(2, true, 0, false, true);
					break;

					default:
						break;
				}
			}

			// オブジェクトの可視状態の更新
			if(stageMenuParam.getSelectedStage() == -1){
				stageMenuParam.setObjectVisible(1, false);
				stageMenuParam.setObjectVisible(2, false);
				stageMenuParam.setObjectVisible(3, false);
				stageMenuParam.setObjectVisible(8, true);
			}else{
				stageMenuParam.setObjectVisible(1, true);
				stageMenuParam.setObjectVisible(2, true);
				stageMenuParam.setObjectVisible(3, true);
				stageMenuParam.setObjectVisible(8, false);
			}
		}

		stageMenuParam.addFrame();
	}
}
