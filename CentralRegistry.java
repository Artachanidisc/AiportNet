import java.util.ArrayList;
import java.util.Collections;

public class CentralRegistry {

	public static ArrayList<Airport> airports = new ArrayList<Airport>();
	public static ArrayList<Flight> flights = new ArrayList<Flight>();
	public static ArrayList<Airport> airportsIndConnected = new ArrayList<>();
	public static ArrayList<Flight> flightsConnected = new ArrayList<>();
	public static int counterAirports = 0;
	public static int counterFlights = 0;
	private static int i;
	private static int j;
	private static ArrayList<Integer> hubs = new ArrayList<Integer>(Collections.nCopies(6, 0));

	public static ArrayList<Airport> getAirports() {
		return airports;
	}
	
	public static String getAirport(String CityName) {
		boolean notFound = true;
		for (Airport airport : airports) {
			if (airport.getTown().equals(CityName)) {
				CityName = airport.getName();
				notFound = false;
			}
		}
		if (notFound) {
			CityName = null;
		}
		return CityName;
	}

	public static String getDirectFlightsDetails(Airport a, Airport b) {
		String text="";
		String text2="DIRECT FLIGHTS DETAILS\n";
		if (a.isDirectlyConnectedTo(b)) {
			a.fillConnected(b);
			Airport.setConnections(CentralRegistry.flightsConnected);
			for (i = 0; i <CentralRegistry.flightsConnected.size(); i++) {
				text+=("[" + (i + 1)+ "]" + CentralRegistry.flightsConnected.get(i).toString()+"\n");
			}
		}
		else {
			text2="";
		}
		
		return (text2+text);
	}
	public static String getInDirectFlightsDetails(Airport a,Airport b) {
		String text="";
		String text2="INDIRECT FLIGHTS through\n";
		if(a.isInDirectlyConnectedTo(b)) {
			a.fillIndConnected(b);
			Airport.setIndConnections(CentralRegistry.airportsIndConnected);
			for(i=0;i<CentralRegistry.airportsIndConnected.size();i++) {
				text+=("[" + (i + 1)+ "]" +CentralRegistry.airportsIndConnected.get(i).getTown()+", " + CentralRegistry.airportsIndConnected.get(i).getCodeName()+" airport"+"\n");
			}
		}
		else {
			text2="";
		}
		return(text2+text);
	}

	public static ArrayList<Flight> getFlights() {
		return flights;
	}

	

	public static int getCounterAirports() {
		return counterAirports;
	}


	public static int getCounterFlights() {
		return counterFlights;
	}

	public static void addAirport(Airport a) {
		airports.add(a);
		counterAirports++;
	}

	public static void addFlight(Flight f) {
		flights.add(f);
		counterFlights++;
	}

	public static Airport getLargestHub() {
		for (i = 0; i < CentralRegistry.flights.size(); i++) {
			for (j = 0; j < airports.size(); j++) {
				if (flights.get(i).getAirportB().getName().equals(airports.get(j).getName())) {
					hubs.set(j, hubs.get(j) + 1);
				}
			}
		}
		int max = hubs.get(0);
		int maxP = 0;
		for (i = 0; i < hubs.size(); i++) {
			if (hubs.get(i) > max) {
				max = hubs.get(i);
				maxP = i;
			}
		}
		return airports.get(maxP);
	}

	public static Flight getLongestFlight() {

		int max = flights.get(0).getDuration();
		int maxP = 0;
		for (i = 0; i < CentralRegistry.flights.size(); i++) {
			if (flights.get(i).getDuration() > max) {
				max = flights.get(i).getDuration();
				maxP = i;
			}
		}
		return flights.get(maxP);
	}
}
