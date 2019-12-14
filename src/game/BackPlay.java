package game;

public class BackPlay extends PlayClass{
	/** 背景のパラメータを管理するクラスのオブジェクト */
	private BackParam backParam;

	/** コンストラクタ */
	public BackPlay(BackParam backParam, GameParam gameParam){
		super(gameParam);
		this.backParam = backParam;
	}

	public void play() {
		backParam.addFrame();
	}
}
