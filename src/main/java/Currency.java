
public class Currency {
	
	public static final double FLOOR = .07;

	private String code;
	private double amount;
	private double targetMark;
	private double exchangeRate;
	private Average avg;
	
	public Currency(String code, double fiatAmountOwned, double exchangeRate) {
		this.code = code;
		this.amount = (fiatAmountOwned/exchangeRate);
		this.targetMark = fiatAmountOwned;
		this.exchangeRate = exchangeRate;
		this.avg = new Average();
		avg.input(exchangeRate);
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public double getAverage() {
		//System.out.print("Entries for "+ code + ": ");
		return avg.getAverage();
	}
	
	public double getExchangeRate() {
		return this.exchangeRate;
	}
	
	public double getFiatValue() {
		return (this.amount * this.exchangeRate);
	}
	
	public double getTargetMark() {
		return this.targetMark;
	}
	
	public void modifyValue(double fiatAmount) {
		this.amount += (fiatAmount/this.exchangeRate);
	}
	
	public boolean belowFloor() {
		return (this.getFiatValue() < (this.targetMark * (1-FLOOR)));
	}
	
	public void setExchangeRate(double rate) {
		this.exchangeRate = rate;
		//System.out.print("Inputted exchange rate of " + rate + " for " + code + ", value of ");
		avg.input(rate);
	}
	
	public String getCode() {
		return this.code;
	}

}
