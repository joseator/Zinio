package testzinio.stepDefinition;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.Assert;
import testzinio.utils.SecureEncrypt;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class StepDefinitions {
    private Properties properties = new Properties();
    private SecureEncrypt secureEncrypt= new SecureEncrypt();
    private JSONObject catalogInfo;
    private HttpResponse<JsonNode> catalogResponse;
    @Given("send a POST request to take the authentication token")
    public void takeAuthToken() throws UnirestException, IOException {
        properties.load(new FileReader("src/test/resources/project.properties"));

        HttpResponse<JsonNode> response = Unirest.post(properties.getProperty("url_token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("client_id", properties.getProperty("client_id"))
                .field("client_secret", secureEncrypt.decrypt(properties.getProperty("client_secret"), properties.getProperty("key")))
                .field("grant_type", properties.getProperty("grant_type"))
                .asJson();

        Assert.assertEquals("The response is a 200 OK", 200, response.getStatus());

        JSONObject body = response.getBody().getObject();
        String accessToken = body.get("access_token").toString();
        properties.setProperty("access_token", secureEncrypt.encrypt(accessToken, properties.getProperty("key")));

    }

    @Given("an invalid token")
    public void setInvalidToken() throws UnirestException, IOException {
        properties.load(new FileReader("src/test/resources/project.properties"));
        String accessToken = "1234567890";
        properties.setProperty("access_token", secureEncrypt.encrypt(accessToken, properties.getProperty("key")));
    }

    @Given("an empty token")
    public void setEmptyToken() throws UnirestException, IOException {
        properties.load(new FileReader("src/test/resources/project.properties"));
        properties.setProperty("access_token", "");
    }

    @When("send a GET request to get the Zinio Catalog")
    public void getCatalog() throws UnirestException {
        String token;
        if(properties.getProperty("access_token").equals("")){
            token = "";
        }
        else {
            token = secureEncrypt.decrypt(properties.getProperty("access_token"), properties.getProperty("key"));
        }
        catalogResponse = Unirest.get(properties.getProperty("url_catalog"))
                .header("Authorization","Bearer " + token)
                .asJson();
    }

    @Then("check the catalog request is correct")
    public void checkCatalogRequest() {

        Assert.assertEquals("The response is a 200 OK", 200, catalogResponse.getStatus());
        catalogInfo = catalogResponse.getBody().getObject();

        Assert.assertNotNull("Status field exists", catalogInfo.get("status"));
        Assert.assertEquals("Status field is True","true", catalogInfo.get("status").toString());

        JSONArray catalog = (JSONArray) catalogInfo.get("data");
        Assert.assertEquals("Catalog data has 10 elements", 10, catalog.length());

        JSONObject element = (JSONObject) catalog.get(0);
        Assert.assertEquals("Catalog element has an Integer id", "java.lang.Integer", element.get("id").getClass().getName());
//        Assert.assertEquals("Catalog first element has the id: 2009", 2009, element.get("id"));

        Assert.assertEquals("Catalog element has a String name", "java.lang.String", element.get("name").getClass().getName());
//        Assert.assertEquals("Catalog first element has the name: 333350725", "333350725", element.get("name"));

        Assert.assertEquals("Catalog element has a String description", "java.lang.String", element.get("description").getClass().getName());
//        Assert.assertEquals("Catalog first element has the description: Zinio Demo Catalog Description", "Zinio Demo Catalog Description", element.get("description"));

        Assert.assertEquals("Catalog element has a String remote_identifier", "java.lang.String", element.get("remote_identifier").getClass().getName());
//        Assert.assertEquals("Catalog first element has the remote_identifier: 098XYZ123", "098XYZ123", element.get("remote_identifier"));

        Assert.assertEquals("Catalog element has a Integer status", "java.lang.Integer", element.get("status").getClass().getName());
//        Assert.assertEquals("Catalog first element has the status: 1", 1, element.get("status"));

        Assert.assertEquals("Catalog element has a String legacy_identifier", "java.lang.String", element.get("legacy_identifier").getClass().getName());
//        Assert.assertEquals("Catalog first element has the legacy_identifier: 1234567", "1234567", element.get("legacy_identifier"));

        Assert.assertEquals("Catalog element has a String created_at", "java.lang.String", element.get("created_at").getClass().getName());
//        Assert.assertEquals("Catalog first element has the created_at: 2022-07-08T11:43:45+0000", "2022-07-08T11:43:45+0000", element.get("created_at"));

        Assert.assertEquals("Catalog element has a String modified_at", "java.lang.String", element.get("modified_at").getClass().getName());
//        Assert.assertEquals("Catalog first element has the modified_at: 2022-07-08T11:43:45+0000", "2022-07-08T11:43:45+0000", element.get("modified_at"));
    }

    @Then("check the catalog request return a 401 Unauthorized: The access token is invalid or has expired")
    public void checkInvalidToken() {
        Assert.assertEquals("The response is a 401 Unauthorized", 401, catalogResponse.getStatus());
        Assert.assertEquals("The status text is Unauthorized", "Unauthorized", catalogResponse.getStatusText());
        Assert.assertEquals("The error is: The access token is invalid or has expired", "The access token is invalid or has expired",  catalogResponse.getBody().getObject().get("error_description"));
    }

    @Then("check the catalog request return a 401 Unauthorized: The access token is missing")
    public void checkMissingToken() {
        Assert.assertEquals("The response is a 401 Unauthorized", 401, catalogResponse.getStatus());
        Assert.assertEquals("The status text is Unauthorized", "Unauthorized", catalogResponse.getStatusText());
        Assert.assertEquals("The error is: The access token is missing", "The access token is missing",  catalogResponse.getBody().getObject().get("error_description"));
    }
}
