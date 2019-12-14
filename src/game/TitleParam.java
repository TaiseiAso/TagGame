package game;

public class TitleParam extends ParamClass{
	/** コンストラクタ */
	public TitleParam(GameParam gameParam){
		super(gameParam);
		this.dataIO.loadObject("objectTitle.txt", this.objectList);
		setNo(1);
	}

	/** 変数の初期化 */
	public void clear() {
		setFrame(0);
	}
}
