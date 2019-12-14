package game;

import java.awt.Point;
import java.util.ArrayList;

public class ImageObject {
	private String name;

	////////////// アニメーション管理 ////////
	private ArrayList<AnimationParam> animationList;

	////////////// マウス関連 /////////////////
	/** クリックフラグ 0:クリック不可能 1:クリック可能*/
	private int clickFlag;
	/** クリック適用範囲の左上のx座標 */
	private int x;
	/** クリック適用範囲の左上のy座標 */
	private int y;
	/** クリック適用範囲の横幅 */
	private int w;
	/** クリック適用範囲の縦幅 */
	private int h;
	/** マウスカーソルが上にあるときの拡大率 */
	private float magnification;

	////////////// 画像関係 ////////////////
	/** 使用する画像の番号 */
	private int imgNo;

	////////////// 描画関係 ////////////////
	/** 可視状態かどうか */
	private boolean visible;

	/** コンストラクタ */
	public ImageObject(){
		setName("No Name");
		setAnimationList(new ArrayList<AnimationParam>());
		setClickFlag(0);
		setX(0);
		setY(0);
		setW(0);
		setH(0);
		setMagnification(1.1f);
		setImgNo(0);
		setVisible(true);
	}

	/** クリックフラグを更新する */
	public boolean clickableCheck(Point mousePoint){
		if(clickFlag == 1){	// クリック可能
			// 今回マウスが中にあるかどうかを確認する
			if(x <= mousePoint.x && mousePoint.x <= x+w &&
					y <= mousePoint.y && mousePoint.y <= y+h){
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	////////////// アニメーション管理 ////////
	public ArrayList<AnimationParam> getAnimationList() {
		return animationList;
	}
	public void setAnimationList(ArrayList<AnimationParam> animationList) {
		this.animationList = animationList;
	}

	//////////////マウス関連 /////////////////
	public int getClickFlag() {
		return clickFlag;
	}
	public void setClickFlag(int clickFlag) {
		this.clickFlag = clickFlag;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public float getMagnification() {
		return magnification;
	}
	public void setMagnification(float magnification) {
		this.magnification = magnification;
	}

	//////////////画像関係 ////////////////
	public int getImgNo() {
		return imgNo;
	}
	public void setImgNo(int imgNo) {
		this.imgNo = imgNo;
	}

	//////////////描画関係 ////////////////
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
