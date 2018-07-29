# AndroidDemo06-DialogDemo

![image](https://github.com/Jimison-TW/AndroidDemo06-DialogDemo/blob/master/Snap19.jpg?raw=true)

## 開發版本
Andorid 3.1.2 </br>
SdkVersion 27 </br>
minSdkVersion 15 </br>
targetSdkVersion 27 </br>

## 學習重點
1. 如何在App中播放影音資源
2. 學習Android的lifecycle

## 如何播放Media資源
1. 一般所有需要串連硬體資源的功能，官方建議寫在onResume()中調用，來避免程式錯誤
2. 透過`android.media.MediaPlayer`來實作播放功能
```java=
@Override
protected void onResume() {
    super.onResume();
    mediaPlayer = new MediaPlayer();
    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song);
    try {
        mediaPlayer.setDataSource(this, uri);
    } catch (Exception e) {
        Toast.makeText(getApplicationContext(), "播放檔錯誤", Toast.LENGTH_SHORT).show();
    }
    mediaPlayer.setOnPreparedListener(this);
    mediaPlayer.setOnErrorListener(this);
    mediaPlayer.setOnCompletionListener(this);
}
```
3. 我們透過實作MediaPlayer的OnPreparedListener、OnCompletionListener、OnErrorListener的介面
來控制播放的各種狀態
```java=
@Override
public void onPrepared(MediaPlayer mp) {
    mediaPlayer.seekTo(0);
    mediaPlayer.start();
}

@Override
public void onCompletion(MediaPlayer mp) {
    mediaPause.setImageResource(android.R.drawable.ic_media_play);  //android.R是android的預設資源路徑，這邊的資料是你看不到的，為系統保留的資源
}

@Override
public boolean onError(MediaPlayer mp, int what, int extra) {
    mediaPlayer.release();
    mediaPlayer = null;
    Toast.makeText(getApplicationContext(), "播放錯誤", Toast.LENGTH_SHORT).show();
    return true;
}
```

4. 在App跳轉或切換畫面時一定要記得釋放資源，才不會造成崩潰
```java=
@Override
protected void onStop() {
    super.onStop();
    mediaPlayer.release();
    mediaPlayer = null;
}
```