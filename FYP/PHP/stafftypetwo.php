<?php

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all products from products table
if (isset($_GET["block"])) {
    $block = $_GET['block'];
    $result = mysql_query("SELECT *FROM Staff WHERE block = '$block' AND position='Technician' AND access=0 ORDER BY State DESC") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["products"] = array();

    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["St_id"] = $row["St_id"];
        $St_id= $row["St_id"];
        $product["name"] = $row["name"];
        $result1 = mysql_query("SELECT * FROM Report WHERE St_id= $St_id AND progress='Assigned'") or die(mysql_error());
          
        
        $product["task"] = mysql_num_rows($result1);                                    

         
        
        $product["State"] = $row["State"];
        
        

        // push single product into final response array
        array_push($response["products"], $product);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";

    // echo no users JSON
    echo json_encode($response);
}}
?>