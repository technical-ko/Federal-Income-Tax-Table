public static class TaxBracket
{


private Money minimum;
private Percent taxRate;

public TaxBracket(String minimum, String taxRate)
{
	this.minimum = new Money(minimum);
	this.taxRate = new Percent(taxRate);
}


public Money getMinimum(){return this.minimum;}
public Percent getTaxRate(){return this.taxRate;}

public int compareTo(TaxBracket other){}


} //class
