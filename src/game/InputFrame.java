package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class InputFrame extends JFrame implements ActionListener{
	/** ゲーム全体に共通するパラメータを管理するクラスのオブジェクト */
	private GameParam gameParam;
	/** ステージ作成画面のパラメータを管理するクラスのオブジェクト */
	private StageCreateParam stageCreateParam;

	/** 名前を変更するステージ */
	private Stage stage;

	/** テキストフィールド */
	private JTextField text;
	/** 決定ボタン */
	private JButton btn1;
	/** キャンセルボタン */
	private JButton btn2;

	/** コンストラクタ */
	public InputFrame(String name, StageCreateParam stageCreateParam, GameParam gameParam){
		super(name);

		this.stageCreateParam = stageCreateParam;
		this.gameParam = gameParam;
		this.stage = stageCreateParam.getDrawingStage();

		setLayout(null);

		btn1 = new JButton("decide");
		btn1.addActionListener(this);
		btn1.setBounds(400, 0, 100, 80);
		add(btn1);

		btn2 = new JButton("cancel");
		btn2.addActionListener(this);
		btn2.setBounds(300, 0, 100, 80);
		add(btn2);

		text = new JTextField(stage.getName(), 20);
		text.setBounds(0, 0, 300, 80);
		add(text);

		setAlwaysOnTop(true);
		setBounds(400, 300, 500, 80);
		setUndecorated(true);
		setVisible(true);
		setResizable(false);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if(cmd.equals("decide")){
			String inputName = text.getText();

			if(!stage.getName().equals(inputName)){
				stage.setName(inputName);
				stageCreateParam.setSaved(false);

				stageCreateParam.setEditName(false);
				gameParam.setOperation(true);
				this.dispose();
			}
		}else if(cmd.equals("cancel")){
			stageCreateParam.setEditName(false);
			gameParam.setOperation(true);
			this.dispose();
		}
	}
}
