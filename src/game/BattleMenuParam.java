package game;

public class BattleMenuParam extends ParamClass {
	/** 参戦するCPUの数 */
	private int cpuSum;

	/** 選択中のステージ番号 */
	private int selectedStage;

	/** マウスを動かすとステージセレクトバーがスクロール可能であるかどうか */
	private boolean scrollable;
	/** ステージセレクトバーのスクロール距離 */
	private int selectScroll;
	/** ステージセレクトバーの移動前のスクロール距離を保存 */
	private int scroll;
	/** ステージセレクトバーを押し始めたときのマウスのx座標 */
	private int mouseScroll;

	/** ステージ描画 */
	private StagePaint2D stagePaint;

	/** コンストラクタ */
	public BattleMenuParam(GameParam gameParam){
		super(gameParam);
		this.dataIO.loadObject("objectBattleMenu.txt", this.objectList);
		setNo(3);

		setCpuSum(1);
		setSelectedStage(-1);
		setScrollable(false);
		setSelectScroll(350);
		setScroll(0);
		setMouseScroll(0);

		this.setStagePaint(new StagePaint2D());
	}

	/** 変数の初期化 */
	public void clear() {
		setFrame(0);
		setMouseOn(0);

		setCpuSum(1);
		setSelectedStage(-1);
		setScrollable(false);
		setSelectScroll(350);
		setScroll(0);
		setMouseScroll(0);

		setObjectVisible(2, false);
		setObjectVisible(3, false);
		setObjectVisible(4, true);
		setObjectVisible(7, false);
		setObjectVisible(8, false);
		setObjectVisible(9, false);
		setObjectVisible(10, false);
		setObjectVisible(11, false);
		setObjectVisible(12, false);
	}

	public int getSelectedStage() {
		return selectedStage;
	}
	public void setSelectedStage(int selectedStage) {
		this.selectedStage = selectedStage;
	}
	public boolean isScrollable() {
		return scrollable;
	}
	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}
	public int getSelectScroll() {
		return selectScroll;
	}
	public void setSelectScroll(int selectScroll) {
		this.selectScroll = selectScroll;
	}
	public int getScroll() {
		return scroll;
	}
	public void setScroll(int scroll) {
		this.scroll = scroll;
	}
	public int getMouseScroll() {
		return mouseScroll;
	}
	public void setMouseScroll(int mouseScroll) {
		this.mouseScroll = mouseScroll;
	}
	public StagePaint2D getStagePaint() {
		return stagePaint;
	}
	public void setStagePaint(StagePaint2D stagePaint) {
		this.stagePaint = stagePaint;
	}
	public int getCpuSum() {
		return cpuSum;
	}
	public void setCpuSum(int cpuSum){
		this.cpuSum = cpuSum;
	}
	public void addCpuSum(){
		this.cpuSum ++;
	}
	public void minusCpuSum(){
		this.cpuSum --;
	}
}
