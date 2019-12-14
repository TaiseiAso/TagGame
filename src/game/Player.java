package game;

import java.awt.Color;

public class Player {
	public static final float width = 0.75f;
	public static final float height = 0.9f;

	private final float g = 0.01f;
	private final float maxSpeedX = 0.12f;
	private final float maxSpeedY = 0.30f;
	private final float maxSpeedXinWater = 0.06f;
	private final float maxSpeedYinWater = 0.15f;
	private final float maxSpeedXOgre = 0.15f;
	private final float maxSpeedYOgre = 0.38f;

	/** ジャンプ間隔 */
	private int jumpInterval;

	/** ランダムに決定したアクション */
	private int randomAction;
	/** ランダムアクションのひとつのアクションの持続時間 */
	private int randomActionInterval;

	/** プレイヤーの名前 */
	private String name;

	/** プレイヤーの色 */
	private Color color;
	/** 人間か鬼か */
	private boolean human;
	/** 自分かCPUか */
	private boolean user;

	/** ジャンプの段階 */
	private int jump;

	/** 着地しているかどうか */
	private boolean landing;
	/** 水中にいるかどうか */
	private boolean inWater;

	/** 位置 */
	private float x;
	private float y;
	/** 速度 */
	private float vx;
	private float vy;

	/** 得点 */
	private int point;
	/** 最大スタミナ */
	private int maxStamina;
	/** 残りスタミナ */
	private int stamina;

	/** 順位 */
	private int rank;
	/** 残り硬直時間 */
	private int stopTime;

	/** コンストラクタ */
	public Player(String name, Color color, boolean user){
		this.jumpInterval = 0;
		this.randomAction =  -1;
		this.randomActionInterval = 0;
		this.name = name;
		this.color = color;
		this.human = true;
		this.user = user;
		this.jump = 0;
		this.landing = true;
		this.inWater = false;
		this.x = 0;
		this.y = 0;
		this.vx = 0;
		this.vy = 0;
		this.point = 0;
		this.maxStamina = 1000;
		this.stamina = 1000;
		this.rank = 1;
		this.stopTime = 0;
	}

	public void clear(){
		this.jumpInterval = 0;
		this.randomAction =  -1;
		this.randomActionInterval = 0;
		this.jump = 0;
		this.landing = true;
		this.inWater = false;
		this.vx = 0;
		this.vy = 0;
		this.point = 0;
		this.maxStamina = 1000;
		this.stamina = 1000;
		this.stopTime = 0;
	}

	public void addStamina(int add){
		this.stamina += add;
		if(stamina > maxStamina){
			stamina = maxStamina;
		}
	}

	public void minusStamina(int minus){
		this.stamina -= minus;
		if(stamina < 0){
			stamina = 0;
		}
	}

	public int getJump() {
		return jump;
	}

	public void setJump(int jump) {
		this.jump = jump;
	}

	public void addJump(){
		this.jump ++;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void addX(){
		this.x += this.vx;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void addY(){
		this.y += this.vy;
	}

	public float getVx() {
		return vx;
	}

	public float getMax(boolean x, boolean inWater){
		if(!human){
			if(x){
				return maxSpeedXOgre;
			}else{
				return maxSpeedYOgre;
			}
		}else if(inWater){
			if(x){
				return maxSpeedXinWater;
			}else{
				return maxSpeedYinWater;
			}
		}else{
			if(x){
				return maxSpeedX;
			}else{
				return maxSpeedY;
			}
		}
	}

	public void setVx(float vx) {
		float max = getMax(true, inWater);

		if(vx < -max){
			vx = -max;
		}else if(vx > max){
			vx = max;
		}
		this.vx = vx;
	}

	public void addVx(float dvx){
		this.vx += dvx;

		float max = getMax(true, inWater);

		if(this.vx > max){
			this.vx = max;
		}else if(this.vx < -max){
			this.vx = -max;
		}
	}

	public float getVy() {
		return vy;
	}

	public void setVy(float vy) {
		float max = getMax(false, inWater);

		if(vy < -maxSpeedY){
			vy = -maxSpeedY;
		}else if(vy > max){
			vy = max;
		}
		this.vy = vy;
	}

	public void addGVy(){
		if(inWater){
			this.vy += this.g*0.7f;
		}else{
			this.vy += this.g;
		}

		float max = getMax(false, inWater);

		if(this.vy > max){
			this.vy = max;
		}else if(this.vy < -maxSpeedY){
			this.vy = -maxSpeedY;
		}
	}

	public Color getColor() {
		return color;
	}

	public boolean isHuman() {
		return human;
	}

	public void setHuman(boolean human) {
		this.human = human;
	}

	public boolean isUser() {
		return this.user;
	}

	public void setUser(boolean user) {
		this.user = user;
	}

	public boolean isLanding() {
		return landing;
	}

	public void setLanding(boolean landing) {
		this.landing = landing;
	}

	public String getName() {
		return name;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public void addPoint(){
		this.point ++;
	}

	public void decreasePoint(){
		this.point = 3*this.point/4;
	}

	public int getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public boolean isInWater() {
		return inWater;
	}

	public void setInWater(boolean inWater) {
		this.inWater = inWater;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getStopTime() {
		return stopTime;
	}

	public void setStopTime(int stopTime) {
		this.stopTime = stopTime;
	}

	public void minusStopTime(){
		this.stopTime --;
	}

	public int getJumpInterval() {
		return jumpInterval;
	}

	public void setJumpInterval(int jumpInterval) {
		this.jumpInterval = jumpInterval;
	}

	public void minusJumpInterval(){
		if(this.jumpInterval > 0){
			this.jumpInterval --;
		}
	}

	public int getRandomAction() {
		return randomAction;
	}

	public void setRandomAction(int randomAction) {
		this.randomAction = randomAction;
	}

	public int getRandomActionInterval() {
		return randomActionInterval;
	}

	public void setRandomActionInterval(int randomActionInterval) {
		this.randomActionInterval = randomActionInterval;
	}

	public void minusRandomActionInterval(){
		this.randomActionInterval --;
	}

	public void heelStamina(){
		this.stamina = this.maxStamina;
	}
}
