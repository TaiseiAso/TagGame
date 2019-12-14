package game;

public class BackParam extends ParamClass{
	/** コンストラクタ */
	public BackParam(GameParam gameParam){
		super(gameParam);
		setNo(0);
	}

	/** 変数の初期化 */
	public void clear() {
		setFrame(0);
	}
}
