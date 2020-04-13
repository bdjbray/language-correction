package citiaps.twitter_api;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



    public class output{
    public final static String OAUTH_CONSUMER_KEY = "ZiICEsE9nAMb4zztIEcEMsCmj";
    public final static String OAUTH_CONSUMER_SECRET = "dvGvcLd4pSTHjNrk7vyVgJpB4Zoi6JqulnGCRuFHWRpzIJoOdD";
    public final static String OAUTH_ACCESS_TOKEN = "1227718321874669568-A7vLJQS9Ol2gz6efap1XOnUDJ7nQym";
    public final static String OAUTH_ACCESS_TOKEN_SECRET = "CHaWZg0DOrdLh0PZwGno4uchq0IwD2pgAdR5jQe23i9vM";

    public static void main(String[] args) throws TwitterException {
        new output().write(args);
    }
    public void write(String[] args){
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true);
            cb.setOAuthConsumerKey(OAUTH_CONSUMER_KEY);
            cb.setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET);
            cb.setOAuthAccessToken(OAUTH_ACCESS_TOKEN);
            cb.setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);
            TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

            StatusListener listener = new StatusListener() {
                @Override
                public void onException(Exception e) {

                }
                @Override
                public void onStatus(Status status) {
                    System.out.println(status.getCreatedAt() + " - " + status.getText());
                    try {
                        MediaEntity[] entities = status.getMediaEntities();
                        if (status.getRetweetedStatus() != null) {
                            Util.writeStringToFile("infectiousKeywords.txt", status.getText() +"\r\n");
                        } else if (status.getRetweetedStatus() == null) {
                            Util.writeStringToFile("infectiousKeywords.txt", status.getText() +"\r\n");
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

                }

                @Override
                public void onTrackLimitationNotice(int i) {

                }

                @Override
                public void onScrubGeo(long l, long l1) {

                }

                @Override
                public void onStallWarning(StallWarning stallWarning) {

                }
        };
        FilterQuery fq = new FilterQuery();
        String keywords[] = {"convid-19", "Boston","CDC"};

        fq.track(keywords);
        fq.language(new String[]{"en"});

        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }
 }
