package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActionPlay {
	private StageGamePaint3D stagePaint;
	private GameParam gameParam;
	private ParamClass param;

	/** 乱数生成 */
	private Random rnd;

	/** コンストラクタ */
	public ActionPlay(GameParam gameParam, ParamClass param){
		this.gameParam = gameParam;
		this.param = param;
		this.rnd = new Random();
	}

	/** プレイヤーとカメラの相対距離をもとにカメラ座標の移動を返す */
	private float cameraMove(float dl){
		if(-0.1f < dl && dl < 0.1f){
			return 0.0f;
		}else{
			return dl /= 7.0f;
		}
	}

	/** 2つのdouble型を比較する */
	public int compDouble(double a, double b){
		if(Math.abs(a-b) < 0.0001){
			return 0;
		}else if(a > b){
			return 1;
		}else{
			return -1;
		}
	}

	/** 評価マップを合成して評価値を返す */
	public double mapVal(double[][] pointMap, double[][] playerMap, int x, int y, boolean isHuman){
		if(isHuman){
			return pointMap[y][x] + 3*(1-playerMap[y][x]);
		}else{
			return pointMap[y][x] + 4*playerMap[y][x];
		}
	}

	/** CPUのAI */
	public int playerAI(int[][] block, double[][] pointMap, double[][] playerMap, Player player){
		 int x = (int)(player.getX()+0.5*Player.width);
		 int y = (int)(player.getY()+0.5*Player.height);
		 boolean isHuman = player.isHuman();

		double center = mapVal(pointMap, playerMap, x, y, isHuman);
		double up, right, down, left;
		if(block[y-1][x] == 1 || (player.isHuman() && player.getStamina() < (player.getJump() + 1)*20) || player.getJumpInterval() > 0){
			up = -1;
		}else{
			up = mapVal(pointMap, playerMap, x, y-1, isHuman);
		}
		if(block[y][x+1] == 1){
			right = -1;
		}else{
			right = mapVal(pointMap, playerMap, x+1, y, isHuman);
		}
		if(block[y+1][x] == 1){
			down = -1;
		}else{
			down = mapVal(pointMap, playerMap, x, y+1, isHuman);
		}
		if(block[y][x-1] == 1){
			left = -1;
		}else{
			left = mapVal(pointMap, playerMap, x-1, y, isHuman);
		}

		double max = center;
		int actionFlag = 0;

		if(compDouble(up, max) > 0){
			max = up;
			actionFlag = 1;
		}
		if(compDouble(right, max) > 0){
			max = right;
			actionFlag = 2;
		}
		if(compDouble(down, max) > 0){
			max = down;
			actionFlag = 3;
		}
		if(compDouble(left, max) > 0){
			max = left;
			actionFlag = 4;
		}

		return actionFlag;
	}

	/** プレイヤーのアクション実行 */
	public boolean play(){
		boolean rankingUpdateNeed = false;

		List<Point> points = this.stagePaint.getPoints();
		List<Player> players = this.stagePaint.getPlayers();
		Stage stage = this.stagePaint.getDrawedStage();

		for(int i = 0; i < players.size(); i ++){
			Player player = players.get(i);

			if(player.isUser()){	// ユーザ(必ず1人)
				// カメラがユーザを追尾する
				float dx = player.getX() + Player.width/2- stagePaint.cameraPoint.x;
				float dy = player.getY() + Player.height/2 - stagePaint.cameraPoint.y;
				stagePaint.addCameraPoint(cameraMove(dx), cameraMove(dy));

				if(player.getStopTime() == 0){
					// 左右移動
					float x = stagePaint.getCameraPoint().x + (gameParam.getMouseParam().getPoint().x - 400)/stagePaint.getBlockWidthMiddle();
					float dvx = 0.05f*(x - player.getX() - Player.width/2);
					if(dvx < -0.03f || 0.03f < dvx){
						player.setVx(dvx);
					}else{
						player.setVx(0.0f);
					}

					// ジャンプ
					if(gameParam.getMouseParam().isLeftClickMoment() &&
					(!player.isHuman() || (player.isHuman() && player.getStamina() >= (player.getJump() + 1)*20)) &&
					param.getMouseOn() == 0){
						player.addJump();
						if(player.isHuman()){
							player.minusStamina(player.getJump()*20);
						}
						player.setLanding(false);
						player.setVy(-0.2f);
					}
				}
			}else{	// CPU
				if(player.getStopTime() == 0){
					int actionFlag;

					double[][] playerMap;
					if(player.isHuman()){
						playerMap = stagePaint.getOgreMap();
					}else{
						playerMap = stagePaint.getHumanMap();
					}
					actionFlag = playerAI(stage.getBlock(), stagePaint.getPointMap(), playerMap, player);

					switch(actionFlag){
					case 0:	// ランダム行動
						if(player.getJumpInterval() == 0 && rnd.nextInt(60) == 0 &&
						(!player.isHuman() || (player.isHuman() && player.getStamina() >= (player.getJump() + 1)*20))){
							player.addJump();
							if(player.isHuman()){
								player.minusStamina(player.getJump()*20);
							}
							player.setLanding(false);
							player.setVy(-0.2f);
							player.setJumpInterval(20);
						}
						if(player.getRandomActionInterval() == 0){
							player.setRandomAction(rnd.nextInt(2));
							player.setRandomActionInterval(20);
						}else{
							player.minusRandomActionInterval();

							switch(player.getRandomAction()){
							case 0:
								player.setVx(0.12f);
								break;
							case 1:
								player.setVx(-0.12f);
								break;
							}
						}
						break;
					case 1:	// ジャンプ
						player.addJump();
						if(player.isHuman()){
							player.minusStamina(player.getJump()*20);
						}
						player.setLanding(false);
						player.setVy(-0.2f);
						player.setJumpInterval(20);
						break;
					case 2:	// 右移動
						player.setVx(0.12f);
						break;
					case 4:	// 左移動
						player.setVx(-0.12f);
						break;
						default:
							break;
					}
				}
				player.minusJumpInterval();
			}

			// 水中判定
			if(!player.isInWater()){
				if(stage.getBlock()[(int)player.getY()][(int)player.getX()] == 2 ||
					stage.getBlock()[(int)(player.getY() + Player.height)][(int)player.getX()] == 2 ||
					stage.getBlock()[(int)player.getY()][(int)(player.getX() + Player.width)] == 2 ||
					stage.getBlock()[(int)(player.getY() + Player.height)][(int)(player.getX() + Player.width)] == 2){
					player.setInWater(true);
				}
			}else{
				if(stage.getBlock()[(int)player.getY()][(int)player.getX()] != 2 &&
					stage.getBlock()[(int)(player.getY() + Player.height)][(int)player.getX()] != 2 &&
					stage.getBlock()[(int)player.getY()][(int)(player.getX() + Player.width)] != 2 &&
					stage.getBlock()[(int)(player.getY() + Player.height)][(int)(player.getX() + Player.width)] != 2){
					player.setInWater(false);
				}
			}

			if(player.getStopTime() == 0){
				// 落下判定
				if(player.isLanding()
					&& stage.getBlock()[(int)(player.getY() + Player.height)][(int)player.getX()] != 1
					&& stage.getBlock()[(int)(player.getY() + Player.height)][(int)(player.getX() + Player.width)] != 1){
					player.setLanding(false);
				}

				// 壁とのあたり判定
				player.addX();
				if(player.getVx() < 0 && (stage.getBlock()[(int) player.getY()][(int) player.getX()] == 1
						|| stage.getBlock()[(int) (player.getY() + Player.height - 0.01f)][(int) player.getX()] == 1)){
					player.setX((int) player.getX() + 1.0f);
					player.setVx(0.0f);
				}else if(player.getVx() >= 0 && (stage.getBlock()[(int) player.getY()][(int) (player.getX() + Player.width - 0.01f)] == 1
						|| stage.getBlock()[(int) (player.getY() + Player.height - 0.01f)][(int) (player.getX() + Player.width - 0.01f)] == 1)){
					player.setX((int) (player.getX() + Player.width) - Player.width);
					player.setVx(0.0f);
				}
			}

			// 天井と地面とのあたり判定
			player.addY();
			if(player.getVy() < 0 && (stage.getBlock()[(int) player.getY()][(int) player.getX()] == 1
					|| stage.getBlock()[(int) player.getY()][(int) (player.getX() + Player.width - 0.01f)] == 1)){
				player.setY((int) player.getY() + 1.0f);
				player.setVy(-0.3f*player.getVy());
			}else if(player.getVy() >= 0 && (stage.getBlock()[(int) (player.getY() + Player.height - 0.01f)][(int) player.getX()] == 1
					|| stage.getBlock()[(int) (player.getY() + Player.height - 0.01f)][(int) (player.getX() + Player.width - 0.01f)] == 1)){
				player.setY((int) (player.getY() + Player.height) - Player.height);
				player.setVy(0.0f);
				player.setJump(0);
				player.setLanding(true);
			}

			// 重力加速度を速度に足す
			if(!player.isLanding()){
				player.addGVy();
			}

			// スタミナを回復させる
			if(player.isLanding()){
				player.addStamina(6);
			}else{
				player.addStamina(2);
			}

			if(player.getStopTime() == 0){
				// ポイントとのあたり判定
				List<Integer> removeList = new ArrayList<Integer>();
				for(int j = 0; j < points.size(); j ++){
					Point point = points.get(j);
					if(player.getX() < point.getX()+0.6f && point.getX()+0.4f < player.getX()+Player.width &&
					player.getY() < point.getY()+0.6f && point.getY()+0.4f < player.getY()+Player.height){
						removeList.add(j);
						if(player.isHuman()){
							rankingUpdateNeed = true;
							player.addPoint();
						}
					}
				}
				this.stagePaint.addPoint(removeList.size());
				for(int j = 0; j < removeList.size(); j ++){
					points.remove(removeList.get(j)-j);
				}
			}else{
				player.minusStopTime();
			}
		}

		// プレイヤー同士のあたり判定
		for(int i = 0; i < players.size()-1; i ++){
			Player player1 = players.get(i);
			for(int j = i+1; j < players.size(); j ++){
				Player  player2 = players.get(j);

				if(player1.isHuman() && !player2.isHuman() && player2.getStopTime() == 0){
					if(player1.getX() < player2.getX()+Player.width && player2.getX() < player1.getX()+Player.width &&
					player1.getY() < player2.getY()+Player.height && player2.getY() < player1.getY()+Player.height){
						player1.setHuman(false);
						player1.heelStamina();
						player1.setJump(0);
						player1.setStopTime(300);
						player1.decreasePoint();
						player2.setHuman(true);
					}
				}else if(!player1.isHuman() && player1.getStopTime() == 0 && player2.isHuman()){
					if(player1.getX() < player2.getX()+Player.width && player2.getX() < player1.getX()+Player.width &&
					player1.getY() < player2.getY()+Player.height && player2.getY() < player1.getY()+Player.height){
						player2.setHuman(false);
						player2.heelStamina();
						player2.setJump(0);
						player2.setStopTime(300);
						player2.decreasePoint();
						player1.setHuman(true);
					}
				}
			}
		}
		this.stagePaint.humanMapUpdate();
		this.stagePaint.ogreMapUpdate();

		return rankingUpdateNeed;
	}

	public StageGamePaint3D getStagePaint() {
		return stagePaint;
	}
	public void setStagePaint(StageGamePaint3D stagePaint) {
		this.stagePaint = stagePaint;
	}
}
