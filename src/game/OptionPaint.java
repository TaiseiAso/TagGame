package game;

import java.awt.Graphics;

public class OptionPaint extends PaintClass{
	/** オプション画面のパラメータを管理するクラスのオブジェクト */
	private OptionParam optionParam;

	/** コンストラクタ */
	public OptionPaint(OptionParam optionParam, GameParam gameParam){
		super(gameParam);
		this.optionParam = optionParam;
	}

	public void paint(Graphics g) {
		paintObject(g, optionParam);
	}
}
