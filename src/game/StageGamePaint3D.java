package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StageGamePaint3D extends StagePaint3D{
	/** プレイヤー */
	private List<Player> players;
	/** プレイヤーの中にユーザーがどこに位置するか */
	private int user;
	/** ポイント */
	private List<Point> points;
	/** ポイントの数の最大数 */
	private int pointMaxSum;
	/** 乱数生成 */
	private Random rnd;

	/** ポイントにどれだけ近いかの評価マップ */
	private double[][] pointMap;
	/** 人間にどれだけ近いかの評価マップ */
	private double[][] humanMap;
	/** 鬼にどれだけ近いかの評価マップ */
	private double[][] ogreMap;

	/** コンストラクタ */
	public StageGamePaint3D(List<Player> players, int user){
		super();
		setUser(user);
		setPlayers(players);
		setPoints(new ArrayList<Point>());
		setPointMaxSum(0);
		this.rnd = new Random();
		setPointMap(new double[Stage.getHeight()][Stage.getWidth()]);
		setHumanMap(new double[Stage.getHeight()][Stage.getWidth()]);
		setOgreMap(new double[Stage.getHeight()][Stage.getWidth()]);
	}

	/** 評価マップを0で初期化する */
	public void mapInit(double[][] map){
		for(int i = 0; i < Stage.getHeight(); i ++){
			for(int j = 0; j < Stage.getWidth(); j ++){
				map[i][j] = 0;
			}
		}
	}

	/** 評価値を1つずつ更新する */
	public void pointMapping(double[][] map, int x, int y, int r, int rmax){
		if(0 < x && x < Stage.getWidth() && 0 < y && y < Stage.getHeight()){
			double mapping;
			if(r == rmax){
				mapping = 1;
			}else{
				mapping = r/(double)rmax;
			}

			if(mapping > map[y][x]){
				map[y][x] = r/(double)rmax;

				if(r > 1){
					int[][] block = this.drawedStage.getBlock();
					if(block[y+1][x] != 1){
						pointMapping(map, x, y+1, r-1, rmax);
					}
					if(block[y-1][x] != 1){
						pointMapping(map, x, y-1, r-1, rmax);
					}
					if(block[y][x+1] != 1){
						pointMapping(map, x+1, y, r-1, rmax);
					}
					if(block[y][x-1] != 1){
						pointMapping(map, x-1, y, r-1, rmax);
					}
				}
			}
		}
	}

	/** ポイント評価マップを更新する */
	public void pointMapUpdate(){
		// 評価マップを0で初期化
		mapInit(pointMap);

		for(Point point : points){
			if(this.drawedStage.getObstacle()[point.y][point.x] != 1){
				pointMapping(pointMap, point.x, point.y, 7, 7);
			}
		}
	}

	/** 人間の評価マップを更新する */
	public void humanMapUpdate(){
		// 更新の必要性の検証
		boolean updateNeed = false;
		for(Player player : players){
			if(player.isHuman()){
				if(humanMap[(int)(player.getY()+0.5*Player.height)][(int)(player.getX()+0.5*Player.width)] < 0.999){
					updateNeed = true;
					break;
				}
			}
		}

		if(updateNeed){
			// 評価マップを0で初期化
			mapInit(humanMap);

			for(Player player : players){
				if(player.isHuman()){
					if(this.drawedStage.getObstacle()[(int)(player.getY()+0.5*Player.height)][(int)(player.getX()+0.5*Player.width)] != 1){
						pointMapping(humanMap, (int)(player.getX()+0.5*Player.width), (int)(player.getY()+0.5*Player.height), 12, 12);
					}
				}
			}
		}
	}

	/** 鬼の評価マップを更新する */
	public void ogreMapUpdate(){
		// 更新の必要性の検証
		boolean updateNeed = false;
		for(Player player : players){
			if(!player.isHuman()){
				if(ogreMap[(int)(player.getY()+0.5*Player.height)][(int)(player.getX()+0.5*Player.width)] < 0.999){
					updateNeed = true;
					break;
				}
			}
		}

		if(updateNeed){
			// 評価マップを0で初期化
			mapInit(ogreMap);

			for(Player player : players){
				if(!player.isHuman()){
					if(this.drawedStage.getObstacle()[(int)(player.getY()+0.5*Player.height)][(int)(player.getX()+0.5*Player.width)] != 1){
						pointMapping(ogreMap, (int)(player.getX()+0.5*Player.width), (int)(player.getY()+0.5*Player.height), 12, 12);
					}
				}
			}
		}
	}

	/** ポイントを1つ追加する */
	public void addPoint(){
		int[][] block = this.drawedStage.getBlock();

		int x = rnd.nextInt(Stage.getWidth() - 2) + 1;
		int y = rnd.nextInt(Stage.getHeight() - 2) + 1;
		int startX = x;
		int startY = y;
		// 初期位置がブロックに埋まらないようにする
		while(block[y][x] == 1 || points.contains(new Point(x, y))){
			x ++;
			if(x == Stage.getWidth() - 1){
				x = 1;
				y ++;
				if(y == Stage.getHeight() - 1){
					y = 1;
				}
			}
			// どこもスペースがなければ配置しない
			if(x == startX && y == startY){
				return;
			}
		}
		points.add(new Point(x, y));
	}

	/** ポイントを複数個追加する */
	public void addPoint(int sum){
		// 初期位置を決める
		for(int i = 0; i < sum; i ++){
			addPoint();
		}
		pointMapUpdate();
	}

	/** ポイントの配置を初期化する */
	public void pointsInit(){
		this.points.clear();
		// 初期位置を決める
		for(int i = 0; i < pointMaxSum; i ++){
			addPoint();
		}
		pointMapUpdate();
	}

	/** プレイヤーの初期位置を決定しパラメータを初期化する */
	public void playersInit(){
		int[][] block = this.drawedStage.getBlock();

		for(Player player : players){
			// 初期位置を決める
			int x = rnd.nextInt(Stage.getWidth() - 2) + 1;
			int y = rnd.nextInt(Stage.getHeight() - 2) + 1;
			// 初期位置がブロックに埋まらないようにする
			while(block[y][x] == 1){
				x ++;
				if(x == Stage.getWidth() - 1){
					x = 1;
					y ++;
					if(y == Stage.getHeight() - 1){
						y = 1;
					}
				}
			}
			player.setX(x);
			player.setY(y);

			// プレイヤーの初期化
			player.clear();
		}
		humanMapUpdate();
		ogreMapUpdate();
	}

	public void setUpGame(int disToBlock){
		this.disToBlock = disToBlock;
		updateBlockWidth();
		getCameraPoint().x = players.get(user).getX() + Player.width*0.5f;
		getCameraPoint().y = players.get(user).getY() + Player.height*0.5f;
	}

	private void paintPlayer(Graphics g, Player player, int x, int y, int w, int h){
		int dw = w/5;
		int dw2 = w/10;
		int dh = h/6;
		int dh2 = h/4;

		g.setColor(player.getColor());

		// 頭部の描画
		if(player.isHuman()){
			g.fillRect(x, y, w, dh);
		}else{
			int[] xPoints1 = {x, x+w/6, x+w/3};
			int[] yPoints1 = {y+dh, y, y+dh};
			g.fillPolygon(xPoints1, yPoints1, 3);

			int[] xPoints2 = {x+w*2/3, x+w*5/6, x+w};
			int[] yPoints2 = {y+dh, y, y+dh};
			g.fillPolygon(xPoints2, yPoints2, 3);
		}

		// 体の描画
		g.fillRect(x, y+dh, w, h-2*dh);
		g.fillRect(x+dw2, y+h-dh, dw, dh);
		g.fillRect(x+w-dw-dw2, y+h-dh, dw, dh);

		// 目の描画
		if(player.isHuman()){
			g.setColor(Color.WHITE);
			g.fillRect(x+dw, y+dh2, dw, dh2);
			g.fillRect(x+w-2*dw, y+dh2, dw, dh2);
		}else{
			g.setColor(Color.RED);
			g.fillRect(x+dw, y+2*dh, dw, dh2);
			g.fillRect(x+w-2*dw, y+2*dh, dw, dh2);
		}
	}

	private void paintPlayers(Graphics g, int x, int y, int w, int h, int i, int j){
		g.setClip(x, y, w, h);

		for(Player player : players){
			if((player.getStopTime()/15)%2 == 0){
				float px = player.getX();
				float py = player.getY();

				if(px < i + 1.0f && px + Player.width > i
					&& py < j + 1.0f && py + Player.height > j){

					int drawX = 400 + (int)(blockWidthMiddle*(px - cameraPoint.x));
					int drawY = 300 + (int)(blockWidthMiddle*(py - cameraPoint.y));
					int drawW = (int)(blockWidthMiddle*Player.width);
					int drawH = (int)(blockWidthMiddle*Player.height);

					paintPlayer(g, player, drawX, drawY, drawW, drawH);
				}
			}
		}
		g.setClip(null);
	}

	private void paintPoint(Graphics g, int x, int y, int w, int h, int i, int j){
		if(points.contains(new Point(i, j))){
			g.setColor(new Color(220, 220, 0));
			g.fillOval(x+2*w/5, y+2*h/5, w/5, h/5);
			g.setColor(new Color(120, 120, 20));
			g.drawOval(x+2*w/5, y+2*h/5, w/5, h/5);
		}
	}

	/** プレイヤーを含む3Dマップの描画(ブロック単位) */
	public void paint3DBlock(Graphics g, int i, int j, boolean up, boolean left, boolean down, boolean right){
		int[][] block = drawedStage.getBlock();
		int[][] obstacle = drawedStage.getObstacle();
		int[][] back = drawedStage.getBack();

		float blockX = i - cameraPoint.x;
		float blockY = j - cameraPoint.y;

		int backLeftX = 400 + (int)(blockWidthBack*blockX);
		int frontLeftX = 400 + (int)(blockWidthFront*blockX);
		int middleLeftX = 400 + (int)(blockWidthMiddle*blockX);
		int backRightX = 400 + (int)(blockWidthBack*(blockX + 1));
		int frontRightX = 400 + (int)(blockWidthFront*(blockX + 1));
		int middleRightX = 400 + (int)(blockWidthMiddle*(blockX + 1));

		int backUpY = 300 + (int)(blockWidthBack*blockY);
		int frontUpY = 300 + (int)(blockWidthFront*blockY);
		int middleUpY = 300 + (int)(blockWidthMiddle*blockY);
		int backDownY = 300 + (int)(blockWidthBack*(blockY + 1));
		int frontDownY = 300 + (int)(blockWidthFront*(blockY + 1));
		int middleDownY = 300 + (int)(blockWidthMiddle*(blockY + 1));

		int frontWidth = frontRightX - frontLeftX;
		int frontHeight = frontDownY - frontUpY;
		int backWidth = backRightX - backLeftX;
		int backHeight = backDownY - backUpY;
		int middleWidth = middleRightX - middleLeftX;
		int middleHeight = middleDownY - middleUpY;

		if(block[j][i] == 1){
			// プレイヤー描画
			paintPlayers(g, middleLeftX, middleUpY, middleWidth, middleHeight, i, j);

			blockPaint.paintGrassBlockFront(g, drawedStage.getDistBlock()[j][i],
					frontLeftX,
					frontUpY,
					frontWidth, frontHeight);

			if(up && block[j - 1][i] != 1){
				blockPaint.paintGrassBlockSide(g, 1,
						backLeftX, backUpY,
						frontLeftX, frontUpY,
						frontRightX, frontUpY,
						backRightX, backUpY);
			}
			if(left && block[j][i - 1] != 1){
				blockPaint.paintGrassBlockSide(g, drawedStage.getDistBlock()[j][i] + 1,
						backLeftX, backUpY,
						backLeftX, backDownY,
						frontLeftX, frontDownY,
						frontLeftX, frontUpY);
			}
			if(down && block[j + 1][i] != 1){
				blockPaint.paintGrassBlockSide(g, 6,
						frontLeftX, frontDownY,
						backLeftX, backDownY,
						backRightX, backDownY,
						frontRightX, frontDownY);
			}
			if(right && block[j][i + 1] != 1){
				blockPaint.paintGrassBlockSide(g, drawedStage.getDistBlock()[j][i] + 1,
						frontRightX, frontUpY,
						frontRightX, frontDownY,
						backRightX, backDownY,
						backRightX, backUpY);
			}
		}else{
			if(back[j][i] == 1){
				blockPaint.paintBack(g,
						backLeftX, backUpY,
						backWidth, backHeight);
			}

			// プレイヤー描画
			paintPlayers(g, middleLeftX, middleUpY, middleWidth, middleHeight, i, j);
			// ポイント描画
			paintPoint(g, middleLeftX, middleUpY, middleWidth, middleHeight, i, j);

			if(block[j][i] == 2){
				if(block[j - 1][i] == 0){
					blockPaint.paintWaterBlockSide(g, 1,
							backLeftX, backUpY,
							frontLeftX, frontUpY,
							frontRightX, frontUpY,
							backRightX, backUpY);
				}
				if(block[j][i - 1] == 0){
					blockPaint.paintWaterBlockSide(g, drawedStage.getDistBlock()[j][i] + 1,
							backLeftX, backUpY,
							backLeftX, backDownY,
							frontLeftX, frontDownY,
							frontLeftX, frontUpY);
				}
				if(block[j + 1][i] == 0){
					blockPaint.paintWaterBlockSide(g, 6,
							frontLeftX, frontDownY,
							backLeftX, backDownY,
							backRightX, backDownY,
							frontRightX, frontDownY);
				}
				if(block[j][i + 1] == 0){
					blockPaint.paintWaterBlockSide(g, drawedStage.getDistBlock()[j][i] + 1,
							frontRightX, frontUpY,
							frontRightX, frontDownY,
							backRightX, backDownY,
							backRightX, backUpY);
				}
			}
			if(obstacle[j][i] == 1){
				blockPaint.paintObstacle(g,
						frontLeftX, frontUpY,
						frontWidth, frontHeight);
			}
			if(block[j][i] == 2){
				blockPaint.paintWaterBlockFront(g, drawedStage.getDistBlock()[j][i],
						frontLeftX, frontUpY,
						frontWidth, frontHeight);
			}
		}

		// 評価マップを描画
		//polygonPaint.fillRectangle(g, Color.YELLOW, frontLeftX, frontUpY, frontWidth, frontHeight, 0.7f*(float)pointMap[j][i]);
		//polygonPaint.fillRectangle(g, Color.BLUE, frontLeftX, frontUpY, frontWidth, frontHeight, 0.7f*(float)humanMap[j][i]);
		//polygonPaint.fillRectangle(g, Color.RED, frontLeftX, frontUpY, frontWidth, frontHeight, 0.7f*(float)ogreMap[j][i]);
	}

	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public Player getUserFromPlayers(){
		return players.get(user);
	}
	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	public int getPointMaxSum() {
		return pointMaxSum;
	}
	public void setPointMaxSum(int pointMaxSum) {
		this.pointMaxSum = pointMaxSum;
	}
	public double[][] getPointMap() {
		return pointMap;
	}
	public void setPointMap(double[][] pointMap) {
		this.pointMap = pointMap;
	}
	public double[][] getHumanMap() {
		return humanMap;
	}
	public void setHumanMap(double[][] humanMap) {
		this.humanMap = humanMap;
	}
	public double[][] getOgreMap() {
		return ogreMap;
	}
	public void setOgreMap(double[][] ogreMap) {
		this.ogreMap = ogreMap;
	}
}
