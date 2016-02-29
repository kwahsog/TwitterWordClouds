package exampleCode;

import twitterdata.TwitterPosts;

/** Example class to create word cloud locally.
 * 
 * @author Alex
 *
 */

public class ExampleWordClouds {
	
	public static void main(String[] args){
		
		TwitterPosts NewSearch = new TwitterPosts("#java");
		NewSearch.beginSearch(500);
		//create sample files locally.
		NewSearch.writetoText("c://testwrite.txt");
		NewSearch.createWordCloud("c://twitterwordcloud.png");
		
	}
	

}
