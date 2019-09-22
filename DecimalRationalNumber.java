import java.util.*;
public class DecimalRationalNumber implements Comparable<DecimalRationalNumber>
{
/*
5/11/2018
Keith O'Neal
CISC 230 Section 2
Class Description: This is a class designed to model numbers that are rational. The getAsString method's outputs are in decimal form. It can add, multiply,
subtract, and divide. Contains a Constant enum with a get() method that will return a DecimalRationalNumber equivalent to the called for enum name.
Implements the Comparable interface. Immutable.
________________________


+ DecimalRationalNumber(in value : String)
  ^Constructor for DecimalRationalNumber objects. Accepts any correctly formed number as a parameter.

+ DecimalRationalNumber(in integer : Long)
  ^Constructor for DecimalRationalNumber objects. Accepts whole numbers as a parameter

+ DecimalRationalNumber(in numerator : long, in decimalDenominator : long)
  ^Constructor for DecimalRationalNumber objects. Requires the denominator to be a power of ten.

+ DecimalRationalNumber(in numerator : long, in decimalDenominator : long, in digitsOfPrecision, in round : boolean)
  ^Constructor for DecimalRationalNumber objects. Requires the denominator to be a power of ten. Can round the input to maxDigitsOfPrecision to the
   right of the decimal point.


- greatestCommonDivisor(in x : long, in y : long)
  ^returns the largest number than can evenly divide into both the inputs

+ isPowerOfTen(in x : long) : boolean
  ^Returns true if the input is a power of ten


+ getAsString(): String
  ^Returns the instance variables combined into their decimal value as a String

- getPowerOfTenOfDenominator() : int
  ^Returns the power of ten of the denominator




UML:
				DecimalRationalNumber
- numerator : long
- denominator : long
------------------------
+ DecimalRationalNumber(in value : String)
+ DecimalRationalNumber(in integer : long)
+ DecimalRationalNumber(in numerator : long, in decimalDenominator : long)
+ DecimalRationalNumber(in numerator : long, in decimalDenominator : long, in round : boolean)
- greatestCommonDivisor(in x : long, in y : long)
+ isPowerOfTen(in x : long) : boolean
+ getAsString(): String
- getNumerator() : long
- getDenominator(): long
- getPowerOfTenOfDenominator() : int
+ changeSign(): DecimalRationalNumber
+ plus(in addend : DecimalRationalNumber) : DecimalRationalNumber
+ minus(in subtrahend : DecimalRationalNumber) : DecimalRationalNumber
+ times(in multiplier : DecimalRationalNumber) : DecimalRationalNumber
+ dividedBy(in divisor : DecimalRationalNumber, in digitsOfPrecision : int, in round : boolean) : DecimalRationalNumber
+ isLessThanZero() : boolean
+ isGreaterThanZero() : boolean
+ isZero() : boolean
+ toString() : String
+ equals(in other : Object) : boolean
+ hashCode() : int
+ changeDigitsOfPrecision(in digitsOfPrecision : int, in round : boolean) : DecimalRationalNumber
+ toString() : String


*/


public enum Constant
{
	Zero(new DecimalRationalNumber(0)),

	One(new DecimalRationalNumber(1)),

	Ten(new DecimalRationalNumber(10)),

	OneHundred(new DecimalRationalNumber(100)),

	OneThousand(new DecimalRationalNumber(1000)),

	TenThousand(new DecimalRationalNumber(10000));

	private DecimalRationalNumber value;

	private Constant(DecimalRationalNumber value){this.value = value;}

	public final DecimalRationalNumber get(){return this.value;}

}//enum Constant


private long denominator;
private long numerator;



public DecimalRationalNumber(String value)
{
	int loc;
	String left;
	String right;
	long longLeft;
	long longRight;
	long denominator;
	long numerator;
	long number;
	long multiplier;

	if(value == null){throw new IllegalArgumentException(getClass().getName() + ".Constructor(): String parameter is null");}

	value = value.trim();

	loc = value.indexOf(".");

	if(loc < 0)//if input has no decimal point
	{
		denominator = 1;
		try{numerator = Long.parseLong(value);}
		catch(NumberFormatException e){throw new IllegalArgumentException(getClass().getName() + " " + value + " is not a valid number");}
	}
	else
	{//input has decimal point
		left = value.substring(0, loc);

		//check to prevent out of bounds
		if(loc == value.length() - 1){right = "0";}
		else
		{
			right = value.substring(loc + 1, value.length());
		}

		//check to prevent passing empty string to Long.parseLong()
		if(left.equals("")){left = "0";}
		if(left.equals("-")){left = "-0";}

		//check to ensure input is properly formed number
		try{number = Long.parseLong(left + right);}
		catch(NumberFormatException e){throw new IllegalArgumentException(getClass().getName() + " " + value + "is not a valid number");}

		//remove zeroes from left  by converting to long and right w/loop:
		longLeft = Long.parseLong(left);

		while(right.charAt(right.length()-1) == '0' && right.length() > 1)
		{
			right = right.substring(0, right.length() - 1);
		}

		multiplier = 1;

		//check to ensure that XX.00 inputs are constructed as X/1 and not (X*10)/10
		if(!right.equals("0"))
		{
			for(int i = 0; i < right.length(); i++)
			{
				multiplier = multiplier * 10;
			}
		}

		longRight = Long.parseLong(right);

		//multiply left of decimal point by 10 to the power of the number of digits in right
		longLeft = longLeft * multiplier;

		//create numerator while preserving negative sign
		if(left.charAt(0) == '-'){numerator = -(Math.abs(longLeft) + longRight);}
		else{numerator = longLeft + longRight;}

		denominator = multiplier;
	}


	this.numerator = numerator;
	this.denominator = denominator;
}

public DecimalRationalNumber(long integer){this(integer, 1, 0, false);}

public DecimalRationalNumber(long numerator, long decimalDenominator)
{
	if(!isPowerOfTen(decimalDenominator)){throw new IllegalArgumentException(getClass().getName() + " long parameter decimalDenominator is not multiple of ten");}
	if(decimalDenominator == 0){throw new IllegalArgumentException(getClass().getName() + ".Constructor(): Denominator parameter equals 0");}
	if(decimalDenominator < 0){numerator = -numerator;}
	decimalDenominator = Math.abs(decimalDenominator);


	while(numerator % 10 == 0 && decimalDenominator > 1)//reduction
	{
		numerator = numerator / 10;
		decimalDenominator = decimalDenominator / 10;
	}

	this.numerator = numerator;
	this.denominator = decimalDenominator;
}

public DecimalRationalNumber(long numerator, long denominator, long maxDigitsOfPrecision, boolean round)
{
	long newDenominator;
	long oldNumerator;
	boolean isNegative;

	if(denominator == 0){throw new IllegalArgumentException(getClass().getName() + ".Constructor(): Denominator parameter equals 0");}
	if(denominator < 0){numerator = -numerator;}

	denominator = Math.abs(denominator);
	if(Math.abs(numerator) != numerator){isNegative = true;}
	else{isNegative = false;}

	if(numerator != 0)
	{
		newDenominator = 1;

		for(int i = 0; i < maxDigitsOfPrecision; i++)//prep for rounding
		{
			numerator = numerator * 10;
			newDenominator = newDenominator * 10;
		}


		if(!round){numerator = numerator / denominator;}
		else
		{
			if(isNegative){numerator = (((numerator * 10) / denominator) - 5) / 10;}//rounding
			else{numerator = (((numerator * 10) / denominator) + 5) / 10;}
		}


		while(numerator % 10 == 0 && newDenominator > 1)//reduction
		{
			numerator = numerator / 10;
			newDenominator = newDenominator / 10;
		}


	}//	if(numerator != 0)
	else
	{
		newDenominator = 1;
	}

		this.numerator = numerator;
		this.denominator = newDenominator;
}

public DecimalRationalNumber changeSign(){return new DecimalRationalNumber(getNumerator()*(-1), getDenominator());}

public String getAsString()
{
	String result;
	String numeratorString;
	boolean hit;

	numeratorString = "" + getNumerator();
	result = numeratorString.substring(0, numeratorString.length() - getPowerOfTenOfDenominator()) + "." + numeratorString.substring(numeratorString.length() - getPowerOfTenOfDenominator(), numeratorString.length());
	hit = true;

	if(result.charAt(0) == '.')
	{
		result = "0" + result;
	}

	while(result.length() > 1 && hit && result.charAt(result.length() - 1) == '0' || result.charAt(result.length() - 1) == '.')
	{
		if(result.charAt(result.length() - 1) == '.'){hit = false;}
		result = result.substring(0, result.length() -1);
	}



	return result;
}

private long getNumerator(){return this.numerator;}

private long getDenominator(){return this.denominator;}

private long greatestCommonDivisor(long numerator, long denominator)//switch void to long
{
	long result;
	long count;
	long multiplier;
	int i;

	result = 1;

	if(numerator % 10 == 0)
	{
		count = 0;
		while(numerator > 0)
		{
		numerator = numerator / 10;
		count = count + 1;
		}

		i = 1;

		while(i < count)
		{
		result = result * 10;
		i = i + 1;
		}

		result = Math.min(result, denominator);
	}

	return result;
}


public static boolean isPowerOfTen(long x)
{
	boolean result;

	result = true;
	x = Math.abs(x);

	if(x != 1)
	{
		if(x % 10 == 0)
		{
 			while(x > 10)
			{
				x = x / 10;
			}
		}
		result = x % 10 == 0;
	}

	return result;
}


private void makeDivisorPowerOfTen(long numerator, long denominator, int digitsOfPrecision, boolean round)//for reference, delete for final build
{
	long newDenominator;
	int powerOfTen;

	newDenominator = 1;

	for(int i = 0; i < digitsOfPrecision; i++)
	{
		numerator = numerator * 10;
		newDenominator = newDenominator * 10;
	}


	numerator = numerator / denominator;
}



public boolean isZero(){return getNumerator() == 0;}

public boolean isLessThanZero(){return getNumerator() < 0;}

public boolean isGreaterThanZero(){return getNumerator() > 0;}


public DecimalRationalNumber times(DecimalRationalNumber multiplier)
{
	if(multiplier == null){throw new IllegalArgumentException(getClass().getName() + ".times(DecimalRationalNumber): DecimalRationalNumber parameter is null");}

	return new DecimalRationalNumber(multiplier.getNumerator() * getNumerator(), multiplier.getDenominator() * getDenominator());
}


public DecimalRationalNumber minus(DecimalRationalNumber subtrahend)
{
	DecimalRationalNumber result;
	long commonDenominator;
	long numerator;

	if(subtrahend == null){throw new IllegalArgumentException(getClass().getName() + ".minus(DecimalRationalNumber): DecimalRationalNumber parameter is null");}

	if(subtrahend.getDenominator() == getDenominator()){result = new DecimalRationalNumber(getNumerator() - subtrahend.getNumerator(), getDenominator());}
	else
	{
		commonDenominator = subtrahend.getDenominator() * getDenominator();
		numerator = (getNumerator() * subtrahend.getDenominator()) - (subtrahend.getNumerator() * getDenominator());
		result = new DecimalRationalNumber(numerator, commonDenominator);
	}

	return result;
}

public DecimalRationalNumber plus(DecimalRationalNumber addend)
{
	DecimalRationalNumber result;
	long commonDenominator;
	long numerator;

	if(addend == null){throw new IllegalArgumentException(getClass().getName() + ".plus(DecimalRationalNumber): DecimalRationalNumber parameter is null");}

	if(addend.getDenominator() == getDenominator()){result = new DecimalRationalNumber(addend.getNumerator() + getNumerator(), getDenominator());}
	else
	{
		commonDenominator = addend.getDenominator() * getDenominator();
		numerator = ( addend.getNumerator() * getDenominator() ) + ( getNumerator() * addend.getDenominator() );
		result = new DecimalRationalNumber(numerator, commonDenominator);
	}

	return result;
}

public DecimalRationalNumber dividedBy(DecimalRationalNumber divisor, int digitsOfPrecision, boolean round)
{
	long numerator;
	long denominator;
	long hold;
	long divisorNumerator;
	long divisorDenominator;

	if(divisor == null){throw new IllegalArgumentException(getClass().getName() + ".divisor(DecimalRationalNumber, int, boolean): DecimalRationalNumber parameter is null");}

	if(divisor.isZero()){throw new IllegalArgumentException(getClass().getName() + ".dividedBy(): DecimalRationalNumber divisor parameter cannot equal 0");}

	numerator = getNumerator();
	denominator = getDenominator();
	divisorNumerator = divisor.getNumerator();
	divisorDenominator = divisor.getDenominator();

	for(int i = 0; i < digitsOfPrecision; i++)
	{
		numerator = numerator * 10;
		denominator = denominator * 10;
	}

	if(round){numerator = (((numerator * 10) / divisorNumerator) + 5) / 10;}
	else{numerator = numerator / divisorNumerator;}

	denominator = denominator / divisorDenominator;


	return new DecimalRationalNumber(numerator, denominator, digitsOfPrecision, true);
}


private int getPowerOfTenOfDenominator()
{
	long denominator;
	int count;

	denominator = getDenominator();
	count = -1;

	while(denominator != 0)
	{
		denominator = denominator / 10;
		count = count + 1;
	}

	return count;
}


@Override
public boolean equals(Object other)
{
	DecimalRationalNumber hold;
	long otherDenominator;
	long thisDenominator;
	long otherNumerator;
	long thisNumerator;

	hold = (DecimalRationalNumber) other;
	otherDenominator = hold.getDenominator();
	thisDenominator = getDenominator();
	otherNumerator = hold.getNumerator();
	thisNumerator = getNumerator();

	return thisNumerator == otherNumerator && thisDenominator == otherDenominator;
}


@Override
public int hashCode()
{
	return Objects.hash(getNumerator(), getDenominator());
}


@Override
public int compareTo(DecimalRationalNumber other){return (int)minus(other).getNumerator();}



public DecimalRationalNumber changePrecision(int digitsOfPrecision, boolean round)
{
	denominator = getDenominator();
	numerator = getNumerator();

	for(int i = 0; i < digitsOfPrecision - getPowerOfTenOfDenominator(); i++)
	{
		numerator = numerator * 10;
		denominator = denominator * 10;
	}

	return new DecimalRationalNumber(numerator, denominator, digitsOfPrecision, round);
}


public String toString(){return getAsString();}

} //class
