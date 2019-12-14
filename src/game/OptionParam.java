package game;

public class OptionParam extends ParamClass{
	/** コンストラクタ */
	public OptionParam(GameParam gameParam){
		super(gameParam);
		this.dataIO.loadObject("objectOption.txt", this.objectList);
		setNo(6);
	}

	/** 変数の初期化 */
	public void clear() {
		setFrame(0);
		setMouseOn(0);
	}
}
