package game;

public class ResultData {
	/** レーティング */
	private int rating;
	/** 優勝回数 */
	private int winCount;
	/** バトル回数 */
	private int battleCount;

	/** コンストラクタ */
	public ResultData(){
		setRating(1500);
		setWinCount(0);
		setBattleCount(0);
	}

	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public void addRating(int addRating){
		this.rating += addRating;
	}
	public int getWinCount() {
		return winCount;
	}
	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}
	public void addWinCount(){
		this.winCount ++;
	}
	public int getBattleCount() {
		return battleCount;
	}
	public void setBattleCount(int battleCount) {
		this.battleCount = battleCount;
	}
	public void addBattleCount(){
		this.battleCount ++;
	}
}
