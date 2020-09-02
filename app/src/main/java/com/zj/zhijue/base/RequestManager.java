package com.zj.zhijue.base;

import android.content.Context;

public class RequestManager {
    private static RequestManager instance;
    private Context mContext;

    private RequestManager(Context context) {
        this.mContext = context;
    }

    private RequestManager() {

    }

    public static RequestManager getInstance(Context context) {
       instance = SingleTon.single;
       instance.setContext(context);
        return instance;
    }

    private static class SingleTon{
        private static final RequestManager single = new RequestManager();
    }

    private void setContext(Context context) {
        this.mContext = context;
    }


}
