package model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.*;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;

public class Plant_Identification {

	public static String imageToBase64(String imagePath) throws IOException {
		File file = new File(imagePath);
		byte[] bytesArray = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(bytesArray);
		fis.close();
		return Base64.getEncoder().encodeToString(bytesArray);
	}

	public static String getPlantIDAPIResult(String imagePath) throws IOException {
		String base64Image = imageToBase64(imagePath);

		String url = "https://plant.id/api/v3/identification";
		String apiKey = "bGyxGZY0G38H9nbLMYq0FbqmLZEQZYSUn98DunmqkmNglnQXDO";

		// 设置请求数据
		String jsonData = "{\"images\": [\"data:image/jpg;base64," + base64Image + "\"]}";

		// 发送 POST 请求
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// 设置请求头
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Api-Key", apiKey);

		// 向服务器发送数据
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(jsonData);
		wr.flush();
		wr.close();

		// 读取服务器响应
		int responseCode = con.getResponseCode();
		System.out.println("Response Code: " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// 打印响应内容
//		System.out.println("Response: " + response.toString());
		return response.toString();
	}

	public static String getPlantNetAPIResult(String imagePath) {
		StringBuilder response = new StringBuilder();

		String API_KEY = "2b10sgwYhB8pSqL6gMuqa3R"; // Your API_KEY here
		String PROJECT = "all"; // try specific floras: "weurope", "canada"…
		String api_endpoint = String.format("https://my-api.plantnet.org/v2/identify/%s?api-key=%s",
				PROJECT, API_KEY);

		File imageFile = new File(imagePath);

		Map<String, Object> data = new HashMap<>();
		data.put("organs", new String[]{"flower"}); // leaf

		try {
			URL url = new URL(api_endpoint);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);

			String boundary = UUID.randomUUID().toString();
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			OutputStream outputStream = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

			// Adding data
			for (Map.Entry<String, Object> entry : data.entrySet()) {
				writer.write("--" + boundary + "\r\n");
				writer.write("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
				if (entry.getValue() instanceof String) {
					writer.write((String) entry.getValue() + "\r\n");
				} else if (entry.getValue() instanceof String[]) {
					String[] values = (String[]) entry.getValue();
					for (String value : values) {
						writer.write(value + "\r\n");
					}
				}
			}

			// Adding file
			writer.write("--" + boundary + "\r\n");
			writer.write("Content-Disposition: form-data; name=\"images\"; filename=\"" + imageFile.getName() + "\"\r\n");
			writer.write("Content-Type: " + HttpURLConnection.guessContentTypeFromName(imageFile.getName()) + "\r\n\r\n");
			writer.flush();

			FileInputStream fileInputStream = new FileInputStream(imageFile);
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			fileInputStream.close();

			writer.write("\r\n");
			writer.write("--" + boundary + "--\r\n");
			writer.close();

			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println("Response: " + response.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}

	private static Uri getFileUriFromMediaStore(Context context, String filePath) {
		// 查询 MediaStore 获取文件的 Uri
		Uri fileUri = null;
		String[] projection = {MediaStore.Images.Media._ID};
		String selection = MediaStore.Images.Media.DATA + "=?";
		String[] selectionArgs = {filePath};
		Cursor cursor = null;

		try {
			cursor = context.getContentResolver().query(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					projection,
					selection,
					selectionArgs,
					null
			);

			if (cursor != null && cursor.moveToFirst()) {
				int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
				long imageId = cursor.getLong(columnIndex);
				fileUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Long.toString(imageId));
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return fileUri;
	}

	public static String getPlantNetAPIResultOKHttp(String imagePath) {

		String API_KEY = "2b10sgwYhB8pSqL6gMuqa3R"; // Your API_KEY here
		String PROJECT = "all"; // try specific floras: "weurope", "canada"…
		String API_ENDPOINT = "https://my-api.plantnet.org/v2/identify/" + PROJECT + "?api-key=" + API_KEY;
		String responseBody = "";

		File file = new File(imagePath);
		// 检查文件是否存在
		if (file.exists()) {
			// 文件存在，进行相应操作
			System.out.println("File exists!");
		} else {
			// 文件不存在，进行相应处理
			System.out.println("File does not exist!");
		}
		OkHttpClient client = new OkHttpClient();

		RequestBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("organs", "flower")
				.addFormDataPart("images", imagePath, RequestBody.create(MediaType.parse("image/jpeg"), new File(imagePath)))
				.addFormDataPart("organs", "leaf")
				.addFormDataPart("images", imagePath, RequestBody.create(MediaType.parse("image/jpeg"), new File(imagePath)))
				.build();

		Request request = new Request.Builder()
				.url(API_ENDPOINT)
				.post(requestBody)
				.build();

		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				responseBody = response.body().string();
				System.out.println("Response Code: " + response.code());
				System.out.println("Response Body: " + responseBody);
			} else {
				System.out.println("Request failed: " + response.code());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseBody;
	}


//	public static String getPlantNetAPIResultApache(String imagePath) {
//
//		final String PROJECT = "all"; // try specific floras: "weurope", "canada"…
//		final String URL = "https://my-api.plantnet.org/v2/identify/" + PROJECT + "?api-key=2b10sgwYhB8pSqL6gMuqa3R";
//
//		File file = new File(imagePath);
//
//		HttpEntity entity = MultipartEntityBuilder.create()
//				.addPart("images", new FileBody(file)).addTextBody("organs", "flower")
//				.addPart("images", new FileBody(file)).addTextBody("organs", "leaf")
//				.build();
//
//		HttpPost request = new HttpPost(URL);
//		request.setEntity(entity);
//
//		HttpClient client = HttpClientBuilder.create().build();
//		HttpResponse response;
//		String jsonString = "";
//		try {
//			response = client.execute(request);
//			jsonString = EntityUtils.toString(response.getEntity());
//			System.out.println(jsonString);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return jsonString;
//	}

	public static String getFromWiki(String sciName, String type) {
		String requestUrl;
		switch (type) {
			case "image": {
				requestUrl = "https://en.wikipedia.org/w/api.php?format=json&action=query&titles=" +
						sciName.replace(" ", "%20") + "&prop=pageimages&pithumbsize=300";
				break;
			}
			case "content": {
				requestUrl = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles=" +
						sciName.replace(" ", "%20");
				break;
			}
			default: {
				requestUrl = "";
				break;
			}
		}

		OkHttpClient client = new OkHttpClient();
		// 创建 Request 对象
		Request request = new Request.Builder()
				.url(requestUrl)
				.build();
		// 发送请求并获取响应
		try {
			Response response = client.newCall(request).execute();
			// 判断请求是否成功
			if (response.isSuccessful()) {
				// 获取 JSON 数据
				String jsonData = response.body().string();
				System.out.println(jsonData);
				JSONObject jsonObject = new JSONObject(jsonData);
				JSONObject firstPageObject = jsonObject.getJSONObject("query")
						.getJSONObject("pages")
						.getJSONObject(
								jsonObject.getJSONObject("query")
								.getJSONObject("pages")
								.keys()
								.next());
				System.out.println(jsonData);
				switch (type) {
					case "image": {
						return (String) firstPageObject.getJSONObject("thumbnail").get("source");
					}
					case "content":
						return (String) firstPageObject.get("extract");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
