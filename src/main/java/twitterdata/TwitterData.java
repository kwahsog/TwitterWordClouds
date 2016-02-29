package twitterdata;

import javax.persistence.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

	/** Hibernate POJO for twitter data.
	 * 
	 * @author Alex
	 *
	 */

@Entity
@Table(name = "TWITTERDATA")
public class TwitterData {

	@Id 
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	@Column(name= "ID")
	private int ID;
	
	@Column(name= "TweetID")
	private long TweetID;
	
	@Column(name= "TwitterUser")
	private String TwitterUser;
	
	@Column(name= "TweetMessage")
	private String TweetMessage;
	
	@Column(name= "RetweetCount")
	private long RetweetCount;
	
	@Column(name= "FavouriteCount")
	private long FavouriteCount; 

	public TwitterData() {}
	
	public TwitterData(long TweetID, String TwitterUser, String TweetMessage, int RetweetCount, int FavouriteCount) {
		this.TweetID = TweetID;
		this.TwitterUser = TwitterUser;
		this.TweetMessage = TweetMessage;
		this.RetweetCount = RetweetCount;
		this.FavouriteCount = FavouriteCount;
	}

	public int getID(){		
	return ID;
	}
	
	public void setID(int ID){
	this.ID = ID;
	}
	
	public String getTwitterUser(){		
	return TwitterUser;
	}
	
	public void setTwitterUser(String TwitterUser){
	this.TwitterUser = TwitterUser;
	}
	
	public String getTweetMessage(){		
	return TweetMessage;
	}
	
	public void setTweetMessage(String TweetMessage){
	this.TweetMessage = TweetMessage;
	}
	
	public long getRetweetCount(){		
	return RetweetCount;
	}
	
	public void setRetweetCount(long RetweetCount){
	this.RetweetCount = RetweetCount;
	}
	
	public long getFavouriteCount(){		
	return FavouriteCount;
	}
	
	public void setFavouriteCount(long FavouriteCount){
	this.FavouriteCount = FavouriteCount;
	}
	
	
}
