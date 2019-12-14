package game;

import java.awt.Point;
import java.awt.geom.Point2D;

public class StageCreatePlay extends PlayClass{
	/** ステージ作成画面のパラメータを管理するクラスのオブジェクト */
	private StageCreateParam stageCreateParam;
	/** リザルト画面のパラメータを管理するクラスのオブジェクト */
	private TestParam  testParam;

	/** コンストラクタ */
	public StageCreatePlay(StageCreateParam stageCreateParam, TestParam testParam, GameParam gameParam){
		super(gameParam);
		this.stageCreateParam = stageCreateParam;
		this.testParam = testParam;
	}

	/** 履歴を更新する */
	private void updateHistory(){
		// 編集を進めたらその先の履歴は消す
		if(stageCreateParam.getStageList().size() > stageCreateParam.getActiveStage() + 1){
			// 履歴の中の編集中のステージを変更
			stageCreateParam.plusActiveStage();

			int removePoint = stageCreateParam.getActiveStage();
			int removeSum = stageCreateParam.getStageList().size() - removePoint;
			for(int i = 0; i < removeSum; i ++){
				stageCreateParam.getStageList().remove(removePoint);
			}
		}else{
			// 履歴を残しすぎないように50個を超えると最古のものから消していく
			if(stageCreateParam.getStageList().size() == 50){
				stageCreateParam.getStageList().remove(0);
			}else{
				// 履歴の中の編集中のステージを変更
				stageCreateParam.plusActiveStage();
			}
		}
		stageCreateParam.setSaved(false);
		// 進めた編集データを履歴に残す
		stageCreateParam.getStageList().add(stageCreateParam.getDrawingStage().clone());

		stageJudge();
	}

	/** 編集中のステージがプレイ可能で保存可能なものかどうかを確認 */
	public void stageJudge(){
		// もし編集中のステージが全面ブロックであった場合保存不可能にする
		if(stageCreateParam.getDrawingStage().isAllGrass()){
			stageCreateParam.setSaveable(false);
		}else{
			stageCreateParam.setSaveable(true);
		}
	}

	/** 塗り予定のブロックを取り消す */
	private void nextDrawClear(int[][] nextDraw){
		for(int i = 0; i < Stage.getHeight(); i ++){
			for(int j = 0; j < Stage.getWidth(); j ++){
				nextDraw[i][j] = 0;
			}
		}
	}

	/** 塗り予定のブロックを実際に塗る */
	private void nextDrawToStage(int[][] nextDraw, int selectBlock){
		Stage stage = stageCreateParam.getDrawingStage();
		int[][] block = stage.getBlock();
		int[][] obstacle = stage.getObstacle();
		int[][] back = stage.getBack();

		for(int i = 0; i < Stage.getHeight(); i ++){
			for(int j = 0; j < Stage.getWidth(); j ++){
				if(nextDraw[i][j] == 1){
					switch(selectBlock){
					case 1:
						if(block[i][j] != 1){
							stageCreateParam.setEditAdvanced(true);
							block[i][j] = 1;		obstacle[i][j] = 0;	back[i][j] = 0;
						}
						break;
					case 2:
						if(block[i][j] != 2){
							stageCreateParam.setEditAdvanced(true);
							block[i][j] = 2;
						}
						break;
					case 3:
						if(obstacle[i][j] != 1){
							stageCreateParam.setEditAdvanced(true);
							if(block[i][j] == 1){
								block[i][j] = 0;
							}
							obstacle[i][j] = 1;
						}
						break;
					case 4:
						if(back[i][j] != 1){
							stageCreateParam.setEditAdvanced(true);
							if(block[i][j] == 1){
								block[i][j] = 0;
							}
							back[i][j] = 1;
						}
						break;
					case 5:
						if(block[i][j] > 0){
							stageCreateParam.setEditAdvanced(true);
							block[i][j] = 0;
						}
						if(obstacle[i][j] == 1){
							stageCreateParam.setEditAdvanced(true);
							obstacle[i][j] = 0;
						}
						if(back[i][j] == 1){
							stageCreateParam.setEditAdvanced(true);
							back[i][j] = 0;
						}
						break;
						default:
							break;
					}
				}
			}
		}
	}

	/** 塗り予定のブロックをエリア選択で更新する */
	private void addNextDrawArea(int[][] nextDraw, int selectBlock, int x, int y){
		Stage stage = stageCreateParam.getDrawingStage();
		int[][] block = stage.getBlock();
		int[][] obstacle = stage.getObstacle();
		int[][] back = stage.getBack();

		nextDraw[y][x] = 1;

		if(selectBlock == 5){
			if(x > 1){
				if(nextDraw[y][x - 1] == 0 && block[y][x] == block[y][x - 1] && obstacle[y][x] == obstacle[y][x - 1] && back[y][x] == back[y][x - 1]){
					addNextDrawArea(nextDraw, selectBlock, x - 1, y);
				}
			}
			if(x < Stage.getWidth() - 2){
				if(nextDraw[y][x + 1] == 0 && block[y][x] == block[y][x + 1] && obstacle[y][x] == obstacle[y][x + 1] && back[y][x] == back[y][x + 1]){
					addNextDrawArea(nextDraw, selectBlock, x + 1, y);
				}
			}
			if(y > 1){
				if(nextDraw[y - 1][x] == 0 && block[y][x] == block[y - 1][x] && obstacle[y][x] == obstacle[y - 1][x] && back[y][x] == back[y - 1][x]){
					addNextDrawArea(nextDraw, selectBlock, x, y - 1);
				}
			}
			if(y < Stage.getHeight() - 2){
				if(nextDraw[y + 1][x] == 0 && block[y][x] == block[y + 1][x] && obstacle[y][x] == obstacle[y + 1][x] && back[y][x] == back[y + 1][x]){
					addNextDrawArea(nextDraw, selectBlock, x, y + 1);
				}
			}
		}else{
			int array[][];

			if(selectBlock == 4){
				array = back;
			}else{
				array = obstacle;
			}

			if(x > 1){
				if(nextDraw[y][x - 1] == 0 && block[y][x] == block[y][x - 1] && array[y][x] == array[y][x - 1]){
					addNextDrawArea(nextDraw, selectBlock, x - 1, y);
				}
			}
			if(x < Stage.getWidth() - 2){
				if(nextDraw[y][x + 1] == 0 && block[y][x] == block[y][x + 1] && array[y][x] == array[y][x + 1]){
					addNextDrawArea(nextDraw, selectBlock, x + 1, y);
				}
			}
			if(y > 1){
				if(nextDraw[y - 1][x] == 0 && block[y][x] == block[y - 1][x] && array[y][x] == array[y - 1][x]){
					addNextDrawArea(nextDraw, selectBlock, x, y - 1);
				}
			}
			if(y < Stage.getHeight() - 2){
				if(nextDraw[y + 1][x] == 0 && block[y][x] == block[y + 1][x] && array[y][x] == array[y + 1][x]){
					addNextDrawArea(nextDraw, selectBlock, x, y + 1);
				}
			}
		}
	}

	/** 塗り予定のブロックを線選択で更新する. */
	private void addNextDrawLine(int[][] nextDraw, Point prePoint,  Point nextPoint, boolean isFront){
		StagePaint3D stagePaint = stageCreateParam.getStagePaint();

		double x1 = prePoint.getX();
		double y1 = prePoint.getY();
		double x2 = nextPoint.getX();
		double y2 = nextPoint.getY();

		double dl = 1.0;
		double l = Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
		double dx = dl*(x2 - x1)/l;
		double dy = dl*(y2 - y1)/l;
		int count = (int)(l/dl);

		double x = x1;
		double y = y1;
		int blockX, blockY;
		while(count >= 0){
			if(isFront){
				blockX = (int)(stagePaint.getCameraPoint().getX() + (x - 400)/stagePaint.getBlockWidthFront());
				blockY = (int)(stagePaint.getCameraPoint().getY() + (y - 300)/stagePaint.getBlockWidthFront());
			}else{
				blockX = (int)(stagePaint.getCameraPoint().getX() + (x - 400)/stagePaint.getBlockWidthBack());
				blockY = (int)(stagePaint.getCameraPoint().getY() + (y - 300)/stagePaint.getBlockWidthBack());
			}

			if(0 < blockX && blockX < Stage.getWidth() - 1 && 0 < blockY && blockY < Stage.getHeight() - 1){
				nextDraw[blockY][blockX] = 1;
			}
			x += dx;
			y += dy;
			count --;
		}

		if(isFront){
			blockX = (int)(stagePaint.getCameraPoint().getX() + (x2 - 400)/stagePaint.getBlockWidthFront());
			blockY = (int)(stagePaint.getCameraPoint().getY() + (y2 - 300)/stagePaint.getBlockWidthFront());
		}else{
			blockX = (int)(stagePaint.getCameraPoint().getX() + (x2 - 400)/stagePaint.getBlockWidthBack());
			blockY = (int)(stagePaint.getCameraPoint().getY() + (y2 - 300)/stagePaint.getBlockWidthBack());
		}

		if(0 < blockX && blockX < Stage.getWidth() - 1 && 0 < blockY && blockY < Stage.getHeight() - 1){
			nextDraw[blockY][blockX] = 1;
		}
	}

	/** 塗り予定のブロックを四角形選択で更新する. */
	private void addNextDrawRectangle(int[][] nextDraw, int preX, int preY, int nextX, int nextY, boolean isBlock){
		if(preX > nextX){
			int buf = preX;
			preX = nextX;
			nextX = buf;
		}
		if(preY > nextY){
			int buf = preY;
			preY = nextY;
			nextY = buf;
		}
		preX = Math.max(1, preX);
		preY = Math.max(1, preY);
		nextX = Math.min(Stage.getWidth() - 2, nextX);
		nextY = Math.min(Stage.getHeight() - 2, nextY);
		for(int i = preY; i <= nextY; i ++){
			for(int j = preX; j <= nextX; j ++){
				if(isBlock){
					nextDraw[i][j] = 1;
				}else{
					nextDraw[i][j] = 0;
				}
			}
		}
	}

	/** マウスホイールの回転を取得 */
	private void mouseWheelUpdate(){
		int notches = gameParam.getMouseParam().getNotchesMoment();

		float disToBlock = stageCreateParam.getStagePaint().getDisToBlock();
		if(notches > 0){
			disToBlock -= 100.0f;
			if(disToBlock < 400.0f){
				disToBlock = 400.0f;
			}
			stageCreateParam.getStagePaint().setDisToBlock(disToBlock);

		}else if(notches < 0){
			disToBlock += 100.0f;
			if(disToBlock > 2500.0f){
				disToBlock = 2500.0f;
			}
			stageCreateParam.getStagePaint().setDisToBlock(disToBlock);
		}
	}

	/** マウス上にあるブロックの位置を更新 */
	private void mouseOnBlockPositionUpdate(){
		Point point = gameParam.getMouseParam().getPoint();
		StageMakePaint3D stagePaint = stageCreateParam.getStagePaint();

		int x, y;
		if(stageCreateParam.getSelectBlock() == 4){
			x = (int)(stagePaint.getCameraPoint().getX() + (point.x - 400)/stagePaint.getBlockWidthBack());
			y = (int)(stagePaint.getCameraPoint().getY() + (point.y - 300)/stagePaint.getBlockWidthBack());
		}else{
			x = (int)(stagePaint.getCameraPoint().getX() + (point.x - 400)/stagePaint.getBlockWidthFront());
			y = (int)(stagePaint.getCameraPoint().getY() + (point.y - 300)/stagePaint.getBlockWidthFront());
		}

		stageCreateParam.setNextMouseOnBlockX(x);
		stageCreateParam.setNextMouseOnBlockY(y);
	}

	/** フレーム経過による塗り予定ブロックの更新 */
	private void nextDrawUpdate(){
		int[][] nextDraw = stageCreateParam.getStagePaint().getNextDraw();
		int selectDrawWay = stageCreateParam.getSelectDrawWay();
		int selectBlock = stageCreateParam.getSelectBlock();
		int mouseOnBlockX = stageCreateParam.getMouseOnBlockX();
		int mouseOnBlockY = stageCreateParam.getMouseOnBlockY();
		int nextMouseOnBlockX = stageCreateParam.getNextMouseOnBlockX();
		int nextMouseOnBlockY = stageCreateParam.getNextMouseOnBlockY();
		Point prePoint = gameParam.getMouseParam().getPrePoint();
		Point point = gameParam.getMouseParam().getPoint();

		if(selectDrawWay == 1){
			nextDrawClear(nextDraw);
			addNextDrawLine(nextDraw, prePoint, point, selectBlock != 4);
		}else{
			if(!stageCreateParam.isDrawable()){
				if(0 < mouseOnBlockX && mouseOnBlockX < Stage.getWidth() - 1 && 0 < mouseOnBlockY && mouseOnBlockY < Stage.getHeight() - 1){
					nextDraw[mouseOnBlockY][mouseOnBlockX] = 0;
				}
				if(0 < nextMouseOnBlockX && nextMouseOnBlockX < Stage.getWidth() - 1 && 0 < nextMouseOnBlockY && nextMouseOnBlockY < Stage.getHeight() - 1){
					nextDraw[nextMouseOnBlockY][nextMouseOnBlockX] = 1;
				}
			}
		}
	}

	/** 左クリックを押したときの処理 */
	private void leftClickMomentUpdate(){
		int[][] nextDraw = stageCreateParam.getStagePaint().getNextDraw();
		int selectDrawWay = stageCreateParam.getSelectDrawWay();
		int selectBlock = stageCreateParam.getSelectBlock();
		Point point = gameParam.getMouseParam().getPoint();
		int nextMouseOnBlockX = stageCreateParam.getNextMouseOnBlockX();
		int nextMouseOnBlockY = stageCreateParam.getNextMouseOnBlockY();
		StageMakePaint3D stagePaint = stageCreateParam.getStagePaint();

		switch(stageCreateParam.getMouseOn()){
		case 0:
			stageCreateParam.setDrawable(true);
			if(selectDrawWay == 2){
				stageCreateParam.getPreMousePoint().x = point.x;
				stageCreateParam.getPreMousePoint().y = point.y;
			}else if(selectDrawWay == 3){
				stageCreateParam.setPreMouseOnBlockX(nextMouseOnBlockX);
				stageCreateParam.setPreMouseOnBlockY(nextMouseOnBlockY);
			}else if(selectDrawWay == 4){
				if(0 < nextMouseOnBlockX && nextMouseOnBlockX < Stage.getWidth() - 1 && 0 < nextMouseOnBlockY && nextMouseOnBlockY < Stage.getHeight() - 1){
					nextDrawClear(nextDraw);
					Stage stage = stageCreateParam.getDrawingStage();
					int[][] block = stage.getBlock();
					int[][] obstacle = stage.getObstacle();
					int[][] back = stage.getBack();

					if(!((selectBlock == 1 && block[nextMouseOnBlockY][nextMouseOnBlockX] == 1) || (selectBlock == 2 && block[nextMouseOnBlockY][nextMouseOnBlockX] == 2)
							|| (selectBlock == 3 && obstacle[nextMouseOnBlockY][nextMouseOnBlockX] == 1) || (selectBlock == 4 && back[nextMouseOnBlockY][nextMouseOnBlockX] == 1)
							|| (selectBlock == 5 && block[nextMouseOnBlockY][nextMouseOnBlockX] == 0 && obstacle[nextMouseOnBlockY][nextMouseOnBlockX] == 0 && back[nextMouseOnBlockY][nextMouseOnBlockX] == 0))){
						addNextDrawArea(nextDraw, selectBlock, nextMouseOnBlockX, nextMouseOnBlockY);
					}
				}
			}
			break;
		case 5:	// 名前の変更
			gameParam.setOperation(false);
			stageCreateParam.setEditName(true);
			new InputFrame("名前を入力してください", stageCreateParam, gameParam);
			break;
		case 6:	// 足場選択
			stageCreateParam.setSelectBlock(1);
			stagePaint.setGrid(1);
			break;
		case 7:	// 水中選択
			stageCreateParam.setSelectBlock(2);
			stagePaint.setGrid(1);
			break;
		case 8:	// 障害物選択
			stageCreateParam.setSelectBlock(3);
			stagePaint.setGrid(1);
			break;
		case 9:	// 背景選択
			stageCreateParam.setSelectBlock(4);
			stagePaint.setGrid(2);
			break;
		case 10:	// 消しゴム選択
			stageCreateParam.setSelectBlock(5);
			stagePaint.setGrid(1);
			break;
		case 11:	// 点選択
			stageCreateParam.setSelectDrawWay(1);
			break;
		case 12:	// 線選択
			stageCreateParam.setSelectDrawWay(2);
			break;
		case 13:	//  四角形選択
			stageCreateParam.setSelectDrawWay(3);
			break;
		case 14: 	// エリア選択
			stageCreateParam.setSelectDrawWay(4);
			break;
		case 19:	// テストプレイ開始
			if(stageCreateParam.isSaveable()){
				nextDrawClear(nextDraw);
				testParam.getStagePaint().setDrawedStage(stageCreateParam.getDrawingStage());
				toGameMove(8, true, 1, true, true);
			}
			break;
		case 15:	// 終了
			nextDrawClear(nextDraw);
			toGameMove(4, true, 1, false, false);
			break;
		case 16:	// 保存
			if(!stageCreateParam.isSaved() && stageCreateParam.isSaveable()){
				stageCreateParam.setSaved(true);
				// 新規保存
				if(stageCreateParam.isNewStageMake()){
					gameParam.getCreatedStages().add(
							stageCreateParam.getDrawingStage().clone()
					);
					stageCreateParam.setNewStageMake(false);
					stageCreateParam.setEditStageNo(gameParam.getCreatedStages().size() - 1);
				}else{	// 上書き保存
					gameParam.getCreatedStages().set(
						stageCreateParam.getEditStageNo(),
						stageCreateParam.getDrawingStage().clone()
					);
				}
				gameParam.getDataIO().saveStage("createStages.txt", gameParam.getCreatedStages(), true);
			}
			break;
		case 17:	// 1手戻る
			if(stageCreateParam.getActiveStage() > 0){
				stageCreateParam.setSaved(false);
				stageCreateParam.minusActiveStage();
				stageCreateParam.getDrawingStage().copy(stageCreateParam.getStageList().get(stageCreateParam.getActiveStage()));
				stageJudge();
			}
			break;
		case 18:	// 1手進む
			if(stageCreateParam.getActiveStage() < stageCreateParam.getStageList().size() - 1){
				stageCreateParam.setSaved(false);
				stageCreateParam.plusActiveStage();
				stageCreateParam.getDrawingStage().copy(stageCreateParam.getStageList().get(stageCreateParam.getActiveStage()));
				stageJudge();
			}
			break;
			default:
				break;
		}
	}

	/** 左クリックが押されているときの処理 */
	private void leftClickUpdate(){
		if(stageCreateParam.isDrawable()){
			int[][] nextDraw = stageCreateParam.getStagePaint().getNextDraw();
			int selectBlock = stageCreateParam.getSelectBlock();
			int selectDrawWay = stageCreateParam.getSelectDrawWay();
			int mouseOnBlockX = stageCreateParam.getMouseOnBlockX();
			int mouseOnBlockY = stageCreateParam.getMouseOnBlockY();
			int nextMouseOnBlockX = stageCreateParam.getNextMouseOnBlockX();
			int nextMouseOnBlockY = stageCreateParam.getNextMouseOnBlockY();
			Point point = gameParam.getMouseParam().getPoint();
			Point preMousePoint  = stageCreateParam.getPreMousePoint();
			int preMouseOnBlockX = stageCreateParam.getPreMouseOnBlockX();
			int preMouseOnBlockY = stageCreateParam.getPreMouseOnBlockY();

			switch(selectDrawWay){
			case 1:
				nextDrawToStage(nextDraw, selectBlock);
				stageCreateParam.getDrawingStage().distMap(mouseOnBlockX, mouseOnBlockY, nextMouseOnBlockX, nextMouseOnBlockY);
				break;
			case 2:
				nextDrawClear(nextDraw);
				addNextDrawLine(nextDraw, preMousePoint, point, selectBlock != 4);
				break;
			case 3:
				if(nextMouseOnBlockX != mouseOnBlockX || nextMouseOnBlockY != mouseOnBlockY){
					addNextDrawRectangle(nextDraw, preMouseOnBlockX, preMouseOnBlockY, mouseOnBlockX, mouseOnBlockY, false);
					addNextDrawRectangle(nextDraw, preMouseOnBlockX, preMouseOnBlockY, nextMouseOnBlockX, nextMouseOnBlockY, true);
				}
				break;
			case 4:
				if(nextMouseOnBlockX != mouseOnBlockX || nextMouseOnBlockY != mouseOnBlockY){
					if(0 < nextMouseOnBlockX && nextMouseOnBlockX < Stage.getWidth() - 1 && 0 < nextMouseOnBlockY && nextMouseOnBlockY < Stage.getHeight() - 1){
						if(nextDraw[nextMouseOnBlockY][nextMouseOnBlockX] == 0){
							nextDrawClear(nextDraw);
							Stage stage = stageCreateParam.getDrawingStage();
							int[][] block = stage.getBlock();
							int[][] obstacle = stage.getObstacle();
							int[][] back = stage.getBack();

							if(!((selectBlock == 1 && block[nextMouseOnBlockY][nextMouseOnBlockX] == 1) || (selectBlock == 2 && block[nextMouseOnBlockY][nextMouseOnBlockX] == 2)
									|| (selectBlock == 3 && obstacle[nextMouseOnBlockY][nextMouseOnBlockX] == 1) || (selectBlock == 4 && back[nextMouseOnBlockY][nextMouseOnBlockX] == 1)
									|| (selectBlock == 5 && block[nextMouseOnBlockY][nextMouseOnBlockX] == 0 && obstacle[nextMouseOnBlockY][nextMouseOnBlockX] == 0 && back[nextMouseOnBlockY][nextMouseOnBlockX] == 0))){
								addNextDrawArea(nextDraw, selectBlock, nextMouseOnBlockX, nextMouseOnBlockY);
							}
						}
					}else{
						nextDrawClear(nextDraw);
					}
				}
				break;
				default:
					break;
			}
		}
	}

	/** 左クリックを離したときの処理 */
	private void leftLeaveMomentUpdate(){
		int[][] nextDraw = stageCreateParam.getStagePaint().getNextDraw();
		int selectDrawWay = stageCreateParam.getSelectDrawWay();
		int selectBlock = stageCreateParam.getSelectBlock();
		int mouseOnBlockX = stageCreateParam.getMouseOnBlockX();
		int mouseOnBlockY = stageCreateParam.getMouseOnBlockY();
		int preMouseOnBlockX = stageCreateParam.getPreMouseOnBlockX();
		int preMouseOnBlockY = stageCreateParam.getPreMouseOnBlockY();

		if(stageCreateParam.isDrawable()){
			if(selectDrawWay == 2 || selectDrawWay == 4){
				nextDrawToStage(nextDraw, selectBlock);
				nextDrawClear(nextDraw);
				stageCreateParam.getDrawingStage().distMap();
			}else if(selectDrawWay == 3){
				nextDrawToStage(nextDraw, selectBlock);
				addNextDrawRectangle(nextDraw, preMouseOnBlockX, preMouseOnBlockY, mouseOnBlockX, mouseOnBlockY, false);
				stageCreateParam.getDrawingStage().distMap(preMouseOnBlockX, preMouseOnBlockY, mouseOnBlockX, mouseOnBlockY);
			}
			if(stageCreateParam.isEditAdvanced()){
				stageCreateParam.setEditAdvanced(false);
				updateHistory();
			}
			stageCreateParam.setDrawable(false);
		}
	}

	/** 画面スクロール */
	private void scrollUpdate(){
		Point point = gameParam.getMouseParam().getPoint();
		float disToBlock = stageCreateParam.getStagePaint().getDisToBlock();

		// 画面スクロール
		if(gameParam.getMouseParam().isRightClick()){
			if(stageCreateParam.isScrollable()){
				float v = disToBlock/40000.0f;
				float scrX = stageCreateParam.getCameraPoint().x - (point.x - stageCreateParam.getMousePoint().x)*v;
				float scrY = stageCreateParam.getCameraPoint().y - (point.y - stageCreateParam.getMousePoint().y)*v;
				if(scrX < 0){
					scrX = 0.0f;
				}else if(scrX >= Stage.getWidth()){
					scrX = (float)(Stage.getWidth() - 0.01);
				}
				if(scrY < 0){
					scrY = 0.0f;
				}else if(scrY >= Stage.getHeight()){
					scrY = (float)(Stage.getHeight() - 0.01);
				}
				stageCreateParam.getStagePaint().setCameraPoint(new Point2D.Float(scrX, scrY));
			}
		}else{
			stageCreateParam.setScrollable(false);
		}

		// 画面スクロール開始
		if(gameParam.getMouseParam().isRightClickMoment()){
			stageCreateParam.setCameraPoint(stageCreateParam.getStagePaint().getCameraPoint());
			stageCreateParam.setMousePoint(point);
			stageCreateParam.setScrollable(true);
		}
	}

	/** オブジェクトの可視状態の更新 */
	private void objectVisibleUpdate(){
		if(stageCreateParam.isSaved() || !stageCreateParam.isSaveable()){
			stageCreateParam.setObjectVisible(15, false);
		}else{
			stageCreateParam.setObjectVisible(15, true);
		}
		if(stageCreateParam.getActiveStage() == 0){
			stageCreateParam.setObjectVisible(16, false);
		}else{
			stageCreateParam.setObjectVisible(16, true);
		}
		if(stageCreateParam.getActiveStage() == stageCreateParam.getStageList().size() - 1){
			stageCreateParam.setObjectVisible(17, false);
		}else{
			stageCreateParam.setObjectVisible(17, true);
		}
		if(stageCreateParam.isSaveable()){
			stageCreateParam.setObjectVisible(18, true);
		}else{
			stageCreateParam.setObjectVisible(18, false);
		}
	}

	public void play() {
		if(gameParam.isOperation()){
			stageCreateParam.setMouseOn(mouseLook(stageCreateParam.getObjectList()));

			// マウスホイールの回転を取得
			mouseWheelUpdate();

			// マウス上にあるブロックの位置を計算
			mouseOnBlockPositionUpdate();

			// フレーム経過による塗り予定ブロックの更新
			nextDrawUpdate();

			// 左クリックが押されたときの処理
			if(gameParam.getMouseParam().isLeftClickMoment()){
				leftClickMomentUpdate();
			}

			// 左クリックが押されているときの処理
			if(gameParam.getMouseParam().isLeftClick()){
				leftClickUpdate();
			}

			// 左クリックが離されたときの処理
			if(gameParam.getMouseParam().isLeftLeaveMoment()){
				leftLeaveMomentUpdate();
			}

			// マウス上にあるブロックの位置を更新
			stageCreateParam.mouseOnBlockUpdate();

			// 画面スクロール
			scrollUpdate();

			// オブジェクトの可視状態の更新
			objectVisibleUpdate();
		}

		stageCreateParam.addFrame();
	}
}
