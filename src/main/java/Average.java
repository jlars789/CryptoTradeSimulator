import java.util.ArrayList;

public class Average {
	
	private ArrayList<Double> entries;
	
	public Average() {
		entries = new ArrayList<Double>();
	}
	
	public void input(double j) {
		entries.add(j);
		//System.out.print(j + " was added to average. ");
		if(entries.size() > 24) {
			//System.out.println(entries.get(0) + " was removed.");
			entries.remove(0);
			
		}
	}
	
	public double getAverage() {
		double sum = 0;
		for(int i = 0 ; i < entries.size(); i++) {
			sum += entries.get(i);
			//System.out.print(entries.get(i) + " ");
		}
		//System.out.println(" ");
		
		double divisor = entries.size();
		//System.out.println("Average " + (sum/divisor));
		return (sum/divisor);
	}

}
