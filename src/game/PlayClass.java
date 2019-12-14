package game;

import java.awt.Point;
import java.util.ArrayList;

public abstract class PlayClass{
	/** ゲーム全体に共通するパラメータを管理するクラスのオブジェクト */
	protected GameParam gameParam;

	/** コンストラクタ */
	public PlayClass(GameParam gameParam){
		this.gameParam = gameParam;
	}

	/** ゲーム画面の処理の実行 */
	public abstract void play();

	/** すべてのオブジェクトとマウスとの判定を行う */
	protected int mouseLook(ArrayList<ImageObject> objectList){
		Point mousePoint = gameParam.getMouseParam().getPoint();

		int mouseOn = 0;
		for(int i = 0; i < objectList.size(); i ++){
			ImageObject object = objectList.get(i);

			if(object.clickableCheck(mousePoint)){
				mouseOn = i+1;
				break;
			}
		}
		return mouseOn;
	}

	/** ゲーム画面遷移を開始する */
	protected void toGameMove(int postGameMode, boolean gameMoveClear, int gameMoveFade, boolean gameMoveNext, boolean postGameMoveNext){
		gameParam.setGameMoveStart(true);
		gameParam.setGameMove(true);
		gameParam.setOperation(false);
		gameParam.setPostGameMode(postGameMode);
		gameParam.setGameMoveClear(gameMoveClear);
		gameParam.setGameMoveFade(gameMoveFade);
		gameParam.setGameMoveNext(gameMoveNext);
		gameParam.setPostGameMoveNext(postGameMoveNext);
	}
}
