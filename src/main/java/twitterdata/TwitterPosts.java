package twitterdata;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import wordcloud.CollisionMode;
import wordcloud.WordCloud;
import wordcloud.WordFrequency;
import wordcloud.bg.CircleBackground;
import wordcloud.font.scale.SqrtFontScalar;
import wordcloud.nlp.FrequencyAnalyzer;
import wordcloud.palette.LinearGradientColorPalette;

/** TwitterPosts class to retrieve and process data from the Twitter API using the Twitter4J library.
 * Methods to write to SQL db, text files and create a word cloud.
 * 
 * @author Alex
 *
 */
public class TwitterPosts {
	
	ArrayList<Status> tweets = new ArrayList<Status>();
	Query SearchQuery = new Query();
	private static SessionFactory factory; 
	int noTweets;
	private String TextPath;
	
	/** TwitterPosts constructor method.  
	 * 
	 * @param Search input search string.
	 */
	public TwitterPosts(String Search){	
		SearchQuery.query(Search);
	}
	
	/** TwitterPosts constructor method with no argument.  
	 * 
	 */
	public TwitterPosts(){	
		SearchQuery.query(null);
	}
		
	/** Method to return the current search string.
	 * 
	 * @return String the current search string.
	 */
	public String getQuery(){	
		return SearchQuery.getQuery();
	}
	
	/** Method to set the query string.
	 * 
	 * @param Search the search String.
	 */
	public void setQuery(String Search){	
		SearchQuery.setQuery(Search);
	}
	
	/** Method to return tweets ArrayList.
	 * 
	 * @return
	 */
	public ArrayList<Status> getPosts(){
		return tweets;
	}
	
	/** Method to return tweets ArrayList at index i.
	 * 
	 * @return index i
	 */
	public Status getPostsIndex(int i){
		return tweets.get(i);
	}
	
	/** Method to empty tweets ArrayList. 
	 * 
	 */

	public void emptyTweets(){
		tweets.clear();
	}
		
	/** Method to populate tweets ArrayList with tweets from the given search query.
	 * 
	 * @param resultsize int number of tweets to insert into ArrayList.
	 */
	public void beginSearch(int resultsize){
	
	    noTweets = resultsize;
	    long lastID = Long.MAX_VALUE;
	    Twitter twitter = TwitterFactory.getSingleton();

	    while (tweets.size () < noTweets) {
	      if (noTweets - tweets.size() > 100){
	    	  SearchQuery.setCount(100);
	      }
	      else {
	    	  SearchQuery.setCount(noTweets - tweets.size());
	      }
	      try {
	        QueryResult result = twitter.search(SearchQuery);
	        tweets.addAll(result.getTweets());
	        for (Status t: tweets) 
	          if(t.getId() < lastID) 
	              lastID = t.getId();
	      }
	      catch (TwitterException te) {
	        System.out.println("Could not connect: " + te);
	      }; 
	      SearchQuery.setMaxId(lastID-1);
	    }
	}
	
	
	/** Example method to read data from TwitterData table on SQL db.
	 * 
	 * @return TwitterList a List<TwitterData> from the SQL db table.
	 */
	
	public List<TwitterData> getSQLdata(){
		
		 try{
	         factory = new Configuration().configure().buildSessionFactory();
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }

		 Session session = factory.openSession();
		 org.hibernate.Query query = session.createQuery("from TwitterData");
		 List<TwitterData> TwitterList = query.list();
		 return TwitterList;
	}
	
	
	/** Example method to display functionality to write tweets ArrayList to SQL db, using the TwitterData POJO.
	 * 
	 */
	
	public void writeDataSQL(){
		
		//loop through array, creating object then writing to DB.
		
		 try{
	         factory = new Configuration().configure().buildSessionFactory();
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
		 
		    Session session = factory.openSession();
		    Transaction tx = null;
		    Integer ID = null;
		    
		    for (int i = 1; i < noTweets; i++){
			    try{
				       tx = session.beginTransaction();
				    	TwitterData user = new TwitterData(tweets.get(i).getId(), tweets.get(i).getUser().getScreenName(), tweets.get(i).getText(),
				    			tweets.get(i).getRetweetCount(), tweets.get(i).getFavoriteCount());
				       ID = (Integer) session.save(user); 
				       session.save(user); 
				       tx.commit();
				    }catch (HibernateException e) {
				       if (tx!=null) tx.rollback();
				       e.printStackTrace(); 
				    }
		    }
			session.close(); 

	}
	
	/** Testing method to check setup of db and TwitterData POJO is correct.
	 * 
	 */
	public void testWriteSQL(){
		
		 try{
	         factory = new Configuration().configure().buildSessionFactory();
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
		 
	    Session session = factory.openSession();
	    Transaction tx = null;
	    Integer ID = null;	    
	    try{
	       tx = session.beginTransaction();
	       TwitterData user = new TwitterData(1000,"user", "message", 1, 1);
	       ID = (Integer) session.save(user); 
	       session.save(user); 
	       tx.commit();
	    }catch (HibernateException e) {
	       if (tx!=null) tx.rollback();
	       e.printStackTrace(); 
	    }finally {
	       session.close(); 
	    }
	}   
	

	/** Method to create local text file with Text message from each tweet in the tweets Arraylist.
	 * 
	 * @param path .txt file location.
	 */
	public void writetoText(String path){
		try {
			TextPath = path;
			File file = new File(TextPath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (int i = 1; i < noTweets; i++){
			bw.write(tweets.get(i).getText());
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	/** Method to create word cloud using Kumo Word Cloud API using generated text file from writetoText(). 
	 * 
	 * @param imagepath .png file location.
	 */
	public void createWordCloud(String imagepath){

		InputStream fileInputStream = null;
		try {
	    	fileInputStream = new FileInputStream(TextPath);
	    } catch (FileNotFoundException e) {

	        e.printStackTrace();
	    } 
		
		final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		//configure amount of unique words to scan for
		frequencyAnalyzer.setWordFrequencesToReturn(100);
		frequencyAnalyzer.setMinWordLength(3); 
		List<WordFrequency> wordFrequencies = null;
		try {
			wordFrequencies = frequencyAnalyzer.load(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//wordcloud setup
		final WordCloud wordCloud = new WordCloud(800, 800, CollisionMode.PIXEL_PERFECT);
		wordCloud.setBackgroundColor(Color.WHITE);
		wordCloud.setPadding(2);
		wordCloud.setBackground(new CircleBackground(400));
		wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, 30, Color.ORANGE, 30 , Color.YELLOW));
		//change scaling on size of fonts.
		wordCloud.setFontScalar(new SqrtFontScalar(10, 175));
		wordCloud.build(wordFrequencies);
		wordCloud.writeToFile(imagepath);		
	}
	
}
