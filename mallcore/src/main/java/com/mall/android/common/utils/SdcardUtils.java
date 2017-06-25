package com.mall.android.common.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class SdcardUtils {
	
	public static boolean hasSDCard() {
		boolean mHasSDcard = false;
		if (Environment.MEDIA_MOUNTED.endsWith(Environment.getExternalStorageState())) {
			mHasSDcard = true;
		} else {
			mHasSDcard = false;
		}

		return mHasSDcard;
	}

	@SuppressLint("SdCardPath")
	public static String getSdcardPath() {

		if (hasSDCard())
			return Environment.getExternalStorageDirectory().getAbsolutePath();

		return "/sdcard/";
	}

	public static File getExternalCacheDir(){

		if (SdcardUtils.hasSdcardAndCanWrite()) {
//            File rootFile = new File(SupportLibraryApp.getInstance().getAppPath());
			// TODO
			File rootFile = new File("/ktr/");
			if (!rootFile.exists())
				rootFile.mkdirs();

			// 数据缓存目录设置
			File jsonFile = new File(getSdcardPath() + "buyer");
			if (!jsonFile.exists())
				jsonFile.mkdirs();

//			// 缓存目录设置
//			File imageFile = new File(rootFile.getAbsolutePath() + File.separator + "");
//			if (!imageFile.exists())
//				imageFile.mkdirs();

			return jsonFile;
		}

		return null;
	}

	private static boolean sdcardCanWrite() {
		return Environment.getExternalStorageDirectory().canWrite();
	}

	public static boolean hasSdcardAndCanWrite() {
		return hasSDCard() && sdcardCanWrite();
	}

	/**
	 * 获取SDCARD的可用大小,单位字节
	 * 
	 * @return
	 */
	public long getSdcardtAvailableStore() {

		if (hasSdcardAndCanWrite()) {
			String path = getSdcardPath();
			if (path != null) {
				StatFs statFs = new StatFs(path);

				@SuppressWarnings("deprecation")
				long blocSize = statFs.getBlockSize();

				@SuppressWarnings("deprecation")
				long availaBlock = statFs.getAvailableBlocks();

				return availaBlock * blocSize;
			}
		}

		return 0;
	}


}
