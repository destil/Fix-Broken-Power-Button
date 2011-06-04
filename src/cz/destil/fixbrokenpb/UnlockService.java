/*
Copyright (C) 2010 Haowen Ning

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

*/
package cz.destil.fixbrokenpb;

import android.app.KeyguardManager;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;


public class UnlockService extends Service{
    /* Magic number :D */
    private final int NOTIFY_ID = 0x994231;
    private BroadcastReceiver mReceiver;
    private DevicePolicyManager mDPM;
    private ComponentName mAdminReceiver;
    private Handler mHandler;
    
    @Override
    public void onCreate(){
        Log.v("FBPB", "UnlockService onCreate");
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction("android.intent.action.PHONE_STATE");
        mReceiver = new LockScreenReceiver();
        registerReceiver(mReceiver, filter);
        mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        mAdminReceiver = new ComponentName(this, AdminReceiver.class);
        mHandler = new Handler();
    }

    public void onDestroy(){
    	Log.v("FBPB", "UnlockService onDestroy");
        KeyguardManager km = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("AnyUnlock enable");
        kl.reenableKeyguard();
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /* We want this service to continue running until it is explicitly
         stopped, so return sticky.*/
        if(intent != null && intent.getAction() != null){
            if(intent.getAction().equals("anyunlock_lockscreen_intent")){
            	Log.v("FBPB", "Starting unlock screen");
                Intent myIntent = new Intent(this, LockScreen.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
            }
            else if(intent.getAction().equals("anyunlock_power_off_intent")){
                if(mDPM.isAdminActive(mAdminReceiver)){
                    mDPM.lockNow();
                }
            }
        }
        return START_STICKY;
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new Binder() {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply,
                int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };

}
