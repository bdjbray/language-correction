package citiaps.twitter_api;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RestAPI {

	public final static String OAUTH_CONSUMER_KEY = "5BPSMCofACuMtRLPEWM5RGQoO";
	public final static String OAUTH_CONSUMER_SECRET = "oKr8gArD2uwgOz4cnFzw7JqmmMyX51o3oMOkl5fhWSR591Gs1F";
	public final static String OAUTH_ACCESS_TOKEN = "1227718321874669568-tjbyvDD73So1buu8Wx8kPKSJBj8GJb";
	public final static String OAUTH_ACCESS_TOKEN_SECRET = "TceCoqwG0MtaaXDxjSSxPyCtGURSs4aMc4b5rMw2zTZJZ";

	public static void main(String[] args) {
		new RestAPI().doMain(args);
	}

	public void searchByWords(String terms) throws IOException {

		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query(terms);
		QueryResult result = null;
		try {
			result = twitter.search(query);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		for (Status status : result.getTweets()) {
			//System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			BufferedWriter out = new BufferedWriter(new FileWriter("twitter.txt"));
			out.write("@" + status.getUser().getScreenName() + ":" + status.getText());
			out.close();
		}

	}

	public void searchUserByScreenName(String username) {

		Twitter twitter = TwitterFactory.getSingleton();

		ResponseList<User> users;

		try {
			users = twitter.searchUsers(username, 0);
			for (User user : users) {
				if (user.getStatus() != null) {
					System.out.println("@" + user.getScreenName() + " - " + user.getStatus().getText());
				} else {
					// the user is protected
					System.out.println("@" + user.getScreenName());
				}
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}

	public void searchFF(String username) {

		Twitter twitter = TwitterFactory.getSingleton();

		ResponseList<User> users;

		try {
			users = twitter.searchUsers(username, 0);
			for (User user : users) {
				if (user.getStatus() != null) {
					System.out.println("@" + user.getScreenName() + " - " + user.getStatus().getText());
				} else {
					// the user is protected
					System.out.println("@" + user.getScreenName());
				}
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}

	public void doMain(String[] args) {

		searchUserByScreenName("Juan");

	}

}
