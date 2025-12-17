/**
 * 
 */
package com.rg.practice.systemdesign;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gorle
 */
public class MessagingSystemDemo {

	public static void main(String args[]) {
		MessageService messageService = new MessageService();

		User alice = new User(1, "Alice");
		User bob = new User(2, "Bob");
		User carol = new User(3, "Carol");

		messageService.addUser(alice);
		messageService.addUser(bob);
		messageService.addUser(carol);

		messageService.sendMessage(1, 2, "Hi Bob! How are you?");
		messageService.sendMessage(2, 1, "I'm good, Alice. Thanks!");
		messageService.sendMessage(3, 1, "Hello Alice!");

		alice.printInbox();
		bob.printInbox();
		carol.printInbox();
	}
}

class MessageService {
	private final Map<Integer, User> users = new HashMap<>();

	// Add a user to the system
	public void addUser(User user) {
		users.put((int) user.getId(), user);
	}

	// Send a message from one user to another
	public void sendMessage(int fromUserId, int toUserId, String content) {
		User fromUser = users.get(fromUserId);
		User toUser = users.get(toUserId);

		if (fromUser == null || toUser == null) {
			System.out.println("Invalid user ID(s) for sending message.");
			return;
		}

		Message message = new Message(fromUserId, toUserId, content);
		toUser.receiveMessage(message);
		System.out.println(fromUser.getName() + " sent a message to " + toUser.getName() + ": " + content);
	}
}

class Message {
	private final int fromUserId;
	private final int toUserId;
	private final String content;
	private final long timestamp;

	public Message(int fromUserId, int toUserId, String content) {
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.content = content;
		this.timestamp = System.currentTimeMillis();
	}

	public int getFromUserId() {
		return fromUserId;
	}

	public int getToUserId() {
		return toUserId;
	}

	public String getContent() {
		return content;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "From: " + fromUserId + ", To: " + toUserId + ", Time: " + timestamp + ", Message: " + content;
	}
}