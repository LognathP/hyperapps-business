import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.hyperapps.logger.ConfigProperties;
import com.hyperapps.model.DeliveryInfo;
import com.hyperapps.model.Store;
import com.hyperapps.util.DESEncryptor;

public class Test {
	 @Autowired
	    private Environment env;
	
	public void smsTest()
	{
//		final String uri = "http://bhashsms.com/api/sendmsg.php?user=8754556606&pass=984b5ac&sender=HPRLNK&phone=9965861660&text=Test SMS&priority=ndnd&stype=normal";
//
//	    RestTemplate restTemplate = new RestTemplate();
//	    String result = restTemplate.getForObject(uri, String.class);
//
//	    System.out.println(result);
		
		System.out.println(env);
		
		String smsUrl = env.getProperty("sms.url")+env.getProperty("sms.username")
		+env.getProperty("sms.password")+env.getProperty("sms.senderid")
		+"&phone="+"9965861660"+env.getProperty("sms.login.msg")+"32423"
		+env.getProperty("sms.priority.ndnd")+env.getProperty("sms.type.normal");
		
		System.out.println(smsUrl);
	}
	public static void main(String[] args) throws Exception {
		
			//new Test().smsTest();
//		System.out.println(DESEncryptor.encrypt("Test@123", "hyperapp123"));
//		String en = DESEncryptor.encrypt("Test@123", "hyperapp123");
//		System.out.println(DESEncryptor.decrypt(en, "hyperapp123"));
//		Gson gson = new Gson(); 
//		//String userJson = "[{\"name\":\"Chennai\",\"lat\":\"12.920423\",\"long\":\"80.097207\",\"address\":\"51, Ramani Nagar, West Tambaram, Chennai, Tamilnadu - 600045, India\"}]";
//		String userJson = "[{\"phone\": \"8888888888\"},{\"phone\": \"9952175446\"},{\"phone\": \"9952175444\"}]";
//		
//		BusinessPhone [] userArray = gson.fromJson(userJson, BusinessPhone[].class); 
//		for (BusinessPhone store : userArray) {
//			System.out.println(store.getPhone());
//		}
		
//		 JSONParser parser = new JSONParser();
//	      try {
//	         Object obj = parser.parse(new FileReader("C:\\Users\\fsslaptop9\\Downloads\\State-zip-code-GeoJSON-master\\State-zip-code-GeoJSON-master\\dc_district_of_columbia_zip_codes_geo.min.json"));
//	         JSONObject jsonObject = (JSONObject)obj;
//	        
//	         JSONArray subjects = (JSONArray) jsonObject.get("features.properties");
//	        
//	         System.out.println(subjects);
//	         
////	        JSONArray j = (JSONArray)subjects.get("properties");
////	      
////	         System.out.println("features: "+j);
////	         Iterator iterator = j.iterator();
////	         while (iterator.hasNext()) {
////	            System.out.println(iterator.next());
////	         }
//	      } catch(Exception e) {
//	         e.printStackTrace();
//	      }
//		Calendar calendar = Calendar.getInstance();
//		Date date = calendar.getTime();
//	System.out.println(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()).toLowerCase());
//	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
//	System.out.println(formatter.format(calendar.getTime()));
//	
//		String string1 = "20:11";
//	    Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
//	    Calendar calendar1 = Calendar.getInstance();
//	    calendar1.setTime(time1);
//	    calendar1.add(Calendar.DATE, 1);
//
//
//	    String string2 = "23:49";
//	    Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
//	    Calendar calendar2 = Calendar.getInstance();
//	    calendar2.setTime(time2);
//	    calendar2.add(Calendar.DATE, 1);
//
//	    String someRandomTime = formatter.format(calendar.getTime());
//	    Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
//	    Calendar calendar3 = Calendar.getInstance();
//	    calendar3.setTime(d);
//	    calendar3.add(Calendar.DATE, 1);
//	    Date x = calendar3.getTime();
//	    System.out.println(x + " " +calendar1.getTime() + " " +calendar2.getTime() );
//
//	    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
//	        //checkes whether the current time is between 14:49:00 and 20:11:13.
//	        System.out.println("true");
//	    }
//		
//	   
//	    System.out.println(new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()));  
	   
//		JSONArray ja = new JSONArray();
//		JSONObject jo = new JSONObject();
//		JSONObject rest = new JSONObject();
//		jo.put("message", "");
//		jo.put("status", 0);
//		jo.put("errorCode", "");
//		jo.put("result",rest );
//		jo.put("responseCode", "");
//		ja.add(jo);
//		System.out.println(ja.toString());
//		
//		System.out.println(Double.parseDouble("90.00"));
//		
//	LinkedList<Integer> l = new LinkedList<>();
//	l.add(5);
//	l.add(1);
//	String m = "Hello World!";
//	String h = m.substring(6,12) + m.substring(12,6);
//	System.out.println(h);
//		String s = "[{\"lng\":\"80.215625\",\"name\":\"Chennai\",\"lat\":\"12.979255\"}]";
//		Object jp = new JSONParser().parse(s);
//		JSONArray j = new JSONArray();
//		j =  (JSONArray) jp;
//		System.out.println(j.get(0));
//		JSONObject jb = new JSONObject();
//		jb = (JSONObject) j.get(0);
//		System.out.println(jb.get("lat"));
//		System.out.println(jb.get("lng"));
//		System.out.println(jb.get("name"));
		
		List<String> lis = new ArrayList<String>();
		lis.add("1");
		lis.add("2");
		lis.add("3");
		lis.add("4");
		lis.add("5");
		lis.add("6");
		lis.add("7");
	}
}
