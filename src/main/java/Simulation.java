import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Simulation implements Runnable {

	private static String[] FILES = {
			"Coinbase_BTCUSD_1h.csv", 
			"Coinbase_ETHUSD_1h.csv", 
			"Coinbase_LTCUSD_1h.csv", 
			"Kraken_XRPUSD_1h.csv"
	};
	
	public static final int MAX_HOURS = 25933;
	public static final LocalDateTime FIRST_DATE = LocalDateTime.of(2017, 7, 1, 11, 0);
	public static final LocalDateTime LAST_DATE = LocalDateTime.of(2020, 6, 15, 0, 0);
	
	@SuppressWarnings("unchecked")
	private ArrayList<Double>[] dataIterable = (ArrayList<Double>[]) new ArrayList[4];
	private Portfolio portfolio;
	private long currentHour;
	private long hours;
	private long skip;
	
	public Simulation(double[] fiatAmount, int weeks, LocalDateTime startDate) {
		this.skip = Duration.between(FIRST_DATE, startDate).toHours();
		this.hours = skip + (weeks * 186);
		if(hours > MAX_HOURS) hours = MAX_HOURS;
		this.currentHour = skip;
		this.dataInit();
		this.initializePortfolio(fiatAmount);
	}

	@Override
	public void run() {
		while(currentHour < hours) {
			this.updateVals();
			
			//this.evaluate();
			
			currentHour++;
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		portfolio.printFinal();
		
	}
	
	private void evaluate() {
		
		for(int i = 0; i < dataIterable.length; i++) {
			portfolio.evalScalp(portfolio.getCode(i));
			portfolio.evalPanic(portfolio.getCode(i));
		}
		
		for(int i = 0; i < dataIterable.length; i++) {
			//if(true == false) {
			if(portfolio.belowMark(portfolio.getCode(i))) {
				double[] d = toArray(i);
				double predicted = PolynomialRegression.evaluate(d, (int)currentHour);
				double delta = Math.abs((predicted - dataIterable[i].get((int)currentHour))/dataIterable[i].get((int)currentHour)) * 100;
				//System.out.println(delta);
				//System.out.println("Predicted " + predicted);
				//System.out.println("Average of " + portfolio.getCode(i) + " $" + portfolio.getAvg(portfolio.getCode(i)));
				if(predicted > portfolio.getAvg(portfolio.getCode(i))) {
					if(dataIterable[i].get((int)currentHour) > portfolio.getAvg(portfolio.getCode(i)) && delta > 4) {
						portfolio.buyToMark(portfolio.getCode(i));
					}
					else if(dataIterable[i].get((int)currentHour) < portfolio.getAvg(portfolio.getCode(i))) {
						portfolio.buyToMark(portfolio.getCode(i));
					}
				}
			}
		}
	}
	
	public void outputArrayList(ArrayList<Double> arr, int start, int stop) {
		for(int i = start; i < stop; i++) {
			System.out.println(arr.get(i));
		}
	}
	
	private double[] toArray(int index) {
		double[] d = new double[dataIterable[index].size()];
		for(int i=0; i < d.length; i++) {
			d[i] = dataIterable[index].get(i);
		}
		return d;
	}
	
	private void updateVals() {
		double[] rates = new double[4];
		for(int i = 0 ; i < 4; i++) {
			rates[i] = dataIterable[i].get((int)currentHour);
		}
		this.portfolio.feedValues(rates);
	}
	
	private void initializePortfolio(double[] fiat) {
		double[] rates = new double[4];
		for(int i = 0 ; i < 4; i++) {
			rates[i] = dataIterable[i].get((int)currentHour);
		}
		
		this.portfolio = new Portfolio(fiat, rates);
	}
	
	public void dataInit() {
		this.dataIterable[0] = getData("BTC");
		this.dataIterable[1] = getData("ETH");
		this.dataIterable[2] = getData("LTC");
		this.dataIterable[3] = getData("XRP");
	}
	
	public static ArrayList<Double> getData(String code) {
		ArrayList<Double> data = new ArrayList<Double>();
		
		String filePath = "";
		for(int i = 0 ; i < FILES.length; i++) {
			if(FILES[i].contains(code)) {
				filePath = FILES[i];
				break;
			}
		}
		
		try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/" + filePath));) {
		    String[] values = null;
		    csvReader.readNext();
		    csvReader.readNext();
		    while ((values = csvReader.readNext()) != null) {
		        data.add(Double.parseDouble(values[5]));
		    }
		    //csvReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Collections.reverse(data);
		
		return data;
		
	}

}
