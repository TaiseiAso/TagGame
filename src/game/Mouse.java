package game;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener{
	/** マウスの座標 */
	private Point point;
	/** マウス左クリック */
	private boolean leftClick;
	/** マウスホイールプッシュ */
	private boolean middleClick;
	/** マウス右クリック */
	private boolean rightClick;
	/** マウスホイール回転 */
	private int notches;

	/** コンストラクタ */
	public Mouse(){
		this.point = new Point();
		this.leftClick = false;
		this.middleClick = false;
		this.rightClick = false;
		this.notches = 0;
	}

	/** 外部にマウスの座標を取得させる */
	public Point getPoint(){
		return this.point;
	}

	/** 外部に左クリックを取得させる */
	public boolean getLeftClick(){
		return this.leftClick;
	}

	/** 外部にマウスホイールを取得させる */
	public boolean getMiddleClick(){
		return this.middleClick;
	}

	/** 外部に右クリックを取得させる */
	public boolean getRightClick(){
		return this.rightClick;
	}

	/** 外部にマウスホイール回転を取得させる */
	public int getNotches(){
		return this.notches;
	}

	/** マウスがドラッグされたときの処理 */
	public void mouseDragged(MouseEvent e) {
		this.point = e.getPoint();
	}

	/** マウスが動かされたときの処理 */
	public void mouseMoved(MouseEvent e) {
		this.point = e.getPoint();
	}

	/** マウスをクリックするために押したときの処理 */
	public void mousePressed(MouseEvent e) {
		int modi = e.getModifiers();
		if ((modi & MouseEvent.BUTTON1_MASK) != 0) {
			//左
			this.leftClick = true;
		}else if ((modi & MouseEvent.BUTTON2_MASK) != 0) {
			//真ん中
			this.middleClick = true;
		}else if ((modi & MouseEvent.BUTTON3_MASK) != 0) {
			//右
			this.rightClick = true;
		}
	}

	/** マウスを離したときの処理 */
	public void mouseReleased(MouseEvent e) {
		int modi = e.getModifiers();
		if ((modi & MouseEvent.BUTTON1_MASK) != 0) {
			//左
			this.leftClick = false;
		}else if ((modi & MouseEvent.BUTTON2_MASK) != 0) {
			//真ん中
			this.middleClick = false;
		}else if ((modi & MouseEvent.BUTTON3_MASK) != 0) {
			//右
			this.rightClick = false;;
		}
	}

	/** マウスホイールが回転されたときの処理 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		int n = e.getWheelRotation();
		this.notches += n/Math.abs(n);	// 1か-1
		if(this.notches < -100){
			this.notches = -100;
		}else if(this.notches > 100){
			this.notches = 100;
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {}
}
