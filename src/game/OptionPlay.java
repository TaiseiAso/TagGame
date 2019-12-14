package game;

public class OptionPlay extends PlayClass{
	/** オプション画面のパラメータを管理するクラスのオブジェクト */
	private OptionParam optionParam;

	/** コンストラクタ */
	public OptionPlay(OptionParam optionParam, GameParam gameParam){
		super(gameParam);
		this.optionParam = optionParam;
	}

	public void play() {
		if(gameParam.isOperation()){
			optionParam.setMouseOn(mouseLook(optionParam.getObjectList()));

			if(gameParam.getMouseParam().isLeftClickMoment()){
				switch(optionParam.getMouseOn()){
				case 1:
					toGameMove(2, true, 0, false, true);
					break;
				case 2:	// ステージデータ削除
					gameParam.stageInit();
					gameParam.getDataIO().saveStage("createStages.txt", gameParam.getCreatedStages(), true);
					break;
				case 3:	// 戦績削除
					gameParam.resultInit();
					gameParam.getDataIO().saveResult("saveResult.txt", gameParam.getResultData());
					break;
					default:
						break;
				}
			}
		}

		optionParam.addFrame();
	}
}
