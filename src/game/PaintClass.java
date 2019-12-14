package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class PaintClass {
	/** ゲーム全体に共通するパラメータを管理するクラスのオブジェクト */
	protected GameParam gameParam;
	/** 描画を手助けするクラスのオブジェクト */
	protected PolygonPaint polygonPaint;
	/** ゲームに使用するすべての画像を番号順に管理するクラスのオブジェクト */
	protected ImageBox imageBox;

	/** コンストラクタ */
	public PaintClass(GameParam gameParam){
		this.gameParam = gameParam;
		this.polygonPaint = gameParam.getPolygonPaint();
		this.imageBox = gameParam.getImageBox();
	}

	/** 整数のけた数を返す */
	public int integerSize(int num){
		for(int i = 0;; i ++){
			num /= 10;
			if(num == 0){
				return i+1;
			}
		}
	}

	/** 整数を画像で描画する */
	public void paintNumber(Graphics g, int num, int flag, int x,  int y){
		int size = integerSize(num);
		for(int i = 0; i < size; i ++){
			imageBox.drawImage(g, 54+num%10, x+40*(size-i-1), y, 40, 50, 0, 0, 0, 1, 1);
			num /= 10;
		}
		if(flag == 1){
			imageBox.drawImage(g, 64, x+40*size, y, 40, 50, 0, 0, 0, 1, 1);
		}else if(flag == 2){
			imageBox.drawImage(g, 65, x+40*size, y, 60, 50, 0, 0, 0, 1, 1);
		}else if(flag == 3){
			imageBox.drawImage(g, 66, x+40*size, y, 40, 50, 0, 0, 0, 1, 1);
		}
	}

	/** ゲーム画面を描画 */
	public abstract void paint(Graphics g);

	/** 2つの整数値の中間を返す */
	private int middleCalcInt(int a, int b, int frame){
		return 2*((gameParam.getGameMoveMaxFrame()/2 - frame)*a + frame*b)/gameParam.getGameMoveMaxFrame();
	}

	/** 2つの小数点数値の中間を返す */
	private float middleCalcFloat(float a, float b, int frame){
		return 2*((gameParam.getGameMoveMaxFrame()/2 - frame)*a + frame*b)/(float)gameParam.getGameMoveMaxFrame();
	}

	/** フェードアウトを描画 */
	protected void paintFade(Graphics g){
		int gameMoveFade = gameParam.getGameMoveFade();

		if(gameMoveFade != 0){
			Color c;
			if(gameMoveFade == 1){
				c = Color.BLACK;
			}else{
				c = Color.WHITE;
			}
			polygonPaint.fillRectangle(g, c, 0, 0, 800, 600,
					1.0f - 2*Math.abs(gameParam.getGameMoveFrame() - gameParam.getGameMoveMaxFrame()/2)/(float)gameParam.getGameMoveMaxFrame());
		}
	}

	/** 画面遷移中のオブジェクトを描画 */
	private void paintMoveObject(Graphics g, int imgNo, AnimationParam animation1, AnimationParam animation2, AnimationParam animation3, int frame, float mag){
		imageBox.drawImage(g, imgNo,
				middleCalcInt(animation1.getX(), animation2.getX(), frame),
				middleCalcInt(animation1.getY(), animation2.getY(), frame),
				middleCalcInt(animation1.getW(), animation2.getW(), frame),
				middleCalcInt(animation1.getH(), animation2.getH(), frame),
				animation3.getAxisX(),
				animation3.getAxisY(),
				middleCalcInt(animation1.getAngle(), animation2.getAngle(), frame),
				mag*middleCalcFloat(animation1.getMagnification(), animation2.getMagnification(), frame),
				middleCalcFloat(animation1.getTransparency(), animation2.getTransparency(), frame));
	}

	/** オブジェクトを描画 */
	protected void paintObject(Graphics g, ParamClass param){
		int gameMode = param.getNo();
		ArrayList<ImageObject> objectList = param.getObjectList();
		int mouseOn = param.getMouseOn();

		int gameMoveFrame = gameParam.getGameMoveFrame();
		int gameMoveHalfFrame = gameParam.getGameMoveMaxFrame()/2;

		boolean gameMoveNext = gameParam.isGameMoveNext();
		boolean postGameMoveNext = gameParam.isPostGameMoveNext();

		for(int i = 0; i < objectList.size(); i ++){
			ImageObject object = objectList.get(i);

			if(object.isVisible()){
				ArrayList<AnimationParam> animationList = object.getAnimationList();

				if(gameParam.isGameMove()){
					if(gameParam.getPostGameMode() == gameMode && gameMoveFrame >= gameMoveHalfFrame){
						if(postGameMoveNext){
							paintMoveObject(g, object.getImgNo(), animationList.get(0), animationList.get(1), animationList.get(0), gameMoveFrame - gameMoveHalfFrame, 1.0f);
						}else{
							paintMoveObject(g, object.getImgNo(), animationList.get(2), animationList.get(1), animationList.get(2), gameMoveFrame - gameMoveHalfFrame, 1.0f);
						}
					}else if(gameParam.getGameMode() == gameMode && gameMoveFrame < gameMoveHalfFrame){
						if(mouseOn == i+1){
							paintMoveObject(g, object.getImgNo(), animationList.get(1), animationList.get(3), animationList.get(3), gameMoveFrame, object.getMagnification());
						}else if(gameMoveNext){
							paintMoveObject(g, object.getImgNo(), animationList.get(1), animationList.get(2), animationList.get(2), gameMoveFrame, 1.0f);
						}else{
							paintMoveObject(g, object.getImgNo(), animationList.get(1), animationList.get(0), animationList.get(0), gameMoveFrame, 1.0f);
						}
					}
				}else{
					float mag;
					if(mouseOn == i+1){
						mag = object.getMagnification();
					}else{
						mag = 1.0f;
					}

					AnimationParam animation = animationList.get(1);
					imageBox.drawImage(g, object.getImgNo(),  animation.getX(), animation.getY(),
							animation.getW(), animation.getH(), animation.getAxisX(), animation.getAxisY(),
							animation.getAngle(), mag, animation.getTransparency());
				}
			}
		}
	}
}
