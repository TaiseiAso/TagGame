package game;

public class ResultPlay extends PlayClass{
	/** リザルト画面のパラメータを管理するクラスのオブジェクト */
	private ResultParam resultParam;

	/** コンストラクタ */
	public ResultPlay(ResultParam resultParam, GameParam gameParam){
		super(gameParam);
		this.resultParam = resultParam;
	}

	public void play() {
		if(gameParam.isOperation()){
			resultParam.setMouseOn(mouseLook(resultParam.getObjectList()));

			if(gameParam.getMouseParam().isLeftClickMoment()){
				switch(resultParam.getMouseOn()){
				case 1:	// 戻る
					toGameMove(2, true, 0, false, true);
					break;
					default:
						break;
				}
			}
		}

		resultParam.addFrame();
	}
}
