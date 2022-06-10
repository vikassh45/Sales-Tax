package test;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

import salestax.Receipt;

public class SalesTaxTest {
	
	Receipt r1=new Receipt("src/testdata/test5");
	
	@Test
    public void testImportedBookTax() throws FileNotFoundException {
		r1.getTotalAmounts();
		Assert.assertEquals(0.5,r1.getSalestotalTax(),0);
    }
	
	Receipt r2=new Receipt("src/testdata/test6");
	@Test
    public void testPillTax() throws FileNotFoundException {
		r2.getTotalAmounts();
		Assert.assertEquals(0,r2.getSalestotalTax(),0);
    }
	
	Receipt r3=new Receipt("src/testdata/test7");
	@Test
    public void testDomesticProductTax() {
		r3.getTotalAmounts();
		Assert.assertEquals(50,r3.getSalestotalTax(),0);
    }
	
	Receipt r4=new Receipt("src/testdata/test1");
	@Test
    public void testTotalAmount() {
		r4.getTotalAmounts();
		Assert.assertEquals(29.83,r4.getTotal(),0);
    }
}
