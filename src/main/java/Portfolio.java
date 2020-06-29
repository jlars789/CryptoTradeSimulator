

public class Portfolio {
	
	public static final double[] SCALP_AMOUNT = {0.001, 0.01, 0.1, 1};
	
	public static final String[] CODES = {"BTC", "ETH", "LTC", "XRP"};
	
	private Currency currency[];
	private boolean[] trade;
	private double USD;
	private double fiatValue;
	private double fiatInitial;

	public Portfolio(double[] vals, double[] er) {
		this.USD = 200;
		for(int i = 0 ; i < 4; i ++) {
			this.fiatInitial += vals[i];
		}
		this.fiatInitial += USD;
		this.fiatValue = fiatInitial;
		currency = new Currency[4];
		trade = new boolean[4];
		currency[0] = new Currency("BTC", vals[0], er[0]);
		currency[1] = new Currency("ETH", vals[1], er[1]);
		currency[2] = new Currency("LTC", vals[2], er[2]);
		currency[3] = new Currency("XRP", vals[3], er[3]);
		for(int i = 0 ; i < 4; i++) {
			trade[i] = true;
		}
		
	}
	
	public void evalScalp(String code) {
		double scalpAmount = SCALP_AMOUNT[getByCode(code)] * currency[getByCode(code)].getExchangeRate();
		if(currency[getByCode(code)].getFiatValue() > currency[getByCode(code)].getTargetMark() + scalpAmount) {
			scalp(code);
		}
	}
	
	public void scalp(String code) {
		double amount = SCALP_AMOUNT[getByCode(code)] * currency[getByCode(code)].getExchangeRate();
		tradeToUSD(code, amount);
	}
	
	public String getCode(int i) {
		return CODES[i];
	}
	
	public double getAvg(String code) {
		return currency[getByCode(code)].getAverage();
	}
	
	public void tradeToUSD(String code, double amount) {
		double tradeMin = SCALP_AMOUNT[getByCode(code)] * currency[getByCode(code)].getExchangeRate();
		
		if(tradeMin <= amount) {
			double fee = amount * 0.005;
			System.out.println("Fiat value of " + code + ": $" + currency[getByCode(code)].getFiatValue());
			if(currency[getByCode(code)].getFiatValue() >= amount) {
				currency[getByCode(code)].modifyValue(-amount);
				this.USD += (amount - fee);
				System.out.println("USD: $" + USD);
				System.out.println("Sold " + code + " for $" + amount);
				printBalance();
				System.out.println();
			} else {
				System.out.println("Not enough " + code);
			}
		} else {
			System.out.println("Minimum value required: $" + tradeMin + ", $" + amount + " was too small.");
		}
	}
	
	public void buyToMark(String code) {
		double amount = currency[getByCode(code)].getTargetMark() - currency[getByCode(code)].getFiatValue();
		buyWithUSD(code, amount);
	}
	
	public void buyWithUSD(String code, double amount) {
		//System.out.println("Amount of " + code + ": $" + amount);
		double fee = amount * 0.005;
		if((USD+fee) >= amount && trade[getByCode(code)] && amount >= 10) {
			this.USD -= (amount+fee);
			currency[getByCode(code)].modifyValue(amount);
			System.out.println("Bought " + code + " for $" + amount);
			printBalance();
			System.out.println();
		} else {
			
		}
	}
	
	public void sellAll(String code) {
		if(trade[getByCode(code)]) {
			System.out.println("SOLD ALL OF " + code);
			double amount = currency[getByCode(code)].getFiatValue();
			//System.out.println("Tried to sell " + code + " for $" + amount);
			this.tradeToUSD(code, amount);
			
			trade[getByCode(code)] = false;
			//System.exit(1);
		}
	}
	
	public void printBalance() {
		System.out.println("Current Balance: $" + this.getFiatValue());
	}
	
	public void tradeCurrencies(String fromCode, String toCode, double amount) {
		tradeToUSD(fromCode, amount);
		buyWithUSD(toCode, amount);
	}
	
	public boolean gainedMoney() {
		return (fiatValue > fiatInitial);
	}
	
	public boolean belowMark(String code) {
		return (currency[getByCode(code)].getTargetMark() - currency[getByCode(code)].getFiatValue() > 0);
	}
	
	public void evalPanic(String code) {
		if(currency[getByCode(code)].belowFloor()) {
			sellAll(code);
			
		}
	}
	
	public void feedValues(double[] vals) {
		for(int i = 0; i < 4; i++) {
			//System.out.println("Inputted " + vals[i] + " to " + CODES[i]);
			currency[i].setExchangeRate(vals[i]);
		}
	}
	
	public double getFiatValue() {
		double sum = 0;
		for(int i = 0 ; i < 4 ; i++) {
			sum += currency[i].getFiatValue();
			//System.out.println(currency[i].getFiatValue());
		}
		return sum + USD;
	}
	
	public int getByCode(String code) {
		int c = 0;
		for(int i = 0; i < 4; i++) {
			if(currency[i].getCode().equals(code)) {
				c = i;
				break;
			}
		}
		return c;
	}
	
	public void printFinal() {
		System.out.println("You finished with $" + this.getFiatValue() + " and started with $" + this.fiatInitial);
		System.out.println("This is a net change of $" + (this.getFiatValue() - this.fiatInitial));
		System.out.println("Distribution: ");
		System.out.println("USD: $" + USD);
		System.out.println("BTC: $" + currency[0].getFiatValue());
		System.out.println("ETH: $" + currency[1].getFiatValue());
		System.out.println("LTC: $" + currency[2].getFiatValue());
		System.out.println("XRP: $" + currency[3].getFiatValue());
		
	}

}
