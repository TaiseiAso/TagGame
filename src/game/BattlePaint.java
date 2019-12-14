package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class BattlePaint extends PaintClass {
	/** メニュー画面のパラメータを管理するクラスのオブジェクト */
	private BattleParam battleParam;

	/** コンストラクタ */
	public BattlePaint(BattleParam battleParam, GameParam gameParam){
		super(gameParam);
		this.battleParam = battleParam;
	}

	/** ランキングの描画 */
	public void paintRanking(Graphics g, int x, int y){
		StageGamePaint3D stagePaint = battleParam.getStagePaint();
		List<Player> players = stagePaint.getPlayers();
		int[] ranking = battleParam.getRanking();

		polygonPaint.fillRectangle(g, Color.BLACK, x, y, 180, 165, 0.5f);

		g.setColor(Color.WHITE);
		for(int i = 0; i < battleParam.getCpuSum()+1; i ++){
			Player player = players.get(ranking[i]);

			int rank = player.getRank();
			g.drawString(String.valueOf(rank), 10+x, 15+i*20+y);
			if(rank == 1){
				g.drawString("st", 18+x, 15+i*20+y);
			}else if(rank == 2){
				g.drawString("nd", 18+x, 15+i*20+y);
			}else if(rank == 3){
				g.drawString("rd", 18+x, 15+i*20+y);
			}else{
				g.drawString("th", 18+x, 15+i*20+y);
			}
			if(player.isHuman()){
				g.drawString("HUMAN", 40+x, 15+i*20+y);
			}else{
				g.drawString("OGRE", 40+x, 15+i*20+y);
			}
			g.drawString(player.getName(), 90+x, 15+i*20+y);
			g.drawString(String.valueOf(player.getPoint())+"p", 140+x, 15+i*20+y);
		}
	}

	public void paint(Graphics g) {
		int gameMoveHalfFrame = gameParam.getGameMoveMaxFrame()/2;
		if(!gameParam.isGameMove() ||
		(gameParam.getPostGameMode() == 9 && gameParam.getGameMoveFrame() >= gameMoveHalfFrame) ||
		(gameParam.getGameMode() == 9 && gameParam.getGameMoveFrame() < gameMoveHalfFrame)){

			// 背景の描画
			g.setColor(new Color(160, 216, 239));	// 空色
			g.fillRect(0, 0, 800, 600);
			g.setColor(Color.WHITE);
			g.fillRect((battleParam.getFrame()/2)%1100-300, 70, 200, 50);
			g.fillRect((battleParam.getFrame()/2-100)%1100-300, 120, 200, 50);
			g.fillRect((battleParam.getFrame()/2+400)%1100-300, 180, 200, 50);
			g.fillRect((battleParam.getFrame()/2+500)%1100-300, 200, 200, 50);

			// マップの描画
			StageGamePaint3D stagePaint = battleParam.getStagePaint();
			stagePaint.paint3D(g);

			// スタミナゲージの描画
			Player user = stagePaint.getUserFromPlayers();
			int stamina = user.getStamina();
			int maxStamina = user.getMaxStamina();
			Color c;
			if(stamina <= maxStamina*0.25){
				c = Color.RED;
			}else if(stamina <= maxStamina*0.5){
				c = Color.ORANGE;
			}else if(stamina <= maxStamina*0.8){
				c = Color.GREEN;
			}else{
				c = new Color(0, 180, 255);
			}
			polygonPaint.fillRectangle(g, c, 20, 550, (int)(user.getStamina()*0.3), 30, 0.5f);
			g.setColor(Color.BLACK);
			g.drawRect(20, 550, (int)(user.getMaxStamina()*0.3), 30);

			// 残り時間の描画
			int countTime = battleParam.getCountTime();
			int num;
			if(countTime == battleParam.getBattleMaxTime()){
				num = countTime/60;
			}else if(countTime == 0){
				num = 0;
			}else{
				num = countTime/60 + 1;
			}
			paintNumber(g, num, 2, 350, 15);

			// 獲得ポイントの描画
			paintNumber(g, user.getPoint(), 3, 600, 540);
		}

		if(battleParam.isRankingView()){
			paintRanking(g, 10, 5);
		}

		if(battleParam.getFlag() == 0){
			polygonPaint.fillRectangle(g, Color.BLACK, 0, 0, 800, 600, 0.5f);

			if(!gameParam.isGameMove()){
				g.setColor(Color.RED);
				if(battleParam.getWaitTime() < 180){
					g.fillArc(250, 150, 300, 300, 90, -6*(60-battleParam.getWaitTime()%60));
				}
				g.setColor(Color.BLACK);
				g.drawOval(250, 150, 300, 300);
				if(battleParam.getWaitTime() >= 120){
					imageBox.drawImage(g, 44, 350, 250, 100, 100, 400, 300, 0, 1, 1);
				}else if(battleParam.getWaitTime() >= 60){
					imageBox.drawImage(g, 45, 350, 250, 100, 100, 400, 300, 0, 1, 1);
				}else{
					imageBox.drawImage(g, 46, 350, 250, 100, 100, 400, 300, 0, 1, 1);
				}
			}
		}else if(battleParam.getFlag() == 2){
			polygonPaint.fillRectangle(g, Color.BLACK, 0, 0, 800, 600, 0.5f);
			imageBox.drawImage(g, 47, 275, 225, 250, 150, 400, 300, 0, 1, 1);
		}else if(battleParam.getFlag() == 3){
			if(!gameParam.isGameMove() || (gameParam.isGameMove() && gameParam.getGameMoveFrame() < gameMoveHalfFrame)){
				polygonPaint.fillRectangle(g, Color.BLACK, 0, 0, 800, 600, 0.5f);
				paintRanking(g, 310, 218);
			}
		}

		if(battleParam.isPause()){
			polygonPaint.fillRectangle(g, Color.BLACK, 0, 0, 800, 600, 0.5f);
		}

		if(gameParam.isGameMove()){
			paintFade(g);
		}
	}
}
