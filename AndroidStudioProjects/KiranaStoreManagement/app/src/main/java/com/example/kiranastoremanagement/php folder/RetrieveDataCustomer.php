<?php

include "DatabaseHelper.php";

//creating a query

$customerResults=$conn->prepare("SELECT id,customer_image,customer_name,customer_address,customer_number,customer_balance,customer_status,customer_notes FROM add_customer;");

//executing the query

$customerResults->execute();

//binding results to the query

$customerResults->bind_result($id,$customer_image,$customer_name,$customer_address,$customer_number,$customer_balance,$customer_status,$customer_notes);

$add_customer=array();

//traversing through all the result

while ($customerResults->fetch()) {
	$temp=array();
	$temp['id']=$id;
	$temp['customer_image']=$customer_image;
	$temp['customer_name']=$customer_name;
	$temp['customer_address']=$customer_address;
	$temp['customer_number']=$customer_number;
	$temp['customer_balance']=$customer_balance;
	$temp['customer_status']=$customer_status;
	$temp['customer_notes']=$customer_notes;
	array_push($add_customer, $temp);
}

//displaying the result in json format
echo json_encode($add_customer);
?>