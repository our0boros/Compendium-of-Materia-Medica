package model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

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

	public static String getAPIResult(String imagePath) throws IOException {
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

}
