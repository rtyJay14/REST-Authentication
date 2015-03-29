package android.liferay.http_get;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.authorwjf.http_get.R;

public class MainActivity extends Activity implements OnClickListener {

	ProgressDialog svcDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViewById(R.id.my_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		Button b = (Button) findViewById(R.id.my_button);
		b.setClickable(false);
		showProgressDialog();
		writeJSON();
		RequestResponse lrg = new RequestResponse();
		lrg.onClickResultListener(new ResultListener() {
			@Override
			public void onResultListener(String results) {
				svcDialog.dismiss();
				if (results != null) {
					EditText et = (EditText) findViewById(R.id.my_edit);
					et.setText(results);
				}
				Button b = (Button) findViewById(R.id.my_button);
				b.setClickable(true);
			}
		});
		lrg.execute();
	}

	protected void showProgressDialog() {
		svcDialog = new ProgressDialog(MainActivity.this);
		svcDialog.setMessage("Requesting data. . .");
		// svcDialog .setProgressDrawable(MainActivity.this.getWallpaper());
		svcDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		svcDialog.setCancelable(false);
		svcDialog.show();
	}

	public void writeJSON() {
		JSONObject object = new JSONObject();
		try {
			object.put("name", "Jack the Bean");
			object.put("score", new Integer(200));
			object.put("current", new Double(152.32));
			object.put("nickname", "Titan");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(), ""+object, Toast.LENGTH_SHORT).show();
	}
}