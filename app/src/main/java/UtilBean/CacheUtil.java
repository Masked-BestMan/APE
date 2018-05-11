package UtilBean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.util.LruCache;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import businessmonitor.com.example.newbusinessmonitor.AbstractDataBean;

/**
 * Created by Administrator on 2018/4/22.
 */

public class CacheUtil {

    @SuppressLint("StaticFieldLeak")
    private static CacheUtil cacheUtil = null;
    /**
     * 数据缓存技术的核心类，用于缓存所有下载好的数据，在程序内存达到设定值时会将最少最近使用的数据移除掉。
     *
     * 1.app无网状态下，会先从内存中加载，再从磁盘加载
     * 2.app有网状态下，只从内存中加载
     */
    private LruCache<String, AbstractDataBean> mMemoryCache;
    private DiskLruCache mDiskCache;
    private Context context;

    private CacheUtil(Context context) {
        this.context=context;
        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        // 设置图片缓存大小为程序最大可用内存的1/8
        mMemoryCache = new LruCache<String, AbstractDataBean>(cacheSize) {
            @Override
            protected int sizeOf(String key, AbstractDataBean values) {
                return values.getObjectSize();
            }
        };

        try {
            //磁盘缓存大小为10M
            mDiskCache = DiskLruCache.open(getDiskCacheDir(context.getApplicationContext()),
                    getAppVersion(context.getApplicationContext()), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CacheUtil getCacheUtilInstance(Context context) {
        if (cacheUtil == null)
            cacheUtil = new CacheUtil(context.getApplicationContext());
        return cacheUtil;
    }

    //把数据添加进缓存(同时内存+磁盘)
    public void addStringToCache(String key, AbstractDataBean values) {
        mMemoryCache.put(key, values);

        try {
            DiskLruCache.Editor editor = mDiskCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                if (chartDataToStream(values, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
            mDiskCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //将数据写入流
    private boolean chartDataToStream(AbstractDataBean dataBean, OutputStream outputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(dataBean);
            outputStream.write(byteArrayOutputStream.toByteArray());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //从流中读数据
    private AbstractDataBean chartDataFromStream(InputStream inputStream) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (AbstractDataBean) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //先从内存中获取数据，没有再检查是否联网来决定是否从磁盘中获取
    public AbstractDataBean getStringFromCache(final String key) {
        AbstractDataBean value = mMemoryCache.get(key);
        if (value != null) {
            return value;
        }
        if(isNetworkAvailable(context.getApplicationContext())){
            return null;          //联网状态
        }
        try {
            DiskLruCache.Snapshot snapshot = mDiskCache.get(key);
            if (snapshot!=null)
                return chartDataFromStream(snapshot.getInputStream(0));
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    当SD卡存在或者SD卡不可被移除的时候，就调用getExternalCacheDir()方法来获取缓存路径，
    否则就调用getCacheDir()方法来获取缓存路径。前者获取到的就是 /sdcard/Android/data/<application package>/cache
     这个路径，而后者获取到的是 /data/data/<application package>/cache 这个路径。
     */
    private File getDiskCacheDir(Context context) {
        String cachePath;

        //判断SD卡是否已经挂载，并且挂载点可读/写或SD卡是否可移除
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        File cacheDir = new File(cachePath + File.separator + "chart");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }

    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        return networkinfo != null && networkinfo.isAvailable();
    }
}


