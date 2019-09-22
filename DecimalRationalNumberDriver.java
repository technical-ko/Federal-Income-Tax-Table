
public class DecimalRationalNumberDriver
{
 public static void main( String[] args )
 {
	FederalIncomeTaxTable2018Single thing;

	thing = FederalIncomeTaxTable2018Single.newInstance();

	System.out.println(thing.calculateTax(new Money("45250")));


 }//main


} //class