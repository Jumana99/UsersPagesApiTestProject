import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

public class GetUsersPages {

	
	
@Test
public static void getAllUsers() throws IOException, ParseException {
		// TODO Auto-generated method stub
    	
    	final String MAIN_URL = "https://reqres.in"; 		
    	final String GET_ALL_USERS_URL = MAIN_URL + "/api/users?page=";
    	

    	
    	int i=1;

    	while(true) {
    		
    	   String GET_ALL_USERS_URL_PAGES = GET_ALL_USERS_URL + i;

    	   //System.out.println(GET_ALL_USERS_URL_PAGES);
    		
    		URL url = new URL(GET_ALL_USERS_URL_PAGES);
    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    		connection.setRequestMethod("GET");
    		connection.connect();
    		
    		//Getting the response code
    		int responseCode = connection.getResponseCode();
    		
    		if (responseCode != 200) {
    			throw new RuntimeException("HttpResponseCode: " + responseCode);
    			
			      } else {
			        	
			        	
			    	     String urlResponse = getResponse(url);	
	      
			            //print all users 
			        	 List<User> usersList = convertURLResponseToUsersObjects(urlResponse);
			    	     for (User user: usersList) {
			    	        	
			    				System.out.println(user);
			    				System.out.println("------------------------------------------------------------------------\n\n");
			    			} 
			     }
    		i++;
    	}
 	
}

public static String getResponse (URL url ) throws IOException {
	
	Scanner scanner = new Scanner(url.openStream());
	String urlResponse = "";
	//Write all the JSON data into a string using a scanner
    while (scanner.hasNext()) {
    	urlResponse += scanner.nextLine();
       // System.out.println(urlResponse);
    }

    //Close the scanner
    scanner.close();
    
    return urlResponse;
}

			
public static  List <User> convertURLResponseToUsersObjects(String urlResponse) throws ParseException {
	
	//Using the JSON simple library parse the string into a json object
	JSONParser parse = new JSONParser();
	JSONObject dataObject = (JSONObject) parse.parse(urlResponse);
	
	JSONArray array = (JSONArray) dataObject.get("data");
	List<User> usersList = new ArrayList<>();
	//int counter = 1;
	
	for (int i = 0; i < array.size(); i++) {
		
		JSONObject newObject = (JSONObject) array.get(i);
		String id = newObject.get("id").toString();
		String lastName = newObject.get("last_name").toString();
		String firstName = newObject.get("first_name").toString();
		String email = newObject.get("email").toString();
		String avatar = newObject.get("avatar").toString();
  
		//create object of user
		User user = new User (id, firstName, lastName, email, avatar);
		
		usersList.add(user);

	}
    return usersList;
}

}
