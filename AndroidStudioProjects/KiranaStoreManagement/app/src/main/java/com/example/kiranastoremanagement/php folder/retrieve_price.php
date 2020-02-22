<?php

include "DatabaseHelper.php";

//creating  query

$stmt=$conn->prepare("SELECT id, product_image,product_name,available_stock,min_stock,measurement_unit,purchase_cost,sale_cost FROM add_price;");

//excuting the query

$stmt->execute();


//binding results to the query

$stmt->bind_result($id,$product_image,$product_name,$available_stock,$min_stock,$measurement_unit,$purchase_cost,$sale_cost);

$products=array();

//traversing through all the result

while ($stmt->fetch()) {

	$temp=array();
	$temp['id']=$id;
	$temp['product_image']=$product_image;
	$temp['product_name']=$product_name;
	$temp['available_stock']=$available_stock;
	$temp['min_stock']=$min_stock;
	$temp['measurement_unit']=$measurement_unit;
	$temp['purchase_cost']=$purchase_cost;
	$temp['sale_cost']=$sale_cost;
	array_push($products, $temp);

}

//displaying the result in the json format
	echo json_encode($products);


?>