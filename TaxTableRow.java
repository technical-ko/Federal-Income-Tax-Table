public class TaxTableRow
{
/*
3/19/2018
Keith O'Neal
CISC 230 Section 2
Class Description:
________________________


+ Alphabet(in alphabet : String)
  ^Constructor for Alphabet objects


UML:
				Alphabet
------------------------
+ Alphabet(in alphabet : String)

*/



private Money minimum;
private Money maximum;
private Percent taxRate;


public TaxTableRow(Money minimum, Money maximum, Percent taxRate)
{
	if(minimum == null){throw new IllegalArgumentException(getClass().getName() + ".TaxTableRow(Money, Money, Percent): Money parameter is null");}
	if(maximum == null){throw new IllegalArgumentException(getClass().getName() + ".TaxTableRow(Money, Money, Percent): Money parameter is null");}
	if(taxRate == null){throw new IllegalArgumentException(getClass().getName() + ".TaxTableRow(Money, Money, Percent): Percent parameter is null");}

	this.minimum = minimum;
	this.maximum = maximum;
	this.taxRate = taxRate;
}

public Money getMaximum(){return this.maximum;}

public Money getMinimum(){return this.minimum;}

public Percent getTaxRate(){return this.taxRate;}

public Money calculate(Money amount)
{


}


} //class
