package exampleCode;
import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;

import twitter4j.Status;
import twitterdata.TwitterPosts;

/** Example class to write to SQL db setup in hibernate.cfg.xml
 * 
 * @author Alex
 *
 */

public class ExampleWritetoSQL {
	
	public static void main(String[] args){
		//BasicConfigurator.configure();
		//search for 100 results then write to SQL db.
		TwitterPosts NewSearch = new TwitterPosts("#java");
		NewSearch.beginSearch(100);
		NewSearch.writeDataSQL();
		
	}

}
