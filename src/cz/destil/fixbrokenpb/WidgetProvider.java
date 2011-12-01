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

import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


public class WidgetProvider extends AppWidgetProvider{

    private final static String TAG = "AnyUnlockWidgetProvider";
    private static final Random RANDOM = new Random();

    @Override
    public void onEnabled(Context context){
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context){
        super.onDisabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){


        final int N = appWidgetIds.length;

        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            Intent myIntent = new Intent(context, LockScreenService.class);
            // need a random requestId so that multiple copies of the same app widget will all work
            int randomRequestId = RANDOM.nextInt();
            PendingIntent pendingIntent = PendingIntent.getService(context, randomRequestId, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.off_button, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, updateViews);
        }

    }

}
