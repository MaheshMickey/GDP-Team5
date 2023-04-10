package models;

public class Order {
	private int orderId;
	private int productId;
	private String productName;
	private int ownerId;
	private int quantity;
	private boolean approval;
	private String timestamp;
	private int workerId;
	private String clientName;
	private String clientAddress;
	private String ownerName;
	private String workerName;

	public Order() {
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean getApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getWorkerId() {
		return workerId;
	}

	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
