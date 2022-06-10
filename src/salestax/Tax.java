package salestax;

public class Tax {

	public static void main(String[] args) {
		
		Receipt receipt1 = new Receipt("src/testdata/test1");	
		
		receipt1.getTotalAmounts();
		
		System.out.println("Output 1");
		receipt1.printReceipt();
		System.out.println();

		Receipt receipt2 = new Receipt("src/testdata/test2");

		receipt2.getTotalAmounts();
		
		System.out.println("Output 2");
		receipt2.printReceipt();
		System.out.println();
		
		Receipt receipt3 = new Receipt("src/testdata/test3");
		
		receipt3.getTotalAmounts();
		
		System.out.println("Output 3");
		receipt3.printReceipt();
		System.out.println();
		
		Receipt receipt4 = new Receipt("src/testdata/test4");
		
		receipt4.getTotalAmounts();
		
		System.out.println("Output 4");
		receipt4.printReceipt();
	}

}