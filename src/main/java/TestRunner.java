import java.time.LocalDateTime;

public class TestRunner {
	
	public static final double[] VALUES = {200, 200, 200, 200}; 
	public static final LocalDateTime DATE = LocalDateTime.of(2018, 4, 1, 0, 0); 

	public static void main(String[] args) {
		Simulation s = new Simulation(VALUES, 0.4, DATE);
		s.run();
	}

}
