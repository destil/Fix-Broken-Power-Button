package cz.destil.fixbrokenpb;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class BootReceiver extends BroadcastReceiver
{
	/* Magic number */
	private final static int REQ_CODE = 0x575f72a;

	@Override
    public void onReceive(Context context, Intent intent) {
	    //need to setup alarms after start
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		if (settings.getBoolean("enabled", false)) {
			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmSender = PendingIntent.getService(context, REQ_CODE, new Intent(
		        context, UnlockService.class), 0);
			long firstTime = SystemClock.elapsedRealtime()+1000;
			am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime,
			        600 * 1000, alarmSender);
			KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
			KeyguardManager.KeyguardLock kl = km.newKeyguardLock("AnyUnlock");
			kl.disableKeyguard();
		}
    }
}
