package si.skrjanec.example.taxation;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Outgoing {
	private double possibleReturnAmount;
	private double possibleReturnAmountBefTax;
	// TODO: check if this field is needed
	private double possibleReturnAmountAfterTax;
	private double taxRate;
	private double taxAmount;

	public double getPossibleReturnAmount() {
		return possibleReturnAmount;
	}

	public void setPossibleReturnAmount(double possibleReturnAmount) {
		this.possibleReturnAmount = possibleReturnAmount;
	}

	public double getPossibleReturnAmountBefTax() {
		return possibleReturnAmountBefTax;
	}

	public void setPossibleReturnAmountBefTax(double possibleReturnAmountBefTax) {
		this.possibleReturnAmountBefTax = possibleReturnAmountBefTax;
	}

	public double getPossibleReturnAmountAfterTax() {
		return possibleReturnAmountAfterTax;
	}

	public void setPossibleReturnAmountAfterTax(double possibleReturnAmountAfterTax) {
		this.possibleReturnAmountAfterTax = possibleReturnAmountAfterTax;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

}