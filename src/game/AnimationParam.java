package game;

public class AnimationParam {
	/** x座標 */
	private int x;
	/** y座標 */
	private int y;
	/** 横幅 */
	private int w;
	/** 縦幅 */
	private int h;
	/** 角度 */
	private int angle;
	/** 軸のx座標 */
	private int axisX;
	/** 軸のy座標 */
	private int axisY;
	/** 拡大率 */
	private float magnification;
	/** 透過率 */
	private float transparency;

	/** コンストラクタ */
	public AnimationParam(){
		setX(0);
		setY(0);
		setW(0);
		setH(0);
		setAngle(0);
		setAxisX(0);
		setAxisY(0);
		setMagnification(1.0f);
		setTransparency(1.0f);
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
	public int getAngle() {
		return angle;
	}
	public void setAngle(int angle) {
		this.angle = angle;
	}
	public int getAxisX() {
		return axisX;
	}
	public void setAxisX(int axisX) {
		this.axisX = axisX;
	}
	public int getAxisY() {
		return axisY;
	}
	public void setAxisY(int axisY) {
		this.axisY = axisY;
	}
	public float getMagnification() {
		return magnification;
	}
	public void setMagnification(float magnification) {
		this.magnification = magnification;
	}
	public float getTransparency() {
		return transparency;
	}
	public void setTransparency(float transparency) {
		this.transparency = transparency;
	}
}
