package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.LinkedHashMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import testbase.TestBase;
import utilities.ExcelUtils;

public class RestAssuredTest {

	// Registration of weather station without key

	@Test(priority = 0)
	public void restTest1() {
		RestAssured.baseURI = "http://api.openweathermap.org";
		given().header("Content-Type", "application/json")
				.body("{\r\n" + "  \"external_id\": \"DEMO_TEST001\",\r\n"
						+ "  \"name\": \"Interview Station12345\",\r\n" + "  \"latitude\": 33.33,\r\n"
						+ "  \"longitude\": -111.43,\r\n" + "  \"altitude\": 444\r\n" + "}")
				.when().post("/data/3.0/stations").then().log().all().statusCode(401).body("message",
						equalTo("Invalid API key. Please see http://openweathermap.org/faq#error401 for more info."));
	}

	// Successful Reg 1

	@Test(priority = 1)
	public void restTest21() {
		RestAssured.baseURI = "http://api.openweathermap.org";
		// Successful registration 1
		String response = given().queryParam("appid", "2a843ee2fc05eeb573b1ba400a470af5")
				.header("Content-Type", "application/json")
				.body("{\r\n" + "  \"external_id\": \"DEMO_TEST001\",\r\n"
						+ "  \"name\": \"Interview Station12345\",\r\n" + "  \"latitude\": 33.33,\r\n"
						+ "  \"longitude\": -111.43,\r\n" + "  \"altitude\": 444\r\n" + "}")
				.when().post("/data/3.0/stations").then().log().all().statusCode(201).extract().response().asString();

		JsonPath js = new JsonPath(response);
		String id1 = js.get("ID");
		ExcelUtils exut = new ExcelUtils();
		exut.writeInExcel(TestBase.USER_DIR + "\\src\\main\\java\\testdata\\testData.xlsx", "Sheet2", "ID1", id1);
	}

	// Successful Reg 2

	@Test(priority = 2)
	public void restTest22() {
		RestAssured.baseURI = "http://api.openweathermap.org";
		String response = given().queryParam("appid", "2a843ee2fc05eeb573b1ba400a470af5")
				.header("Content-Type", "application/json")
				.body("{\r\n" + "  \"external_id\": \"Interview1\",\r\n"
						+ "  \"name\": \"Interview Station123456\",\r\n" + "  \"latitude\": 33.44,\r\n"
						+ "  \"longitude\": -12.44,\r\n" + "  \"altitude\": 444\r\n" + "}")
				.when().post("/data/3.0/stations").then().log().all().statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(response);
		String id2 = js.get("ID");
		ExcelUtils exut = new ExcelUtils();
		exut.writeInExcel(TestBase.USER_DIR + "\\src\\main\\java\\testdata\\testData.xlsx", "Sheet2", "ID2", id2);
	}

	// Getting and validating stations Reg1
	@Test(priority = 3)
	public void restTest31() {
		RestAssured.baseURI = "http://api.openweathermap.org";
		ExcelUtils exut = new ExcelUtils();
		LinkedHashMap<String, String> testdata = exut
				.readExcel(TestBase.USER_DIR + "\\src\\main\\java\\testdata\\testData.xlsx", "Sheet2");
		String id1 = testdata.get("ID1");
		given().queryParam("appid", "2a843ee2fc05eeb573b1ba400a470af5").header("Content-Type", "application/json")
				.when().get("/data/3.0/stations/" + id1).then().log().all().statusCode(200)
				.body("external_id", equalTo("DEMO_TEST001"));
	}

	// Getting and validating stations Reg2
	@Test(priority = 4)
	public void restTest32() {
		RestAssured.baseURI = "http://api.openweathermap.org";
		ExcelUtils exut = new ExcelUtils();
		LinkedHashMap<String, String> testdata = exut
				.readExcel(TestBase.USER_DIR + "\\src\\main\\java\\testdata\\testData.xlsx", "Sheet2");
		String id2 = testdata.get("ID2");
		given().queryParam("appid", "2a843ee2fc05eeb573b1ba400a470af5").header("Content-Type", "application/json")
				.when().get("/data/3.0/stations/" + id2).then().log().all().statusCode(200)
				.body("external_id", equalTo("Interview1"));
	}

	// Deleting successfully Reg1

	@Test(priority = 5)
	public void restTest41() {
		RestAssured.baseURI = "http://api.openweathermap.org";
		ExcelUtils exut = new ExcelUtils();
		LinkedHashMap<String, String> testdata = exut
				.readExcel(TestBase.USER_DIR + "\\src\\main\\java\\testdata\\testData.xlsx", "Sheet2");
		String id1 = testdata.get("ID1");
		given().queryParam("appid", "2a843ee2fc05eeb573b1ba400a470af5").header("Content-Type", "application/json")
				.when().delete("/data/3.0/stations/" + id1).then().log().all().statusCode(204);
	}

	// Deleting successfully Reg2

	@Test(priority = 6)
	public void restTest42() {
		RestAssured.baseURI = "http://api.openweathermap.org";
		ExcelUtils exut = new ExcelUtils();
		LinkedHashMap<String, String> testdata = exut
				.readExcel(TestBase.USER_DIR + "\\src\\main\\java\\testdata\\testData.xlsx", "Sheet2");
		String id2 = testdata.get("ID2");
		given().queryParam("appid", "2a843ee2fc05eeb573b1ba400a470af5").header("Content-Type", "application/json")
				.when().delete("/data/3.0/stations/" + id2).then().log().all().statusCode(204);
	}

	// verify station not found message for reg1
	@Test(priority = 7)
	public void restTest51() {
		RestAssured.baseURI = "http://api.openweathermap.org";
		ExcelUtils exut = new ExcelUtils();
		LinkedHashMap<String, String> testdata = exut
				.readExcel(TestBase.USER_DIR + "\\src\\main\\java\\testdata\\testData.xlsx", "Sheet2");
		String id1 = testdata.get("ID1");
		given().queryParam("appid", "2a843ee2fc05eeb573b1ba400a470af5").header("Content-Type", "application/json")
				.when().delete("/data/3.0/stations/" + id1).then().log().all().statusCode(404)
				.body("message", equalTo("Station not found"));
	}

	// verify station not found message for reg2
	@Test(priority = 8)
	public void restTest52() {
		RestAssured.baseURI = "http://api.openweathermap.org";
		ExcelUtils exut = new ExcelUtils();
		LinkedHashMap<String, String> testdata = exut
				.readExcel(TestBase.USER_DIR + "\\src\\main\\java\\testdata\\testData.xlsx", "Sheet2");
		String id2 = testdata.get("ID2");
		given().queryParam("appid", "2a843ee2fc05eeb573b1ba400a470af5").header("Content-Type", "application/json")
				.when().delete("/data/3.0/stations/" + id2).then().log().all().statusCode(404)
				.body("message", equalTo("Station not found"));
	}
}
