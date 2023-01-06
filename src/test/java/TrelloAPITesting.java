import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

public class TrelloAPITesting {
    static String key = "d7cfa7723f9746a1ba201e064993ae3d";
    static String token ="ATTAdca3b4ee074d7154ee6f38d4701487594b5228ad0eb543d79ab0dc9984e33636F8FB3861";
    static String base_uri="https://api.trello.com";
    static String memberID=""; // Static variables to save values received from the response
    static String organizationID=""; // Static variables to save values received from the response
    static String boardID=""; // Static variables to save values received from the response
    static String listID=""; // Static variables to save values received from the response


    public static void main(String[] args) {
        checkAuthority();
        createNewOrganization();
        getMemberID();
        getOrganizationsForMember();
        createBoardInsideOrganization();
        getBoardsInOrganization();
        createListInsideBoard();
        getListsInBoard();
        archiveList();
        deleteBoard();
        deleteOrganization();

    }

    /**
     *  This function tests the "invalid token" response body (GET method)
     *  which means you don't have the authority to access this API
     *  so need to get your account credentials which will be sent with every request so the server
     */
    public static void checkAuthority(){
        System.out.println("Test Case: check Authority - invalid token scenario");
        //Building the request
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/members/me")
                .when()
                .get();
        response.then()
                .statusCode(400)
                .and()
                .statusLine("HTTP/1.1 400 Bad Request")
                .and()
                .body(Matchers.equalTo("invalid token"));
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("The response body is ");
        response.prettyPrint();
        System.out.println("as expected");
        System.out.println("----------------------------");
    }
    /**
     * This function tests the create new Organization API (Post method)
     * It calls its corresponding API and checks both the response code and status
     * In case of success the created organization ID is assigned to the variable  "organizationID"
     */
    public static void createNewOrganization() {
        System.out.println("Test Case: create a New Organization");
        //Building the request
        RequestSpecification rs = RestAssured
                .given()
                .baseUri(base_uri+"/1/organizations?")
                .queryParam("displayName","Mobile Testing Again")
                .queryParam("key",key)
                .queryParam("token",token)
                .header("Content-Type","application/json")
                .header("Accept","*/*");
        //sending the request and receiving its response
        Response response= rs
                .when()
                .post();
        //Checking the response code and status
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        //assigning the static variable organizationID with the ID of the created organization
        organizationID=response.path("id");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("New organization is created successfully with id "+ organizationID);
        System.out.println("----------------------------");
    }
    /**
     * This function tests the get Member ID API (GET method)
     * It calls its corresponding API and checks both the response code and status
     * In case of success the returned ID is assigned to the variable  "memberID"
     */
    public static void getMemberID() {
        System.out.println("Test Case: check Authority - valid key and token scenario");
        //Building the request
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/members/me")
                .queryParam("key",key)
                .queryParam("token",token)
                //sending the request and receiving its response
                .when()
                .get();
        //Checking the response code and status
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        memberID=response.path("id");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("The member id is "+ memberID);
        System.out.println("----------------------------");
    }

    /**
     * This function tests the get Organizations for Member API (GET method)
     * It calls its corresponding API and checks both the response code and status
     */
    public static void getOrganizationsForMember() {
        System.out.println("Test Case: get Organizations for Member with memberID");
        //Building the request
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/members/"+memberID+"/organizations")
                .queryParam("key",key)
                .queryParam("token",token)
                //sending the request and receiving its response
                .when()
                .get();
        //Checking the response code and status
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("----------------------------");
    }
    /**
     * This function tests create Board inside an Organization API (POST method)
     * It calls its corresponding API and checks both the response code and status
     * In case of success the returned ID is assigned to the variable  "boardID"
     */
    public static void createBoardInsideOrganization(){
        System.out.println("Test Case: create Board inside Organization with ID organizationID");
        //Building the request
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/boards/")
                .header("Content-Type","application/json; charset=utf-8")
                .header("Accept","application/json")
                //.header("Accept-Encoding","gzip, deflate, br")
                .queryParam("key",key)
                .queryParam("token",token)
                .queryParam("name","Mobile Testing")
                .queryParam("defaultLists","false")
                .queryParam("idOrganization",""+organizationID)
                .when()
                .post();
        //Checking the response code and status
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        boardID=response.path("id");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("New board is created successfully with id "+ boardID);
        System.out.println("----------------------------");
    }
    /**
     * This function tests the get Boards for an Organization API (GET method)
     * It calls its corresponding API and checks both the response code and status
     */
    public static void getBoardsInOrganization() {
        System.out.println("Test Case: get Boards in an Organization with organizationID");
        //Building the request
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/organizations/"+organizationID+"/boards")
                .queryParam("key",key)
                .queryParam("token",token)
                //sending the request and receiving its response
                .when()
                .get();
        //Checking the response code and status
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("----------------------------");
    }

    /**
     * This function tests create List inside a Board API (POST method)
     * It calls its corresponding API and checks both the response code and status
     * In case of success the returned ID is assigned to the variable  "listID"
     */
    public static void createListInsideBoard(){
        System.out.println("Test Case: create a list inside a Board with boardID");
        //Building the request
        RequestSpecification rs= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/lists?")
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .queryParam("name","ToDo")
                .queryParam("idBoard",boardID)
                .queryParam("key",key)
                .queryParam("token",token);
        //sending the request and receiving its response
        Response response = rs
               .when()
               .post();
        //Checking the response code and status
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        listID=response.path("id");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("New list is created successfully with id "+ listID);
        System.out.println("----------------------------");
    }
    /**
     * This function tests the get Lists for a Board API (GET method)
     * It calls its corresponding API and checks both the response code and status
     */
    public static void getListsInBoard() {
        System.out.println("Test Case: get Lists In a Board");
        //Building the request
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/boards/"+boardID+"/lists")
                .param("key",key)
                .param("token",token)
                //sending the request and receiving its response
                .when()
                .get();
        //Checking the response code and status
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("----------------------------");
    }

    /**
     * This function tests the archive List API (PUT method)
     * It calls its corresponding API and checks both the response code and status
     */
    public static void archiveList(){
        System.out.println("Test Case: archive List");
        //Building the request
        Response response= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/lists/"+listID+"/closed?")
                .queryParam("key",key)
                .queryParam("token",token)
                .queryParam("value","true")
                //sending the request and receiving its response
                .when()
                .put();
        //Checking the response code and status
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("----------------------------");
    }
    /**
     * This function tests the delete Board API (DELETE method)
     * It calls its corresponding API and checks both the response code and status
     */
    public static void deleteBoard(){
        System.out.println("Test Case: delete a Board with board ID "+boardID);
        //Building the request
        RequestSpecification rs= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/boards/"+boardID)

                .queryParam("key",key)
                .queryParam("token",token);
        //sending the request and receiving its response
        Response response = rs
                .when()
                .delete();
        //Checking the response code and status
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("----------------------------");
    }

    /**
     * This function tests the delete Organization API (DELETE method)
     * It calls its corresponding API and checks both the response code and status
     */
    public static void deleteOrganization(){
        System.out.println("Test Case: delete an Organization with organization ID "+organizationID);
        //Building the request
        RequestSpecification rs= RestAssured
                .given()
                .baseUri(base_uri)
                .basePath("/1/organizations/"+organizationID)
                .queryParam("key",key)
                .queryParam("token",token);
        //sending the request and receiving its response
        Response response = rs
                .when()
                .delete();
        //Checking the response code and status
        response.then().statusCode(200);
        response.then().statusLine("HTTP/1.1 200 OK");
        System.out.println("The response status is "+response.statusCode()+" as expected");
        System.out.println("response execution time:"+ response.time()+"ms");
        System.out.println("----------------------------");
    }

}

