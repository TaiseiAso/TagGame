package game;

import java.awt.Graphics;

public class TitlePaint extends PaintClass{
	/** タイトル画面のパラメータを管理するクラスのオブジェクト */
	private TitleParam titleParam;

	/** コンストラクタ */
	public TitlePaint(TitleParam titleParam, GameParam  gameParam){
		super(gameParam);
		this.titleParam = titleParam;
	}

	public void paint(Graphics g) {
		paintObject(g, titleParam);

		if(gameParam.isGameMove()){
			paintFade(g);
		}
	}
}
