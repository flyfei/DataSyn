package cn.tovi.datasyn;

import android.accounts.Account;
import android.content.ContentResolver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void refresh(View v) {
        Bundle extras = new Bundle();
        extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);

//        Account account = (getApplication())
//                .getCurrentAccount();
//
//        if (ContentResolver.isSyncPending(account,
//                TodoContentProvider.AUTHORITY)) {
//            ContentResolver
//                    .cancelSync(account, TodoContentProvider.AUTHORITY);
//        }
//
//        ContentResolver.requestSync(account, TodoContentProvider.AUTHORITY,
//                extras);
    }

}
