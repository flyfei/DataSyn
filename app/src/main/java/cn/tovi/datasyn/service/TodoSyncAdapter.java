/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package cn.tovi.datasyn.service;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.manning.androidhacks.hack023.authenticator.AuthenticatorActivity;
import com.manning.androidhacks.hack023.exception.AndroidHacksException;
import com.manning.androidhacks.hack023.model.Todo;
import com.manning.androidhacks.hack023.provider.StatusFlag;

import java.io.IOException;
import java.util.List;

public class TodoSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = TodoSyncAdapter.class
            .getCanonicalName();
    //    private final ContentResolver mContentResolver;
    private AccountManager mAccountManager;
//    private final static TodoDAO mTodoDAO = TodoDAO.getInstance();

    public TodoSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
//        mContentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras,
                              String authority, ContentProviderClient provider,
                              SyncResult syncResult) {

        String authtoken = null;
        try {
            authtoken = mAccountManager.blockingGetAuthToken(account,
                    AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE, true);

            /**
             * 执行同步
             */
            //上传本地删除
            //从服务器同步数据到本地
            //上传本地修改

            /**
             * 具体操作
             */
            //获取服务器全部数据data
//      syncRemoteDeleted(data);
//      syncFromServerToLocalStorage(data);
//      syncDirtyToServer(mTodoDAO.getDirtyList(mContentResolver));

        } catch (Exception e) {
            handleException(authtoken, e, syncResult);
        }

    }

    /**
     * 将本地修改过的数据同步到服务器
     *
     * @param dirtyList
     * @throws IOException
     * @throws AndroidHacksException
     */
    protected void syncDirtyToServer(List<Todo> dirtyList)
            throws IOException,
            AndroidHacksException {
        // Push new Info to Server
        // Modify ? Sorry,no Supported
        // Push Del Info to Server
        for (Todo todo : dirtyList) {
            Log.d(TAG, "Dirty list: " + todo);

            switch (todo.getStatus()) {
                case StatusFlag.ADD://如果本地添加数据，通知服务器添加数据
//                    pushNewTodo(todo);
                    break;
                case StatusFlag.MOD://如果本地修改数据
                    throw new AndroidHacksException(
                            "Todo title modification is not supported");
                case StatusFlag.DELETE://如果本地删除数据，通知服务器删除
//                    pushDeleteTodo(todo);
                    break;
                default:
                    throw new RuntimeException("Invalid status: "
                            + todo.getStatus());
            }
        }
    }

    /**
     * 同步服务器的删除到本地<br>
     * 处理本地没有被处理过的（添加、删除、修改之外的）数据
     *
     * @param remoteData
     */
    protected void syncRemoteDeleted(List<Todo> remoteData) {
        Log.d(TAG, "Syncing remote deleted lists...");

        //获取没有被处理过的（添加、删除、修改之外的）数据
//        List<Todo> localClean = mTodoDAO.getCleanTodos(mContentResolver);
        //如果服务器没有这个数据，本地也删除
//        for (Todo cleanTodo : localClean) {
//
//            if (!remoteData.contains(cleanTodo)) {
//                Log.d(TAG, "Todo with id " + cleanTodo.getId()
//                        + " has been deleted remotely.");
//                mTodoDAO.forcedDeleteTodo(mContentResolver, cleanTodo.getId());
//            }
//        }
    }

    /**
     * 同步服务器数据到本地
     *
     * @param data
     */
    protected void syncFromServerToLocalStorage(List<Todo> data) {
        for (Todo todoFromServer : data) {
//            Todo todoInDb = mTodoDAO.isTodoInDb(mContentResolver,
//                    todoFromServer.getId());

            //如果服务器的数据，本地不存在，则添加
//            if (todoInDb == null) {
//                Log.d(TAG, "Adding new todo from server: " + todoFromServer);
//                mTodoDAO.addNewTodo(mContentResolver, todoFromServer,
//                        StatusFlag.CLEAN);
//
//            } else
            // 如果服务器的数据存在，本地的不存在，则通知服务器删除数据
//                if (todoInDb.getStatus() == StatusFlag.CLEAN) {
//                    Log.d(TAG, "Modifying list from server: " + todoInDb);
//                    mTodoDAO.modifyTodoFromServer(mContentResolver, todoFromServer);
//                }

        }
    }

    private void handleException(String authtoken, Exception e,
                                 SyncResult syncResult) {
        if (e instanceof AuthenticatorException) {
            syncResult.stats.numParseExceptions++;
            Log.e(TAG, "AuthenticatorException", e);
        } else if (e instanceof OperationCanceledException) {
            Log.e(TAG, "OperationCanceledExcepion", e);
        } else if (e instanceof IOException) {
            Log.e(TAG, "IOException", e);
            syncResult.stats.numIoExceptions++;
        } else
//        if (e instanceof AuthenticationException) {
//            mAccountManager.invalidateAuthToken(
//                    AuthenticatorActivity.PARAM_ACCOUNT_TYPE, authtoken);
//            // The numAuthExceptions require user intervention and are
//            // considered hard errors.
//            // We automatically get a new hash, so let's make SyncManager retry
//            // automatically.
//            syncResult.stats.numIoExceptions++;
//            Log.e(TAG, "AuthenticationException", e);
//        } else
//        if (e instanceof ParseException) {
//            syncResult.stats.numParseExceptions++;
//            Log.e(TAG, "ParseException", e);
//        } else
//        if (e instanceof JsonParseException) {
//            syncResult.stats.numParseExceptions++;
//            Log.e(TAG, "JSONException", e);
//        } else
            if (e instanceof AndroidHacksException) {
                Log.e(TAG, "AndroidHacksException", e);
            }
    }

}
