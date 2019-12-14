package game;

import java.util.ArrayList;

public class GameParam {
	/** ゲーム画面を識別する */
	private int gameMode;
	/** ゲーム起動時からの経過フレーム */
	private long totalFrame;
	/** 操作可能かどうか */
	private boolean operation;

	///////////// 画面遷移関連 //////////////////
	/** 画面遷移の長さ */
	private int gameMoveMaxFrame;
	/** 画面遷移が開始された瞬間かどうかを識別する */
	private boolean gameMoveStart;
	/** 画面遷移中かどうか */
	private boolean gameMove;
	/** 画面遷移の経過フレーム数 */
	private int gameMoveFrame;
	/** 画面遷移後のゲーム画面識別番号 */
	private int postGameMode;
	/** 遷移先の画面の初期化を行うかどうか */
	private boolean gameMoveClear;
	/** 画面遷移のときブラックアウトまたはホワイトアウトを描画するかどうか
	 * 0:なし 1:ブラックアウト 2:ホワイトアウト*/
	private int gameMoveFade;
	/** 画面遷移前の画面の遷移の方向 */
	private boolean gameMoveNext;
	/** 画面遷移後の画面の遷移の方向 */
	private boolean postGameMoveNext;

	////////////// マウス関連 ///////////////////
	/** マウス情報を管理するクラスのオブジェクト */
	private MouseParam mouseParam;

	///////////// セーブするパラメータ ///////////
	/** 作成したステージを管理 */
	private ArrayList<Stage> createdStages;
	/** 戦績データを管理 */
	private ResultData resultData;

	///////////// データの入出力 /////////////
	/** データの保存や読み込みを行うクラスのオブジェクト */
	private DataInputOutput dataIO;

	//////////////画像関係 ////////////////
	/** ゲームに使用するすべての画像を番号順に管理するクラスのオブジェクト */
	private ImageBox imageBox;
	/** 描画を手助けするクラスのオブジェクト */
	private PolygonPaint polygonPaint;

	/** コンストラクタ */
	public GameParam(){
		setMouseParam(new MouseParam());
		this.dataIO = new DataInputOutput();
		this.imageBox = new ImageBox();
		this.polygonPaint = new PolygonPaint();
		clear();
	}

	/** 変数の初期化 */
	public void clear(){
		setGameMode(1);
		setTotalFrame(0);
		setOperation(true);

		///////////// 画面遷移関連 //////////////////
		setGameMoveMaxFrame(60);
		setGameMoveStart(false);
		setGameMove(false);
		setGameMoveFrame(0);
		setPostGameMode(0);
		setGameMoveClear(false);
		setGameMoveFade(0);
		setGameMoveNext(true);
		setPostGameMoveNext(true);

		///////////// セーブするパラメータ ///////////
		setCreatedStages(new ArrayList<Stage>());
		dataIO.loadStage("createStages.txt", createdStages);
		setResultData(new ResultData());
		dataIO.loadResult("saveResult.txt", resultData);
	}

	/** ステージデータを初期化 */
	public void stageInit(){
		this.createdStages.clear();
	}

	/** 戦績を初期化 */
	public void resultInit(){
		this.resultData.setRating(1500);
		this.resultData.setWinCount(0);
		this.resultData.setBattleCount(0);
	}

	public int getGameMode() {
		return gameMode;
	}
	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}
	public long getTotalFrame() {
		return totalFrame;
	}
	public void setTotalFrame(long totalFrame) {
		this.totalFrame = totalFrame;
	}
	public void addTotalFrame(){
		this.totalFrame ++;
	}
	public boolean isOperation() {
		return operation;
	}
	public void setOperation(boolean operation) {
		this.operation = operation;
	}

	///////////// 画面遷移関連 //////////////////
	public int getGameMoveMaxFrame() {
		return gameMoveMaxFrame;
	}
	public void setGameMoveMaxFrame(int gameMoveMaxFrame) {
		this.gameMoveMaxFrame = gameMoveMaxFrame;
	}
	public boolean isGameMoveStart() {
		return gameMoveStart;
	}
	public void setGameMoveStart(boolean gameMoveStart) {
		this.gameMoveStart = gameMoveStart;
	}
	public boolean isGameMove() {
		return gameMove;
	}
	public void setGameMove(boolean gameMove) {
		this.gameMove = gameMove;
	}
	public int getGameMoveFrame() {
		return gameMoveFrame;
	}
	public void setGameMoveFrame(int gameMoveFrame) {
		this.gameMoveFrame = gameMoveFrame;
	}
	public void addGameMoveFrame(){
		this.gameMoveFrame ++;
	}
	public int getPostGameMode() {
		return postGameMode;
	}
	public void setPostGameMode(int postGameMode) {
		this.postGameMode = postGameMode;
	}
	public void updateGameMode(){
		this.gameMode = this.postGameMode;
	}
	public boolean isGameMoveClear() {
		return gameMoveClear;
	}
	public void setGameMoveClear(boolean gameMoveClear) {
		this.gameMoveClear = gameMoveClear;
	}
	public int getGameMoveFade() {
		return gameMoveFade;
	}
	public void setGameMoveFade(int gameMoveFade) {
		this.gameMoveFade = gameMoveFade;
	}
	public boolean isGameMoveNext() {
		return gameMoveNext;
	}
	public void setGameMoveNext(boolean gameMoveNext) {
		this.gameMoveNext = gameMoveNext;
	}
	public boolean isPostGameMoveNext() {
		return postGameMoveNext;
	}
	public void setPostGameMoveNext(boolean postGameMoveNext) {
		this.postGameMoveNext = postGameMoveNext;
	}

	//////////////マウス関連 ///////////////////
	public MouseParam getMouseParam() {
		return mouseParam;
	}
	public void setMouseParam(MouseParam mouseParam) {
		this.mouseParam = mouseParam;
	}

	///////////// セーブするパラメータ ///////////
	public ArrayList<Stage> getCreatedStages() {
		return createdStages;
	}
	public void setCreatedStages(ArrayList<Stage> createdStages) {
		this.createdStages = createdStages;
	}
	public ResultData getResultData() {
		return resultData;
	}
	public void setResultData(ResultData resultData) {
		this.resultData = resultData;
	}

	///////////// データの入出力 /////////////
	public DataInputOutput getDataIO() {
		return dataIO;
	}

	//////////////画像関係 ////////////////
	public ImageBox getImageBox() {
		return imageBox;
	}
	public PolygonPaint getPolygonPaint() {
		return polygonPaint;
	}
}
