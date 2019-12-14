package game;

public class ResultParam extends ParamClass{
	/** コンストラクタ */
	public ResultParam(GameParam gameParam){
		super(gameParam);
		this.dataIO.loadObject("objectResult.txt", this.objectList);
		setNo(5);
	}

	/** 変数の初期化 */
	public void clear() {
		setFrame(0);
		setMouseOn(0);
	}
}
