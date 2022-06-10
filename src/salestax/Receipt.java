package salestax;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.*;
import java.text.DecimalFormat;

public class Receipt {

	private ArrayList<Product> productsList = new ArrayList<Product>();
	private double total;
	private double totalTax;
	
	@SuppressWarnings("resource")
	public Receipt(String fileName){

		try {

            Scanner input = new Scanner(System.in);

            File file = new File(fileName);

            input = new Scanner(file);
            
            while (input.hasNextLine()) {
            	
            	String line = input.nextLine();
            	String[] words = line.split(" "); 
            	int quantity = Integer.parseInt(words[0]);
            	boolean isImported = line.contains("imported");
            	
            	String[] exemptedItems =  new String[]{"pills","medicine","book","chocolate","syrup"};            	
            	int exemptedItemIndex = containsItemFromArray(line,exemptedItems);
            	
            	String exemptedType = null;
            	
            	if(exemptedItemIndex != -1){
                	exemptedType = exemptedItems[exemptedItemIndex];
            	}
            	// checking if we have the price available in the line or not
            	int atIndex = line.lastIndexOf("at");
            	
            	if(atIndex == -1){
            		System.out.println("Not able to process data due to wrong format");
            	} else {
                	float price = Float.parseFloat((line.substring(atIndex + 2))); 
                    
                	String name = line.substring(1, atIndex);
                	
                    for(int i = 0;i<quantity;i++){
                    	Product newProduct = null;
                    	
                    	if(isImported){ // if the product is imported
                    		
                        	if(exemptedType != null){
                        		
                        		if(exemptedType == "book"){
                        			newProduct = new Product(name,price,ProductType.IMPORTED_BOOK);
                        		} else if(exemptedType == "pills"){
                        			newProduct = new Product(name,price,ProductType.IMPORTED_MEDICAL);
                        		} else if(exemptedType == "chocolate"){
                        			newProduct = new Product(name,price,ProductType.IMPORTED_FOOD);
                        		}

                        	} else {
                        		newProduct = new Product(name,price,ProductType.IMPORTED_OTHERS);
                        	}
                        	
                    	} else {
                    		// if the product is not imported
                        	if(exemptedType != null){
                        		//the product is domestic and is exempt of sales tax
                        		
                        		if(exemptedType == "book"){
                        			newProduct = new Product(name,price,ProductType.BOOK);
                        		} else if(exemptedType == "pills"){
                        			newProduct = new Product(name,price,ProductType.MEDICAL);
                        		} else if(exemptedType == "chocolate"){
                        			newProduct = new Product(name,price,ProductType.FOOD);
                        		} else if(exemptedType == "medicine"){
                        			newProduct = new Product(name,price,ProductType.MEDICAL);
                        		} else if(exemptedType == "syrup"){
                        			newProduct = new Product(name,price,ProductType.MEDICAL);
                        		}

                        	} else {
                        		newProduct = new Product(name,price,ProductType.OTHERS);
                        	}
                    	}
                    	
                        productsList.add(newProduct);
                    }
            	}
            	
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public void getTotalAmounts(){
		int numOfItems = productsList.size();
		
		BigDecimal currentSum = new BigDecimal("0");
		BigDecimal currentTaxSum = new BigDecimal("0");
		
		for(int i = 0;i<numOfItems;i++){
			
			currentTaxSum = BigDecimal.valueOf(0);
			
			BigDecimal totalBeforeTax = new BigDecimal(String.valueOf(this.productsList.get(i).getProductPrice()));
			
			currentSum = currentSum.add(totalBeforeTax);
			
			if(productsList.get(i).isSalesTaxable()){
				//This item is sales taxable so charge 10% tax and round to the nearest 0.05
			    BigDecimal salesTaxPercent = new BigDecimal(".10");
			    BigDecimal salesTax = salesTaxPercent.multiply(totalBeforeTax);
			    
			    salesTax = round(salesTax, BigDecimal.valueOf(0.05), RoundingMode.UP);
			    currentTaxSum = currentTaxSum.add(salesTax);			       
			} 
			
			if(productsList.get(i).isImportedTaxable()){
				//this item is import taxable so charge 5% tax and round to the nearest 0.05

			    BigDecimal importTaxPercent = new BigDecimal(".05");
			    BigDecimal importTax = importTaxPercent.multiply(totalBeforeTax);
			    
			    importTax = round(importTax, BigDecimal.valueOf(0.05), RoundingMode.UP);
			    currentTaxSum = currentTaxSum.add(importTax);
			}

			productsList.get(i).setProductPrice(currentTaxSum.floatValue() + productsList.get(i).getProductPrice());

			totalTax += currentTaxSum.doubleValue();
			currentSum = currentSum.add(currentTaxSum);
		}
			totalTax = roundTwoDecimals(totalTax);
			total = currentSum.doubleValue();
	}
	
	public void setTotal(BigDecimal amount){
		total = amount.doubleValue();
	}
	
	public double getTotal(){
		return total;
	}
	public void setSalestotalTax(BigDecimal amount){
		totalTax = amount.doubleValue();
	}
	
	public double getSalestotalTax(){
		return totalTax;
	}
	
	public static int containsItemFromArray(String inputString, String[] items) {
		int index = -1;
		
		for(int i = 0;i<items.length;i++){
			index = inputString.indexOf(items[i]);

			if(index != -1)
				return i;
		}
		return -1;
		
	}
	
	public static BigDecimal round(BigDecimal value, BigDecimal increment,RoundingMode roundingMode) {
		
		if (increment.signum() == 0) {
		return value;
		} else {
			BigDecimal divided = value.divide(increment, 0, roundingMode);
			BigDecimal result = divided.multiply(increment);
			result.setScale(2, RoundingMode.UNNECESSARY);
			return result;
		}
	}
	
	public double roundTwoDecimals(double d) {
	    DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDecimalForm.format(d));
	}
	
	public void printReceipt(){
		int numOfItems = productsList.size();
		for(int i = 0;i<numOfItems;i++){
			System.out.println("1" + productsList.get(i).getProductName() + "at " + productsList.get(i).getProductPrice());
		}
		System.out.printf("Sales Taxes: %.2f\n", totalTax);
		System.out.println("Total: " + total);
	}
}