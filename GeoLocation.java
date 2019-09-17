package project;
/*Geolocation class that manipulates the geolocation data 
Diana Butnaru
11/26/2018*/
public class GeoLocation {
	//latitude
	private double lat;
	//longitude
	private double lng; 
	//default constructor
	public GeoLocation() {
		lat = 0;
		lng = 0;
	}
	//non-default constructor
	public GeoLocation(double lat, double lng) {
		setLat(lat);
		setLng(lng);
	}
	//accessor method for latitude
	public double getLat() {
		return lat;
	}
	//accessor method for longitude
	public double getLng() {
		return lng;
	}
	//mutator method for latitude
	public void setLat(double lat) {
		if (-90<=lat && lat<=90)
		this.lat = lat;
	}
	//mutator method for longitude
	public void setLng(double lng) {
		if (-180<=lng && lng<=180)
		this.lng = lng;
	}
	//return the location in the format "(*lat*, *lng*)"
	private String constructString() {
		return "(" + lat + ", " + lng + ")";
	}
	//toString method
	public String toString() {
		return constructString();
	}

	//returns the distance between 2 locations taking 2 GeoLocations as parameters
	public static double calcDistance(GeoLocation loc1, GeoLocation loc2) {
		return Math.sqrt(Math.pow(loc1.getLat()-loc2.getLat(),2)+Math.pow(loc1.getLng()-loc2.getLng(),2));
	}
	//returns the distance between 2 locations taking lat and long coordinates as parameters
	public static double calcDistance(double lat1, double lat2, double long1, double long2) {
		return Math.sqrt(Math.pow(lat1-lat2,2)+Math.pow(long1-long2,2));
	}
	//equals method
	public boolean equals(GeoLocation loc) {
		return (lat == loc.getLat()) && (lng == loc.getLng());
	}
}

