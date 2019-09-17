package project;
/*CTARoute class, that manipulates the data about a line/route
Diana Butnaru
11/26/2018*/
import java.util.ArrayList;

public class CTARoute{
	//name of the cta line/route
	private String name;
	//arraylist of stations on the line/route
	private ArrayList<CTAStation> stops;
	//default constructor
	public CTARoute() {
		name = "Green Line";
		stops = new ArrayList<CTAStation>();
	}
	//non-default constructor
	public CTARoute(String name, ArrayList<CTAStation> stops) {
		this.name = name;
		this.stops = stops;
	}
	//adds a station to the route
	public void addStation(CTAStation station) {
		stops.add(station);
	}
	//removes a station from the route
	public void removeStation(CTAStation station) {
		stops.remove(station);
	}
	//inserts a station at a certain position
	public void insertStation(int position, CTAStation station) {
		stops.add(position, station);
	}
	//looks up for a station with a specific name
	public CTAStation lookupStation(String name) {
		for(int i = 0; i<stops.size(); i++) {
			if(stops.get(i).getName().equalsIgnoreCase(name)) return stops.get(i);
		}
		return null;
	}
	//returns the nearest station to user's location
	public CTAStation nearestStation(double lat, double lng) {
		GeoLocation user = new GeoLocation(lat, lng);
		GeoLocation min = stops.get(0);
		int index = 0;
		for(int i = 0; i<stops.size(); i++) {
			if(GeoLocation.calcDistance(user, min) > GeoLocation.calcDistance(user, stops.get(i))) {
				min = stops.get(i);
				index = i;
			}
		}
		return stops.get(index);
	}
	//returns the nearest station to user's location
	public CTAStation nearestStation(GeoLocation location) {
		GeoLocation user = location;
		GeoLocation min = stops.get(0);
		int index = 0;
		for(int i = 0; i<stops.size(); i++) {
			if(GeoLocation.calcDistance(user, min) > GeoLocation.calcDistance(user, stops.get(i))) {
				min = stops.get(i);
				index = i;
			}
		}
		return stops.get(index);
	}
	//getter and setter for name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	//getter and setter for stops
	public ArrayList<CTAStation> getStops() {
		return stops;
	}
	public void setStops(ArrayList<CTAStation> stops) {
		this.stops = stops;
	}
	//equals method
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CTARoute other = (CTARoute) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stops == null) {
			if (other.stops != null)
				return false;
		} else if (!stops.equals(other.stops))
			return false;
		return true;
	}
	//toString method
	public String toString() {
		return "CTARoute name=" + name + ", stops=" + stops;
	}
}