package models;

public class ChatMessage {

	private int messageId;
	private String message;
	private int senderId;
	private String chatRoomId;
	private String timeStamp;
	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	
	public String getChatRoomId() {
		return chatRoomId;
	}
	public void setChatRoomId(String chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
