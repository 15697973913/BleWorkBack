package com.android.common.baselibrary.util;

import android.app.Activity;
import android.os.Process;


import java.util.List;
import java.util.Stack;

public final class ActivityStackUtil {
    private static Stack<Activity> activityStack;
    private static ActivityStackUtil instance = null;

    private ActivityStackUtil() {
    }

    public static ActivityStackUtil getInstance() {
        if (null == instance) {
            instance = SingleTon.single;
        }
        return instance;
    }

    private static class SingleTon {
        private static final ActivityStackUtil single = new ActivityStackUtil();
    }

    public int getCount() {
        return activityStack.size();
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack();
        }
        activityStack.add(activity);
    }

    public Activity topActivity() {
        if (activityStack == null) {
            throw new NullPointerException("Activity stack is Null,your Activity must extend XActivity");
        } else if (activityStack.isEmpty()) {
            return null;
        } else {
            return activityStack.lastElement();
        }
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public synchronized void finishActivity(List<Class<?>> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);

            boolean contain = false;
            for (Class clsp : cls) {
                if (activity.getClass().equals(clsp)) {
                    contain = true;
                    break;
                }
            }

            if (contain) {
                if (activity != null) {
                    activityStack.remove(activity);
                    i--;
                    activity.finish();
                }
            }
        }
    }

    /**
     * 保留参数中的  Activity
     *
     * @param cls
     */
    public synchronized void finishOthersActivity(List<Class<?>> cls) {
        if (activityStack == null) return;

        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);

            boolean contain = false;
            for (Class clsp : cls) {
                if (activity.getClass().equals(clsp)) {
                    contain = true;
                    break;
                }
            }

            if (!contain) {
                if (activity != null) {
                    activityStack.remove(activity);
                    i--;
                    activity.finish();
                }
            }
        }
    }

    public synchronized void finishAllActivity() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (activityStack.get(i) != null) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void appExit() {
        try {
            this.finishAllActivity();
            System.exit(0);
            Process.killProcess(Process.myPid());
        } catch (Exception E) {
            System.exit(-1);
        }

    }
}
