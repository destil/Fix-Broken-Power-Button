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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;


public class LockScreen extends Activity implements View.OnClickListener {
    private DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT);
    private TelephonyManager tm;
    private TextView timeView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen);
        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        timeView = (TextView)findViewById(R.id.time);

    }
    @Override
    public void onResume(){
        Date nd = Calendar.getInstance().getTime();
        timeView.setText(timeFormatter.format(nd));
        if(tm.getCallState() != 0){
            finish();
        }
        super.onResume();
    }
    @Override
    public void onClick(View v) {}
}
