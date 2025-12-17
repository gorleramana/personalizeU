/**
 * Social media system
 */
package com.rg.practice.systemdesign;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SocialMediaPlatformDemo {

	private static Random ran = new Random();

	public static void main(String args[]) {
		List<User> users = new ArrayList<>();

		Long userId = ran.nextLong();
		User user1 = new User(userId, "ramana");
		Post first = new Post(ran.nextLong(), userId, "my first post");
		user1.addPosts(first);
		
		User user2 = new User(userId, "haneesh");
		user1.addFollowers(user2);
		user1.notifyFollowers(first);
		users.add(user1);
	}
}

class User {
	private long id;
	private String name;
	private List<User> followers;
	private List<User> followees;
	private List<Post> posts;

	//messaging system props
	private final List<Message> inbox;
	
	public User(long id, String name) {
		this.id = id;
		this.name = name;

		followers = new ArrayList<>();
		followees = new ArrayList<>();
		posts = new ArrayList<>();
		inbox = new ArrayList<>();
	}

	public void addFollowers(User follower) {
		System.out.println(follower.name + " is following "+this.name);
		this.followers.add(follower);
	}

	public void removeFollower(User follower) {
		System.out.println(follower.name + " has unfollowed "+this.name);
		this.followers.remove(follower);
	}

	public void addPosts(Post post) {
		System.out.println(this.name + " posted: " + post.getContent());
		this.posts.add(post);
	}

	public void notifyFollowers(Post post) {
		for (User user : followers) {
			System.out.println(
					user.getName() + " received a notifcation: " + "\"" +this.name + " posted: " + post.getContent()+"\"");
		}
	}

	public void addFollowee(User followee) {
		System.out.println(this.name + " is following "+ followee.name);
		this.followees.add(followee);
	}

	public void removeFollowee(User followee) {
		System.out.println(this.name + " has unfollowed "+ followee.name);
		this.followees.remove(followee);
	}

	public void deletePost(Post post) {
		System.out.println(this.name + " has deleted "+ post.getContent());
		this.posts.remove(post);
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the followers
	 */
	public List<User> getFollowers() {
		return followers;
	}

	/**
	 * @param followers the followers to set
	 */
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	/**
	 * @return the followees
	 */
	public List<User> getFollowees() {
		return followees;
	}

	/**
	 * @param followees the followees to set
	 */
	public void setFollowees(List<User> followees) {
		this.followees = followees;
	}

	/**
	 * @return the posts
	 */
	public List<Post> getPosts() {
		return posts;
	}

	/**
	 * @param posts the posts to set
	 */
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	/**
	 * @return the inbox
	 */
	public List<Message> getInbox() {
		return inbox;
	}
	
	/**
	 * Messaging demo methods
	 * @param message
	 */
	
	// User receives a message
    public void receiveMessage(Message message) {
        inbox.add(message);
    }

    // View all messages received by this user
    public void printInbox() {
        System.out.println("Inbox for " + name + ":");
        for (Message msg : inbox) {
            System.out.println(msg);
        }
        if (inbox.isEmpty()) {
            System.out.println("No messages yet.");
        }
        System.out.println();
    }
}

class Post {
	private Long id;
	private Long userId;// foreign key
	private String content;
	private Integer likes;
	private List<String> comments;

	public Post(Long id, Long userid, String content) {
		this.id = id;
		this.userId = userid;
		this.content = content;
		this.likes = 0;
		this.comments = new ArrayList<>();
	}

	public void addComments(String comment) {
		this.getComments().add(comment);
	}

	public void addLikes(int like) {
		this.likes = this.likes + 1;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the likes
	 */
	public Integer getLikes() {
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	/**
	 * @return the comments
	 */
	public List<String> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
}
