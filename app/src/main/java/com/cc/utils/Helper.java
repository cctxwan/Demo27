package com.cc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Helper
{
  public static int getLayoutId(Context paramContext, String paramString)
  {
    return paramContext.getResources().getIdentifier(paramString, "layout", paramContext.getPackageName());
  }
  
  public static int getLayoutAnim(Context paramContext, String paramString)
  {
    return paramContext.getResources().getIdentifier(paramString, "anim", paramContext.getPackageName());
  }

  public static int getResClo(Context paramContext, String paramString)
  {
    return paramContext.getResources().getIdentifier(paramString, "color", paramContext.getPackageName());
  }

  public static int getResDraw(Context paramContext, String paramString)
  {
    return paramContext.getResources().getIdentifier(paramString, "drawable", paramContext.getPackageName());
  }

  public static int getResId(Context paramContext, String paramString)
  {
    return paramContext.getResources().getIdentifier(paramString, "id", paramContext.getPackageName());
  }

  public static int getResStr(Context paramContext, String paramString)
  {
    return paramContext.getResources().getIdentifier(paramString, "string", paramContext.getPackageName());
  }

  public static int getResStyle(Context paramContext, String paramString)
  {
    return paramContext.getResources().getIdentifier(paramString, "style", paramContext.getPackageName());
  }

  public static int isTabletDevice(Context paramContext)
  {
    if (((TelephonyManager)paramContext.getSystemService("phone")).getPhoneType() == 0);
    for (int i = 1; ; i = 0)
      return i;
  }

  public static String replaceString(String paramString, int paramInt)
  {
    char[] arrayOfChar = paramString.toCharArray();
    int i = paramString.length();
    for (int j = 0; ; j++)
    {
      if (j >= paramInt)
        return String.valueOf(arrayOfChar);
      arrayOfChar[(-5 + (i - j))] = '*';
    }
  }

  public static void toastShow(String paramString, Context paramContext)
  {
    View localView = LayoutInflater.from(paramContext).inflate(getLayoutId(paramContext, "xdw_toast_item"), null);
    Toast localToast = new Toast(paramContext);
    localToast.setView(localView);
    localToast.setGravity(17, 0, 0);
    ((TextView)localView.findViewById(getResId(paramContext, "xdw_toast_mes"))).setText(paramString);
    localToast.show();
  }
  
  public static boolean isAppOnForeground(Context ctx) {
		ActivityManager activityManager = (ActivityManager) ctx
				.getApplicationContext()//
				.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = ctx.getApplicationContext().getPackageName();

		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null) {
			return false;
		}

		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}
		return false;
	}
  /**
   * 获取某些 配置文件中的 值
   * @param activity
   * @param filename  "/mmiap.xml" 是移动的 mzyw.xml是拇指游玩的配置
   * @param keyName channel 移动渠道号 ZTY_GAME_ID拇指gameId ZTY_PACKET_ID拇指渠道号 
   * @return
   */
	public static String getCofigValue(Activity activity,String filename,String keyName) {
		String ret = "";
		InputStream is = activity.getClass().getResourceAsStream(filename);
		if (is != null) {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = null;

			try {
				builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Document doc = null;

			try {
				doc = builder.parse(is);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			doc.normalize();
			NodeList list = doc.getElementsByTagName(keyName);
			if (list!=null&&list.getLength()>0) {
				ret = list.item(0).getFirstChild()
						.getNodeValue();
			}
		}
		return ret;
	}
}