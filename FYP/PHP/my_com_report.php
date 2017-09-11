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
if (isset($_GET["Re_id"])) {
    $Re_id = $_GET['Re_id'];
    $result = mysql_query("SELECT *FROM Report WHERE Re_id = $Re_id AND progress='Completed' ORDER BY finished_at DESC") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["products"] = array();

    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["Rid"] = $row["Rid"];
        $product["type"] = $row["type"];
        $product["progress"] = $row["progress"];
        $product["block"] = $row["block"];
        $product["location"] = $row["location"];
        $product["created_at"] = $row["created_at"];
        $product["finished_at"] = $row["finished_at"];
        

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
