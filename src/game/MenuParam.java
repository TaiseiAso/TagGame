package game;

public class MenuParam extends ParamClass{
	/** コンストラクタ */
	public MenuParam(GameParam gameParam){
		super(gameParam);
		this.dataIO.loadObject("objectMenu.txt", this.objectList);
		setNo(2);
	}

	/** 変数の初期化 */
	public void clear() {
		setFrame(0);
		setMouseOn(0);
	}
}
