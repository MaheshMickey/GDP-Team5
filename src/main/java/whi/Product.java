/**
 * 
 */
package whi;

/**
 * @author S546551
 *
 */
public class Product {
	
		  private String productId;
		  private String productName;
		  private String cost;
		  private String customerName;
		  
		  public Product() {
			super();
		}

		public Product(String productId, String productName, String cost, String customerName) {
		    this.productId = productId;
		    this.productName = productName;
		    this.cost = cost;
		    this.customerName = customerName;
		  }

		  public String getProductId() {
		    return productId;
		  }

		  public void setProductId(String productId) {
		    this.productId = productId;
		  }

		  public String getProductName() {
		    return productName;
		  }

		  public void setProductName(String productName) {
		    this.productName = productName;
		  }

		  public String getCost() {
		    return cost;
		  }

		  public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public void setCost(String cost) {
		    this.cost = cost;
		  }
}
