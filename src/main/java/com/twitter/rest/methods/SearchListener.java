package com.twitter.rest.methods;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/*
 * Central class to application. Connects to twitter, authenticates user, opens twitter stream, stores data
 */
public class SearchListener implements ActionListener {
    public static String filePath = "", OAuthConsumerKey = "", OAuthConsumerSecret = "", OAuthAccessToken = "", OAuthAccessTokenSecret = "";
    public static String consumerKey = System.getenv("ROOT") + "consumerKey", consumerSecret = System.getenv("ROOT") + "consumerSecret", accessToken = System.getenv("ROOT") + "accessToken", accessTokenSecret = System.getenv("ROOT") + "accessTokenSecret";
    public static String dateString, content, username, profileLocation;//static twitter ouput vars
    public static List<String[]> data = new ArrayList<String[]>();//ArrayList for twitter stream data
    public static double positiveCount = 0;//Keeps track of positive sentiment
    public static double negativeCount = 0;//Keeps track of negative sentiment
    public static List<Map<String, String>> urlList = new ArrayList<>(); //Array of url
    public URLEntity[] urlEntity;
    public static List<Map<String,String>> statusString = new ArrayList<>();
    private static String mood = "Neutral";//Keeps track of mood
    int total = 0;
    int urlTotal = 0;
    int tweetTotal=0;
    public static TwitterStream twitStream = null;
    public static Twitter twit =null;
    public static String moodType;
    public static double moodCount;
    public static AlchemyLanguage service = new AlchemyLanguage();
    public static ConfigurationBuilder c=null;

    public void actionPerformed(ActionEvent e) {
        //MainGui.standardOut = System.out;//re-assigns standard output stream and error to text area
        //System.setOut(MainGui.printStream);//re-assigns standard output stream to printStream var
        //System.setErr(MainGui.printStream);//re-assigns error stream to printStream var

        statusString.clear();
        tweetTotal=0;

        service.setApiKey("fd26299dc5eac76bfdb794070a57f565bf5c3e48");

        negativeCount = 0;
        positiveCount = 0;
        mood = "Neutral";
        System.out.println("Angry Count: " + negativeCount);
        System.out.println("Happy Count: " + positiveCount);
        MainGui.Mood.setText(String.valueOf(mood));

        if (twitStream != null) {
            twitStream.cleanUp();
            twitStream.clearListeners();
            twitStream.shutdown();
        }


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

        //Twitter4J code block:
            c = new ConfigurationBuilder();
            c.setDebugEnabled(true);
            c.setOAuthConsumerKey(OAuthConsumerKey);
            c.setOAuthConsumerSecret(OAuthConsumerSecret);
            c.setOAuthAccessToken(OAuthAccessToken);
            c.setOAuthAccessTokenSecret(OAuthAccessTokenSecret);

            twitStream = new TwitterStreamFactory(c.build()).getInstance();


        final StatusListener statusEar = new StatusListener() {
            @Override
            public void
            onException(Exception arg0) {
                //nothing here
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                //nothing here
            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                //nothing here
            }

            @Override
            public void onStatus(Status status)// scrape all the twitters!!!
            {
                User user = status.getUser();//gets user
                username = status.getUser().getScreenName();// gets username
                profileLocation = user.getLocation();//location data
                content = status.getText();//tweet content
                dateString = status.getCreatedAt().toString();//time of tweet
                System.out.println("\nTime: " + dateString);
                System.out.println("By: " + username);
                System.out.println("From: " + profileLocation);
                System.out.println("Tweet: " + content + "\n");
                String[] rowData = {dateString, username, profileLocation, content};
                data.add(rowData);


                //Check for words in content indicating mood
                for (String word : content.split("\\s+")) {
                    if (Main.angry.containsValue(word.trim().toLowerCase())) {
                        negativeCount++;
                    }
                    if (Main.happy.containsValue(word.trim().toLowerCase())) {
                        positiveCount++;
                    }
                }

                if (negativeCount > positiveCount) {
                    mood = "Angry";
                }
                if (positiveCount > negativeCount) {
                    mood = "Happy";
                }
                if (positiveCount == negativeCount) {
                    mood = "Neutral";
                }

                //Mix in the free with the alchemy api///

//                Map<String,Object> params = new HashMap<String, Object>();
//                params.put(AlchemyLanguage.TEXT, content);
//                DocumentSentiment sentiment =  service.getSentiment(params);
//
//                System.out.println(sentiment.getSentiment());
//
//                 moodType=sentiment.getSentiment().getType().toString().toLowerCase();
//                 moodCount=sentiment.getSentiment().getScore();
//
//                if(moodType.equals("positive")){
//                    positiveCount+=moodCount;
//                }else if(moodType.equals("negative")){
//                    negativeCount+=moodCount;
//                }
//
//                if (negativeCount + positiveCount>0 && negativeCount + positiveCount<100) {
//                    mood = "Positive";
//                }
//                if (negativeCount + positiveCount>100) {
//                    mood = "Very Positive";
//                }
//
//                if (positiveCount + negativeCount<0 && positiveCount + negativeCount>-100) {
//                    mood = "Negative";
//                }
//                if (positiveCount + negativeCount<-100) {
//                    mood = "Very Negative";
//                }
//                if (positiveCount + negativeCount == 0) {
//                    mood = "Neutral";
//                }

                Map<String, String> tweetMap = new HashMap<>();
                tweetMap.put("username", username);
                tweetMap.put("tweet", content);
                statusString.add(tweetTotal, tweetMap);
                tweetTotal++;

                urlEntity = status.getURLEntities();

                //Cycle through url Entities and add to urlList
                for (int i = 0; i < urlEntity.length; i++) {

                    URLEntity url = urlEntity[i];

                    Map<String, String> urlMap = new HashMap<>();
                    urlMap.put("url", url.getExpandedURL().trim());
                    if (!urlList.contains(urlMap)) {
                        urlList.add(urlTotal, urlMap);
                        urlTotal++;
                    }
                }

                total += 1;//counts total number of captured treats
                if (total % 100 == 0)//on multiples of 100 -- or on every 100 tweets...
                {
                    MainGui.jta.setText("");//resets text to blank screen -- long scrolling screen takes lots of ram, so reset to keep size contained.
                    System.out.println("Negative Sentiment: " + negativeCount);
                    System.out.println("Positive Sentiment: " + positiveCount);
                    System.out.println("\nLinks: \n");
                    statusString.clear();
                    tweetTotal=0;
                    //urlList.clear();

                    for (int j = 0; j < urlList.size(); j++) {
                        System.out.println(urlList.get(j));
                    }

                    System.out.println("\n-----------------------\n");
                }

                MainGui.Mood.setText(String.valueOf(mood));

            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                //nothing here
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                //nothing here
            }
        };
        FilterQuery f = new FilterQuery();
        String x = MainGui.searchTerms.getText();
        String keywords[] = {x};
        f.track(keywords);
        urlList.clear();
        urlTotal = 0;
        twitStream.addListener(statusEar);
        twitStream.filter(f);

    }

    public static String getMood() {

        return mood;
    }

    public static List<Map<String, String>> getUrlList() {

        return urlList;

    }

    public static Twitter getTwitter(){
        return twit;
    }

    public static ConfigurationBuilder getConfigBuilder(){
        return c;
    }

    public static double getMoodCount(){
        return positiveCount-negativeCount;
    }

    public static List<Map<String, String>> getStatusString(){
        return statusString;
    }

    public static String getOAuthConsumerKey(){
        return OAuthConsumerKey;
    }

    public static String getOAuthConsumerSecret(){
        return OAuthConsumerSecret;
    }

    public static String getOAuthAccessToken(){
        return OAuthAccessToken;
    }

    public static String getOAuthAccessTokenSecret(){
        return OAuthAccessTokenSecret;
    }

}