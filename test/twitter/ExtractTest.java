/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.List;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
   
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    @Test
    public void testGetTimespanSingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start and end to be the same", d1, timespan.getStart());
        assertEquals("expected start and end to be the same", d1, timespan.getEnd());
    }

    @Test
    public void testGetTimespanSameTimestamp() {
        Tweet tweet3 = new Tweet(3, "user", "identical timestamp", d1);
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet3));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    @Test
    public void testGetTimespanEarlierTimestamp() {

        Instant earlierTimestamp = Instant.parse("2015-01-01T09:00:00Z"); 
        Tweet tweetEarlier = new Tweet(3, "user", "Earlier tweet", earlierTimestamp);

  
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweetEarlier));


        assertEquals("expected start to be the earlier timestamp", earlierTimestamp, timespan.getStart());
        assertEquals("expected end to be the later timestamp", d1, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersOneMention() {
        Tweet tweetWithMention = new Tweet(3, "user", "Hello @alyssa", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMention));
        
        assertTrue("expected set to contain alyssa", mentionedUsers.contains("alyssa"));
    }

    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Tweet tweetWithMentions = new Tweet(3, "user", "Hello @alyssa , @bbitdiddle and @charlie !", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMentions));
        assertTrue("expected set to contain alyssa", mentionedUsers.contains("alyssa"));
        assertTrue("expected set to contain bbitdiddle", mentionedUsers.contains("bbitdiddle"));
        assertTrue("expected set to contain charlie", mentionedUsers.contains("charlie"));
    }

    @Test
    public void testGetMentionedUsersCaseInsensitive() {
        Tweet tweetWithCaseSensitiveMention = new Tweet(3, "user", "Hello @ALYSSA", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithCaseSensitiveMention));
        
        assertTrue("expected set to contain alyssa in lowercase", mentionedUsers.contains("alyssa"));
    }

    @Test
    public void testGetMentionedUsersNoMentionSpecialChars() {
        Tweet tweetWithNoMention = new Tweet(3, "user", "rivest@mit.com", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithNoMention));
        
        assertTrue("expected empty set for no valid mentions", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testMultipleTweetsMultipleMentions() {
        Tweet tweetWithMentions = new Tweet(3, "user", "Hello @alyssa , @bbitdiddle and @charlie !", d1);
        Tweet tweetWithMentions2 = new Tweet(3, "user", "Hello @barty , @bbitdiddle and @charlie !", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMentions,tweetWithMentions2));
        System. out. println(mentionedUsers);
        assertTrue("expected set to contain alyssa", mentionedUsers.contains("alyssa"));
        assertTrue("expected set to contain bbitdiddle", mentionedUsers.contains("bbitdiddle"));
        assertTrue("expected set to contain charlie", mentionedUsers.contains("charlie"));
        assertTrue("expected set to contain bart", mentionedUsers.contains("barty"));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
