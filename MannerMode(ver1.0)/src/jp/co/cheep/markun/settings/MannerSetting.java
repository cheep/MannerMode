package jp.co.cheep.markun.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

public class MannerSetting extends Activity {

	private static final String MANNER_ON = "マナーモードに設定しました";
	private static final String MANNER_OFF = "マナーモードを解除しました";
	private static final String OTHER = "設定できませんでした";

	private static final String INITIAL = "INITIAL";
	private static final String MUSIC_VOL = "MUSIC_VOL";
	private static final String RING_VOL = "RING_VOL";
	private static final String NOTIFY_VOL = "NOTIFY_VOL";
	private static final String SYSTEM_VOL = "SYSTEM_VOL";

	SharedPreferences spf;
	SharedPreferences.Editor spfe;

	boolean b = false;
	int initial = 0;
	int musicVol, ringVol, notifyVol, systemVol = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manner_setting);

        spf = PreferenceManager.getDefaultSharedPreferences(this);

        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

        if(am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE &&
        		spf.getInt(INITIAL, 0)==initial)
        {
        	//System.out.println("初期状態です");
        	am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        	wifiManager.setWifiEnabled(true);
        	Toast.makeText(this, MANNER_OFF, Toast.LENGTH_SHORT).show();
        }
        else if(am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
        {
        	spfe = spf.edit();

        	musicVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            ringVol = am.getStreamVolume(AudioManager.STREAM_RING);
            notifyVol = am.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
            systemVol = am.getStreamVolume(AudioManager.STREAM_SYSTEM);

            //System.out.println("musicVol: " + musicVol);
            //System.out.println("ringVol: " + ringVol);
            //System.out.println("notifyVol: " + notifyVol);
            //System.out.println("systemVol: " + systemVol);

            spfe.putInt(INITIAL, 1);

            spfe.putInt(MUSIC_VOL, musicVol);
            spfe.putInt(RING_VOL, ringVol);
            spfe.putInt(NOTIFY_VOL, notifyVol);
            spfe.putInt(SYSTEM_VOL, systemVol);

            spfe.commit();

            //System.out.println("保存しました");


            am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        	am.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
        	am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
        	am.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);

        	wifiManager.setWifiEnabled(false);

        	am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            Toast.makeText(this, MANNER_ON, Toast.LENGTH_SHORT).show();
        }
        else if(am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE &&
        		spf.getInt(INITIAL, 0)!=initial){

        	am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        	am.setStreamVolume(AudioManager.STREAM_MUSIC, spf.getInt(MUSIC_VOL, 3), 0);
	    	am.setStreamVolume(AudioManager.STREAM_RING, spf.getInt(RING_VOL, 3), 0);
	    	am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, spf.getInt(NOTIFY_VOL, 1), 0);
	    	am.setStreamVolume(AudioManager.STREAM_SYSTEM, spf.getInt(SYSTEM_VOL, 1), 0);

	    	wifiManager.setWifiEnabled(true);

	    	Toast.makeText(this, MANNER_OFF, Toast.LENGTH_SHORT).show();

	    	//System.out.println("戻しました");
        }
        else{
        	Toast.makeText(this, OTHER, Toast.LENGTH_SHORT).show();
        }


//        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//        if(am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
//        {
//        	am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
//        	am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
//        	Toast.makeText(this, MANNER_ON, Toast.LENGTH_SHORT).show();
//        }else if(am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE){
//        	am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//        	am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
//        	Toast.makeText(this, MANNER_OFF, Toast.LENGTH_SHORT).show();
//        }else if(am.getRingerMode() == AudioManager.RINGER_MODE_SILENT){
//        	Toast.makeText(this, OTHER, Toast.LENGTH_SHORT).show();
//        }else{
//        	Toast.makeText(this, OTHER, Toast.LENGTH_SHORT).show();
//        }


        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_manner_setting, menu);
        return true;
    }


}
