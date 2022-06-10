package salestax;

public class Product {
	private String productName;
	private float productPrice;
	private ProductType type;
	
	public Product(String productName, float productPrice, ProductType productType){
		this.productName = productName;
		this.setProductPrice(productPrice);
		this.type = productType;
		
	}
	
	public String toString(){
		return this.productName + this.getProductPrice();
	}

	public float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setName(String productName) {
		this.productName = productName;
	}
	
	public boolean isSalesTaxable() {
		return !this.type.isExempted();
	}

	public boolean isImportedTaxable() {
		return this.type.isImported();
	}

}
enum ProductType{
	BOOK(true,false), // these params are stating which item is tax exempted and imported
	MEDICAL(true,false),
	FOOD(true,false),
	OTHERS ( false , false),
	IMPORTED_BOOK(true,true),
	IMPORTED_MEDICAL(true,true),
	IMPORTED_FOOD(true,true),
	IMPORTED_OTHERS(false,true);
	private boolean isExempted;
	private boolean isImported;
	
	private ProductType(boolean exempted , boolean imported){
		isExempted = exempted;
		isImported = imported;
	}

	public boolean isImported(){
		return isImported;
	}
	public boolean isExempted(){
		return isExempted;
	}

}