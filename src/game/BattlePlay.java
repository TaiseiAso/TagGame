package game;

import java.util.List;

public class BattlePlay extends PlayClass {
	/** バトル画面のパラメータを管理するクラスのオブジェクト */
	private BattleParam battleParam;

	/** コンストラクタ */
	public BattlePlay(BattleParam battleParam, GameParam gameParam){
		super(gameParam);
		this.battleParam = battleParam;
	}

	/** ランキングの更新 */
	public void rankingUpdate(){
		List<Player> players = battleParam.getStagePaint().getPlayers();
		int[] ranking = battleParam.getRanking();

		for(int i = 0; i < battleParam.getCpuSum(); i ++){
			Player prePlayer = players.get(ranking[i]);

			for(int j = i+1; j < battleParam.getCpuSum()+1; j ++){
				Player player = players.get(ranking[j]);

				if(prePlayer.getPoint() < player.getPoint()){
					int buf = ranking[j];
					ranking[j] = ranking[i];
					ranking[i] = buf;
				}
			}
		}
		for(int i = 0; i < battleParam.getCpuSum()+1; i ++){
			Player player = players.get(ranking[i]);

			if(i == 0){
				player.setRank(1);
			}else{
				Player prePlayer2 = players.get(ranking[i-1]);
				if(player.getPoint() == prePlayer2.getPoint()){
					player.setRank(prePlayer2.getRank());
				}else{
					player.setRank(i+1);
				}
			}
		}
	}

	/** スタート待機中の処理 */
	public void playFlag0(){
		battleParam.minusWaitTime();
		if(battleParam.getWaitTime() == 0){
			battleParam.setFlag(1);
		}
	}

	/** ゲーム中の処理 */
	public void playFlag1(){
		if(!battleParam.isPause()){
			battleParam.minusCountTime();
		}

		if(battleParam.getCountTime() == 0){
			battleParam.setFlag(2);
			battleParam.setWaitTime(180);
			battleParam.setRankingView(false);
		}else{
			if(!battleParam.isPause()){
				if(battleParam.getActionPlay().play()){
					rankingUpdate();
				}
			}

			if(gameParam.getMouseParam().isMiddleClickMoment()){
				// ポーズ画面を切り替える
				if(battleParam.isPause()){
					battleParam.setPause(false);
				}else{
					battleParam.setPause(true);
				}
			}

			if(gameParam.getMouseParam().isRightClickMoment()){
				// 戦況確認画面の表示を切り替える
				if(battleParam.isRankingView()){
					battleParam.setRankingView(false);
				}else{
					battleParam.setRankingView(true);
				}
			}
		}
	}

	/** 終了処理 */
	public void playFlag2(){
		battleParam.minusWaitTime();
		if(gameParam.getMouseParam().isLeftClickMoment() || battleParam.getWaitTime() == 0){
			battleParam.setFlag(3);
		}
	}

	/** 全プレイヤーのポイントの平均を返す */
	public double calcAverage(List<Player> players){
		double average = 0;
		for(Player player : players){
			average += player.getPoint();
		}
		return average/players.size();
	}

	/** 全プレイヤーのポイントの二乗の平均を返す */
	public double calcAverage2(List<Player> players){
		double average = 0;
		for(Player player : players){
			average += player.getPoint()*player.getPoint();
		}
		return average/players.size();
	}

	/** リザルト画面表示中 */
	public void playFlag3(){
		if(gameParam.getMouseParam().isLeftClickMoment()){
			ResultData resultData = gameParam.getResultData();
			// バトル回数を1増やす
			resultData.addBattleCount();

			// 優勝していたら優勝回数を1増やす
			Player user = battleParam.getStagePaint().getUserFromPlayers();
			if(user.getRank() == 1){
				resultData.addWinCount();
			}

			// レーティングを更新する
			List<Player> players = battleParam.getStagePaint().getPlayers();
			double average = calcAverage(players);
			double average2 = calcAverage2(players);
			int addRating = (int)((user.getPoint()-average)/Math.sqrt(average2-average*average)*10);
			resultData.addRating(addRating);

			// 戦績をセーブする
			gameParam.getDataIO().saveResult("saveResult.txt", resultData);

			toGameMove(3, false, 1, false, false);
		}
	}

	public void play() {
		if(gameParam.isOperation()){
			switch(battleParam.getFlag()){
			case 0:	// スタート待機中
				playFlag0();
				break;
			case 1:	// ゲーム中
				playFlag1();
				break;
			case 2:	// 終了処理中
				playFlag2();
				break;
			case 3:	// リザルト画面表示中
				playFlag3();
				break;
				default:
					break;
			}
		}

		battleParam.addFrame();
	}
}