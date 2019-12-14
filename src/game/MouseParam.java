package game;

import java.awt.Point;

public class MouseParam {
	////////////// マウス座標関連 ////////////////
	/** 1フレーム前のマウスの座標 */
	private Point prePoint;
	/** マウスの位置 */
	private Point point;

	////////////// 押されているかどうか ///////////
	/** 左クリックが押されているか */
	private boolean leftClick;
	/** マウスホイールが押されているか */
	private boolean middleClick;
	/** 右クリックが押されているか */
	private boolean rightClick;

	/////////////// 押された瞬間かどうか //////////////
	/** 左クリックが押された瞬間かどうかを識別する */
	private boolean leftClickMoment;
	/** マウスホイールが押された瞬間かどうかを識別する */
	private boolean middleClickMoment;
	/** 右クリックが押された瞬間かどうかを識別する */
	private boolean rightClickMoment;

	//////////////// 離された瞬間かどうか //////////////
	/** 左クリックを離した瞬間かどうかを識別する */
	private boolean leftLeaveMoment;
	/** マウスホイールを離した瞬間かどうかを識別する */
	private boolean middleLeaveMoment;
	/** 右クリックを離した瞬間かどうかを識別する */
	private boolean rightLeaveMoment;

	//////////////// マウスホイール回転関連 //////////////////
	/** マウスホイールの回転 */
	private int notches;
	/** 1つ前のフレームからのマウスホイールの回転の変位 */
	private int notchesMoment;

	///////////////////////////////////////////////////////////

	/** コンストラクタ */
	public MouseParam(){
		//////////////マウス座標関連 ////////////////
		setPrePoint(new Point(0, 0));
		setPoint(new Point(0, 0));

		////////////// 押されているかどうか ///////////
		setLeftClick(false);
		setMiddleClick(false);
		setRightClick(false);

		/////////////// 押された瞬間かどうか //////////////
		setLeftClickMoment(false);
		setMiddleClickMoment(false);
		setRightClickMoment(false);

		////////////////離された瞬間かどうか //////////////
		setLeftLeaveMoment(false);
		setMiddleLeaveMoment(false);
		setRightLeaveMoment(false);

		////////////////マウスホイール回転関連 //////////////////
		setNotches(0);
		setNotchesMoment(0);
	}

	/**
	 * マウス情報を更新する
	 * @param mouse マウスイベントを受け取るクラスのオブジェクト
	 */
	public void updateMouse(Mouse mouse){
		// マウス座標を更新
		updatePoint();
		setPoint(mouse.getPoint());

		// マウスホイールの回転を更新
		int notches = mouse.getNotches();
		setNotchesMoment(notches - getNotches());
		setNotches(notches);

		// 左クリック関連
		if(!isLeftClick()){
			if(mouse.getLeftClick()){
				setLeftClickMoment(true);
				setLeftClick(true);
			}
			setLeftLeaveMoment(false);
		}else{
			if(!mouse.getLeftClick()){
				setLeftClick(false);
				setLeftLeaveMoment(true);
			}
			setLeftClickMoment(false);
		}

		// マウスホイール関連
		if(!isMiddleClick()){
			if(mouse.getMiddleClick()){
				setMiddleClickMoment(true);
				setMiddleClick(true);
			}
			setMiddleLeaveMoment(false);
		}else{
			if(!mouse.getMiddleClick()){
				setMiddleClick(false);
				setMiddleLeaveMoment(true);
			}
			setMiddleClickMoment(false);
		}

		// 右クリック関連
		if(!isRightClick()){
			if(mouse.getRightClick()){
				setRightClickMoment(true);
				setRightClick(true);
			}
			setRightLeaveMoment(false);
		}else{
			if(!mouse.getRightClick()){
				setRightClick(false);
				setRightLeaveMoment(true);
			}
			setRightClickMoment(false);
		}
	}

	//////////////マウス座標関連 ////////////////
	public Point getPrePoint() {
		return prePoint;
	}
	public void setPrePoint(Point prePoint) {
		this.prePoint = prePoint;
	}
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public void updatePoint(){
		this.prePoint = this.point;
	}
	public boolean movePoint(){
		if(point != prePoint){
			return true;
		}
		return false;
	}

	////////////// 押されているかどうか ///////////
	public boolean isLeftClick() {
		return leftClick;
	}
	public void setLeftClick(boolean leftClick) {
		this.leftClick = leftClick;
	}
	public boolean isMiddleClick() {
		return middleClick;
	}
	public void setMiddleClick(boolean middleClick) {
		this.middleClick = middleClick;
	}
	public boolean isRightClick() {
		return rightClick;
	}
	public void setRightClick(boolean rightClick) {
		this.rightClick = rightClick;
	}

	/////////////// 押された瞬間かどうか //////////////
	public boolean isLeftClickMoment() {
		return leftClickMoment;
	}
	public void setLeftClickMoment(boolean leftClickMoment) {
		this.leftClickMoment = leftClickMoment;
	}
	public boolean isMiddleClickMoment() {
		return middleClickMoment;
	}
	public void setMiddleClickMoment(boolean middleClickMoment) {
		this.middleClickMoment = middleClickMoment;
	}
	public boolean isRightClickMoment() {
		return rightClickMoment;
	}
	public void setRightClickMoment(boolean rightClickMoment) {
		this.rightClickMoment = rightClickMoment;
	}

	////////////////離された瞬間かどうか //////////////
	public boolean isLeftLeaveMoment() {
		return leftLeaveMoment;
	}
	public void setLeftLeaveMoment(boolean leftLeaveMoment) {
		this.leftLeaveMoment = leftLeaveMoment;
	}
	public boolean isMiddleLeaveMoment() {
		return middleLeaveMoment;
	}
	public void setMiddleLeaveMoment(boolean middleLeaveMoment) {
		this.middleLeaveMoment = middleLeaveMoment;
	}
	public boolean isRightLeaveMoment() {
		return rightLeaveMoment;
	}
	public void setRightLeaveMoment(boolean rightLeaveMoment) {
		this.rightLeaveMoment = rightLeaveMoment;
	}

	////////////////マウスホイール回転関連 //////////////////
	public int getNotches() {
		return notches;
	}
	public void setNotches(int notches) {
		this.notches = notches;
	}
	public int getNotchesMoment() {
		return notchesMoment;
	}
	public void setNotchesMoment(int notchesMoment) {
		this.notchesMoment = notchesMoment;
	}
}
