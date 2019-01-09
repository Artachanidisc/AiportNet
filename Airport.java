import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Airport {
	private String name;
	private String codeName;
	private String town;
	private String country;
	private static int countConnections = 0;
	
	
	public Airport(String name, String codeName, String town, String country) {
		this.name = name;
		this.codeName = codeName;
		this.town = town;
		this.country = country;
	}

	public String getName() {
		return name;
	}


	public String getCodeName() {
		return codeName;
	}


	public String getTown() {
		return town;
	}

	public String getCountry() {
		return country;
	}

	public static int getCountConnections() {
		return countConnections;
	}
	public static void setIndConnections(ArrayList<Airport> a) {
		Set<Airport> as = new HashSet<>();
		as.addAll(a);
		a.clear();
		a.addAll(as);
	}
	public static void setConnections(ArrayList<Flight> a) {
		Set<Flight> fs = new HashSet<>();
		fs.addAll(a);
		a.clear();
		a.addAll(fs);
	}

	public boolean isDirectlyConnectedTo(Airport anAirport) {
		boolean isConnected = false;
		for (int i = 0; i < CentralRegistry.flights.size(); i++) {
			if ((CentralRegistry.flights.get(i).getAirportB().getName().equals(this.getName())
					&& CentralRegistry.flights.get(i).getAirportA().getName().equals(anAirport.getName()))
					|| (CentralRegistry.flights.get(i).getAirportB().getName().equals(anAirport.getName())
							&& CentralRegistry.flights.get(i).getAirportA().getName().equals(this.getName()))) {
							isConnected=true;
			}
		}
		return isConnected;
	}
	public void fillConnected(Airport anAirport) {
		for (int i = 0; i < CentralRegistry.flights.size(); i++) {
			if ((CentralRegistry.flights.get(i).getAirportB().getName().equals(this.getName())
					&& CentralRegistry.flights.get(i).getAirportA().getName().equals(anAirport.getName()))
					|| (CentralRegistry.flights.get(i).getAirportB().getName().equals(anAirport.getName())
							&& CentralRegistry.flights.get(i).getAirportA().getName().equals(this.getName()))) {		
				CentralRegistry.flightsConnected.add(CentralRegistry.flights.get(i));
				countConnections++;
			}
		}
		
	}
	public void fillIndConnected(Airport anAirport) {
		for (int i = 0; i < CentralRegistry.flights.size();i++) {
			if (this.isDirectlyConnectedTo(CentralRegistry.flights.get(i).getAirportA())||this.isDirectlyConnectedTo(CentralRegistry.flights.get(i).getAirportB())) {
				for (int j = 0; j < CentralRegistry.flights.size(); j++) {
					if(CentralRegistry.flights.get(i).getAirportA().isDirectlyConnectedTo(CentralRegistry.flights.get(j).getAirportA())
							||CentralRegistry.flights.get(i).getAirportA().isDirectlyConnectedTo(CentralRegistry.flights.get(j).getAirportB())
							||CentralRegistry.flights.get(i).getAirportB().isDirectlyConnectedTo(CentralRegistry.flights.get(j).getAirportA())
							||CentralRegistry.flights.get(i).getAirportB().isDirectlyConnectedTo(CentralRegistry.flights.get(j).getAirportB())) {
						if(CentralRegistry.flights.get(j).getAirportA().equals(anAirport)) {
							CentralRegistry.airportsIndConnected.add(CentralRegistry.flights.get(j).getAirportA());
						}
						else if(CentralRegistry.flights.get(j).getAirportA().equals(anAirport)) {
							CentralRegistry.airportsIndConnected.add(CentralRegistry.flights.get(j).getAirportB());
						}
					}
				}				
			}
			break;
		}	
	}


	public boolean isInDirectlyConnectedTo(Airport anAirport) {
		boolean isIndConnected=false;
		if(this.isDirectlyConnectedTo(anAirport)) {
			return isIndConnected;
		}
		for (int i = 0; i < CentralRegistry.flights.size();i++) {
			if (this.isDirectlyConnectedTo(CentralRegistry.flights.get(i).getAirportA())||this.isDirectlyConnectedTo(CentralRegistry.flights.get(i).getAirportB())) {
				for (int j = 0; j < CentralRegistry.flights.size(); j++) {
					if(CentralRegistry.flights.get(i).getAirportA().isDirectlyConnectedTo(CentralRegistry.flights.get(j).getAirportA())
							||CentralRegistry.flights.get(i).getAirportA().isDirectlyConnectedTo(CentralRegistry.flights.get(j).getAirportB())
							||CentralRegistry.flights.get(i).getAirportB().isDirectlyConnectedTo(CentralRegistry.flights.get(j).getAirportA())
							||CentralRegistry.flights.get(i).getAirportB().isDirectlyConnectedTo(CentralRegistry.flights.get(j).getAirportB())) {
						if(CentralRegistry.flights.get(j).getAirportA().equals(anAirport)) {
							isIndConnected=true;
						}
						else if(CentralRegistry.flights.get(j).getAirportA().equals(anAirport)) {
							isIndConnected=true;
						}
					}
				}				
			}
			return isIndConnected;
		}
			return isIndConnected;
	}


	public ArrayList<Airport> getCommonConnections(Airport anAirport) {

		ArrayList<Airport> common = new ArrayList<Airport>();

		for (int i = 0; i < CentralRegistry.flights.size(); i++) {
			if (this.isDirectlyConnectedTo(CentralRegistry.flights.get(i).getAirportB())) {
				for (int j = 0; j < CentralRegistry.flights.size(); j++) {
					if (anAirport.isDirectlyConnectedTo(CentralRegistry.flights.get(j).getAirportB())) {
						if (CentralRegistry.flights.get(i).getAirportB()
								.equals(CentralRegistry.flights.get(j).getAirportB())) {
							anAirport = CentralRegistry.flights.get(j).getAirportB();
							common.add(anAirport);
						}
					}
				}
			}
		}
		return common;
	}

	public void printCompanies() {
		for (int i = 0; i < CentralRegistry.flights.size(); i++) {
			if (this.getName().equals(CentralRegistry.flights.get(i).getAirportA().getName())
					|| this.getName().equals(CentralRegistry.flights.get(i).getAirportB().getName())) {
				System.out.println(CentralRegistry.flights.get(i).getCompany());
			}
		}
	}

}
