package project;
/*Application class
Diana Butnaru
11/26/2018*/
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import project.GeoLocation;
import project.CTAStation;

public class CTAapp {
	//main method that implements the application
	public static void main(String[] args) {
		//data base new file
		File cta_file = new File("C:\\Users\\Diana\\git\\cs201-201808-dbutnaru\\src\\project\\CTAStops.csv");
		//arraylists for each line/route of the CTA
		ArrayList<CTAStation> green_list = new ArrayList<CTAStation>();
		ArrayList<CTAStation> red_list = new ArrayList<CTAStation>();
		ArrayList<CTAStation> blue_list = new ArrayList<CTAStation>();
		ArrayList<CTAStation> brown_list = new ArrayList<CTAStation>();
		ArrayList<CTAStation> purple_list = new ArrayList<CTAStation>();
		ArrayList<CTAStation> pink_list = new ArrayList<CTAStation>();
		ArrayList<CTAStation> orange_list = new ArrayList<CTAStation>();
		ArrayList<CTAStation> yellow_list = new ArrayList<CTAStation>();
		//reads cta_file and completes the arraylists with data about each route, then sorts 
		completeRouteData(cta_file,green_list,red_list,blue_list,brown_list,
				purple_list,pink_list,orange_list,yellow_list);	
		//creating all cta routes
		CTARoute green = new CTARoute("green",green_list);
		CTARoute red = new CTARoute("red",red_list);
		CTARoute blue = new CTARoute("blue",blue_list);
		CTARoute brown = new CTARoute("brown",brown_list);
		CTARoute purple = new CTARoute("purple",purple_list);
		CTARoute pink = new CTARoute("pink",pink_list);
		CTARoute orange = new CTARoute("orange",orange_list);
		CTARoute yellow = new CTARoute("yellow",yellow_list);
		//flag variable
		boolean done = false;
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		//integer for the menu command selected
		int command = 8;
		//temporary route
		CTARoute route;
		//temporary string to store the input
		String input = "";
		//menu loop
		while(!done) {
			System.out.println("MENU:\r\n" +
					"0.Display all stations of a route\r\n" + 
					"1.Add a new station\r\n" + 
					"2.Delete an existing station\r\n" + 
					"3.Modify existing station\r\n" + 
					"4.Display information for a station with a specific name\r\n" + 
					"5.Display the nearest station to a location\r\n" + 
					"6.Display the stations with wheelchair access\r\n" + 
					"7.Create transit path\r\n" + 
					"8.Exit");
			command = -1;
			//in case an error comes up
			try {
				command = Integer.parseInt(in.next());
			}catch(NumberFormatException nfe) {
				System.out.println("Enter a number!");
			}
			//chooses the command to be executed
			switch (command) {
			case 0:  System.out.println("Enter route:"); input = in.next().toLowerCase();
				route = findRoute(input,red,green,blue,brown,purple,pink,orange,yellow);
				//in case an error comes up
				while(route == null) {
					System.out.println("Enter route:"); input = in.next().toLowerCase();
					route = findRoute(input,red,green,blue,brown,purple,pink,orange,yellow);
				}
				printStations(route); break;
			case 1: System.out.println("Enter route:"); input = in.next().toLowerCase();
				route = findRoute(input,red,green,blue,brown,purple,pink,orange,yellow);
				//in case an error comes up
				while(route == null) {
					System.out.println("Enter route:"); input = in.next().toLowerCase();
					route = findRoute(input,red,green,blue,brown,purple,pink,orange,yellow);
				}
				addStation(route); break;
			case 2:System.out.println("Enter route:"); input = in.next().toLowerCase();
				route = findRoute(input,red,green,blue,brown,purple,pink,orange,yellow);
				//in case an error comes up
				while(route == null) {
					System.out.println("Enter route:"); input = in.next().toLowerCase();
					route = findRoute(input,red,green,blue,brown,purple,pink,orange,yellow);
				}
				System.out.println("Enter name:"); input = in.next().toLowerCase();
				removeStation(route,input);
				break;
			case 3:System.out.println("Enter route:"); input = in.next().toLowerCase();
				route = findRoute(input,red,green,blue,brown,purple,pink,orange,yellow);
				//in case an error comes up
				while(route == null) {
					System.out.println("Enter route:"); input = in.next().toLowerCase();
					route = findRoute(input,red,green,blue,brown,purple,pink,orange,yellow);
				}
				System.out.println("Enter name:"); input = in.next().toLowerCase();
				modifyStation(route,input);
				break;
			case 4:System.out.println("Enter name:"); input = in.next().toLowerCase();
				infoStation(input,red,green,blue,brown,purple,pink,orange,yellow);
				break;
			case 5:nearestStation(red,green,blue,brown,purple,pink,orange,yellow);
				break;
			case 6:wheelchair(red,green,blue,brown,purple,pink,orange,yellow);
				break;
			case 7:path(red,green,blue,brown,purple,pink,orange,yellow);
				break;
			case 8:System.out.println("Good bye!");
				done = true; break;
			default: System.out.println("Enter a valid menu command (1-8)!"); break;
			}
		}
	}
	
	//asks if the user wants to save the path to a file
	public static boolean save(String in) {
		//flag variable
		boolean chosen = false;
		while(!chosen) {
			switch (in) {
			case "0": chosen = true;return false;
			case "1": chosen = true;return true;
			default: System.out.println("Enter '1' or '0':");
			}
		}
		return false;
	}
	//prints out all stations for the route
	public static void printStations(CTARoute route){
		for(int i=0; i<route.getStops().size();i++) {
			System.out.println(route.getStops().get(i).toString());
		}
	}
	//reads cta_file and completes the arraylists with data about each route
	public static void completeRouteData(File cta_file, ArrayList<CTAStation> green, ArrayList<CTAStation> red,
			ArrayList<CTAStation> blue,ArrayList<CTAStation> brown, ArrayList<CTAStation> purple,
			ArrayList<CTAStation> pink, ArrayList<CTAStation> orange,ArrayList<CTAStation> yellow) {
		try {
			//scanner to get the data from the CTA file
			Scanner cta_input;
			cta_input = new Scanner(cta_file);
			//string array to store each line of cta_file
			String[] line = new String[13];
			line = cta_input.nextLine().split(",");
			line = cta_input.nextLine().split(",");
			//temporary CTAStation
			CTAStation temp = new CTAStation();
			for(int i=1; i<=140; i++) {
				line = cta_input.nextLine().split(",");
				if(Integer.parseInt(line[5])!= -1) {
					temp = new CTAStation(Double.parseDouble(line[1]),Double.parseDouble(line[2]),
							line[0],line[3],Boolean.parseBoolean(line[4].toLowerCase()),Integer.parseInt(line[5]));
					temp.toString();
					red.add(temp);
				}
				if(Integer.parseInt(line[6])!= -1) {
					temp = new CTAStation(Double.parseDouble(line[1]),Double.parseDouble(line[2]),
							line[0],line[3],Boolean.parseBoolean(line[4].toLowerCase()),Integer.parseInt(line[6]));
					green.add(temp);
				}
				if(Integer.parseInt(line[7])!= -1) {
					temp = new CTAStation(Double.parseDouble(line[1]),Double.parseDouble(line[2]),
							line[0],line[3],Boolean.parseBoolean(line[4].toLowerCase()),Integer.parseInt(line[7]));
					blue.add(temp);
				}
				if(Integer.parseInt(line[8])!= -1) {
					temp = new CTAStation(Double.parseDouble(line[1]),Double.parseDouble(line[2]),
							line[0],line[3],Boolean.parseBoolean(line[4].toLowerCase()),Integer.parseInt(line[8]));
					brown.add(temp);
				}
				if(Integer.parseInt(line[9])!= -1) {
					temp = new CTAStation(Double.parseDouble(line[1]),Double.parseDouble(line[2]),
							line[0],line[3],Boolean.parseBoolean(line[4].toLowerCase()),Integer.parseInt(line[9]));
					purple.add(temp);
				}
				if(Integer.parseInt(line[10])!= -1) {
					temp = new CTAStation(Double.parseDouble(line[1]),Double.parseDouble(line[2]),
							line[0],line[3],Boolean.parseBoolean(line[4].toLowerCase()),Integer.parseInt(line[10]));
					pink.add(temp);
				}
				if(Integer.parseInt(line[11])!= -1) {
					temp = new CTAStation(Double.parseDouble(line[1]),Double.parseDouble(line[2]),
							line[0],line[3],Boolean.parseBoolean(line[4].toLowerCase()),Integer.parseInt(line[11]));
					orange.add(temp);
				}
				if(Integer.parseInt(line[12])!= -1) {
					temp = new CTAStation(Double.parseDouble(line[1]),Double.parseDouble(line[2]),
							line[0],line[3],Boolean.parseBoolean(line[4].toLowerCase()),Integer.parseInt(line[12]));
					yellow.add(temp);
				}
			}	
			cta_input.close();
		}catch(IOException io) {
			System.out.println("No file found!");
		}
		sortStation(red);
		sortStation(green);
		sortStation(blue);
		sortStation(brown);
		sortStation(purple);
		sortStation(pink);
		sortStation(orange);
		sortStation(yellow);
	}
	//sorts arraylists of stations
	public static void sortStation(ArrayList<CTAStation> list) {
		//counter variable
		int j = 0;
		//temporary cta station
		CTAStation temp = list.get(0);
		for(int i = 1; i<list.size(); i++) {
			j = i;
			while(j>0 && list.get(j-1).getIndex()>list.get(j).getIndex()) {
				temp = list.get(j-1);
				list.set(j-1,list.get(j));
				list.set(j,temp);
				j=j-1;
			}
		}
	}
	//finds the corresponding route to the string inputed by the user
	public static CTARoute findRoute(String input,CTARoute red,CTARoute green,CTARoute blue,CTARoute brown,
			CTARoute purple,CTARoute pink,CTARoute orange,CTARoute yellow){
		//the cta route 
		CTARoute route = null;
		switch (input) {
		case "red": route = red; break;
		case "green": route = green; break;
		case "blue": route = blue; break;
		case "brown": route = brown; break;
		case "purple": route = purple; break;
		case "pink": route = pink; break;
		case "orange": route = orange; break;
		case "yellow": route = yellow; break;
		default: System.out.println("No such route!");break;
		}
		return route;
	}
	//adding a new station
	public static void addStation(CTARoute route) {
		//flag variable for the boolean variable
		boolean bool = false;
		//flag variable for the integer variable
		boolean integer = false;
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		//latitude
		double lat = -1;
		do {
			System.out.println("Enter latitude:");
			try {
				lat = in.nextDouble();
			}catch(InputMismatchException ime) {
				System.out.println("Enter latitude:");
			}
			in.nextLine();
		}while(!(lat>=-90 && lat<=90));
		//longitude
		double lng = -1;
		do {
			System.out.println("Enter longitude:");
			try {
				lng = in.nextDouble();
			}catch(InputMismatchException ime) {
				System.out.println("Enter longitude:");
			}
			in.nextLine();
		}while(!(lng>=-180 && lng<=180));
		
		System.out.println("Enter name:");
		//new name
		String name = in.next();
		System.out.println("Enter location:");
		//new location
		String location = in.next();
		System.out.println("Enter wheelchair access:");
		//wheelchair access
		boolean access = false;
		//in case an error comes up
		do {
			try {
				access = in.nextBoolean();
				bool = true;
			}catch(InputMismatchException ime) {
				System.out.println("Enter true/false:");
			}
			in.nextLine();
		} while(!bool);
		System.out.println("Enter index 0-"+(int)(route.getStops().size())+":");
		//the index of the new station 
		int index = -1;
		//in case an error comes up
		do {
			try {
				index = in.nextInt();
				if(index>=0 && index<=route.getStops().size()) {
					integer = true;
				}else {
					System.out.println("Enter index 0-"+(int)(route.getStops().size())+":");
				}
			}catch(InputMismatchException ime) {
				System.out.println("Enter index 0-"+(int)(route.getStops().size())+":");
			}
			in.nextLine();
		}while(!integer);
		//new station that will be added
		CTAStation station = new CTAStation(lat,lng,name,location,access,index);
		route.getStops().add(index,station);
	}
	//removes a station
	public static void removeStation(CTARoute route,String name) {
		//index of the station that will be removed
		int index = 0;
		//flag variable
		boolean removed = false;
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		//in case an error comes up
		while(!removed) {
			for(int i=0; i<route.getStops().size(); i++) 
				if(route.getStops().get(i).getName().equalsIgnoreCase(name)) {
					index = i;
					route.getStops().remove(index);
					removed = true;
				}
			if(!removed) {
				System.out.println("No such station on this route! Enter name:");
				name = in.nextLine();
			}
		}
	}
	//modifies a station
	public static void modifyStation(CTARoute route,String name) {
		//index of the station that will be modified
		int index = 0;
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		//flag variable
		boolean found = false;
		//in case an error comes up
		while(!found) {
			for(int i=0; i<route.getStops().size(); i++) 
				if(route.getStops().get(i).getName().equalsIgnoreCase(name)) {
					index = i;
					found = true;
				}
			if(!found) {
				System.out.println("No such station on this route! Enter name:");
				name = in.nextLine();
			}
		}
		System.out.println("Enter new name:");
		route.getStops().get(index).setName(in.next());
		//latitude
		double lat = -100;
		//in case an error comes up
		do {
			System.out.println("Enter latitude -90 to 90:");
			try {
				lat = in.nextDouble();
			}catch(InputMismatchException ime) {
				System.out.println("Enter a double value:");
			}
			in.nextLine();
		}while(!(lat>=-90 && lat<=90));
		route.getStops().get(index).setLat(lat);
		//longitude
		double lng = -200;
		//in case an error comes up
		do {
			System.out.println("Enter longitude -180 to 180:");
			try {
				lng = in.nextDouble();
			}catch(InputMismatchException ime) {
				System.out.println("Enter a double value:");
			}
			in.nextLine();
		}while(!(lng>=-180 && lng<=180));
		route.getStops().get(index).setLng(lng);

		System.out.println("Enter location:");
		route.getStops().get(index).setLocation(in.next());
		System.out.println("Enter wheelchair access:");
		//wheelchair access
		boolean access = false;
		//flag variable
		boolean bool = false;
		//in case an error comes up
		do {
			try {
				access = in.nextBoolean();
				bool = true;
			}catch(InputMismatchException ime) {
				System.out.println("Enter true/false:");
			}
			in.nextLine();
		} while(!bool);
		route.getStops().get(index).setWheelchair(access);;
	}
	//prints out the information about a station
	public static void infoStation(String name,CTARoute red,CTARoute green,CTARoute blue,CTARoute brown,
			CTARoute purple,CTARoute pink,CTARoute orange,CTARoute yellow){
		//flag variable
		boolean found = false;
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		while(!found) {
			if(findRoute(red,name)) {
				found = true;
			}
			if(findRoute(green,name)) {
				found = true;
			}
			if(findRoute(blue,name)) {
				found = true;
			}
			if(findRoute(brown,name)) {
				found = true;
			}
			if(findRoute(purple,name)) {
				found = true;
			}
			if(findRoute(pink,name)) {
				found = true;
			}
			if(findRoute(orange,name)) {
				found = true;
			}
			if(findRoute(yellow,name)) {
				found = true;
			}
			if(!found) {
				System.out.println("Enter name:");
				name = in.next();
			}
		}
	}
	//searches for the station on the route
	public static boolean findRoute(CTARoute route,String name) {
		//flag variable
		boolean printed = false;
		for(int i=0; i<route.getStops().size(); i++) 
			if(route.getStops().get(i).getName().equalsIgnoreCase(name)) {
				System.out.println(route.getName());
				System.out.println(route.getStops().get(i).toString());
				printed = true;
			}
		return printed;
	}
	//finds the nearest station to your geolocation
	public static void nearestStation(CTARoute red,CTARoute green,CTARoute blue,CTARoute brown,
			CTARoute purple,CTARoute pink,CTARoute orange,CTARoute yellow){
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		//latitude
		double lat = -100;
		//in case an error comes up
		do {
			System.out.println("Enter latitude -90 to 90:");
			try {
				lat = in.nextDouble();
			}catch(InputMismatchException ime) {
				System.out.println("Enter a double value:");
			}
			in.nextLine();
		}while(!(lat>=-90 && lat<=90));
		//longitude
		double lng = -200;
		//in case an error comes up
		do {
			System.out.println("Enter longitude -180 to 180:");
			try {
				lng = in.nextDouble();
			}catch(InputMismatchException ime) {
				System.out.println("Enter a double value:");
			}
			in.nextLine();
		}while(!(lng>=-180 && lng<=180));
		//nearest station on each line to user's geolocation
		CTAStation red_station = red.nearestStation(lat, lng);
		CTAStation green_station = green.nearestStation(lat, lng);
		CTAStation blue_station = blue.nearestStation(lat, lng);
		CTAStation brown_station = brown.nearestStation(lat, lng);
		CTAStation purple_station = purple.nearestStation(lat, lng);
		CTAStation pink_station = pink.nearestStation(lat, lng);
		CTAStation orange_station = orange.nearestStation(lat, lng);
		CTAStation yellow_station = yellow.nearestStation(lat, lng);
		//array of the distance between all nearest stations on each line to user's geolocation
		double[] min_distance = {GeoLocation.calcDistance(red_station.getLat(),lat,red_station.getLng(),lng),
				GeoLocation.calcDistance(green_station.getLat(),lat,green_station.getLng(),lng),
				GeoLocation.calcDistance(blue_station.getLat(),lat,blue_station.getLng(),lng),
				GeoLocation.calcDistance(brown_station.getLat(),lat,brown_station.getLng(),lng),
				GeoLocation.calcDistance(purple_station.getLat(),lat,purple_station.getLng(),lng),
				GeoLocation.calcDistance(pink_station.getLat(),lat,pink_station.getLng(),lng),
				GeoLocation.calcDistance(orange_station.getLat(),lat,orange_station.getLng(),lng),
				GeoLocation.calcDistance(yellow_station.getLat(),lat,yellow_station.getLng(),lng)};
		//distance to the nearest station
		double min = min_distance[0];
		//index of the nearest station
		int min_index = 0;
		for(int i=0;i<min_distance.length;i++) {
			if(min_distance[i] < min) {
				min = min_distance[i];
				min_index = i;
			}
		}
		switch (min_index) {
		case 0: System.out.println("The nearest station is on red line:"+ red_station.toString()); break;
		case 1: System.out.println("The nearest station is on green line:"+ green_station.toString()); break;
		case 2: System.out.println("The nearest station is on blue line:"+ blue_station.toString()); break;
		case 3: System.out.println("The nearest station is on brown line:"+ brown_station.toString());  break;
		case 4: System.out.println("The nearest station is on purple line:"+ purple_station.toString());  break;
		case 5: System.out.println("The nearest station is on pink line:"+ pink_station.toString()); break;
		case 6: System.out.println("The nearest station is on orange line:"+ orange_station.toString());  break;
		case 7: System.out.println("The nearest station is on yellow line:"+ yellow_station.toString()); break;
		default: System.out.println("No nearest station.");break;
		}
	}
	//prints out the stations with wheelchair access
	public static void wheelchair(CTARoute red,CTARoute green,CTARoute blue,CTARoute brown,
			CTARoute purple,CTARoute pink,CTARoute orange,CTARoute yellow){
		wheelchairRoute(red);
		wheelchairRoute(green);
		wheelchairRoute(blue);
		wheelchairRoute(brown);
		wheelchairRoute(purple);
		wheelchairRoute(pink);
		wheelchairRoute(orange);
		wheelchairRoute(yellow);
	}
	//finds stations with wheelchair acces on a route
	public static void wheelchairRoute(CTARoute route) {
		//counter
		int k = 0;
		System.out.println("\n"+route.getName()+":\n");
		for(int i=0;i<route.getStops().size();i++) {
			if(route.getStops().get(i).isWheelchair()) {
				System.out.println(route.getStops().get(i).getName());;
				k++;
			}
		}
		if(k == 0) System.out.println("none");
	}
	//creates the transit path 
	public static void path(CTARoute red,CTARoute green,CTARoute blue,CTARoute brown,CTARoute purple,
			CTARoute pink,CTARoute orange,CTARoute yellow) {
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		System.out.println("Enter first line (red,green,blue,brown,purple,pink,orange,yellow):");
		//string for user's input
		String input = in.next().toLowerCase();
		//first route
		CTARoute route1 = findRoute(input,red,green,blue,brown,purple,pink,orange,yellow);
		//in case an error comes up
		while(route1 == null) {
			System.out.println("Enter line (red,green,blue,brown,purple,pink,orange,yellow):"); input = in.next().toLowerCase();
			route1 = findRoute(input,red,green,blue,brown,purple,pink,orange,yellow);
		}
		
		System.out.println("Enter first station name:");
		//first station
		String station1 = in.next().toLowerCase();
		//flag variable
		boolean found = false;
		//in case an error comes up
		while(!found) {
			for(int i=0; i<route1.getStops().size(); i++) 
				if(route1.getStops().get(i).getName().equalsIgnoreCase(station1)) {
					found = true;
				}
			if(!found) {
				System.out.println("No such station on this line! Enter station:");
				station1 = in.next().toLowerCase();
			}
		}
		
		System.out.println("Enter second line (red,green,blue,brown,purple,pink,orange,yellow):");
		input = in.next().toLowerCase();
		//second route
		CTARoute route2 = findRoute(input,red,green,blue,brown,purple,pink,orange,yellow);
		//in case an error comes up
		while(route2 == null) {
			System.out.println("Enter line(red,green,blue,brown,purple,pink,orange,yellow):"); input = in.next().toLowerCase();
			route2 = findRoute(input,red,green,blue,brown,purple,pink,orange,yellow);
		}
		System.out.println("Enter second station name:");
		//second station
		String station2 = in.next().toLowerCase();
		found = false;
		//in case an error comes up
		while(!found) {
			for(int i=0; i<route2.getStops().size(); i++) 
				if(route2.getStops().get(i).getName().equalsIgnoreCase(station2)) {
					found = true;
				}
			if(!found) {
				System.out.println("No such station on this line! Enter station:");
				station2 = in.next().toLowerCase();
			}
		}
		
		if(route1.equals(yellow)) {
			yellowLine(route1,route2,yellow,red,purple,station1,station2);
		}else if(route2.equals(yellow)){
			yellowLine_2(route1,yellow,red,station1,station2);
		}else if(route1.equals(route2)) {
			sameLine(route1,route2,station1,station2);
		}else {
			diffLine(route1,route2,station1,station2);
		}
	}
	//in case there are more than one stations with the same name, this method allows the user to specify which one
	public static void diffLine_specify(CTARoute route1, CTARoute route2,String station1,String station2,
			ArrayList<Integer> first,ArrayList<Integer> second) {
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		if(first.size() > 1 && second.size() > 1) {
			System.out.println("Please specify the station:");
			for(int i=0;i<first.size();i++) {
				System.out.println(first.get(i)+" "+route1.getStops().get(first.get(i)).toString());
			}
			//first station index
			int index1 = in.nextInt();
			
			System.out.println("Please specify the station:");
			for(int i=0;i<second.size();i++) {
				System.out.println(second.get(i)+" "+route2.getStops().get(second.get(i)).toString());
			}
			//second station index
			int index2 = in.nextInt();
			diffLine_2(route1,route2,index1,index2);
		}else if(first.size() > 1) {
			System.out.println("Please specify the station:");
			for(int i=0;i<first.size();i++) {
				System.out.println(first.get(i)+" "+route1.getStops().get(first.get(i)).toString());
			}
			//first station index
			int index1 = in.nextInt();
			//second station index
			int index2 = second.get(0);
			diffLine_2(route1,route2,index1,index2);
		}else if(second.size() > 1) {
			System.out.println("Please specify the station:");
			for(int i=0;i<second.size();i++) {
				System.out.println(second.get(i)+" "+route2.getStops().get(second.get(i)).toString());
			}
			//second station index
			int index2 = in.nextInt();
			//first station index
			int index1 = first.get(0);
			diffLine_2(route1,route2,index1,index2);
		}
	}
	//finds the transit path after specifying which station between stations with same name on same line/route
	public static void diffLine_2(CTARoute route1, CTARoute route2,int index1,int index2) {
		//counter
		int j;
		//index of the common station on first route
		int id_common1 = -1;
		//index of the common station on second route
		int id_common2 = -1;
		//flag variable
		boolean found = false;
		
			for(int i=index1; i<route1.getStops().size();i++) {
				j = i;
				for(int k=index2; k>=0;k--) {
					if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
						if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
								(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
							id_common1 = j;
							id_common2 = k;
							found = true;
							break;
						}
					}
				}
				if(found) {
					break;
				}
			}

			for(int i=index1; i>=0;i--) {
				j = i;
				for(int k=index2; k<route2.getStops().size();k++) {
					if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
						if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
								(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
							id_common1 = j;
							id_common2 = k;
							found = true;
							break;
						}
					}
				}
				if(found) {
					break;
				}
			}
			
			if(!found) {
				for(int i=index1;i>=0;i--) {
					j = i;
					for(int k=index2; k>=0;k--) {
						if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
							if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
									(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
								id_common1 = j;
								id_common2 = k;
								found = true;
								break;
							}
						}
					}	
					if(found) {
						break;
					}
				}
			}
			
			if(!found) {
				for(int i=index1;i<route1.getStops().size();i++) {
					j = i;
					for(int k=index2; k<route2.getStops().size();k++) {
						if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
							if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
									(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
								id_common1 = j;
								id_common2 = k;
								found = true;
								break;
							}
						}
					}	
					if(found) {
						break;
					}
				}
			}	
		
			if((id_common1 != -1) && (id_common2 != -1)) {
				System.out.println("YOUR PATH IS\r\n");
				System.out.println(route1.getName().toUpperCase()+"\r\n");
				if(id_common1>index1) {
					for(int i=index1;i<=id_common1;i++)
						System.out.println(route1.getStops().get(i).getName());
					System.out.println();
				}else {
					for(int i=index1;i>=id_common1;i--)
						System.out.println(route1.getStops().get(i).getName());
					System.out.println();
				}
				
				System.out.println(route2.getName().toUpperCase()+"\r\n");
				if(id_common2>index2) {
					for(int i=id_common2;i>=index2;i--)
						System.out.println(route2.getStops().get(i).getName());
					System.out.println();
				}else {
					for(int i=id_common2;i<=index2;i++)
						System.out.println(route2.getStops().get(i).getName());
					System.out.println();
				}
				file_diff_2(route1,route2,id_common1, id_common2,index1,index2);
			}
	}
	//saves the path to a file for the diffLine_2 method
	public static void file_diff_2(CTARoute route1,CTARoute route2,int id_common1,int id_common2,int index1,int index2) {
		System.out.println("Do you want to save the changes in a file? 0 for no, 1 for yes");
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		if(save(in.nextLine())) {
			System.out.println("Enter name of the file:");
			//name of the file
			String name = in.next();
			//flag variable
			boolean wrote = false;
			while(!wrote) {
				try {
					//a file writer 
					FileWriter fw = new FileWriter(name, true);
					fw.write("YOUR PATH IS"+System.getProperty("line.separator")+System.getProperty("line.separator"));
					fw.write(route1.getName().toUpperCase()+System.getProperty("line.separator")+System.getProperty("line.separator"));
					if(id_common1>index1) {
						for(int i=index1;i<=id_common1;i++)
							fw.write(route1.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
					}else {
						for(int i=index1;i>=id_common1;i--)
							fw.write(route1.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
					}
					
					fw.write(route2.getName().toUpperCase()+System.getProperty("line.separator")+System.getProperty("line.separator"));
					if(id_common2>index2) {
						for(int i=id_common2;i>=index2;i--)
							fw.write(route2.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
					}else {
						for(int i=id_common2;i<=index2;i++)
							fw.write(route2.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
					}
					fw.flush();
					fw.close();	
					wrote = true;
				} catch(IOException io) {
					System.out.println("An error occurred with this file");
					System.out.println("Enter name of the file:");
					name = in.next();
				}
			}
		}
	}
	//creates the path if the stations are on different lines
	public static void diffLine(CTARoute route1, CTARoute route2,String station1,String station2) {
			//counter
			int j;
			//index of the common station on the first route
			int id_common1 = -1;
			//index of the common station on the second route
			int id_common2 = -1;
			//arraylist of the indexes of the stations with the name of the first station on the first route
			ArrayList<Integer> first = findIndex(route1,station1);
			//arraylist of the indexes of the stations with the name of the second station on the second route
			ArrayList<Integer> second = findIndex(route2,station2);
			if(first.size() == 1 && second.size() == 1) {
				//flag variable
				boolean found = false;
				
				for(int i=first.get(0); i<route1.getStops().size();i++) {
					j = i;
					for(int k=second.get(0); k>=0;k--) {
						if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
							if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
									(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
								id_common1 = j;
								id_common2 = k;
								found = true;
								break;
							}
						}
					}
					if(found) {
						break;
					}
				}

				if(!found) {
					for(int i=first.get(0);i>=0;i--) {
						j = i;
						for(int k=second.get(0); k>0;k--) {
							if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
								if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
										(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
									id_common1 = j;
									id_common2 = k;
									found = true;
									break;
								}
							}
						}	
						if(found) {
							break;
						}
					}
				}	
				
				if(!found) {
					for(int i=first.get(0);i<route1.getStops().size();i++) {
						j = i;
						for(int k=second.get(0); k<route2.getStops().size();k++) {
							if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
								if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
										(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
									id_common1 = j;
									id_common2 = k;
									found = true;
									break;
								}
							}
						}	
						if(found) {
							break;
						}
					}
				}	
			
			if((id_common1 != -1) && (id_common2 != -1)) {
				System.out.println("YOUR PATH IS\r\n");
				System.out.println(route1.getName().toUpperCase()+"\r\n");
				if(id_common1>first.get(0)) {
					for(int i=first.get(0);i<=id_common1;i++)
						System.out.println(route1.getStops().get(i).getName());
					System.out.println();
				}else {
					for(int i=first.get(0);i>=id_common1;i--)
						System.out.println(route1.getStops().get(i).getName());
					System.out.println();
				}
				
				System.out.println(route2.getName().toUpperCase()+"\r\n");
				if(id_common2>second.get(0)) {
					for(int i=id_common2;i>=second.get(0);i--)
						System.out.println(route2.getStops().get(i).getName());
					System.out.println();
				}else {
					for(int i=id_common2;i<=second.get(0);i++)
						System.out.println(route2.getStops().get(i).getName());
					System.out.println();
				}
				file_diff(route1,route2,id_common1,id_common2,first,second);
			}
		} else {
			diffLine_specify(route1,route2,station1,station2,first,second);
		}		
	}
	//saves the path in a file for the diffLine mathod
	public static void file_diff(CTARoute route1, CTARoute route2,int id_common1, int id_common2,ArrayList<Integer> first,
			ArrayList<Integer> second) {
		System.out.println("Do you want to save the changes in a file? 0 for no, 1 for yes");
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		if(save(in.nextLine())) {
			System.out.println("Enter name of the file:");
			//name of the file
			String name = in.next();
			//flag variable
			boolean wrote = false;
			while(!wrote) {
				try {
					//a file writer 
					FileWriter fw = new FileWriter(name, true);
					fw.write("YOUR PATH IS"+System.getProperty("line.separator")+System.getProperty("line.separator"));
					fw.write(route1.getName().toUpperCase()+System.getProperty("line.separator")+System.getProperty("line.separator"));
					if(id_common1>first.get(0)) {
						for(int i=first.get(0);i<=id_common1;i++)
							fw.write(route1.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
					}else {
						for(int i=first.get(0);i>=id_common1;i--)
							fw.write(route1.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
					}
					
					fw.write(route2.getName().toUpperCase()+System.getProperty("line.separator")+System.getProperty("line.separator"));
					if(id_common2>second.get(0)) {
						for(int i=id_common2;i>=second.get(0);i--)
							fw.write(route2.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
					}else {
						for(int i=id_common2;i<=second.get(0);i++)
							fw.write(route2.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
					}
					fw.flush();
					fw.close();	
					wrote = true;
				} catch(IOException io) {
					System.out.println("An error occurred with this file");
					System.out.println("Enter name of the file:");
					name = in.next();
				}
			}
		}
	}
	//if more than one station is found on the same line, then this method allows the user to specify which station
	public static void sameLine_specify(CTARoute route1, CTARoute route2,String station1,String station2,
			ArrayList<Integer> first,ArrayList<Integer> second) {
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		if(first.size() > 1 && second.size() > 1) {
			System.out.println("Please specify the station:");
			for(int i=0;i<first.size();i++) {
				System.out.println(first.get(i));
			}
			//index of the first station
			int index1 = in.nextInt();
			
			System.out.println("Please specify the station:");
			for(int i=0;i<second.size();i++) {
				System.out.println(second.get(i));
			}
			//index of the second station
			int index2 = in.nextInt();
			sameLine_2(route1,index1,index2);
		}else if(first.size() > 1) {
			System.out.println("Please specify the station:");
			for(int i=0;i<first.size();i++) {
				System.out.println(first.get(i));
			}
			//index of the first station
			int index1 = in.nextInt();
			//index of the seconds station
			int index2 = second.get(0);
			sameLine_2(route1,index1,index2);
		}else if(second.size() > 1) {
			System.out.println("Please specify the station:");
			for(int i=0;i<second.size();i++) {
				System.out.println(second.get(i));
			}
			//index of the second station
			int index2 = in.nextInt();
			//index of the first station
			int index1 = first.get(0);
			sameLine_2(route1,index1,index2);
		}
	}
	//finds the transit path if the stations are on the same line after specifying the stations that had same name as other stations
	//on the same line/route
	public static void sameLine_2(CTARoute route,int index1,int index2) {
		System.out.println("YOUR PATH IS"+"\r\n");
		System.out.println(route.getName().toUpperCase()+"\r\n");
		if(index1<index2) {
			for(int i=index1;i<=index2;i++) {
				System.out.println(route.getStops().get(i).getName());
			System.out.println();
			}
		}else {
			for(int i=index1;i>=index2;i--) {
				System.out.println(route.getStops().get(i).getName());
			System.out.println();
			}
		}
		file_same_2(route,index1,index2);
	}
	//saves the path in a file for the sameline_2 method
	public static void file_same_2(CTARoute route, int index1, int index2) {
		System.out.println("Do you want to save the changes in a file? 0 for no, 1 for yes");
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		if(save(in.nextLine())) {
			System.out.println("Enter name of the file:");
			//name of the file
			String name = in.next();
			//flag variable
			boolean wrote = false;
			while(!wrote) {
				try {
					//a file writer 
					FileWriter fw = new FileWriter(name, true);
					fw.write("YOUR PATH IS"+System.getProperty("line.separator")+System.getProperty("line.separator"));
					
					fw.write(route.getName().toUpperCase()+System.getProperty("line.separator")+System.getProperty("line.separator"));
					if(index1<index2) {
						for(int i=index1;i<=index2;i++) {
							fw.write(route.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
						}
					}else {
						for(int i=index1;i>=index2;i--) {
							fw.write(route.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
						}
					}
					
					fw.flush();
					fw.close();	
					wrote = true;
				} catch(IOException io) {
					System.out.println("An error occurred with this file");
					System.out.println("Enter name of the file:");
					name = in.next();
				}
			}
		}
	}
	//finds transit path if the stations are on the same line
	public static void sameLine(CTARoute route1, CTARoute route2,String station1,String station2) {
		
		if(route1.equals(route2)) {
			//arraylist of the indexes of the stations with the name of the first station
			ArrayList<Integer> first = findIndex(route1,station1);
			//index of the first station
			int first_index;
			//arraylist of the indexes of the stations with the name of the second station
			ArrayList<Integer> second = findIndex(route1,station2);
			//index of the second station
			int second_index;

			if(first.size() == 1 && second.size() == 1) {
				if(first.get(0)<second.get(0)) {
					first_index = first.get(0);
					second_index = second.get(0);
				} else {
					first_index = second.get(0);
					second_index = first.get(0);
				}
				System.out.println("YOUR PATH IS\r\n");
				System.out.println(route1.getName().toUpperCase()+"\r\n");
				for(int i=first_index;i<=second_index;i++)
					System.out.println(route1.getStops().get(i).getName());
				System.out.println();
				file_same(route1,first_index,second_index);
			}else {
				sameLine_specify(route1,route2,station1,station2,first,second);
			}
		}
		
	}
	//saves the path for the same line method
	public static void file_same(CTARoute route1, int first_index,int second_index) {

		System.out.println("Do you want to save the changes in a file? 0 for no, 1 for yes");
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		if(save(in.nextLine())) {
			System.out.println("Enter name of the file:");
			//name of the file
			String name = in.next();
			//flag variable
			boolean wrote = false;
			while(!wrote) {
				try {
					//a file writer 
					FileWriter fw = new FileWriter(name, true);
					fw.write("YOUR PATH IS"+System.getProperty("line.separator")+System.getProperty("line.separator"));
					fw.write(route1.getName().toUpperCase()+System.getProperty("line.separator")+System.getProperty("line.separator"));
					for(int i=first_index;i<=second_index;i++)
						fw.write(route1.getStops().get(i).getName()+System.getProperty("line.separator"));
					fw.write(System.getProperty("line.separator"));
					fw.flush();
					fw.close();	
					wrote = true;
				} catch(IOException io) {
					System.out.println("An error occurred with this file");
					System.out.println("Enter name of the file:");
					name = in.next();
				}
			}
		}
	}
	//finds transit path if the second route is yellow
	public static void yellowLine_2(CTARoute route1,CTARoute yellow,CTARoute red,String station1,String station2) {
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		diffLineForYellow_2(route1,red,station1,"Howard");
		ArrayList<Integer> first_yellow = findIndex(yellow,station2);
		System.out.println("YELLOW\r\n");
		for(int i=yellow.getStops().size()-1; i>=first_yellow.get(0);i--) 
			System.out.println(yellow.getStops().get(i).getName());
		System.out.println();
		
		//counter
		int j;
		//index of the common station on the first route
		int id_common1 = -1;
		//index of the common station on the second route
		int id_common2 = -1;
		//arraylist of the indexes of the stations with the name of the first station on the first route
		ArrayList<Integer> first = findIndex(route1,station1);
		//arraylist of the indexes of the stations with the name of the second station on the second route
		ArrayList<Integer> second = findIndex(red,"Howard");
		if(first.size() == 1 && second.size() == 1) {
			//flag variable
			boolean found = false;
			
			for(int i=first.get(0); i<route1.getStops().size();i++) {
				j = i;
				for(int k=second.get(0); k>=0;k--) {
					if(route1.getStops().get(j).getName().equals(red.getStops().get(k).getName())) {
						if((route1.getStops().get(j).getLat() == red.getStops().get(k).getLat()) && 
								(route1.getStops().get(j).getLng() == red.getStops().get(k).getLng())) {
							id_common1 = j;
							id_common2 = k;
							found = true;
							break;
						}
					}
				}
				if(found) {
					break;
				}
			}

			if(!found) {
				for(int i=first.get(0);i>=0;i--) {
					j = i;
					for(int k=second.get(0); k>0;k--) {
						if(route1.getStops().get(j).getName().equals(red.getStops().get(k).getName())) {
							if((route1.getStops().get(j).getLat() == red.getStops().get(k).getLat()) && 
									(route1.getStops().get(j).getLng() == red.getStops().get(k).getLng())) {
								id_common1 = j;
								id_common2 = k;
								found = true;
								break;
							}
						}
					}	
					if(found) {
						break;
					}
				}
			}	
			
			if(!found) {
				for(int i=first.get(0);i<route1.getStops().size();i++) {
					j = i;
					for(int k=second.get(0); k<red.getStops().size();k++) {
						if(route1.getStops().get(j).getName().equals(red.getStops().get(k).getName())) {
							if((route1.getStops().get(j).getLat() == red.getStops().get(k).getLat()) && 
									(route1.getStops().get(j).getLng() == red.getStops().get(k).getLng())) {
								id_common1 = j;
								id_common2 = k;
								found = true;
								break;
							}
						}
					}	
					if(found) {
						break;
					}
				}
			}	
		//saves the file
		file_yellow_2(yellow,first_yellow,route1,red,id_common1,id_common2,first,second);	
		}
	}
	//a different version for diffLine method special for yellowLine_2
	public static void diffLineForYellow_2(CTARoute route1, CTARoute route2,String station1,String station2) {
		//counter
		int j;
		//index of the common station on the first route
		int id_common1 = -1;
		//index of the common station on the second route
		int id_common2 = -1;
		//arraylist of the indexes of the stations with the name of the first station on the first route
		ArrayList<Integer> first = findIndex(route1,station1);
		//arraylist of the indexes of the stations with the name of the second station on the second route
		ArrayList<Integer> second = findIndex(route2,station2);
		if(first.size() == 1 && second.size() == 1) {
			//flag variable
			boolean found = false;
			
			for(int i=first.get(0); i<route1.getStops().size();i++) {
				j = i;
				for(int k=second.get(0); k>=0;k--) {
					if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
						if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
								(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
							id_common1 = j;
							id_common2 = k;
							found = true;
							break;
						}
					}
				}
				if(found) {
					break;
				}
			}

			if(!found) {
				for(int i=first.get(0);i>=0;i--) {
					j = i;
					for(int k=second.get(0); k>0;k--) {
						if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
							if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
									(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
								id_common1 = j;
								id_common2 = k;
								found = true;
								break;
							}
						}
					}	
					if(found) {
						break;
					}
				}
			}	
			
			if(!found) {
				for(int i=first.get(0);i<route1.getStops().size();i++) {
					j = i;
					for(int k=second.get(0); k<route2.getStops().size();k++) {
						if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
							if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
									(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
								id_common1 = j;
								id_common2 = k;
								found = true;
								break;
							}
						}
					}	
					if(found) {
						break;
					}
				}
			}	
		
		if((id_common1 != -1) && (id_common2 != -1)) {
			System.out.println("YOUR PATH IS\r\n");
			System.out.println(route1.getName().toUpperCase()+"\r\n");
			if(id_common1>first.get(0)) {
				for(int i=first.get(0);i<=id_common1;i++)
					System.out.println(route1.getStops().get(i).getName());
				System.out.println();
			}else {
				for(int i=first.get(0);i>=id_common1;i--)
					System.out.println(route1.getStops().get(i).getName());
				System.out.println();
			}
			
			System.out.println(route2.getName().toUpperCase()+"\r\n");
			if(id_common2>second.get(0)) {
				for(int i=id_common2;i>=second.get(0);i--)
					System.out.println(route2.getStops().get(i).getName());
				System.out.println();
			}else {
				for(int i=id_common2;i<=second.get(0);i++)
					System.out.println(route2.getStops().get(i).getName());
				System.out.println();
			}
		}
	} else {
		diffLine_specify(route1,route2,station1,station2,first,second);
	}		
}
	//saves the yellow line part of the path for the yellowLine_2 method
	public static void file_yellow_2(CTARoute yellow,ArrayList<Integer> first_yellow,CTARoute route1,
			CTARoute route2,int id_common1, int id_common2,ArrayList<Integer> first,
			ArrayList<Integer> second) {
		System.out.println("Do you want to save the changes in a file? 0 for no, 1 for yes");
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		if(save(in.nextLine())) {
			System.out.println("Enter name of the file:");
			//name of the file
			String name = in.next();
			//flag variable
			boolean wrote = false;
			while(!wrote) {
				try {
					//a file writer 
					FileWriter fw = new FileWriter(name, true);
					fw.write("YOUR PATH IS"+System.getProperty("line.separator")+System.getProperty("line.separator"));
					fw.write(route1.getName().toUpperCase()+System.getProperty("line.separator")+System.getProperty("line.separator"));
					if(id_common1>first.get(0)) {
						for(int i=first.get(0);i<=id_common1;i++)
							fw.write(route1.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
					}else {
						for(int i=first.get(0);i>=id_common1;i--)
							fw.write(route1.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
					}
					
					fw.write(route2.getName().toUpperCase()+System.getProperty("line.separator")+System.getProperty("line.separator"));
					if(id_common2>second.get(0)) {
						for(int i=id_common2;i>=second.get(0);i--)
							fw.write(route2.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
					}else {
						for(int i=id_common2;i<=second.get(0);i++)
							fw.write(route2.getStops().get(i).getName()+System.getProperty("line.separator"));
						fw.write(System.getProperty("line.separator"));
					}
					
					fw.write("YELLOW"+System.getProperty("line.separator")+System.getProperty("line.separator"));
					for(int i=yellow.getStops().size()-1; i>=first_yellow.get(0);i--) 
						fw.write(yellow.getStops().get(i).getName()+System.getProperty("line.separator"));
					fw.write(System.getProperty("line.separator"));
					
					fw.flush();
					fw.close();	
					wrote = true;
				} catch(IOException io) {
					System.out.println("An error occurred with this file");
					System.out.println("Enter name of the file:");
					name = in.next();
				}
			}
		}
	}
	//finds the transit path if the first route is yellow
	public static void yellowLine(CTARoute route1, CTARoute route2, CTARoute yellow, CTARoute red,
			CTARoute purple,String station1,String station2) {
		//arraylist with the index of the the yellow station line
		ArrayList<Integer> first_yellow = findIndex(yellow,station1);
		//arraylist with the index of the the purple station line
		ArrayList<Integer> second_purple = findIndex(route2,station2);
		//arraylist with the index of the the red station line
		ArrayList<Integer> second_red = findIndex(route2,station2);
		if(route1.equals(yellow)) {
			System.out.println("YOUR PATH IS\r\n");
			System.out.println("YELLOW\r\n");
			for(int i=first_yellow.get(0); i<yellow.getStops().size();i++) 
				System.out.println(yellow.getStops().get(i).getName());
			System.out.println();
			//second route is purple
			if(route2.equals(purple)) {
				System.out.println("PURPLE\r\n");
				if(second_purple.size() == 1) {
					if(second_purple.get(0)>=8) {
						for(int i=8; i<=second_purple.get(0);i++)
							System.out.println(route2.getStops().get(i).getName());
						System.out.println();
					}else {
						for(int i=8; i>=second_purple.get(0);i--)
							System.out.println(route2.getStops().get(i).getName());
						System.out.println();
					}
				}
				//second route is red
			}else if(route2.equals(red)) {
				System.out.println("RED/r/n");
					for(int i=0; i<=second_red.get(0);i++)
						System.out.println(route2.getStops().get(i).getName());
					System.out.println();
				}else if(!(route2.equals(red) && route2.equals(purple))) {
				diffLineForYellow(red,route2,red.getStops().get(0).getName(),station2);
			}
		}
		
		//saves the file
		file_yellow(yellow,purple,red,route2,first_yellow,second_purple,second_red,station2);
}
	//a different version for diffLine method special for yellowLine
	public static void diffLineForYellow(CTARoute route1, CTARoute route2,String station1,String station2) {
		//counter
		int j;
		//index of the common station on the first route
		int id_common1 = -1;
		//index of the common station on the second route
		int id_common2 = -1;
		//arraylist of the indexes of the stations with the name of the first station on the first route
		ArrayList<Integer> first = findIndex(route1,station1);
		//arraylist of the indexes of the stations with the name of the second station on the second route
		ArrayList<Integer> second = findIndex(route2,station2);
		if(first.size() == 1 && second.size() == 1) {
			//flag variable
			boolean found = false;
			
			for(int i=first.get(0); i<route1.getStops().size();i++) {
				j = i;
				for(int k=second.get(0); k>=0;k--) {
					if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
						if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
								(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
							id_common1 = j;
							id_common2 = k;
							found = true;
							break;
						}
					}
				}
				if(found) {
					break;
				}
			}

			if(!found) {
				for(int i=first.get(0);i>=0;i--) {
					j = i;
					for(int k=second.get(0); k>0;k--) {
						if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
							if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
									(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
								id_common1 = j;
								id_common2 = k;
								found = true;
								break;
							}
						}
					}	
					if(found) {
						break;
					}
				}
			}	
			
			if(!found) {
				for(int i=first.get(0);i<route1.getStops().size();i++) {
					j = i;
					for(int k=second.get(0); k<route2.getStops().size();k++) {
						if(route1.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
							if((route1.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
									(route1.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
								id_common1 = j;
								id_common2 = k;
								found = true;
								break;
							}
						}
					}	
					if(found) {
						break;
					}
				}
			}	
		
		if((id_common1 != -1) && (id_common2 != -1)) {
			System.out.println(route1.getName().toUpperCase()+"\r\n");
			if(id_common1>first.get(0)) {
				for(int i=first.get(0);i<=id_common1;i++)
					System.out.println(route1.getStops().get(i).getName());
				System.out.println();
			}else {
				for(int i=first.get(0);i>=id_common1;i--)
					System.out.println(route1.getStops().get(i).getName());
				System.out.println();
			}
			
			System.out.println(route2.getName().toUpperCase()+"\r\n");
			if(id_common2>second.get(0)) {
				for(int i=id_common2;i>=second.get(0);i--)
					System.out.println(route2.getStops().get(i).getName());
				System.out.println();
			}else {
				for(int i=id_common2;i<=second.get(0);i++)
					System.out.println(route2.getStops().get(i).getName());
				System.out.println();
			}
		}
	} else {
		diffLine_specify(route1,route2,station1,station2,first,second);
	}
}
	//saves the path in case first line is yellow
	public static void file_yellow(CTARoute yellow,CTARoute purple,CTARoute red,CTARoute route2,
			ArrayList<Integer> first_yellow,ArrayList<Integer> second_purple,
			ArrayList<Integer> second_red,String station2) {
		System.out.println("Do you want to save the changes in a file? 0 for no, 1 for yes");
		//scanner for user's input
		Scanner in = new Scanner(System.in);
		if(save(in.nextLine())) {
			System.out.println("Enter name of the file:");
			//name of the file
			String name = in.next();
			//flag variable
			boolean wrote = false;
			while(!wrote) {
				try {
					//a file writer 
					FileWriter fw = new FileWriter(name, true);
					fw.write("YOUR PATH IS"+System.getProperty("line.separator")+System.getProperty("line.separator"));
					
					fw.write("YELLOW"+System.getProperty("line.separator")+System.getProperty("line.separator"));
					for(int i=first_yellow.get(0); i<yellow.getStops().size();i++) 
						fw.write(yellow.getStops().get(i).getName()+System.getProperty("line.separator"));
					fw.write(System.getProperty("line.separator"));
					
					if(route2.equals(purple)) {
						fw.write("PURPLE"+System.getProperty("line.separator")+System.getProperty("line.separator"));
						if(second_purple.size() == 1) {
							if(second_purple.get(0)>=8) {
								for(int i=8; i<=second_purple.get(0);i++)
									fw.write(route2.getStops().get(i).getName()+System.getProperty("line.separator"));
								fw.write(System.getProperty("line.separator"));
							}else {
								for(int i=8; i>=second_purple.get(0);i--)
									fw.write(route2.getStops().get(i).getName()+System.getProperty("line.separator"));
								fw.write(System.getProperty("line.separator"));
							}
						}
						//second route is red
					}else if(route2.equals(red)) {
						fw.write("RED"+System.getProperty("line.separator")+System.getProperty("line.separator"));
							for(int i=0; i<=second_red.get(0);i++)
								fw.write(route2.getStops().get(i).getName()+System.getProperty("line.separator"));
							fw.write(System.getProperty("line.separator"));
						}else if(!(route2.equals(red) && route2.equals(purple))) {
							//counter
							int j;
							//index of the common station on the first route
							int id_common1 = -1;
							//index of the common station on the second route
							int id_common2 = -1;
							//arraylist of the indexes of the stations with the name of the first station on the first route
							ArrayList<Integer> first = findIndex(red,red.getStops().get(0).getName());
							//arraylist of the indexes of the stations with the name of the second station on the second route
							ArrayList<Integer> second = findIndex(route2,station2);
							if(first.size() == 1 && second.size() == 1) {
								//flag variable
								boolean found = false;
								
								for(int i=first.get(0); i<red.getStops().size();i++) {
									j = i;
									for(int k=second.get(0); k>=0;k--) {
										if(red.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
											if((red.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
													(red.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
												id_common1 = j;
												id_common2 = k;
												found = true;
												break;
											}
										}
									}
									if(found) {
										break;
									}
								}

								if(!found) {
									for(int i=first.get(0);i>=0;i--) {
										j = i;
										for(int k=second.get(0); k>0;k--) {
											if(red.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
												if((red.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
														(red.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
													id_common1 = j;
													id_common2 = k;
													found = true;
													break;
												}
											}
										}	
										if(found) {
											break;
										}
									}
								}	
								
								if(!found) {
									for(int i=first.get(0);i<red.getStops().size();i++) {
										j = i;
										for(int k=second.get(0); k<route2.getStops().size();k++) {
											if(red.getStops().get(j).getName().equals(route2.getStops().get(k).getName())) {
												if((red.getStops().get(j).getLat() == route2.getStops().get(k).getLat()) && 
														(red.getStops().get(j).getLng() == route2.getStops().get(k).getLng())) {
													id_common1 = j;
													id_common2 = k;
													found = true;
													break;
												}
											}
										}	
										if(found) {
											break;
										}
									}
								}	
							
							if((id_common1 != -1) && (id_common2 != -1)) {
								fw.write(red.getName().toUpperCase()+System.getProperty("line.separator")+System.getProperty("line.separator"));
								if(id_common1>first.get(0)) {
									for(int i=first.get(0);i<=id_common1;i++)
										fw.write(red.getStops().get(i).getName()+System.getProperty("line.separator"));
									fw.write(System.getProperty("line.separator"));
								}else {
									for(int i=first.get(0);i>=id_common1;i--)
										fw.write(red.getStops().get(i).getName()+System.getProperty("line.separator"));
									fw.write(System.getProperty("line.separator"));
								}
								
								fw.write(route2.getName().toUpperCase()+System.getProperty("line.separator")+System.getProperty("line.separator"));
								if(id_common2>second.get(0)) {
									for(int i=id_common2;i>=second.get(0);i--)
										fw.write(route2.getStops().get(i).getName()+System.getProperty("line.separator"));
									fw.write(System.getProperty("line.separator"));
								}else {
									for(int i=id_common2;i<=second.get(0);i++)
										fw.write(route2.getStops().get(i).getName()+System.getProperty("line.separator"));
									fw.write(System.getProperty("line.separator"));
								}
							}
						}/* else {
							diffLine_specify(red,route2,red.getStops().get(0).getName(),station2,first,second);
						}*/
					}
					fw.flush();
					fw.close();	
					wrote = true;
				} catch(IOException io) {
					System.out.println("An error occurred with this file");
					System.out.println("Enter name of the file:");
					name = in.next();
				}
			}
		}
	}
	//finds the indexes of the stations with the same name on a route
	public static ArrayList<Integer> findIndex(CTARoute route, String name) {
		//arraylist with the indexes of the stations with the same name
		ArrayList<Integer> index = new ArrayList<Integer>();
		for(int i=0; i<route.getStops().size(); i++) 
			if(route.getStops().get(i).getName().equalsIgnoreCase(name)) {
				index.add(i);
			}
		return index;
	}
}

