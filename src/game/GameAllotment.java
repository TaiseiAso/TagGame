package game;

import java.awt.Graphics;

import javax.swing.JPanel;

public class GameAllotment extends JPanel{
	/** ゲーム全体に共通するパラメータを管理するクラスのオブジェクト */
	private GameParam gameParam;

	/** ゲーム実行を担当するクラスのオブジェクトの配列 */
	private PlayClass[] playClass;
	/** 画面描画を担当するクラスのオブジェクトの配列 */
	private PaintClass[] paintClass;
	/** 変数管理を担当するクラスのオブジェクトの配列 */
	private ParamClass[] paramClass;

	/** コンストラクタ */
	public GameAllotment(GameParam gameParam){
		this.gameParam = gameParam;
		this.setDoubleBuffered(true);

		// 変数管理を担当するクラスのオブジェクトを生成する
		BackParam backParam = new BackParam(gameParam);
		TitleParam titleParam = new TitleParam(gameParam);
		MenuParam menuParam = new MenuParam(gameParam);
		BattleMenuParam battleMenuParam = new BattleMenuParam(gameParam);
		StageMenuParam stageMenuParam = new StageMenuParam(gameParam);
		ResultParam resultParam = new ResultParam(gameParam);
		OptionParam optionParam = new OptionParam(gameParam);
		StageCreateParam stageCreateParam = new StageCreateParam(gameParam);
		TestParam testParam = new TestParam(gameParam);
		BattleParam battleParam = new BattleParam(gameParam);

		this.paramClass = new ParamClass[10];
		this.paramClass[0] = backParam;
		this.paramClass[1] = titleParam;
		this.paramClass[2] = menuParam;
		this.paramClass[3] = battleMenuParam;
		this.paramClass[4] = stageMenuParam;
		this.paramClass[5] = resultParam;
		this.paramClass[6] = optionParam;
		this.paramClass[7] = stageCreateParam;
		this.paramClass[8] = testParam;
		this.paramClass[9] = battleParam;

		// 対応する変数を与えて、ゲーム実行を担当するクラスのオブジェクトを生成する
		this.playClass = new PlayClass[10];
		this.playClass[0] = new BackPlay(backParam, gameParam);
		this.playClass[1] = new TitlePlay(titleParam, gameParam);
		this.playClass[2] = new MenuPlay(menuParam, gameParam);
		this.playClass[3] = new BattleMenuPlay(battleMenuParam, battleParam, gameParam);
		this.playClass[4] = new StageMenuPlay(stageMenuParam, stageCreateParam, gameParam);
		this.playClass[5] = new ResultPlay(resultParam, gameParam);
		this.playClass[6] = new OptionPlay(optionParam, gameParam);
		this.playClass[7] = new StageCreatePlay(stageCreateParam, testParam, gameParam);
		this.playClass[8] = new TestPlay(testParam, gameParam);
		this.playClass[9] = new BattlePlay(battleParam, gameParam);

		// 対応する変数を与えて、画面描画を担当するクラスのオブジェクトを生成する
		this.paintClass = new PaintClass[10];
		this.paintClass[0] = new BackPaint(backParam, gameParam);
		this.paintClass[1] = new TitlePaint(titleParam, gameParam);
		this.paintClass[2] = new MenuPaint(menuParam, gameParam);
		this.paintClass[3] = new BattleMenuPaint(battleMenuParam, gameParam);
		this.paintClass[4] = new StageMenuPaint(stageMenuParam, gameParam);
		this.paintClass[5] = new ResultPaint(resultParam, gameParam);
		this.paintClass[6] = new OptionPaint(optionParam, gameParam);
		this.paintClass[7] = new StageCreatePaint(stageCreateParam, gameParam);
		this.paintClass[8] = new TestPaint(testParam, gameParam);
		this.paintClass[9] = new BattlePaint(battleParam, gameParam);
	}

	public boolean backCheck(int gameMode){
		if(1 <= gameMode && gameMode <= 6){
			return true;
		}
		return false;
	}

	/** ゲーム画面の描画 */
	public void paint(Graphics g){
		// 背景を描画
		if(!gameParam.isGameMove()){
			if(backCheck(gameParam.getGameMode())){
				paintClass[0].paint(g);
			}
		}else{
			if(backCheck(gameParam.getGameMode()) && gameParam.getGameMoveFrame() < gameParam.getGameMoveMaxFrame()/2){
				paintClass[0].paint(g);
			}
			if(backCheck(gameParam.getPostGameMode()) && gameParam.getGameMoveFrame() >= gameParam.getGameMoveMaxFrame()/2){
				paintClass[0].paint(g);
			}
		}

		// 遷移前の画面を描画
		paintClass[gameParam.getGameMode()].paint(g);

		// 遷移中のとき遷移後の画面も描画
		if(gameParam.isGameMove()){
			paintClass[gameParam.getPostGameMode()].paint(g);
		}
	}

	/** ゲーム画面の処理を実行 */
	public void play(){
		// 背景の処理
		if(backCheck(gameParam.getGameMode())){
			playClass[0].play();
		}

		// 現在のゲーム画面の処理
		playClass[gameParam.getGameMode()].play();

		// 画面遷移先のゲーム画面の処理
		if(gameParam.isGameMove()){
			// 画面遷移が開始されたとき
			if(gameParam.isGameMoveStart()){
				gameParam.setGameMoveStart(false);
				// 画面遷移先の初期化を行う
				if(gameParam.isGameMoveClear()){
					paramClass[gameParam.getPostGameMode()].clear();
				}
			}
			playClass[gameParam.getPostGameMode()].play();
		}
	}
}
