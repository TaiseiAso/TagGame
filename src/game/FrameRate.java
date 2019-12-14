package game;

public class FrameRate {
	/** 誤差 */
	private long error;
	/** 1フレームあたりの経過時間 */
	private long idealSleep;
	/** 最新の時間 */
	private long oldTime;
	/** 新しく測定された時間 */
	private long newTime;

	/**
	 * コンストラクタ
	 * @param fps フレームレート(fps)
	 */
	public FrameRate(int fps){
		this.error = 0;
		this.idealSleep = (1000 << 16) / fps;
		this.newTime = System.currentTimeMillis() << 16;
		this.oldTime = this.newTime;
	}

	/** フレームレート調整 */
	public void waitTime(){
		this.newTime = System.currentTimeMillis() << 16;
	    long sleepTime = this.idealSleep - (this.newTime - this.oldTime) - this.error;
	    if (sleepTime < 0x20000) sleepTime = 0x20000;
	    this.oldTime = this.newTime;
	    try {
			Thread.sleep(sleepTime >> 16);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    this.newTime = System.currentTimeMillis() << 16;
	    this.error = this.newTime - this.oldTime - sleepTime;
	    this.oldTime = this.newTime;
	}
}
