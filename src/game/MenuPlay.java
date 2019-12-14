package game;

public class MenuPlay extends PlayClass{
	/** メニュー画面のパラメータを管理するクラスのオブジェクト */
	private MenuParam menuParam;

	/** コンストラクタ */
	public MenuPlay(MenuParam menuParam, GameParam gameParam){
		super(gameParam);
		this.menuParam = menuParam;
	}

	public void play() {
		if(gameParam.isOperation()){
			menuParam.setMouseOn(mouseLook(menuParam.getObjectList()));

			if(gameParam.getMouseParam().isLeftClickMoment()){
				switch(menuParam.getMouseOn()){
				case 1:
					toGameMove(3, true, 0, false, true);
					break;
				case 2:
					toGameMove(4, true, 0, false, true);
					break;
				case 3:
					toGameMove(5, true, 0, true, true);
					break;
				case 4:
					toGameMove(6, true, 0, true, true);
					break;
				case 5:
					toGameMove(1, true, 1, false, true);
					break;
					default:
						break;
				}
			}
		}

		menuParam.addFrame();
	}
}
