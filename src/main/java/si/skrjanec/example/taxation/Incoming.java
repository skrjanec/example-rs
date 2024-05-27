package si.skrjanec.example.taxation;

public class Incoming {

	private int traderId;
	private double playedAmount;
	private double odd;

	public int getTraderId() {
		return traderId;
	}

	public void setTraderId(int traderId) {
		this.traderId = traderId;
	}

	public double getPlayedAmount() {
		return playedAmount;
	}

	public void setPlayedAmount(double playedAmount) {
		this.playedAmount = playedAmount;
	}

	public double getOdd() {
		return odd;
	}

	public void setOdd(double odd) {
		this.odd = odd;
	}
}