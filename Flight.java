import java.util.ArrayList;
import java.util.Collections;

public class Flight {
	private Airport takeOff;
	private  Airport landing;
	public int duration;
	public String company;
	
	
	public Flight(Airport takeOff,Airport landing,int duration,String company) {
		this.takeOff=takeOff;
		this.landing=landing;
		this.duration=duration;
		this.company=company;
	}
	public Airport getAirportA() {
		return takeOff;
	}
	public String toString() {
		return ("Flight operated by "+ company+", duration "+duration );
	}
	public Airport getAirportB() {
		return landing;
	}
	public int getDuration() {
		return duration;
	}
	public String getCompany() {
		return company;
	}

}
