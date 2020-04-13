package citiaps.twitter_api;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class StreamingAPI 
{
	public final static String OAUTH_CONSUMER_KEY = "5BPSMCofACuMtRLPEWM5RGQoO";
    public final static String OAUTH_CONSUMER_SECRET = "oKr8gArD2uwgOz4cnFzw7JqmmMyX51o3oMOkl5fhWSR591Gs1F";
    public final static String OAUTH_ACCESS_TOKEN = "1227718321874669568-tjbyvDD73So1buu8Wx8kPKSJBj8GJb";
    public final static String OAUTH_ACCESS_TOKEN_SECRET = "TceCoqwG0MtaaXDxjSSxPyCtGURSs4aMc4b5rMw2zTZJZ";
	
    public static void main(String[] args) {
    	new StreamingAPI().doMain(args);      
    }
    
    public void doMain(String[] args){
    	ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setDebugEnabled(true);
    	cb.setOAuthConsumerKey(OAUTH_CONSUMER_KEY);
    	cb.setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET);
    	cb.setOAuthAccessToken(OAUTH_ACCESS_TOKEN);
    	cb.setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);
    	

    	TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    	StatusListener listener = new StatusListener() {  		

	        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
	            System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
	        }

	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
	            System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
	        }

	        public void onScrubGeo(long userId, long upToStatusId) {
	            System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
	        }

	        public void onException(Exception ex) {
	            ex.printStackTrace();
	        }

			public void onStatus(Status status) {
				System.out.println(status.getText());
				
			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
			}
	    };

	    FilterQuery fq = new FilterQuery();
	    String keywords[] = {"Boston"};

	    fq.track(keywords);

	    twitterStream.addListener(listener);
	    twitterStream.filter(fq);      
    	
    }



}
