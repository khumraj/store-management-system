<?php 

if ($_SERVER['REQUEST_METHOD']=='POST') {
	include "DatabaseHelper.php";

	$id=$_POST['customer_id'];
	$customer_image=$_POST['customer_image'];
	$customer_name=$_POST['customer_name'];
	$customer_address=$_POST['customer_address'];
	$customer_number=$_POST['customer_number'];
	$customer_balance=$_POST['customer_balance'];
	$customer_status=$_POST['customer_status'];
	$customer_notes=$_POST['customer_notes'];

	$sql="UPDATE add_customer SET customer_image='$customer_image', customer_name='$customer_name', customer_address='customer_address',customer_number='customer_number',customer_balance='customer_balance', customer_status='customer_status',customer_note='customer_notes'";

	if (mysqli_query($conn,$sql)) {

		echo "Record Update Successfully";
		# code...
	}else
		{

			echo "Something went wrong";
		}
	}

	mysql_close($conn);


?>