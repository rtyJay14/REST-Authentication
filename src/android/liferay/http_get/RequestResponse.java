package android.liferay.http_get;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Base64;

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

	void onClickResultListener(ResultListener listener) {
		this.rl = listener;
	}

	ResultListener rl;
}
