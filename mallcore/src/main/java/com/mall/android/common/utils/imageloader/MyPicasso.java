package com.mall.android.common.utils.imageloader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import java.io.File;

public class MyPicasso {

    private static MyPicasso instance;

    private static Picasso picasso;

    //记载一些Picasso参数的对象
    private static PicassoBean bean;

    public MyPicasso(Context context) {
        picasso = Picasso.with(context);
        bean = new PicassoBean();
    }

    /***
     * 在Application中初始化，在之后的调用中可以直接使用Util进行使用
     *
     * @param context 上下文
     *                <p>根据Picasso的源码{@link Picasso.Builder#Builder(android.content.Context)}可知，
     *                Picasso初始化即为with(Context context)这里的Context就是去获取相关的Application
     *                级的Context，同时，初始化为单例模式，及时以后再去调用with(Context context)，
     *                也不会再去{@link Picasso.Builder#build()}</p>
     */
    public static void init(Context context) {
        if (instance == null) {
            synchronized (MyPicasso.class) {
                if (instance == null) {
                    instance = new MyPicasso(context);
                }
            }
        }
    }

    /**
     * 获得定义的PicassoBean,便于设置一些参数
     **/
    public static PicassoBean getBean() {
        if (bean != null) {
            return bean;
        } else {
            throw new NullPointerException("PicassonBean is null");
        }
    }

    /**
     * 加载默认数据，对图像不做处理
     *
     * @param object
     * @param iv
     */
    public static void inject(Object object, ImageView iv) {
        inject(object, iv, null);
    }

    /**
     * 加载圆角图像，
     *
     * @param object
     * @param iv
     * @param rangle 圆角角度，单位px
     */
    public static void inject(Object object, ImageView iv, int rangle) {
        inject(object, iv, new RangleTransForm(rangle));
    }

    /**
     *  加载圆形图片
     * @param object
     * @param iv
     * @param isCircle 是否为圆形图片
     */
    public static void inject(Object object, ImageView iv, boolean isCircle) {
        if (isCircle) {
            inject(object, iv, new CircleTransForm());
        } else {
            inject(object,iv);
        }
    }

    /**
     * 加载数据
     *
     * @param object 需传入相关的数据，根据类型来执行不同操作
     * @param iv     需要注入的相关的ImageView
     */
    private static void inject(Object object, ImageView iv, Transformation transformation) {
        if (object instanceof Integer) {
            initArg(picasso.load((Integer) object), transformation).into(iv);
        } else if (object instanceof String) {
            initArg(picasso.load((String) object), transformation).into(iv);
        } else if (object instanceof Uri) {
            initArg(picasso.load((Uri) object), transformation).into(iv);
        } else if (object instanceof File) {
            initArg(picasso.load((File) object), transformation).into(iv);
        } else {
            throw new IllegalArgumentException("Please input correct object arg ");
        }
    }

    /**
     * 加载一些配置
     *
     * @param creator
     * @param transformation 方便自定义一些有特点的图像
     * @return
     */
    private static RequestCreator initArg(RequestCreator creator, Transformation transformation) {
        if (bean.placeholderResId != 0) {
            creator.placeholder(bean.placeholderResId);
        }
        if (bean.errorResId != 0) {
            creator.error(bean.errorResId);
        }
        if (transformation != null) {
            creator.transform(transformation);
        } else {
            if (bean.transformation != null) {
                creator.transform(bean.transformation);
            }
        }
        if (bean.policy != null) {
            creator.memoryPolicy(bean.policy);
        }
        if (bean.networkPolicy != null) {
            creator.networkPolicy(bean.networkPolicy);
        }
        return creator;
    }

    /**
     * 清除缓存
     *
     * @param object 需传入相关的数据，根据类型来执行不同操作
     */
    public static void invalidate(Object object) {
        if (object instanceof String) {
            picasso.invalidate((String) object);
        } else if (object instanceof Uri) {
            picasso.invalidate((Uri) object);
        } else if (object instanceof File) {
            picasso.invalidate((File) object);
        } else {
            throw new IllegalArgumentException("Please input correct arg");
        }
    }

    /**
     * 设置是否指示标志
     *
     * @param indicatorsEnabled
     */
    public static void setIndicatorsEnabled(boolean indicatorsEnabled) {
        picasso.setIndicatorsEnabled(indicatorsEnabled);
    }

    /**
     * 设置是否需要打印日志
     *
     * @param loggingEnabled
     */
    public static void setLoggingEnabled(boolean loggingEnabled) {
        picasso.setLoggingEnabled(loggingEnabled);
    }

    /**
     * 设置cancel的标志
     *
     * @param tag
     */
    public static void cancelTag(Object tag) {
        picasso.cancelTag(tag);
    }

    /**
     * 设置pause的标志
     *
     * @param tag
     */
    public static void pauseTag(Object tag) {
        picasso.pauseTag(tag);
    }

    /**
     * 设置Resume的标志
     *
     * @param tag
     */
    public static void resumeTag(Object tag) {
        picasso.resumeTag(tag);
    }

    /**
     * 停止使用当前Picasso
     * {@link Picasso#shutdown}
     */
    public static void shutdown() {
        picasso.shutdown();
    }

    /**
     * @Description: 方便MyPicasso设置一些参数的Bean
     * <p>主要设置加载中图片，失败图片，自定义修改图片样式</p>
     */
    public class PicassoBean {

        /**
         * 加载时的图像的ID
         **/
        private int placeholderResId = 0;

        /**
         * 加载失败的图像的ID
         **/
        private int errorResId = 0;

        /**
         * 针对一些自定义的图像改变
         **/
        private Transformation transformation = null;

        /**
         * 缓存方式，系统默认有缓存，如果不设置为系统默认
         **/
        private MemoryPolicy policy = null;

        /**
         * 如果在内存缓存中找不到相应的Bitmap,则使用此NetworkPolicy，系统默认，如不设置为默认
         **/
        private NetworkPolicy networkPolicy = null;

        /************************  链式结构，可以更好的设置参数*******************************/

        /**
         * 设置加载中的图片
         **/
        public PicassoBean placeholder(int placeholderResId) {
            this.placeholderResId = placeholderResId;
            return this;
        }

        /**
         * 设置失败时的图片
         **/
        public PicassoBean error(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        /**
         * 设置图片转换的transformation
         *
         * @param transformation {@link Transformation}
         * @return
         */
        public PicassoBean transform(Transformation transformation) {
            this.transformation = transformation;
            return this;
        }

        /**
         * 设置缓存样式
         *
         * @param policy
         * @return
         */
        public PicassoBean memoryPolicy(MemoryPolicy policy) {
            this.policy = policy;
            return this;
        }

        /**
         * 设置在内存缓存中读取不到Bitmap对象，则继续实现的方式
         *
         * @param policy
         * @return
         */
        public PicassoBean networkPolicy(NetworkPolicy policy) {
            this.networkPolicy = policy;
            return this;
        }

    }


}