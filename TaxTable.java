import java.util.*;
abstract public class TaxTable
{
/*
5/11/2018
Keith O'Neal
CISC 230 Section 2
Class Description: Abstract class designed to implement the calculation of taxes for its Singleton subclass. Uses Money and Percent objects. Contains two inner classes:
TaxBracket <-- static public
TaxTableRow <-- private

TaxBracket objects contain the tax bracket information, which the TaxTableRow objects use to calculate the tax on a given amount. The calculateTaxes
method uses an array of rows and sums the results;
________________________


+ TaxTable(in TaxTableRowArray : TaxTableRow[])
  ^Constructor for TaxTable objects

+ calculateTax(in amount : Money) : Money
  ^calculates and returns as a Money object the taxes on the amount passed as an actual parameter

UML:
				TaxTable
- TaxTableRowArray : TaxTableRow[]
------------------------
+ TaxTable(in taxBracketArray : TaxBracket[])
+ getTaxTableRowArray() : TaxTableRow[]
+ calculateTax(in amount : Money) : Money
*/



private TaxTableRow[] taxTableRowArray;


public TaxTable(TaxBracket[] taxBracketArray)
{
	TaxBracket[] taxBracketArrayCopy;
	TaxTableRow[] taxTableRowArray;

	if(taxBracketArray == null){throw new IllegalArgumentException(getClass().getName() + ".constructor(TaxBracket[]): TaxBracket[] parameter is null");}

	for(int i = 0; i < taxBracketArray.length; i++){if(taxBracketArray[i] == null){throw new IllegalArgumentException(getClass().getName() + ".constructor(TaxBracket[]): TaxBracket[" + i + "] is null");}}

	taxBracketArrayCopy = taxBracketArray.clone();

	Arrays.sort(taxBracketArrayCopy);

	taxTableRowArray = new TaxTableRow[taxBracketArray.length];

	if(taxBracketArray.length == 0){taxTableRowArray = new TaxTableRow[0];}
	else
	{
		taxTableRowArray[taxTableRowArray.length - 1] = new TaxTableRow(taxBracketArray[taxBracketArray.length - 1].getMinimum(), new Money("1000000000"), taxBracketArray[taxBracketArray.length - 1].getTaxRate());

		for(int i = taxBracketArray.length - 2; i >= 0; i--)
		{
			taxTableRowArray[i] = new TaxTableRow(taxBracketArray[i].getMinimum(), taxBracketArray[i+1].getMinimum(), taxBracketArray[i].getTaxRate());
		}

	}//else



	this.taxTableRowArray = taxTableRowArray;
}

public TaxTableRow[] getTaxTableRowArray(){return this.taxTableRowArray;}



public Money calculateTax(Money amount)
{
	Money totalTax;
	TaxTableRow[] taxTableRowArray;

	if(amount == null){throw new IllegalArgumentException(getClass().getName() + ".calculateTax(Money): Money parameter is null");}

	totalTax = new Money();
	taxTableRowArray = getTaxTableRowArray();

	for(int i = 0; i < taxTableRowArray.length; i++)
	{
		if(taxTableRowArray[i] == null){System.out.println(i);}
			totalTax = totalTax.plus(taxTableRowArray[i].calculateTax(amount));
	}

	return totalTax;
}

public static class TaxBracket implements Comparable<TaxBracket>
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

public int compareTo(TaxBracket other){return getMinimum().compareTo(other.getMinimum());}


} //class TaxBracket


private class TaxTableRow
{
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

public Money calculateTax(Money amount)
{
	Money result;

	if(amount == null){throw new IllegalArgumentException(getClass().getName() + ".calculateTax(Money): Money parameter is null");}


	if(amount.isGreaterThan(getMaximum())){result = getMaximum().minus(getMinimum()).times(getTaxRate());}
	else
	{
		result = amount.minus(getMinimum()).times(getTaxRate());
	}

	if(result.isLessThanZero()){result = new Money(DecimalRationalNumber.Constant.Zero.get().getAsString());}

	return result;
}


} //class





} //class
