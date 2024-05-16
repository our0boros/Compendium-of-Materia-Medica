package model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

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
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.*;

/**
 * @author: Hongjun Xu
 * @uid: u7733037
 * @datetime: 2024/05/16
 * @description: Encapsulate the API request process into a single method through Facade Pattern and use
 * <NOTE>
 *     In actual use, for data security issues, we should place the API token
 *     in a safe location such as the backend to prevent others from stealing it.
 *     However, there is no relevant hardware facility for the current project,
 *     so we temporarily obtain it as a simple variable.
 * </NOTE>
 */
public class PlantIdentification {
	/**
	 * Convert an image to a Base64 encoded string.
	 * @param imagePath The path to the image file.
	 * @return The Base64 encoded string representation.
	 * @throws IOException If an I/O error occurs.
	 */
	public static String imageToBase64(String imagePath) throws IOException {
		File file = new File(imagePath);
		byte[] bytesArray = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(bytesArray);
		fis.close();
		return Base64.getEncoder().encodeToString(bytesArray);
	}

	/**
	 * Retrieves the result from the Plant ID API using the provided image.
	 * <API>PlantID</API>
	 * @param imagePath The path to the image file.
	 * @return The JSON result from the Plant ID API.
	 * @throws IOException If an I/O error occurs.
	 */
	public static String getPlantIDAPIResult(String imagePath) throws IOException {
		String base64Image = imageToBase64(imagePath);
		// Basic Parameters
		String url = "https://plant.id/api/v3/identification";
		String apiKey = "bGyxGZY0G38H9nbLMYq0FbqmLZEQZYSUn98DunmqkmNglnQXDO";
		// Set request data
		String jsonData = "{\"images\": [\"data:image/jpg;base64," + base64Image + "\"]}";
		// Send POST request
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// Set request headers
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Api-Key", apiKey);
		// Send data to server
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(jsonData);
		wr.flush();
		wr.close();
		// Read server response
		int responseCode = con.getResponseCode();
		System.out.println("Response Code: " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		// return response string
		return response.toString();
	}
	/**
	 * Retrieves the result from the Plant ID API using the provided image.
	 * <API>Pl@ntNet</API>
	 * @param imagePath The path to the image file.
	 * @return The JSON result from the Plant ID API.
	 * @throws IOException If an I/O error occurs.
	 */
	public static String getPlantNetAPIResult(String imagePath) {
		StringBuilder response = new StringBuilder();
		// Basic Parameters
		String API_KEY = "2b10sgwYhB8pSqL6gMuqa3R"; // API_KEY
		String PROJECT = "canada"; // try specific floras: "weurope", "canada"…
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
	/**
	 * Retrieves the result from the Plant ID API using the provided image.
	 * <API>Pl@ntNet</API>
	 * @param imagePath The path to the image file.
	 * @return The JSON result from the Plant ID API.
	 * @throws IOException If an I/O error occurs.
	 */
	public static String getPlantNetAPIResultOKHttp(String imagePath) {

		String API_KEY = "2b10sgwYhB8pSqL6gMuqa3R"; // API_KEY here
		String PROJECT = "all"; // try specific floras: "weurope", "canada"…
		String API_ENDPOINT = "https://my-api.plantnet.org/v2/identify/" + PROJECT + "?api-key=" + API_KEY;
		String responseBody = "";

		File file = new File(imagePath);
		// Check if the file exists
		if (file.exists()) {
			// The file exists, perform corresponding operations
			System.out.println("File exists!");
		} else {
			// The file does not exist, handle it accordingly.
			System.out.println("File does not exist!");
			return "";
		}
		// HTTP Request
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
		// Waiting for request
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

	/**
	 * Return the specific content of the corresponding type related to the input botanical name and wiki api
	 * @param sciName
	 * @param type
	 * @return
	 */
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
		// Create a Request object
		Request request = new Request.Builder()
				.url(requestUrl)
				.build();
		// Send a request and get a response
		try {
			Response response = client.newCall(request).execute();
			// Determine whether the request is successful
			if (response.isSuccessful()) {
				// Get JSON data
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
				// Parse json data to String
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
