package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataInputOutput {
	/** 操作対象のファイル名 */
	private String fileName;

	/** コンストラクタ */
	public DataInputOutput(String fileName){
		this.fileName = fileName;
	}

	/** コンストラクタ */
	public DataInputOutput(){
		this.fileName = "unknown";
	}

	/** 操作対象のファイル名を指定 */
	public void setFileName(String fileName){
		this.fileName = fileName;
	}

	/** 指定したファイルが存在し, ディレクトリではなく, 書き込み許可がされているかを確認する */
	private boolean writeCheck(File file){
		if(file.exists()){
			if(file.isFile() && file.canWrite()){
				return true;
			}
		}
		return false;
	}

	/** オブジェクトデータをロードする */
	public void loadObject(ArrayList<ImageObject> objectList){
		try{
			InputStream is =this.getClass().getClassLoader().getResourceAsStream(fileName);
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ファイルが空ならば読み込みを中断する
			if(!br.ready()){
				br.close();
				return;
			}

			ImageObject object;
			String name;
			while((name = br.readLine()) != null){
				object  = new ImageObject();

				object.setName(name);
				ArrayList<AnimationParam> animationList = object.getAnimationList();
				AnimationParam animation;

				for(int i = 0; i < 4; i ++){
					animation = new AnimationParam();

					animation.setX(Integer.parseInt(br.readLine()));
					animation.setY(Integer.parseInt(br.readLine()));
					animation.setW(Integer.parseInt(br.readLine()));
					animation.setH(Integer.parseInt(br.readLine()));
					animation.setAngle(Integer.parseInt(br.readLine()));
					animation.setAxisX(Integer.parseInt(br.readLine()));
					animation.setAxisY(Integer.parseInt(br.readLine()));
					animation.setMagnification(Float.parseFloat(br.readLine()));
					animation.setTransparency(Float.parseFloat(br.readLine()));

					animationList.add(animation);
				}

				object.setClickFlag(Integer.parseInt(br.readLine()));
				object.setX(Integer.parseInt(br.readLine()));
				object.setY(Integer.parseInt(br.readLine()));
				object.setW(Integer.parseInt(br.readLine()));
				object.setH(Integer.parseInt(br.readLine()));
				object.setMagnification(Float.parseFloat(br.readLine()));
				object.setImgNo(Integer.parseInt(br.readLine()));

				objectList.add(object);
			}

			br.close();
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
	}

	/** ファイル名を指定してオブジェクトデータをロードする */
	public void loadObject(String fileName, ArrayList<ImageObject> objectList){
		setFileName(fileName);
		loadObject(objectList);
	}

	/** 戦績データをロードする */
	public void loadResult(ResultData resultData){
		try{
			File file = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(file));

			resultData.setRating(Integer.parseInt(br.readLine()));
			resultData.setWinCount(Integer.parseInt(br.readLine()));
			resultData.setBattleCount(Integer.parseInt(br.readLine()));

			br.close();
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
	}

	/** ファイル名を指定して戦績データをロードする */
	public void loadResult(String fileName, ResultData resultData){
		setFileName(fileName);
		loadResult(resultData);
	}

	/** 戦績データをセーブする */
	public void saveResult(ResultData resultData){
		try{
			File file = new File(fileName);

			if(writeCheck(file)){
				// 上書き保存(BufferedReaderにする)
				BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));

				// すべて書き込む
				fileWriter.write(String.valueOf(resultData.getRating()));
				fileWriter.newLine();
				fileWriter.write(String.valueOf(resultData.getWinCount()));
				fileWriter.newLine();
				fileWriter.write(String.valueOf(resultData.getBattleCount()));

				fileWriter.close();
			}else{
				System.out.println("error >> cannot write to file!");
			}
		}catch(IOException e){
			System.out.println(e);
		}
	}

	/** ファイル名を指定して戦績データをセーブする */
	public void saveResult(String fileName, ResultData resultData){
		setFileName(fileName);
		saveResult(resultData);
	}

	/** ステージをロードする */
	public void loadStage(ArrayList<Stage> stageList){
		stageList.clear();

		try{
			File file = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(file));

			// ファイルが空ならば読み込みを中断する
			if(!br.ready()){
				br.close();
				return;
			}

			String name;
			while((name = br.readLine()) != null){
				Stage newStage = new Stage();
				newStage.setName(name);

				int[][] newBlock = new int[Stage.getHeight()][Stage.getWidth()];
				int[][] newObstacle = new int[Stage.getHeight()][Stage.getWidth()];
				int[][] newBack = new int[Stage.getHeight()][Stage.getWidth()];

				for(int i = 0; i < Stage.getWidth(); i ++){
					for(int j = 0; j < Stage.getHeight(); j ++){
						newBlock[j][i] = Integer.parseInt(br.readLine());
					}
				}
				for(int i = 0; i < Stage.getWidth(); i ++){
					for(int j = 0; j < Stage.getHeight(); j ++){
						newObstacle[j][i] = Integer.parseInt(br.readLine());
					}
				}
				for(int i = 0; i < Stage.getWidth(); i ++){
					for(int j = 0; j < Stage.getHeight(); j ++){
						newBack[j][i] = Integer.parseInt(br.readLine());
					}
				}

				newStage.setBlock(newBlock);
				newStage.setObstacle(newObstacle);
				newStage.setBack(newBack);
				newStage.distMap();

				stageList.add(newStage);
			}

			br.close();
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
	}

	/** ファイル名を指定してステージをロードする */
	public void loadStage(String fileName, ArrayList<Stage> stageList){
		setFileName(fileName);
		loadStage(stageList);
	}

	/** ステージをセーブする */
	public void saveStage(ArrayList<Stage> stageList, boolean overWrite){
		try{
			File file = new File(fileName);

			if(writeCheck(file)){
				BufferedWriter fileWriter;

				if(overWrite){	// 上書き保存(BufferedReaderにする)
					fileWriter = new BufferedWriter(new FileWriter(file));
				}else{	// 書き込み保存
					fileWriter = new BufferedWriter(new FileWriter(file, true));
				}

				// すべて書き込む
				for(Stage stage : stageList){
					fileWriter.write(stage.getName());
					fileWriter.newLine();

					for(int i = 0; i < Stage.getWidth(); i ++){
						for(int j = 0; j < Stage.getHeight(); j ++){
							fileWriter.write(String.valueOf(stage.getBlock()[j][i]));
							fileWriter.newLine();
						}
					}
					for(int i = 0; i < Stage.getWidth(); i ++){
						for(int j = 0; j < Stage.getHeight(); j ++){
							fileWriter.write(String.valueOf(stage.getObstacle()[j][i]));
							fileWriter.newLine();
						}
					}
					for(int i = 0; i < Stage.getWidth(); i ++){
						for(int j = 0; j < Stage.getHeight(); j ++){
							fileWriter.write(String.valueOf(stage.getBack()[j][i]));
							fileWriter.newLine();
						}
					}
				}

				fileWriter.close();
			}else{
				System.out.println("error >> cannot write to file!");
			}
		}catch(IOException e){
			System.out.println(e);
		}
	}

	/** ファイル名を指定してステージをセーブする */
	public void saveStage(String fileName, ArrayList<Stage> stageList, boolean overWrite){
		setFileName(fileName);
		saveStage(stageList, overWrite);
	}
}
