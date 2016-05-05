package com.twitter.rest.methods;

import twitter4j.*;
import twitter4j.api.TrendsResources;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created by richardfenton on 4/16/16.
 */
public class TopTweets {
    String filePath = "", OAuthConsumerKey = "", OAuthConsumerSecret = "", OAuthAccessToken = "", OAuthAccessTokenSecret = "";
    String consumerKey = System.getenv("ROOT") + "consumerKey", consumerSecret = System.getenv("ROOT") + "consumerSecret", accessToken = System.getenv("ROOT") + "accessToken", accessTokenSecret = System.getenv("ROOT") + "accessTokenSecret";
    static TopTweets topTweets = new TopTweets();

    public static void main(String[] args) throws TwitterException {

        topTweets.getTrends();

    }

    public void getTrends() throws TwitterException {


        try {
            BufferedReader consumerKeyReader = new BufferedReader(new FileReader(consumerKey));
            OAuthConsumerKey = consumerKeyReader.readLine();
            consumerKeyReader.close();
        } catch (Exception consumerKeyReadErr) {
            System.out.println("Error reading consumer key from file...");
        }
        try {
            BufferedReader consumerSecretReader = new BufferedReader(new FileReader(consumerSecret));
            OAuthConsumerSecret = consumerSecretReader.readLine();
            consumerSecretReader.close();
        } catch (Exception consumerSecretReadErr) {
            System.out.println("Error reading consumer secret from file...");
        }
        try {
            BufferedReader accessTokenReader = new BufferedReader(new FileReader(accessToken));
            OAuthAccessToken = accessTokenReader.readLine();
            accessTokenReader.close();
        } catch (Exception accessTokenReadErr) {
            System.out.println("Error reading access token from file...");
        }
        try {
            BufferedReader accessTokenSecretReader = new BufferedReader(new FileReader(accessTokenSecret));
            OAuthAccessTokenSecret = accessTokenSecretReader.readLine();
            accessTokenSecretReader.close();
        } catch (Exception accessTokenSecretReaderErr) {
            System.out.println("Error reading access token secret from file...");
        }


        ConfigurationBuilder c = new ConfigurationBuilder();
        c.setDebugEnabled(true);
        c.setOAuthConsumerKey(OAuthConsumerKey);
        c.setOAuthConsumerSecret(OAuthConsumerSecret);
        c.setOAuthAccessToken(OAuthAccessToken);
        c.setOAuthAccessTokenSecret(OAuthAccessTokenSecret);
        Twitter twitter = new TwitterFactory(c.build()).getInstance();

        ResponseList<Location> trendLocations = twitter.getAvailableTrends();

        System.out.println("Showing available trends");
        for (Location location : trendLocations)
            System.out.println(location.getName() + " (woeid:" + location.getWoeid() + ")");

        Trends trends = twitter.getPlaceTrends(23424977);

        Trend[] trendsList = trends.getTrends();

        for(int i=0; i<trendsList.length; i++) {
            System.out.println(trendsList[i].getName());
        }

    }

}
