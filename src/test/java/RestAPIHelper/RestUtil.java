package RestAPIHelper;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

import java.util.Map;

public class RestUtil {

	public static Response sendGetAPI(){
		return null;
	}
	public static Response sendPostAPI(String postBody,String contentType,Map<String,String> queryParams ){
		Response response = 	
				given().
				header("Content-Type",contentType).
				body(postBody).log().all().
				queryParams(queryParams).
				when().
				post();
		return response;
	}
	public static Response sendGetAPI(String contentType,Map<String,String> queryParams ){
		Response response = 	
				given().
				header("Content-Type",contentType).
				log().all().
				queryParams(queryParams).
				when().
				post();
		return response;
	}
}
