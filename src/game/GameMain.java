package game;

public class GameMain{
	/** メインメソッド */
	public static void main(String[] args){
		// ゲーム実行のためのインスタンス生成
		GameCore gameCore = new GameCore();

		// 画面を可視化する
		gameCore.setVisible(true);

		// ゲームループを開始する
		gameCore.gameloop();

		// プログラムの正常終了をアナウンス
		System.out.println("プログラムは正常終了しました.");
	}
}
