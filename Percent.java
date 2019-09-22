public class Percent
{
/*
5/11/2018
Keith O'Neal
CISC 230 Section 2
Class Description: This is a class designed to model percents. It can add, subtract, divide, and multiply
percents. Immutable.
________________________


+ Percent(in percent : String)
  ^Constructor for Percent objects. Accepts any correctly formed number as a parameter. Initializes digitsOfPrecision instance variable to
  equal the number of digits after the decimal point in the String

+ Percent(in percent : String, in digitsOfPrecision : int, in round : boolean)
  ^Constructor for Percent objects. Accepts any correctly formed number as a parameter. Can round to digitsOfPrecision

- Percent(in DecimalRationalNumber, int digitsOfPrecision : int)
  ^Private constructor used for implementing the operators

+ Percent(in numerator : long, in decimalDenominator : long, in digitsOfPrecision, in round : boolean)
  ^Constructor for DecimalRationalNumber objects. Requires the denominator to be a power of ten. Can round the input to maxDigitsOfPrecision to the
   right of the decimal point.


+ getAsString(): String
  ^Returns the instance variables combined into their decimal value as a String, always with only two places after the decimal point




UML:
				Percent

- Percent : DecimalRationalNumber
- int : digitsOfPrecision
------------------------
+ Percent(in percent : String)
+ Percent(in percent : String, in digitsOfPrecision : int, in round : boolean)
- Percent(in percent : DecimalRationalNumber, in digitsOfPrecision : int)
+ getDigitsOfPrecision() : int
+ getAsString(): String
- getPercent() : DecimalRationalNumber
+ plus(in addend : Percent) : Percent
+ minus(in subtrahend : Percent) : Percent
+ times(in multiplier : Percent) : Percent
+ dividedBy(in divisor : int, in digitsOfPrecision : int, in round : boolean) : Percent
+ toString() : String

*/


private DecimalRationalNumber percent;
private int digitsOfPrecision;

public Percent(String percent, int digitsOfPrecision, boolean round)
{
	DecimalRationalNumber result;
	int loc;
	long percentLong;
	long denominator;
	String errorCopy;

	if(percent == null){throw new IllegalArgumentException(getClass().getName() + ".Constructor(String, int, boolean): String parameter is null");}


	errorCopy = percent;

	loc = percent.indexOf('.');

	if(loc < 0)
	{
		percent = percent + ".";
		loc = percent.indexOf('.');
	}

	if(loc == 0)
	{
		percent = "0" + percent;
		loc = percent.indexOf('.');
	}

	denominator = 1;

	for(int i = 0; i < percent.length() - (loc + 1); i++){denominator = denominator * 10;}

	percent = percent.substring(0, loc) + percent.substring(loc + 1, percent.length());//5.353 --> 5353

	try{percentLong = Long.parseLong(percent);}
	catch(NumberFormatException e){throw new IllegalArgumentException(getClass().getName() + ".constructor(String, boolean):" + errorCopy + " is not a valid percent input");}


	result = new DecimalRationalNumber(percentLong, denominator, digitsOfPrecision, round);

	this.percent = result;
	this.digitsOfPrecision = digitsOfPrecision;
}

public Percent(String percent)
{
	int loc;
	int digitsOfPrecision;

	if(percent == null){throw new IllegalArgumentException(getClass().getName() + ".Constructor(String): String parameter is null");}


	loc = percent.indexOf('.');

	if(loc < 0)
	{
		digitsOfPrecision = 0;
		percent = percent + '.';
		loc = percent.indexOf('.');
	}

	while(percent.length() > 1 && percent.charAt(percent.length() - 1) == 0)
	{
		percent = percent.substring(0, percent.length() - 1);
	}


	digitsOfPrecision = percent.length() - (loc + 1);

	this.digitsOfPrecision = digitsOfPrecision;
	this.percent = new DecimalRationalNumber(percent);
}

private Percent(DecimalRationalNumber percent)
{
	String percentString;
	int loc;
	int digitsOfPrecision;

	percentString = percent.getAsString();
	loc = percentString.indexOf('.');

	if(loc < 0){digitsOfPrecision = 0;}
	else
	{
		digitsOfPrecision = percentString.length() - (loc + 1);
	}


	this.percent = percent;
	this.digitsOfPrecision = digitsOfPrecision;
}

public int getDigitsOfPrecision(){return this.digitsOfPrecision;}

private DecimalRationalNumber getPercent(){return this.percent;}

public Percent plus(Percent addend)
{
	if(addend == null){throw new IllegalArgumentException(getClass().getName() + ".plus(Percent): Percent parameter is null");}
	return new Percent(getPercent().plus(addend.getPercent()));
}

public Percent minus(Percent subtrahend)
{
	if(subtrahend == null){throw new IllegalArgumentException(getClass().getName() + ".minus(Percent): Percent parameter is null");}
	return new Percent(getPercent().minus(subtrahend.getPercent()));
}

public Percent times(Percent multiplier)
{

	if(multiplier == null){throw new IllegalArgumentException(getClass().getName() + ".times(Percent): Percent parameter is null");}

	return new Percent(getPercent().times(multiplier.getPercent()));
}


public Percent dividedBy(int divisor, int digitsOfPrecision, boolean round)
{
	DecimalRationalNumber divisorDecimalRationalNumber;

	if(divisor == 0){throw new IllegalArgumentException(getClass().getName() + ".dividedBy(Percent): Percent parameter cannot equal 0");}

	divisorDecimalRationalNumber = new DecimalRationalNumber(divisor);

	return new Percent(getPercent().dividedBy(divisorDecimalRationalNumber, digitsOfPrecision, round));
}

public String getAsString()
{
	String result;
	int loc;
	int digitsOfPrecision;


	digitsOfPrecision = getDigitsOfPrecision();
	result = this.percent.getAsString();

	loc = result.indexOf('.');

	if(loc < 0 && digitsOfPrecision > 0)
	{
		result = result + ".";

		for(int i = 0; i < digitsOfPrecision; i++)
		{
		result = result + "0";
		}

		loc = result.indexOf('.');
	}

	while(result.length() - (loc + 1) < digitsOfPrecision)//5.3 --> 5.30
	{
		result = result + "0";
	}

	return result;

}

public String toString(){return getAsString();}



} //class
