package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TestParam extends ParamClass{
	/** ステージ描画 */
	private StageGamePaint3D stagePaint;
	/** アクションを実行するクラスのオブジェクト */
	private ActionPlay actionPlay;

	/** コンストラクタ */
	public TestParam(GameParam gameParam){
		super(gameParam);
		this.dataIO.loadObject("objectTest.txt", this.objectList);
		setNo(8);

		List<Player> players = new ArrayList<Player>();
		Player user = new Player("YOU", new Color(30, 30, 255), true);
		user.setHuman(true);
		players.add(user);
		this.stagePaint = new StageGamePaint3D(players, 0);

		setFrame(0);
		setMouseOn(0);

		this.setActionPlay(new ActionPlay(gameParam, this));
	}

	/** 変数の初期化 */
	public void clear() {
		this.stagePaint.playersInit();
		this.stagePaint.setUpGame(300);

		setFrame(0);
		setMouseOn(0);

		this.actionPlay.setStagePaint(stagePaint);
	}

	public StageGamePaint3D getStagePaint() {
		return stagePaint;
	}
	public void setStagePaint(StageGamePaint3D stagePaint) {
		this.stagePaint = stagePaint;
	}
	public ActionPlay getActionPlay() {
		return actionPlay;
	}
	public void setActionPlay(ActionPlay actionPlay) {
		this.actionPlay = actionPlay;
	}
}
