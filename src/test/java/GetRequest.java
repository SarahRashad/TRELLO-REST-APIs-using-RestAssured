import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetRequest {
    private RequestSpecification requestSpecification;
    private Response response;
    static String key = "d7cfa7723f9746a1ba201e064993ae3d";
    static String token ="ATTAdca3b4ee074d7154ee6f38d4701487594b5228ad0eb543d79ab0dc9984e33636F8FB3861";
    static String base_uri="https://api.trello.com";
    public GetRequest(String url, String path){
        requestSpecification=RestAssured.given().baseUri(url).basePath(path);
    }
    public void addHeader(){

    }
    public void addQueryParameter(String key, String value){
        this.requestSpecification.queryParam(key,value);
    }
    public void send(){
        response= requestSpecification.when().get();
    }

    public static void main(String[] args) {
        GetRequest g= new GetRequest(base_uri,"/1/members/me");
        g.addQueryParameter("key",key);
        g.addQueryParameter("token",token);
        g.send();
        g.response.prettyPrint();
    }
}
