public class FederalIncomeTaxTable2018Single extends TaxTable
{
/*
5/11/2018
Keith O'Neal
CISC 230 Section 2
Class Description: Class implementing the Singleton pattern designed to calculate American Federal Income
taxes for the 2018 tax year for someone filing as a single individual. Uses the TaxTable class and all associated classes.
________________________

*/

private static final FederalIncomeTaxTable2018Single instance = new FederalIncomeTaxTable2018Single();

private FederalIncomeTaxTable2018Single taxTableRow[];

private FederalIncomeTaxTable2018Single(){ super(new TaxBracket[]{new TaxBracket("0", "10"),
																  new TaxBracket("9525", "12"),
																  new TaxBracket("38700", "22"),
																  new TaxBracket("82500", "24"),
																  new TaxBracket("157500", "32"),
																  new TaxBracket("200000", "35"),
																  new TaxBracket("500000", "37")
																  });
										 }

public static FederalIncomeTaxTable2018Single getInstance(){return FederalIncomeTaxTable2018Single.instance;}

public static FederalIncomeTaxTable2018Single newInstance(){return FederalIncomeTaxTable2018Single.getInstance();}
























/*
public FederalIncomeTaxTable2018Single(){ super( new TaxBracket[]{

												new TaxBracket("0", "10"),
												new TaxBracket("9525", "12"),
												new TaxBracket("38700", "22"),
												new TaxBracket("82500", "24"),
												new TaxBracket("157500", "32"),
												new TaxBracket("200000", "35"),
												new TaxBracket("500000", "37")

																} );
										}

										*/
} //class
