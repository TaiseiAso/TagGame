package game;

public class TitlePlay extends PlayClass{
	/** タイトル画面のパラメータを管理するクラスのオブジェクト */
	private TitleParam titleParam;

	/** コンストラクタ */
	public TitlePlay(TitleParam titleParam, GameParam gameParam){
		super(gameParam);
		this.titleParam = titleParam;
	}

	public void play() {
		if(gameParam.isOperation()){
			if(gameParam.getMouseParam().isLeftClickMoment()){
				this.toGameMove(2, true, 1, true, true);
			}
		}

		titleParam.addFrame();
	}
}
