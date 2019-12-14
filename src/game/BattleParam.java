package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleParam extends ParamClass {
	/** 場面フラグ */
	private int flag;
	/** カウントダウン */
	private int countTime;
	/** 待ち時間 */
	private int waitTime;
	/** バトルの時間 */
	private int battleMaxTime;

	/** ポーズ中かどうか */
	private boolean pause;
	/** 戦況確認画面を表示しているかどうか */
	private boolean rankingView;
	/** ランキング */
	private int[] ranking;
	/** ステージ描画 */
	private StageGamePaint3D stagePaint;
	/** 参加するcpuの数 */
	private int cpuSum;

	/** 乱数生成 */
	private Random rnd;

	/** 全プレイヤーのベースカラー */
	private Color[] color;

	/** アクションを実行するクラスのオブジェクト */
	private ActionPlay actionPlay;

	/** コンストラクタ */
	public BattleParam(GameParam gameParam){
		super(gameParam);
		setNo(9);

		setFrame(0);
		setMouseOn(0);

		setFlag(0);
		setBattleMaxTime(6000);
		setWaitTime(180);
		setPause(false);
		setRankingView(false);

		this.ranking = new int[8];
		this.stagePaint = new StageGamePaint3D(null, 0);
		rnd = new Random();

		color = new Color[7];
		color[0] = new Color(255,70,220);
		color[1] = new Color(70,255,100);
		color[2] = new Color(255,220, 70);
		color[3] = new Color(255,190,60);
		color[4] = new Color(70,255,255);
		color[5] = new Color(255,80,80);
		color[6] = new Color(130, 130, 130);

		this.setActionPlay(new ActionPlay(gameParam, this));
	}

	/** 変数の初期化 */
	public void clear() {
		setFrame(0);
		setMouseOn(0);

		setFlag(0);
		setCountTime(this.battleMaxTime);
		setWaitTime(180);
		setPause(false);
		setRankingView(false);

		// ランキングの初期化
		for(int i = 0; i < 8; i ++){
			ranking[i] = i;
		}

		// プレイヤーの初期化
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("YOU", new Color(60, 160, 255), true));
		for(int i = 0; i < cpuSum; i ++){
			players.add(new Player("CPU"+String.valueOf(i+1), color[i], false));
		}
		players.get(rnd.nextInt(cpuSum)).setHuman(false);
		this.stagePaint.setPlayers(players);
		this.stagePaint.playersInit();

		// ポイントの初期化
		this.stagePaint.setPointMaxSum(200);
		this.stagePaint.pointsInit();

		// カメラの設定
		this.stagePaint.setUpGame(300);

		// 	アクション実行のためにステージやプレイヤーなどの情報を与える
		this.actionPlay.setStagePaint(stagePaint);
	}

	public boolean isPause() {
		return pause;
	}
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	public StageGamePaint3D getStagePaint() {
		return stagePaint;
	}
	public void setStagePaint(StageGamePaint3D stagePaint) {
		this.stagePaint = stagePaint;
	}
	public int getCpuSum() {
		return cpuSum;
	}
	public void setCpuSum(int cpuSum) {
		this.cpuSum = cpuSum;
	}
	public boolean isRankingView() {
		return rankingView;
	}
	public void setRankingView(boolean rankingView) {
		this.rankingView = rankingView;
	}
	public int[] getRanking() {
		return ranking;
	}
	public void setRanking(int[] ranking) {
		this.ranking = ranking;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getCountTime() {
		return countTime;
	}
	public void setCountTime(int countTime) {
		this.countTime = countTime;
	}
	public int getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	public void minusCountTime(){
		this.countTime --;
	}
	public void minusWaitTime(){
		this.waitTime --;
	}
	public int getBattleMaxTime() {
		return battleMaxTime;
	}
	public void setBattleMaxTime(int battleMaxTime) {
		this.battleMaxTime = battleMaxTime;
	}
	public ActionPlay getActionPlay() {
		return actionPlay;
	}
	public void setActionPlay(ActionPlay actionPlay) {
		this.actionPlay = actionPlay;
	}
}
