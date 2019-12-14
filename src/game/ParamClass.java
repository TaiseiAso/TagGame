package game;

import java.util.ArrayList;

public abstract class ParamClass {
	/** ゲーム全体に共通するパラメータを管理するクラスのオブジェクト */
	protected GameParam gameParam;

	/** 画面の番号 */
	protected int no;
	/** 画面が開始されてからの経過フレーム */
	protected int frame;
	/** 選択中のオブジェクト */
	protected int mouseOn;

	///////////// データの入出力 /////////////
	/** データの保存や読み込みを行うクラスのオブジェクト */
	protected DataInputOutput dataIO;

	////////////// オブジェクト関係 ////////////////
	/** この画面で用いるすべてのオブジェクトを管理するクラスのオブジェクトのリスト */
	protected ArrayList<ImageObject> objectList;

	/** コンストラクタ */
	public ParamClass(GameParam gameParam){
		this.gameParam = gameParam;
		this.dataIO = gameParam.getDataIO();
		this.objectList = new ArrayList<ImageObject>();
		setFrame(0);
		setMouseOn(0);
	}

	/** 変数の初期化 */
	public abstract void clear();

	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getFrame() {
		return frame;
	}
	public void setFrame(int frame) {
		this.frame = frame;
	}
	public void addFrame(){
		this.frame ++;
	}
	public int getMouseOn() {
		return mouseOn;
	}
	public void setMouseOn(int mouseOn) {
		this.mouseOn = mouseOn;
	}

	//////////////オブジェクト関係 ////////////////
	public ArrayList<ImageObject> getObjectList() {
		return objectList;
	}
	public void setObjectVisible(int index, boolean visible){
		this.objectList.get(index).setVisible(visible);
	}
}
