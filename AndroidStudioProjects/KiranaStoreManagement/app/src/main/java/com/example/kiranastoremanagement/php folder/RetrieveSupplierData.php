<?php

include "DatabaseHelper.php";

//creating query
$results=$conn->prepare("SELECT id,supplier_image,store_name,supplier_name,supplier_address,mobile_number,supplier_balance,supplier_status,supplier_notes FROM add_supplier;");

//executing the query

$results->execute();

//binding results to the query

$results->bind_result($id,$supplier_image,$store_name,$supplier_name,$supplier_address,$mobile_number,$supplier_balance,$supplier_status,$supplier_notes);


$add_supplier=array();

//traversing through all the result

while ($results->fetch()) {
	$temp=array();
	$temp['id']=$id;
	$temp['supplier_image']=$supplier_image;
	$temp['store_name']=$store_name;
	$temp['supplier_name']=$supplier_name;
	$temp['supplier_address']=$supplier_address;
	$temp['mobile_number']=$mobile_number;
	$temp['supplier_balance']=$supplier_balance;
	$temp['supplier_status']=$supplier_status;
	$temp['supplier_notes']=$supplier_notes;
	array_push($add_supplier, $temp);

}

//displaying the result in the json format

echo json_encode($add_supplier);

?>