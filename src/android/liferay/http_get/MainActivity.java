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

MainActivity(){
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		new RequestResponse().execute();
	}

}

public class RequestResponse extends AsyncTask<Void, Void, String> {
	
	String a1 = "";
	String a2 = "";
	JSONObject jsonObj;
	JSONArray jArray;
	
	@SuppressLint("NewApi")
	@Override
	protected String doInBackground(Void... params) {
		String response = null;
		String webPage = "http://10.20.10.157:8081/api/jsonws/Employee-portlet.employee/get-employee/name/jay";
		String username = "test@liferay.com";
		String password = "test";

		try {

			URL targetUrl = new URL(webPage);
			String auth = new String(Base64.encode(
					(username + ":" + password).getBytes(), Base64.URL_SAFE | Base64.NO_WRAP));
			HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();
			// conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + auth);
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();

			BufferedReader responseBuffer = null;
			responseBuffer = new BufferedReader(new InputStreamReader(is));
			response = responseBuffer.readLine();
			try {
				jArray = new JSONArray(response);
				// looping through..
				for (int i = 0; i < jArray.length(); i++) {
					
					JSONObject c = jArray.getJSONObject(i);
					a1 += c.getString("name") + " ";
					a1 += c.getString("job_title") + " ";
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response = response + "\n \n" + a1 + a2;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	protected void onPostExecute(String results) {
		super.onPostExecute(results);

		rl.onResultListener(results);
	}
}
