package com.bharatkhoj.metasearch.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * HTTPS client for Serp API
 */
public class GoogleSearchResultsClient {
	// http request configuration
	private int httpConnectionTimeout;
	private int httpReadTimeout;

	public static String VERSION = "1.2.0";
	public static String BACKEND = "https://serpapi.com";

	// initialize gson
	private static Gson gson = new Gson();

	// path
	public String path;

	/***
	 * @param String
	 *            path
	 */
	public GoogleSearchResultsClient(String path) {
		this.path = path;
	}

	/***
	 * Build URL
	 *
	 * @param path
	 *            url end point
	 * @param param
	 *            hash
	 * @return httpUrlConnection
	 * @throws IOException
	 */
	public HttpURLConnection buildConnection(String path, Map<String, String> parameter) throws GoogleSearchException {
		HttpURLConnection con;
		try {
			allowHTTPS();
			String query = ParameterStringBuilder.getParamsString(parameter);
			URL url = new URL(BACKEND + path + "?" + query);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
		} catch (IOException e) {
			throw new GoogleSearchException(e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new GoogleSearchException(e);
		} catch (KeyManagementException e) {
			e.printStackTrace();
			throw new GoogleSearchException(e);
		}

		String outputFormat = parameter.get("output");
		if (outputFormat == null) {
			if (path.startsWith("/search?")) {
				throw new GoogleSearchException("output format must be defined: " + path);
			}
		} else if (outputFormat.startsWith("json")) {
			con.setRequestProperty("Content-Type", "application/json");
		}

		// TODO Enable to set different timeout
		con.setConnectTimeout(getHttpConnectionTimeout());
		con.setReadTimeout(getHttpReadTimeout());

		con.setDoOutput(true);
		return con;
	}

	private void allowHTTPS() throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts;
		trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		/*
		 * end of the fix
		 */
	}

	/***
	 * Get results
	 *
	 * @param parameter
	 * @return http response body
	 */
	public String getResults(Map<String, String> parameter) throws GoogleSearchException {
		HttpURLConnection con = buildConnection(this.path, parameter);

		// Get HTTP status
		int statusCode = -1;
		// Hold response stream
		InputStream is = null;
		// Read buffer
		BufferedReader in = null;
		try {
			statusCode = con.getResponseCode();

			if (statusCode == 200) {
				is = con.getInputStream();
			} else {
				is = con.getErrorStream();
			}

			Reader reader = new InputStreamReader(is);
			in = new BufferedReader(reader);
		} catch (IOException e) {
			throw new GoogleSearchException(e);
		}

		String inputLine;
		StringBuffer content = new StringBuffer();
		try {
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			throw new GoogleSearchException(e);
		}

		// Disconnect
		con.disconnect();

		if (statusCode != 200) {
			triggerGoogleSearchException(content.toString());
		}
		return content.toString();
	}

	public void triggerGoogleSearchException(String content) throws GoogleSearchException {
		String errorMessage;
		try {
			JsonObject element = gson.fromJson(content, JsonObject.class);
			errorMessage = element.get("error").getAsString();
		} catch (Exception e) {
			throw new AssertionError("invalid response format: " + content);
		}
		throw new GoogleSearchException(errorMessage);
	}

	public int getHttpConnectionTimeout() {
		return httpConnectionTimeout;
	}

	public void setHttpConnectionTimeout(int httpConnectionTimeout) {
		this.httpConnectionTimeout = httpConnectionTimeout;
	}

	public int getHttpReadTimeout() {
		return httpReadTimeout;
	}

	public void setHttpReadTimeout(int httpReadTimeout) {
		this.httpReadTimeout = httpReadTimeout;
	}

}