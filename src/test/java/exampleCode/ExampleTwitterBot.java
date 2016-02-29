package exampleCode;

import java.io.File;
import java.util.Random;

import twitter4j.StatusUpdate;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitterdata.TwitterPosts;

public class ExampleTwitterBot {
	
	/** Example class to create and upload word clouds for a random trending topic on the given twitter account.
	 * 
	 * @throws InterruptedException
	 */
public static void main(String[] args) throws InterruptedException{
		
		Random random = new Random();
		int rand = 0;
		
		Twitter twitter = TwitterFactory.getSingleton();
		Trends trends = null;
		try {
			//get UK trends.
			trends = twitter.getPlaceTrends(23424975);
		} catch (TwitterException e) {
			e.printStackTrace();
		}		
		
		while(true){
	    rand = random.nextInt(trends.getTrends().length);
	    String searchstring = trends.getTrends()[rand].getName();
	    TwitterPosts NewSearch = new TwitterPosts(searchstring);
		NewSearch.beginSearch(500);
		NewSearch.writetoText("c://testwrite.txt");
		NewSearch.createWordCloud("c://twitterwordcloud.png");		
		File file = new File("c://twitterwordcloud.png");		
		StatusUpdate status = new StatusUpdate(searchstring+" Word Cloud");
		status.setMedia(file);
		try {
			twitter.updateStatus(status);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	    
		//30 minute wait until next tweet
    	Thread.sleep(1000*60*30);	 
		}
	}
	

}
