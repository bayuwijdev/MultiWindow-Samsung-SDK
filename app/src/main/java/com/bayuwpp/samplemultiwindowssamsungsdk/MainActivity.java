package com.bayuwpp.samplemultiwindowssamsungsdk;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.multiwindow.SMultiWindow;
import com.samsung.android.sdk.multiwindow.SMultiWindowActivity;

public class MainActivity extends ListActivity {
    private static final int MENU_MULTI_WINDOW = 0;
    private static final int MENU_NORMAL_WINDOW = 1;

    private SMultiWindow multiWindow = null;
    private SMultiWindowActivity multiWindowActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] strings = new String[] {
                "1. Normal Window",
                "2. Multi Window"
        };

        setListAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, strings));
        getListView().setTextFilterEnabled(true);

        multiWindow = new SMultiWindow();

        try {
            //inisialisasi multiwindow untuk aktiviti ini
            multiWindow.initialize(this);
        } catch (SsdkUnsupportedException e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        multiWindowActivity = new SMultiWindowActivity(this);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case MENU_NORMAL_WINDOW :
                multiWindowActivity.normalWindow();
                break;
            case MENU_MULTI_WINDOW :
                {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(new ComponentName("com.sec.android.app.sbrowser",
                            "com.sec.android.app.sbrowser.SBrowserMainActivity"));
                    multiWindowActivity.makeMultiWindowIntent(intent,SMultiWindowActivity.ZONE_A);
                    startActivity(intent);
                }
                break;
        }
    }
}
