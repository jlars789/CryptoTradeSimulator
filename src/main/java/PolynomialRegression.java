import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

public class PolynomialRegression {

	public static double evaluate(double[] array, int currentIndex) {
		int startIndex = currentIndex -13;
		int training = 0;
		int eval = 7;
		int day = 14;
		double[][] DATA = new double[13][2];
		for(int i = 0; i < DATA.length; i++) {
			DATA[i][0] = i;
			DATA[i][1] = array[startIndex + i];
		}
		//LocalDateTime init = LocalDateTime.now();
		final WeightedObservedPoints obs = new WeightedObservedPoints();
		for(int i = 0; i < eval; i++) {
			obs.add(DATA[i][0], DATA[i][1]);
		}
		
		PolynomialCurveFitter[] curve = new PolynomialCurveFitter[9];
		double[][] dataAt = new double[curve.length][eval-training];
		for(int i = 0; i < curve.length; i++) {
			curve[i] = PolynomialCurveFitter.create(i);
			double[] coeff = curve[i].fit(obs.toList());
			dataAt[i][0] = i;
			for(int j = 1; j < dataAt[i].length; j++) {
				dataAt[i][j] = new PolynomialFunction(coeff).value(eval);
			}
		}
		
		double[][] sorted = sortByClose(dataAt, DATA[eval][1]);
		
		 WeightedObservedPoints n = new WeightedObservedPoints();
		 
		 for(int i = eval; i < day-1; i++) {
			 n.add(DATA[i][0], DATA[i][1]);
		 }
		 double[] coeff = curve[(int)sorted[0][0]].fit(n.toList());
		 
		 return new PolynomialFunction(coeff).value(day);
		/*
		for(int i = 0; i < sorted.length; i++) {
			//System.out.println("Polynomial degree " + sorted[i][0] + " was off by a margin of " + (Math.abs(sorted[i][1] - DATA[day][1])/DATA[day][1]) + "%");
			System.out.println("Polynomial degree " + sorted[i][0] + 
					" had a MSPE of " + meanSquareError(sorted[i]));
		}
		*/
		//LocalDateTime startUp = LocalDateTime.now();
		//System.out.println("Setup time to complete: " + ((float)(Duration.between(init, startUp).toMillis())/1000) + "s");
		//System.out.println("Memory used: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000 + "MB");
	}
	
	public static double[][] sortByClose(double[][] arr, double actual) {
		int n = arr.length; 
		  
        // One by one move boundary of unsorted subarray 
        for (int i = 0; i < n-1; i++) 
        { 
            // Find the minimum element in unsorted array 
            int min_idx = i; 
            for (int j = i+1; j < n; j++) { 
            	//double com0 = Math.abs(arr[j][1] - DATA[day][1]);
            	//double com1 = Math.abs(arr[min_idx][1] - DATA[day][1]);
            	//double com0 = meanSquareError(arr[j]);
            	//double com1 = meanSquareError(arr[min_idx]);
            	double com0 = percentError(arr[j][1], actual);
            	double com1 = percentError(arr[min_idx][1], actual);
                if (com0 < com1) 
                    min_idx = j; 
            }
  
            // Swap the found minimum element with the first 
            // element 
            /*
            double[] temp = arr[min_idx]; 
            arr[min_idx] = arr[i]; 
            arr[i] = temp; 
            */
            double temp = arr[min_idx][1];
            arr[min_idx][1] = arr[i][1];
            arr[i][1] = temp;
        } 
        return arr;
	}
	
	public static double percentError(double predicted, double actual) {
		double diff = Math.abs(actual - predicted);
		double ratio = diff/actual;
		return ratio * 100;
	}
	
	/*
	public static double meanSquareError(double[] arr, double[] actual) {  
		int n = 7; 
		double sum = 0.0; 
		for (int i = 1; i < n; i++) { 
			double diff = actual[i] - arr[i];     
			sum = sum + diff * diff; 
		} 
		double mse = sum / n;
		double rmse = Math.sqrt(mse);
		return rmse;
	}
	*/
	
}
