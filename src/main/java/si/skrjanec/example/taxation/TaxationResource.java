package si.skrjanec.example.taxation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("taxation")
public class TaxationResource {

	// TODO: should be moved to CDI bean where business logic is implemented
	public static double GENERAL_TAX_RATE = 0.1;
	public static double GENERAL_TAX_AMOUNT = 2;

	public static double WINNINGS_TAX_RATE = 0.1;
	public static double WINNINGS_TAX_AMOUNT = 1;

	@POST
	public Response processTax(Incoming incoming) {

		// TODO: maybe validation is needed? can odd be < 1? can amount be < 0?
		Outgoing outgoing = this.calculateTax(incoming);

		return Response.ok(outgoing).build();
	}

	
	// TODO: should be moved to CDI bean where business logic is implemented
	// If traderId = 1 than follow the general taxation rule, where the country taxing authority will extract max amount.
	// If traderId = 2 than follow the winnings taxation rule, where the country will use the minimal amount of 1 EUR for winning under 10 EUR. 
	private Outgoing calculateTax(Incoming incoming) {
		if (incoming.getTraderId() == 1) {
			return processGeneralTaxation(incoming.getPlayedAmount() * incoming.getOdd());
		} else if (incoming.getTraderId() == 2) {
			return processWinningsTaxation(incoming.getPlayedAmount(), incoming.getPlayedAmount() * (incoming.getOdd() - 1));
		} else {
			throw new BadRequestException();
		}
	}

//	1. General
//    - taxation is done per rate or per amount:
//
//    rate: 10%
//        7.5EUR * 0.1 = 0.75EUR => possible return amount is 7.5EUR - 0.75EUR = 6.75
//    amount: 2EUR
//        7.5EUR - 2EUR = 5.5EUR => possible return amount is 5.5EUR
	private Outgoing processGeneralTaxation (double amount) {
		double rateTax = GENERAL_TAX_RATE * amount;
		
		Outgoing outgoing = new Outgoing();

		if (rateTax > GENERAL_TAX_AMOUNT) {
			outgoing.setTaxRate(GENERAL_TAX_RATE);
			outgoing.setPossibleReturnAmountBefTax(amount);
			// TODO: check if this is duplicated?
			outgoing.setPossibleReturnAmountAfterTax(amount - rateTax);
			outgoing.setPossibleReturnAmount(amount - rateTax);
		} else {
			outgoing.setTaxAmount(GENERAL_TAX_AMOUNT);
			outgoing.setPossibleReturnAmountBefTax(amount);
			// TODO: check if this is duplicated?
			outgoing.setPossibleReturnAmountAfterTax(amount - GENERAL_TAX_AMOUNT);
			outgoing.setPossibleReturnAmount(amount - GENERAL_TAX_AMOUNT);
		}
		
		return outgoing;
	}

//    	2. Winnings
//    	    - winnings amount: 7.5EUR - 5EUR = 2.5EUR
//    	    - taxation is done per rate or per amount:
//    	
//    	    rate: 10%
//    	        2.5EUR * 0.1 = 0.25EUR => possible return amount is 7.5EUR - 0.25EUR = 7.25EUR
//    	    amount: 1EUR
//    	        2.5EUR - 1EUR = 1.5EUR => possible return amount is 1.5EUR
	private Outgoing processWinningsTaxation (double amount, double winningsAmount) {
		Outgoing outgoing = new Outgoing();

		if (winningsAmount > 10) {
			double rateTax = WINNINGS_TAX_RATE * winningsAmount;
			outgoing.setTaxRate(WINNINGS_TAX_RATE);
			outgoing.setPossibleReturnAmountBefTax(amount + winningsAmount);
			// TODO: check if this is duplicated?
			outgoing.setPossibleReturnAmountAfterTax(amount + winningsAmount - rateTax);
			outgoing.setPossibleReturnAmount(amount + winningsAmount - rateTax);
		} else {
			outgoing.setTaxAmount(WINNINGS_TAX_AMOUNT);
			outgoing.setPossibleReturnAmountBefTax(winningsAmount);
			// TODO: check if this is duplicated?
			outgoing.setPossibleReturnAmountAfterTax(winningsAmount - WINNINGS_TAX_AMOUNT);
			outgoing.setPossibleReturnAmount(winningsAmount - WINNINGS_TAX_AMOUNT);
		}
		
		return outgoing;
	}
}