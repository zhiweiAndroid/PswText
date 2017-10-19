package rokudol.com.pswedittext;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import rokudol.com.pswtext.PswText;

public class MainActivity extends AppCompatActivity {

	private PswText mTvPc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTvPc = (PswText) findViewById(R.id.codeinput_diy);
		mTvPc.setInputCallBack(new PswText.InputCallBack() {
			@Override
			public void onInputFinish(String password) {
				Log.d("onInputFinish",password);

			}

			@Override
			public void onUnInputFinish(String password) {
				Log.d("onInputFinish>>>>>>>>>>",password);

			}
		});

		findViewById(R.id.bt4).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
             mTvPc.clearPsw();

			}
		});


		findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(getAppDetailSettingIntent());
			}
		});

		findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoMiuiPermission();

			}
		});

		findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

            gotoMeizuPermission();

			}
		});
	}


	private void gotoSetPermission(){

		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", getPackageName(), null);
		intent.setData(uri);

		// Start for result
		startActivity(intent);
	}


	/**
	 * 华为的权限管理页面
	 */
	private void gotoHuaweiPermission() {
		try {
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
			intent.setComponent(comp);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			startActivity(getAppDetailSettingIntent());
		}

	}

	/**
	 * 获取应用详情页面intent
	 *
	 * @return
	 */
	private Intent getAppDetailSettingIntent() {
		Intent localIntent = new Intent();
		localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Build.VERSION.SDK_INT >= 9) {
			localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			localIntent.setData(Uri.fromParts("package", getPackageName(), null));
		} else if (Build.VERSION.SDK_INT <= 8) {
			localIntent.setAction(Intent.ACTION_VIEW);
			localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
			localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
		}
		return localIntent;
	}


	/**
	 * 跳转到魅族的权限管理系统
	 */
	private void gotoMeizuPermission() {
		Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
		try {
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			gotoHuaweiPermission();
		}
	}


	/**
	 * 跳转到miui的权限管理页面
	 */
	private void gotoMiuiPermission() {
		Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
		ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
		i.setComponent(componentName);
		i.putExtra("extra_pkgname", getPackageName());
		try {
			startActivity(i);
		} catch (Exception e) {
			e.printStackTrace();
			gotoMeizuPermission();
		}
	}


}
