package com.twitter.rest.server;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.sun.media.jfxmedia.Media;
import com.twitter.rest.methods.MainGui;
import com.twitter.rest.methods.SearchListener;
import com.twitter.rest.methods.index;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Plain old Java Object it does not extend as class or implements
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation.
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML.

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Server {

    public static String consumerKey = System.getenv("ROOT") + "consumerKey", consumerSecret = System.getenv("ROOT") + "consumerSecret", accessToken = System.getenv("ROOT") + "accessToken", accessTokenSecret = System.getenv("ROOT") + "accessTokenSecret";
    // This method is called if TEXT_PLAIN is request
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello Jersey Test";
    }

    // This method is called if XML is request
    @GET
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey Test" + "</hello>";
    }

    // This method is called if HTML is request
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "Hello Jersey Test" + "</title>"
                + "<body><h1>" + "Hello Jersey Test" + "</body></h1>" + "</html> ";
    }

    @GET
    @Path("/{param}")
    public Response getMsg(@PathParam("param") String msg) {

        String output = "Jersey say : " + msg;

        return Response.status(200).entity(output).build();

    }

    @GET
    @Path("/mood/{param}")
    public Response startFeed(@PathParam("param") String msg) {

        String[] test = new String[5];
        index.main(test);

        String output = "Current mood : " + SearchListener.getMood();

        return Response.status(200).entity(output).build();

    }

    @GET
    @Path("/mood")
    public String getMood() {

        return SearchListener.getMood();


    }

    @GET
    @Path("/mood/count")
    public double getMoodCount() {

        return SearchListener.getMoodCount();


    }

    @GET
    @Path("/mood/get/tweets")
    public List<Map<String, String>> getTweets() {

        return SearchListener.getStatusString();


    }


    @GET
    @Path("/mood/search/{param}")
    public void search(@PathParam("param") String msg) {

        msg=msg.replace("-", " ");

        MainGui.searchTerms.setText(msg);
        MainGui.clickSearchButton();

    }

    @GET
    @Path("/mood/url")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String,String>> getURls()  {
//        JSONArray json = new JSONArray(SearchListener.getUrlList());
//        JSONObject jo = new JSONObject();
//        jo.put("Array",json);
        return SearchListener.getUrlList();


    }

    @GET
    @Path("/mood/top")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String,String>> getTopTweets() throws TwitterException {

        ConfigurationBuilder c = null;
        Twitter twit = SearchListener.getTwitter();
        String OAuthConsumerKey = SearchListener.getOAuthConsumerKey();
        String OAuthConsumerSecret = SearchListener.getOAuthConsumerSecret();
        String OAuthAccessToken = SearchListener.getOAuthAccessToken();
        String OAuthAccessTokenSecret = SearchListener.getOAuthAccessTokenSecret();

        if(OAuthAccessToken.equals("") && OAuthAccessTokenSecret.equals("")&&
                OAuthConsumerKey.equals("") && OAuthConsumerSecret.equals("")){

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

        }

        if(c==null) {
            c = new ConfigurationBuilder();
            c.setDebugEnabled(true);
            c.setOAuthConsumerKey(OAuthConsumerKey);
            c.setOAuthConsumerSecret(OAuthConsumerSecret);
            c.setOAuthAccessToken(OAuthAccessToken);
            c.setOAuthAccessTokenSecret(OAuthAccessTokenSecret);

        }

        if(twit==null) {
            twit = new TwitterFactory(c.build()).getInstance();
        }

        Trends trends = twit.getPlaceTrends(23424977);

        Trend[] trendsList = trends.getTrends();

      List<Map<String, String>> topTweetMap = new ArrayList<>();

        for(int i=0; i<10; i++) {

            String topTweet = trendsList[i].getName();

            Map<String, String> topTweets = new HashMap<>();
            topTweets.put("top", topTweet);
            topTweetMap.add(i,topTweets);
            System.out.println(trendsList[i].getName());

        }

        return topTweetMap;


    }

}