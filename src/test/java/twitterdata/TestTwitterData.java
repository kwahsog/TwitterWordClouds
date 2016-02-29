package twitterdata;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTwitterData {
	
	TwitterPosts TestSearch;
	
	@Before
	public void setUp() {
		TestSearch = new TwitterPosts("#java");
	}
	
		
	@After
	public void tearDown(){		
		TestSearch = null;		
	}
	
	/**
	 * 
	 * Test TwitterPosts method and get methods.
	 */
	@Test
	public void TestTwitterPostsCreation() {
		TwitterPosts TestSearchA = new TwitterPosts("#java");
		assertEquals("Test TwitterPosts object is created correctly", "#java", TestSearchA.getQuery());
		assertEquals("Test tweets object is empty", 0, TestSearchA.getPosts().size());
	}
	
	/**
	 * 
	 * Test setter method, setQuery.
	 */
	@Test
	public void TestTwitterPostsetQuery() {
		TestSearch.setQuery("#newtest");
		assertEquals("Test setQuery method", "#newtest", TestSearch.getQuery());
	}
	
	
	/**
	 * 
	 * Test beingSearch, check tweets arraylist is populated.
	 */
	@Test
	public void TestTwitterPostbeginSearch() {
		TestSearch.setQuery("#java");
		TestSearch.beginSearch(10);
		assertEquals("Test tweets ArrayList has same size", 10, TestSearch.getPosts().size());
		assertNotNull("Test first Status in ArrayList has correct properties", TestSearch.getPostsIndex(1).getId());
	}
	
	/**
	 * 
	 * Test emptyTweets, check tweets arraylist is emptied.
	 */
	@Test
	public void TestTwitterPostemptyTweets() {
		TestSearch.setQuery("#java");
		TestSearch.beginSearch(10);
		assertEquals("Test tweets ArrayList has same size", 10, TestSearch.getPosts().size());
		TestSearch.emptyTweets();
		assertEquals("Test tweets ArrayList has size 0", 0, TestSearch.getPosts().size());
	}
	
	/**
	 * 
	 * Test writetoText creates text file at given location.
	 */
	
	@Test
	public void TestTwitterPostwritetoText() {
		TestSearch.setQuery("#java");
		TestSearch.beginSearch(10);
		TestSearch.writetoText("c://testwrite.txt");
		File TestFile = new File("c://testwrite.txt");
		assertTrue("Test text file has been created", TestFile.exists());
	}
	
	/**
	 * 
	 * Test createWordCloud creates png file at given location.
	 */
	
	@Test
	public void TestTwitterPostcreateWordCloud() {
		TestSearch.setQuery("#java");
		TestSearch.beginSearch(10);
		TestSearch.writetoText("c://testwrite.txt");
		TestSearch.createWordCloud("c://twitterwordcloud.png");
		File TestFile = new File("c://twitterwordcloud.png");
		assertTrue("Test text file has been created", TestFile.exists());
	}
	
	
}
