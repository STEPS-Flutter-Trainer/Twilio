

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class TwilioRestTest {
	public static final String  ACCOUNT_SID = "AC8b559cf9ce5b7edad254d0078243c758";
	public static final String  AUTH_TOKEN = "ab240f2f592c1faf5f0d16ef08c578f6";
	public static final String  PROXY_ADDRESS = "192.168.18.254";
	public static final int     PROXY_PORT = 3128;
	public static final String  PROXY_USER = "boon_jb110";
	public static final String  PROXY_PASSWORD = "3aesrf3es";

	public static void main(String[] args) {
		HttpHost proxy = new HttpHost(PROXY_ADDRESS, PROXY_PORT);
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				new AuthScope(PROXY_ADDRESS, PROXY_PORT), 
				new UsernamePasswordCredentials(PROXY_USER, PROXY_PASSWORD));
		credsProvider.setCredentials(
				new AuthScope("api.twilio.com", 443), 
				new UsernamePasswordCredentials(ACCOUNT_SID, AUTH_TOKEN));
		CloseableHttpClient httpClient = HttpClients.custom()
				.setRoutePlanner(routePlanner)
				.setDefaultCredentialsProvider(credsProvider)
				.build();
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
		client.setHttpclient(httpClient);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Body", "Hello from Nikitha"));
		params.add(new BasicNameValuePair("To", "+918939693633"));
		params.add(new BasicNameValuePair("From", "+12299997892"));
		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		try {
			Message message;
			message = messageFactory.create(params);
			System.out.println(message.getSid());
		} catch (TwilioRestException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMessage());
			e.printStackTrace();
		}
	}
}
