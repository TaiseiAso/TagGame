package game;

import java.awt.Graphics;

public class MenuPaint extends PaintClass{
	/** メニュー画面のパラメータを管理するクラスのオブジェクト */
	private MenuParam menuParam;

	/** コンストラクタ */
	public MenuPaint(MenuParam menuParam, GameParam gameParam){
		super(gameParam);
		this.menuParam = menuParam;
	}

	public void paint(Graphics g) {
		paintObject(g, menuParam);

		if(gameParam.isGameMove()){
			paintFade(g);
		}
	}
}
