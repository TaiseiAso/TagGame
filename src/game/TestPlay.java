package game;

public class TestPlay extends PlayClass{
	/** リザルト画面のパラメータを管理するクラスのオブジェクト */
	private TestParam  testParam;

	/** コンストラクタ */
	public TestPlay(TestParam testParam, GameParam gameParam){
		super(gameParam);
		this.testParam = testParam;
	}

	public void play() {
		if(gameParam.isOperation()){
			testParam.setMouseOn(mouseLook(testParam.getObjectList()));

			testParam.getActionPlay().play();
			if(gameParam.getMouseParam().isLeftClickMoment() && testParam.getMouseOn() == 1){
				toGameMove(7, false, 1, false, false);
			}
		}

		testParam.addFrame();
	}
}
