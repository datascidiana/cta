package project;
/*CTAStation class, that manipulates the data about a station
Diana Butnaru
11/26/2018*/

public class CTAStation extends GeoLocation {
	//name of the station
	private String name;
	//location of the station
	private String location;
	//wheelchair acces
	private boolean wheelchair;
	//index of the station = position
	private int index;
	//default constructor
	public CTAStation() {
		super();
		name = "CTA station";
		location = "elevated";
		wheelchair = false;
		index = 0;
	}
	//non-default constructor
	public CTAStation(double lat, double lng, String name, String location, boolean wheelchair) {
		super(lat,lng);
		this.name = name;
		this.location = location;
		this.wheelchair = wheelchair;
		index = 0;
	}
	//non-default constructor
	public CTAStation(double lat, double lng, String name, String location, boolean wheelchair, int index) {
		super(lat, lng);
		this.name = name;
		this.location = location;
		this.wheelchair = wheelchair;
		this.index = index;
	}
	//getter and setter for name variable
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	//getter and setter for location variable
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	//getter and setter for wheelchair variable
	public boolean isWheelchair() {
		return wheelchair;
	}
	public void setWheelchair(boolean wheelchair) {
		this.wheelchair = wheelchair;
	}
	//getter and setter for index variable
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	//to string method
	public String toString() {
		return "CTAStation name:" + name + "; geolocation:" + super.toString() + "; location:" + location + "; wheelchair:"
				+ wheelchair;
	}
	//equals method
	public boolean equals(CTAStation s) {
		if(!super.equals(s)) { 
			return false;
		}else if(!name.equals(s.getName())) {
			return false;
		}else if(!location.equals(s.getLocation())) {
			return false;
		}else if(wheelchair != s.isWheelchair()) {
			return false;
		}
		return true;
	}
	
}
