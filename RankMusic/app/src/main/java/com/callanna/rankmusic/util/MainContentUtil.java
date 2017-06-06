package com.callanna.rankmusic.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.callanna.rankmusic.R;

import java.util.ArrayList;

/**
 * Created by Callanna on 2017/6/6.
 */

public class MainContentUtil {
    private static MainContentUtil ourInstance;
    private FragmentManager fragmentManager;
    private String currentTag;
    private ArrayList<String> tagList;

    public static MainContentUtil getInstance() {
        if (ourInstance == null) {
            synchronized (MainContentUtil.class) {
                if (ourInstance == null) {
                    ourInstance = new MainContentUtil();
                }
            }
        }
        return ourInstance;
    }

    private MainContentUtil() {
        tagList = new ArrayList<>();
    }

    public void init(AppCompatActivity activity) {
        init(activity.getSupportFragmentManager());
    }

    public void init(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
    /**
     * 替换主内容区域
     */
    public void addMainContent(Fragment fragment,Boolean flag) {
        if (fragmentManager == null) {
            return;
        }
        currentTag = fragment.getClass().getSimpleName();
        tagList.remove(currentTag);
        tagList.add(currentTag);
        Fragment oldFragment = fragmentManager.findFragmentByTag(currentTag);
        if (oldFragment != null) {
            try {
                fragmentManager.popBackStackImmediate(currentTag, 0);
            } catch (Exception e) {
                Log.e("MainContentUtil",e.getMessage());
            }
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.layout_content, fragment, currentTag);
        if (!TextUtils.isEmpty(currentTag) && flag) {
            transaction.addToBackStack(currentTag);
        }
        transaction.commitAllowingStateLoss();
        Log.d("MainContentManager", "addMainContent: fragmentManager.size = " + fragmentManager.getBackStackEntryCount());
    }
    /**
     * 弹出回退栈顶层fragment
     */
    public void popMainContentFragment() {
        if (fragmentManager.getBackStackEntryCount() >= 1) {
            fragmentManager.popBackStack();
            if (tagList.size() > 1)
                tagList.remove(tagList.size() - 1);
        }
    }

    /**
     * 将fragment管理栈回退至指定fragment，如若fragment不存在，将remove最顶层的fragment后，创建此fragment。
     *
     * @param fragment 指定的fragment
     */
    public void popMainContentFragmentToFragment(Fragment fragment) {
        if (fragmentManager == null) {
            return;
        }
        String tag = fragment.getClass().getSimpleName();
        Fragment oldFragment = fragmentManager.findFragmentByTag(tag);
        if (oldFragment != null) {
            fragmentManager.popBackStack(tag, 0);
            return;
        } else {
            fragmentManager.popBackStack();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.layout_content, fragment, tag);
        if (!TextUtils.isEmpty(tag)) {
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();
    }
    /**
     * 清空Fragment栈
     */
    public void popAllFragment() {
        int size = tagList.size();
        while (size == 0) {
            fragmentManager.popBackStackImmediate();
            size--;
        }
        tagList.clear();
    }

    /**
     * 清空Fragment栈
     */
    public void clearAllFragment() {
        fragmentManager.popBackStack(null,1);
        tagList.clear();
    }


    public void destroy() {
        ourInstance = null;
        fragmentManager = null;
    }
}
