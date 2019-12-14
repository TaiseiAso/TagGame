package game;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GameCore extends JFrame{
	/** パネル */
	private JPanel allotmentPanel;

	/** 時間制御を管理するクラスのオブジェクト */
	private FrameRate frameRate;
	/** マウスを管理するクラスのオブジェクト */
	private Mouse mouse;

	/** ゲームの処理や描画の分担を担当するクラスのオブジェクト */
	private GameAllotment gameAllotment;
	/** ゲーム全体に共通するパラメータを管理するクラスのオブジェクト */
	private GameParam gameParam;
	/** マウス情報を管理するクラスのオブジェクト */
	private MouseParam mouseParam;

	/** コンストラクタ */
	public GameCore(){
		// タイトルテキストの設定
		setTitle("TAG GAME 2017 by Taisei Aso");
		// クローズイベントの設定
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 枠の大きさを考慮して画面サイズを800,600に合わせて固定する
        setVisible(true);
        Insets insets = getInsets();
        setVisible(false);
        int width = 800 + insets.left + insets.right;
        int height = 600 + insets.top + insets.bottom;
        setBounds(200, 100, width, height);
        setResizable(false);

        // フレームレートを60fpsに設定
        this.frameRate = new FrameRate(60);

        // パネルを生成し画面に追加する
        this.allotmentPanel = new JPanel();
        this.allotmentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.allotmentPanel.setLayout(new BorderLayout(0, 0));
        this.allotmentPanel.setVisible(true);
        this.allotmentPanel.setOpaque(false);

        // ゲーム全体に共通するパラメータを管理するクラスのインスタンスを取得
        this.gameParam = new GameParam();

        //	マウス検知を付与し、パネルに貼る
		this.mouse = new Mouse();
		this.gameAllotment = new GameAllotment(gameParam);
		this.gameAllotment.addMouseListener(mouse);
		this.gameAllotment.addMouseMotionListener(mouse);
		this.gameAllotment.addMouseWheelListener(mouse);
		this.allotmentPanel.add(gameAllotment);
		setContentPane(allotmentPanel);

		// マウス情報を管理するクラスのオブジェクトを取得
		this.mouseParam = this.gameParam.getMouseParam();
	}

	/** ゲーム画面遷移中の処理を実行 */
	public void movePlay(){
		// 画面遷移のフレーム数に1足す
		gameParam.addGameMoveFrame();

		// フレーム経過したら画面遷移を止める
		if(gameParam.getGameMoveFrame() == gameParam.getGameMoveMaxFrame()){
			// 前の画面を今の画面にする
			gameParam.updateGameMode();
			// 画面遷移を止める
			gameParam.setGameMove(false);
			// マウス操作可能にする
			gameParam.setOperation(true);
			//	画面遷移のフレーム数を0で初期化する
			gameParam.setGameMoveFrame(0);
		}
	}

	/** ゲームの実行 */
	public void gameloop(){
		// ゲームが終了するまでゲームループを継続する
		// これが1周することを1フレームと呼ぶことにする
		while(gameParam.getGameMode() > 0){
			// マウス情報を更新する
			mouseParam.updateMouse(mouse);

			// ゲーム画面の処理を実行する
			gameAllotment.play();

			// ゲーム開始からの経過フレームに1を足す
			gameParam.addTotalFrame();

			// ゲーム画面遷移中の処理
			if(gameParam.isGameMove()){
				movePlay();
			}

			// ゲーム画面を再描画する
			gameAllotment.repaint();

			// フレームレートを合わせるために待機する
			frameRate.waitTime();
		}
	}
}
