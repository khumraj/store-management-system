<?php 
include "DatabaseHelper.php";

//creating a query

$results=$conn->prepare("SELECT id,first_name,last_name,email_id,mobile_number,new_password,conform_password FROM create_account;");

//executing a query
$results->execute();


//binding results to the query
$results->bind_result($id,$first_name,$last_name,$email_id,$mobile_number,$new_password,$conform_password);

$create_account=array();

//traversing through all the result

while ($results->fetch()) {

	$temp=array();
	$temp['id']=$id;
	$temp['first_name']=$first_name;
	$temp['last_name']=$last_name;
	$temp['email_id']=$email_id;
	$temp['mobile_number']=$mobile_number;
	$temp['new_password']=$new_password;
	$temp['conform_password']=$conform_password;
	array_push($create_account, $temp);
}
//displaying the result in json format

echo json_encode($create_account);

?>