package game;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ImageBox extends JPanel{
	/** ゲームに用いるすべての画像を管理するリスト */
	private ArrayList<Image> imgList;

	/** コンストラクタ */
	public ImageBox(){
		this.imgList = new ArrayList<Image>();

		// すべての画像を読み込む
		ArrayList<String> fileNameList = new ArrayList<String>();
		fileNameList.add("title.png");					//0
		fileNameList.add("start.png");					//1
		fileNameList.add("battle.png");				//2
		fileNameList.add("stagemake.png");		//3
		fileNameList.add("result.png");				//4
		fileNameList.add("config.png");				//5
		fileNameList.add("back.png");					//6
		fileNameList.add("result2.png");				//7
		fileNameList.add("config2.png");				//8
		fileNameList.add("newstage.png");			//9
		fileNameList.add("editstage.png");			//10
		fileNameList.add("copystage.png");			//11
		fileNameList.add("deletestage.png");		//12
		fileNameList.add("stageselectbar.png");	//13
		fileNameList.add("stageview.png");			//14
		fileNameList.add("stagemake2.png");		//15
		fileNameList.add("noimage.png");			//16
		fileNameList.add("nameInput.png");		//17
		fileNameList.add("block.png");				//18
		fileNameList.add("water.png");				//19
		fileNameList.add("obstacle.png");			//20
		fileNameList.add("grass.png");				//21
		fileNameList.add("eraser.png");				//22
		fileNameList.add("drawStagePoint.png");//23
		fileNameList.add("drawStageLine.png");	//24
		fileNameList.add("fillStageRect.png");		//25
		fileNameList.add("fillStageArea.png");		//26
		fileNameList.add("stageExit.png");			//27
		fileNameList.add("saveStage.png");			//28
		fileNameList.add("stageBack.png");			//29
		fileNameList.add("stagePre.png");			//30
		fileNameList.add("nameOutput.png");		//31
		fileNameList.add("stageBlockBar.png");	//32
		fileNameList.add("drawWayBar.png");		//33
		fileNameList.add("underBlack.png");		//34
		fileNameList.add("testPlay.png");			//35
		fileNameList.add("back2.png");				//36
		fileNameList.add("testPlay2.png");			//37
		fileNameList.add("battleGo.png");			//38
		fileNameList.add("cpuDelete.png");			//39
		fileNameList.add("cpuAdd.png");				//40
		fileNameList.add("battleYou.png");			//41
		fileNameList.add("battleCpu.png");			//42
		fileNameList.add("battle2.png");				//43
		fileNameList.add("countDown3.png");		//44
		fileNameList.add("countDown2.png");		//45
		fileNameList.add("countDown1.png");		//46
		fileNameList.add("gameEnd.png");			//47
		fileNameList.add("stageDelete.png");		//48
		fileNameList.add("resultDelete.png");		//49
		fileNameList.add("rating.png");				//50
		fileNameList.add("winCount.png");			//51
		fileNameList.add("battleCount.png");		//52
		fileNameList.add("winLevel.png");			//53
		fileNameList.add("number0.png");			//54
		fileNameList.add("number1.png");			//55
		fileNameList.add("number2.png");			//56
		fileNameList.add("number3.png");			//57
		fileNameList.add("number4.png");			//58
		fileNameList.add("number5.png");			//59
		fileNameList.add("number6.png");			//60
		fileNameList.add("number7.png");			//61
		fileNameList.add("number8.png");			//62
		fileNameList.add("number9.png");			//63
		fileNameList.add("percent.png");				//64
		fileNameList.add("second.png");				//65
		fileNameList.add("point.png");				//66

		for(String fileName : fileNameList){
			//imgList.add(getToolkit().getImage("img/" + fileName));

			URL url = this.getClass().getClassLoader().getResource(fileName);
			try {
				Image im = this.createImage((ImageProducer)url.getContent());
				imgList.add(im);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** 透明度を適切な値に補正する */
	private float correctTransparency(float transparency){
		if(transparency > 1.0f){
			transparency = 1.0f;
		}else if(transparency < 0.0f){
			transparency = 0.0f;
		}
		return transparency;
	}

	/** 拡大率を適切な値に補正する */
	private float correctMagnificationRatio(float magnificationRatio){
		if(magnificationRatio < 0.0f){
			magnificationRatio = 0.0f;
		}
		return magnificationRatio;
	}

	/** 画像を座標、サイズ、軸、回転角度、拡大率、透過率を指定して描画 */
	public void drawImage(Graphics g, int imgNo, int x, int y, int w, int h, int x0, int y0, int angle, float magnificationRatio, float transparency){
		Graphics2D g2 = (Graphics2D)g;
		magnificationRatio = correctMagnificationRatio(magnificationRatio);
		transparency = correctTransparency(transparency);

		// 回転をかける
		AffineTransform af = new AffineTransform();
	    af.rotate(angle*Math.PI/180, x0, y0);
	    g2.setTransform(af);
	    // 透明度をかける
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));

		// 画像を拡大率をかけて描画
		g2.drawImage(imgList.get(imgNo), x0 + (int)(magnificationRatio*(x - x0)), y0 + (int)(magnificationRatio*(y - y0)), (int)(w*magnificationRatio), (int)(h*magnificationRatio), null);

		// 回転を戻す
	    g2.setTransform(new AffineTransform());
		// 透明度を戻す
	 	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}

	/** 画像を取り出す */
	public Image getImg(int index){
		if(index < 0 || imgList.size() <= index){
			return null;
		}
		return  imgList.get(index);
	}
}
