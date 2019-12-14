package game;

import java.awt.Color;
import java.awt.Graphics;

public class BackPaint extends PaintClass{
	/** 背景のパラメータを管理するクラスのオブジェクト */
	private BackParam backParam;

	/** 各画面における背景のベースカラー */
	private Color[] color;

	/** コンストラクタ */
	public BackPaint(BackParam backParam, GameParam gameParam){
		super(gameParam);
		this.backParam = backParam;

		// ベースカラーを登録
		color = new Color[10];
		color[0] = new Color(255, 255, 255);	// 背景色
		color[1] = new Color(255,200,255);	// 模様色(タイトル)
		color[2] = new Color(200,200,255);	// 模様色(メニュー)
		color[3] = new Color(255,200,200);	// 模様色(バトルメニュー)
		color[4] = new Color(200,255,255);	// 模様色(ステージメニュー)
		color[5] = new Color(255,255,200);	// 模様色(リザルト)
		color[6] = new Color(200,255,200);	// 模様色(オプション)
		color[7] = new Color(255, 255, 255);// 模様色(ステージクリエイト)
		color[8] = new Color(255,255,255);	// 模様色(テストプレイ)
		color[9] = new Color(255, 255, 255);// 模様色(バトル)
	}

	public Color mixColor(Color c1, Color c2, float a){
		int r = (int)(c2.getRed()*a + c1.getRed()*(1.0f - a));
		int g = (int)(c2.getGreen()*a + c1.getGreen()*(1.0f - a));
		int b = (int)(c2.getBlue()*a + c1.getBlue()*(1.0f - a));
		if(r < 0) r = 0;
		else if(r > 255) r = 255;
		if(g < 0) g = 0;
		else if(g > 255) g = 255;
		if(b < 0) b = 0;
		else if(b > 255) b = 255;
		return new Color(r, g, b);
	}

	public void paint(Graphics g) {
		// 背景色を描画
		g.setColor(color[0]);
		g.fillRect(0, 0, 800, 600);

		if(gameParam.isGameMove()){
			g.setColor(mixColor(color[gameParam.getGameMode()], color[gameParam.getPostGameMode()], gameParam.getGameMoveFrame()/(float)gameParam.getGameMoveMaxFrame()));
		}else{
			g.setColor(color[gameParam.getGameMode()]);
		}
		for(int i = 0; i <= 15; i ++){
			for(int j = i%2; j <= 20; j +=2){
				g.fillRect((j - 1)*40+(int)(backParam.getFrame()*0.4)%40, (i - 1)*40+(int)(backParam.getFrame()*0.4)%40, 40, 40);
			}
		}
	}
}
