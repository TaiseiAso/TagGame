![title](img/title.png)
## 概要
マウス操作の鬼ごっこアクションゲームです。
ステージ上の黄色い玉をより多く取ると勝利。
鬼の間は取れないので、人間をタッチして鬼を交代しよう。
ステージはゲーム内エディタで自作することもできる。

![demo_taggame](https://user-images.githubusercontent.com/38200445/71121307-95295300-2221-11ea-830d-1f77f89bc4c9.gif)

## ゲーム内容
基本的にマウスを左クリックすることで、各種項目を決定することができます。
1. タイトル画面
『バトル』『ステージ作成』『戦績』『設定』を選択できます。
2. バトル
CPUの人数を追加することができます。s画面下部で遊ぶステージを選択してゲームを開始します。
左クリックで何度もジャンプすることができますが、画面左下のスタミナゲージを消費します。
連続ジャンプ数が増えるほど消費スタミナも増えます。
マウスホイールを押し込むと一時停止、右クリックで途中経過を表示できます。
制限時間以内にステージ上の黄色い玉をより多く取ると勝利ですが、鬼の間は取れないので、人間をタッチして鬼を交代しよう。
3. ステージ作成
ステージの『新規作成』『編集』『複製』『削除』ができる。
ステージ作成画面では、画面左部で『ペン』『直線ツール』『範囲塗り』『内部塗り』の配置モードに切り替えることができる。
画面右部で、配置するブロックを変更できる。
ステージ名を変更したり、テストプレイをしたりすることもできる。
左クリックでブロックを配置でき、配置モードによってはドラッグも使用する。
マウスホイール回転によりカメラをズームインアウトできる。
右クリックをドラッグすることでカメラを移動できる。
4. 戦績
優勝回数や勝率(優勝回数/対戦回数)などが見れる
5. 設定
作ったステージを削除したり、戦績を削除できる。

## 構成
```
.
├─data/             ... ボタンなどのオブジェクトの位置やアニメーション定義
├─img/              ... ゲームに用いる画像群
├─src/game/         ... ゲームに用いるプログラム群
├─createStages.txt  ... 作成したステージの保存ファイル
└─saveResult.txt    ... 戦績の保存ファイル
```