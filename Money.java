public class Money implements Comparable<Money>
{
/*
5/11/2018
Keith O'Neal
CISC 230 Section 2
Class Description: This is a class designed to model money. It can add, subtract, divide, money by itself and multiply money by an integer.
Always rounds values to two places after the decimal place. Implements the comparable interface. Immutable.
________________________


+ Money()
  ^Constructor for Money objects. initializes the instance variable as a DecimalRationalNumber that equals 0.

+ Money(in money : String)
  ^Constructor for Money objects. Accepts correctly formed numbers as a String parameter. Rounds to 2 decimal places.

+ Money(in numerator : long, in decimalDenominator : long)
  ^Constructor for Money objects. Requires the denominator to be a power of ten. Rounds to 2 decimal places.

- Money(in money : DecimalRationalNumber)
  ^Private constructor used for implementing the operators

+ getAsString(): String
  ^Returns the instance variables combined into their decimal value as a String, always with only two places after the decimal point


UML:
				Money
- money : DecimalRationalNumber
------------------------
+ Money(in money : String)
+ Money(in integer : long)
+ Money(in numerator : long, in decimalDenominator : long)
- Money(in money : DecimalRationalNumber)
+ getAsString(): String
- getMoney() : DecimalRationalNumber
+ plus(in addend : Money) : Money
+ minus(in subtrahend : Money) : Money
+ times(in multiplier : int) : Money
+ dividedBy(in divisor : Money) : Percent
+ toString() : String

*/



private DecimalRationalNumber money;


public Money(){this.money = DecimalRationalNumber.Constant.Zero.get();}

public Money(String money)
{
	this(money, false);

	if(money == null){throw new IllegalArgumentException(getClass().getName() + ".Constructor(String, boolean): String parameter is null");}

}

public Money(String money, boolean round)
{
	DecimalRationalNumber result;
	int loc;
	long moneyLong;
	String errorCopy;

	if(money == null){throw new IllegalArgumentException(getClass().getName() + ".Constructor(String, boolean): String parameter is null");}

	errorCopy = money;

	loc = money.indexOf('.');

	if(loc < 0)
	{
		money = money + ".000";
		loc = money.indexOf('.');
	}

	if(loc == 0)
	{
		money = "0" + money;
		loc = money.indexOf('.');
	}

		while(money.length() - (loc + 1) > 3)//5.3535 --> 5.353
		{
			money = money.substring(0, money.length() - 1);
		}

		while(money.length() - (loc + 1) < 3)//5.35 --> 5.350
		{
			money = money + "0";
		}

		money = money.substring(0, loc) + money.substring(loc + 1, money.length());//5.353 --> 5353


	try{moneyLong = Long.parseLong(money);}
	catch(NumberFormatException e){throw new IllegalArgumentException(getClass().getName() + ".constructor(String, boolean):" + errorCopy + " is not a valid money input");}

	result = new DecimalRationalNumber(moneyLong, 1000, 2, round);

	this.money = result;
}



private Money(DecimalRationalNumber money)
{
	money.changePrecision(2, true);
	this.money = money;
}


private DecimalRationalNumber getMoney(){return this.money;}

public Money plus(Money addend)
{
	if(addend == null){throw new IllegalArgumentException(getClass().getName() + ".plus(Money): Money parameter is null");}

	return new Money(getMoney().plus(addend.getMoney()));
}

public Money minus(Money subtrahend)
{
	if(subtrahend == null){throw new IllegalArgumentException(getClass().getName() + ".minus(Money): Money parameter is null");}

	return new Money(getMoney().minus(subtrahend.getMoney()));
}

public Percent dividedBy(Money divisor){return new Percent(getMoney().dividedBy(divisor.getMoney(), 2, true).getAsString());}


public Money times(int multiplier){return new Money(getMoney().times(new DecimalRationalNumber(multiplier)));}


public Money times(Percent percent)
{
	DecimalRationalNumber percentAsDecimalRationalNumber;
	percent = percent.dividedBy(100, 2, true);

	percentAsDecimalRationalNumber = new DecimalRationalNumber(percent.getAsString());

	return new Money(getMoney().times(percentAsDecimalRationalNumber));
}




public String getAsString()
{
	String result;
	int loc;

	result = this.money.getAsString();

	loc = result.indexOf('.');

	if(loc < 0)
	{
		result = result + ".00";
		loc = result.indexOf('.');
	}

	while(result.length() - (loc + 1) < 2)//5.3 --> 5.30
	{
		result = result + "0";
	}

	return result;
}



public String toString(){return getAsString();}



public int compareTo(Money other){return getMoney().compareTo(other.getMoney());}


public boolean isZero(){return getMoney().isZero();}

public boolean isLessThanZero(){return getMoney().compareTo(DecimalRationalNumber.Constant.Zero.get()) < 0;}

public boolean isGreaterThanZero(){return getMoney().compareTo(DecimalRationalNumber.Constant.Zero.get()) > 0;}

public boolean isEqualTo(Money other){return getMoney().compareTo(other.getMoney()) == 0;}

public boolean isLessThan(Money other){return getMoney().compareTo(other.getMoney()) < 0;}

public boolean isGreaterThan(Money other){return getMoney().compareTo(other.getMoney()) > 0;}


} //class



