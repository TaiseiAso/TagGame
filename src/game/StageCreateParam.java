package game;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class StageCreateParam extends ParamClass{
	/** ステージの編集履歴を保存するリスト */
	private ArrayList<Stage> stageList;
	/** 編集中のステージ */
	private Stage drawingStage;
	/** 編集履歴のうちどれを編集しようとしているかを管理 */
	private int activeStage;
	/** 選択中の選択方法 */
	private int selectDrawWay;
	/** 選択中のブロック */
	private int selectBlock;
	/** 右クリックを押し始めたときのマウスの座標 */
	private Point mousePoint;
	/** 右クリックを押し始めたときのカメラの座標 */
	private Point2D.Float cameraPoint;
	/** カメラスクロール可能かどうか */
	private boolean scrollable;
	/** 編集中のステージが保存可能であるかどうか */
	private boolean saveable;

	/** ブロック描画可能かどうか */
	private boolean drawable;
	/** 編集が進んだかどうか */
	private boolean editAdvanced;
	/** 保存済みかどうか */
	private boolean saved;

	/** ステージ名変更中かどうか */
	private boolean editName;

	/** マウス上にあるブロックのx座標を保存 */
	private int mouseOnBlockX;
	/** マウス上にあるブロックのy座標を保存 */
	private int mouseOnBlockY;
	/** 現在マウス上にあるブロックのx座標を保存 */
	private int nextMouseOnBlockX;
	/** 現在マウス上にあるブロックのy座標を保存 */
	private int nextMouseOnBlockY;
	/** 選択開始したときのマウス上にあるブロックのx座標を保存 */
	private int preMouseOnBlockX;
	/** 選択開始したときのマウス上にあるブロックのy座標を保存 */
	private int preMouseOnBlockY;
	/** 選択開始したときのマウスの座標 */
	private Point preMousePoint;

	/** true:新規作成  false:編集*/
	private boolean newStageMake;
	/** 編集中のステージの番号 */
	private int editStageNo;

	/** ステージ描画 */
	private StageMakePaint3D stagePaint;

	/** コンストラクタ */
	public StageCreateParam(GameParam gameParam){
		super(gameParam);
		this.dataIO.loadObject("objectStageCreate.txt", this.objectList);
		setNo(7);

		setStageList(new ArrayList<Stage>());
		setActiveStage(0);
		setSelectDrawWay(1);
		setSelectBlock(1);
		setScrollable(false);
		setSaveable(true);

		setDrawable(false);
		setEditAdvanced(false);
		setSaved(false);
		setEditName(false);
		setPreMouseOnBlockX(-1);
		setPreMouseOnBlockY(-1);
		setMouseOnBlockX(-1);
		setMouseOnBlockY(-1);
		setNextMouseOnBlockX(-1);
		setNextMouseOnBlockY(-1);
		setNewStageMake(true);
		setEditStageNo(-1);
		setPreMousePoint(new Point());

		setStagePaint(new StageMakePaint3D());
		stagePaint.setGrid(1);
	}

	/** 変数の初期化 */
	public void clear() {
		setFrame(0);
		setMouseOn(0);

		setActiveStage(0);
		setSelectDrawWay(1);
		setSelectBlock(1);
		setScrollable(false);
		setSaveable(true);

		setDrawable(false);
		setEditAdvanced(false);
		setEditName(false);
		setPreMouseOnBlockX(-1);
		setPreMouseOnBlockY(-1);
		setMouseOnBlockX(-1);
		setMouseOnBlockY(-1);
		setNextMouseOnBlockX(-1);
		setNextMouseOnBlockY(-1);

		stagePaint.setGrid(1);
		stagePaint.setUp(2000, 40.0f, 30.0f);

		stageList.clear();

		// 編集するステージを描画するステージにセッティング
		stagePaint.setDrawedStage(drawingStage);
		// 編集履歴のはじめに編集前のステージをコピーする
		stageList.add(drawingStage.clone());

		setObjectVisible(16, false);
		setObjectVisible(17, false);
	}

	public ArrayList<Stage> getStageList() {
		return stageList;
	}
	public void setStageList(ArrayList<Stage> stageList) {
		this.stageList = stageList;
	}
	public Stage getDrawingStage() {
		return drawingStage;
	}
	public void setDrawingStage(Stage drawingStage) {
		this.drawingStage = drawingStage;
	}
	public int getActiveStage() {
		return activeStage;
	}
	public void setActiveStage(int activeStage) {
		this.activeStage = activeStage;
	}
	public int getSelectDrawWay() {
		return selectDrawWay;
	}
	public void setSelectDrawWay(int selectDrawWay) {
		this.selectDrawWay = selectDrawWay;
	}
	public int getSelectBlock() {
		return selectBlock;
	}
	public void setSelectBlock(int selectBlock) {
		this.selectBlock = selectBlock;
	}
	public Point getMousePoint() {
		return mousePoint;
	}
	public void setMousePoint(Point preMousePoint) {
		this.mousePoint = preMousePoint;
	}
	public Point2D.Float getCameraPoint() {
		return cameraPoint;
	}
	public void setCameraPoint(Point2D.Float preCameraPoint) {
		this.cameraPoint = preCameraPoint;
	}
	public boolean isScrollable() {
		return scrollable;
	}
	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}
	public StageMakePaint3D getStagePaint() {
		return stagePaint;
	}
	public void setStagePaint(StageMakePaint3D stagePaint) {
		this.stagePaint = stagePaint;
	}
	public void plusActiveStage(){
		this.activeStage ++;
	}
	public void minusActiveStage(){
		this.activeStage --;
	}
	public int getMouseOnBlockX() {
		return mouseOnBlockX;
	}
	public void setMouseOnBlockX(int mouseOnBlockX) {
		this.mouseOnBlockX = mouseOnBlockX;
	}
	public int getMouseOnBlockY() {
		return mouseOnBlockY;
	}
	public void setMouseOnBlockY(int mouseOnBlockY) {
		this.mouseOnBlockY = mouseOnBlockY;
	}
	public boolean isDrawable() {
		return drawable;
	}
	public void setDrawable(boolean drawable) {
		this.drawable = drawable;
	}
	public boolean isEditAdvanced() {
		return editAdvanced;
	}
	public void setEditAdvanced(boolean editAdvanced) {
		this.editAdvanced = editAdvanced;
	}
	public int getPreMouseOnBlockX() {
		return preMouseOnBlockX;
	}
	public void setPreMouseOnBlockX(int preMouseOnBlockX) {
		this.preMouseOnBlockX = preMouseOnBlockX;
	}
	public int getPreMouseOnBlockY() {
		return preMouseOnBlockY;
	}
	public void setPreMouseOnBlockY(int preMouseOnBlockY) {
		this.preMouseOnBlockY = preMouseOnBlockY;
	}
	public Point getPreMousePoint() {
		return preMousePoint;
	}
	public void setPreMousePoint(Point preMousePoint) {
		this.preMousePoint = preMousePoint;
	}
	public boolean isSaved() {
		return saved;
	}
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	public boolean isEditName() {
		return editName;
	}
	public void setEditName(boolean editName) {
		this.editName = editName;
	}
	public int getNextMouseOnBlockX() {
		return nextMouseOnBlockX;
	}
	public void setNextMouseOnBlockX(int nextMouseOnBlockX) {
		this.nextMouseOnBlockX = nextMouseOnBlockX;
	}
	public int getNextMouseOnBlockY() {
		return nextMouseOnBlockY;
	}
	public void setNextMouseOnBlockY(int nextMouseOnBlockY) {
		this.nextMouseOnBlockY = nextMouseOnBlockY;
	}
	public void mouseOnBlockUpdate(){
		this.mouseOnBlockX = this.nextMouseOnBlockX;
		this.mouseOnBlockY = this.nextMouseOnBlockY;
	}
	public boolean isNewStageMake() {
		return newStageMake;
	}
	public void setNewStageMake(boolean newStageMake) {
		this.newStageMake = newStageMake;
	}
	public int getEditStageNo() {
		return editStageNo;
	}
	public void setEditStageNo(int editStageNo) {
		this.editStageNo = editStageNo;
	}
	public boolean isSaveable() {
		return saveable;
	}
	public void setSaveable(boolean saveable) {
		this.saveable = saveable;
	}
}
