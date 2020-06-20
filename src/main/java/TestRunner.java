import java.time.LocalDateTime;

public class TestRunner {
	
	public static final double[] VALUES = {2000, 2000, 2000, 2000}; 
	public static final LocalDateTime DATE = LocalDateTime.of(2017, 12, 10, 0, 0); 

	public static void main(String[] args) {
		Simulation s = new Simulation(VALUES, 2, DATE);
		s.run();
	}

}
