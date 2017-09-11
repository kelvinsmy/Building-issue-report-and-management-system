<?php
 

 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET["username"])) {
    $username = $_GET['username'];
    $password = $_GET['password'];
    // get a product from products table
    $result = mysql_query("SELECT *FROM Staff WHERE account_name = '$username' AND account_password= '$password'");
 
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $product = array();
            $product["St_id"] = $result["St_id"];
            $product["name"] = $result["name"];
            $product["block"] = $result["block"];
            $product["position"] = $result["position"];
            $product["account_name"] = $result["account_name"];            
            $product["account_password"] = $result["account_password"];
            $product["access"] = $result["access"];
            // success
            $response["success"] = 1;
 
            // user node
            $response["product"] = array();
 
            array_push($response["product"], $product);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";
 
        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>