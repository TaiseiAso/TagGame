package game;

public class Stage{
	/** ステージのブロックの横の数 */
	private static int width = 80;
	/** ステージのブロックの縦の数 */
	private static int height = 60;

	/** ステージの名前 */
	private String name;
	/** ブロックの配置 0:なにもない 1:足場 2:水中*/
	private int[][] block;
	/** 障害物の配置(足場には配置できない) */
	private int[][] obstacle;
	/** 背景の配置(足場には配置できない) */
	private int[][] back;

	/** 描画のためのマップチップの区別(ブロック) */
	private int[][] distBlock;

	/**
	 * コンストラクタ
	 */
	public Stage(){
		setName("No name");
		setBlock(new int[height][width]);
		setObstacle(new int[height][width]);
		setBack(new int[height][width]);

		setDistBlock(new int[height][width]);

		for(int i = 0; i < height; i ++){
			for(int j = 0; j < width; j ++){
				if(i == 0 || i == height-1 || j == 0 || j == width-1){
					block[i][j] = 1;
				}else{
					block[i][j] = 0;
				}
				obstacle[i][j] = 0;
				back[i][j] = 0;
			}
		}

		distMap();
	}

	/** 全面草ブロックかどうかを判定する */
	public  boolean isAllGrass(){
		for(int i = 0; i < height; i ++){
			for(int j = 0; j < width; j ++)
				if(block[i][j] != 1){
					return false;
			}
		}
		return true;
	}

	/** 草ブロックの種類を判定する */
	public int judgeBlock(int i, int j, int flag){
		int b = 2;
		if(i == 0){
			if(block[i + 1][j] != flag){
				b = 3;
			}
		}else if(i == height - 1){
			if(flag == 2){
				if(block[i - 1][j] == 0){
					b = 1;
				}
			}else if(block[i - 1][j] != flag){
				b = 1;
			}
		}else{
			if(flag == 2){
				if(block[i - 1][j] == 0 && block[i + 1][j] != flag){
					b = 4;
				}else if(block[i - 1][j] == 0){
					b = 1;
				}else if(block[i + 1][j] != flag){
					b = 3;
				}
			}else{
				if(block[i - 1][j] != flag && block[i + 1][j] != flag){
					b = 4;
				}else if(block[i - 1][j] != flag){
					b = 1;
				}else if(block[i + 1][j] != flag){
					b = 3;
				}
			}
		}

		return b;
	}

	/** マップの配置より描画のためのマップチップの区別を行う */
	public void distMap(){
		for(int i = 0; i < height; i ++){
			for(int j = 0; j < width; j ++){
				distBlock[i][j] = judgeBlock(i, j, block[i][j]);
			}
		}
	}

	/** マップの配置より描画のためのマップチップの区別を行う */
	public void distMap(int x1, int y1, int x2, int y2){
		if(x1 > x2){
			int buf = x1;
			x1 = x2;
			x2 = buf;
		}
		if(y1 > y2){
			int buf = y1;
			y1 = y2;
			y2 = buf;
		}

		x1 = Math.max(0, x1 - 1);
		y1 = Math.max(0, y1 - 1);
		x2 = Math.min(width - 1, x2 + 1);
		y2 = Math.min(height - 1, y2 + 1);

		for(int i = y1; i <= y2; i ++){
			for(int j = x1; j <= x2; j ++){
				distBlock[i][j] = judgeBlock(i, j, block[i][j]);
			}
		}
	}

	/** 受け取ったステージに自身のステージ情報をコピーする */
	public void copy(Stage stage){
		try{
			this.name = stage.name;
			for(int i = 0; i < height; i ++){
				for(int j = 0; j < width; j ++){
					this.block[i][j] = stage.block[i][j];
					this.obstacle[i][j] = stage.obstacle[i][j];
					this.back[i][j] = stage.back[i][j];

					this.distBlock[i][j] = stage.distBlock[i][j];
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/** 自身のステージ情報と同じ情報をもつステージを返す */
	public Stage clone(){
		Stage stage = new Stage();

		try{
			stage.name = this.name;
			for(int i = 0; i < height; i ++){
				for(int j = 0; j < width; j ++){
					stage.block[i][j] = this.block[i][j];
					stage.obstacle[i][j] = this.obstacle[i][j];
					stage.back[i][j] = this.back[i][j];

					stage.distBlock[i][j] = this.distBlock[i][j];
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return stage;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int[][] getBlock() {
		return block;
	}

	public void setBlock(int[][] block) {
		this.block = block;
	}

	public int[][] getObstacle() {
		return obstacle;
	}

	public void setObstacle(int[][] obstacle) {
		this.obstacle = obstacle;
	}

	public int[][] getBack() {
		return back;
	}

	public void setBack(int[][] back) {
		this.back = back;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public int[][] getDistBlock() {
		return distBlock;
	}

	public void setDistBlock(int[][] distBlock) {
		this.distBlock = distBlock;
	}
}
