package com.cc.utils;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * 专门管理activity的操作
 */
public class ActivityMan {

    //静态参数（初始化）
    public static ActivityMan mInstance;

    //栈
    private static Stack<Activity> activityStack;

    //实例化
    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    /**
     * 实例化当前对象
     * @return
     */
    public static ActivityMan getmInstance(){
        //提升效率。防止重复创建
        if(mInstance == null){
            //同步锁   防止多线程进入
            synchronized (ActivityMan.class){
                //实例化对象
                if(mInstance == null)
                    mInstance = new ActivityMan();
            }
        }
        return mInstance;
    }


    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        A.C_Log(activity, "stack个数为：" + activityStack.size());
        activityStack.add(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            A.C_Log(activity, "stack个数为：" + activityStack.size());
            activity.finish();
            activity = null;
        }
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public Activity getTopActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void finishTopActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        Iterator iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (activity.getClass().equals(cls)) {
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    @SuppressWarnings("WeakerAccess")
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());

        } catch (Exception e) {
        }
    }

    /**
     * 得到指定类名的Activity
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

}
