package model;

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
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Plant_Identification {

	public static String imageToBase64(String imagePath) throws IOException {
		File file = new File(imagePath);
		byte[] bytesArray = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(bytesArray);
		fis.close();
		return Base64.getEncoder().encodeToString(bytesArray);
	}

	public static void main(String[] args) throws IOException {
		String imagePath = "potato.jpg"; // 替换为图像文件的路径
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
		System.out.println("Response: " + response.toString());
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
		StringBuffer response = new StringBuffer();

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
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

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
//			System.out.println("Response Code: " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

//			System.out.println("Response: " + response.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}
}
