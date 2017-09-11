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
    $St_id = $_GET['St_id'];
    $result = mysql_query("SELECT *FROM Staff WHERE St_id=$St_id") or die(mysql_error());

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
        
        $result1 = mysql_query("SELECT * FROM Report WHERE block= '$block' AND progress='In progress'") or die(mysql_error());
          
        
        $product["task"] = mysql_num_rows($result1);                                    

                             
        

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