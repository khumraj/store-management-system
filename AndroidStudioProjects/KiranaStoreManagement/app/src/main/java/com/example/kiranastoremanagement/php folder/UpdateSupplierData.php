
<?php 

if ($_SERVER['REQUEST_METHOD']=='POST') {
	include "DatabaseHelper.php";

	$id=$_POST['customer_id'];
	$supplier_image=$_POST['supplier_image'];
	$store_name=$_POST['store_name'];
	$supplier_name=$_POST['supplier_name'];
	$supplier_address=$_POST['supplier_address'];
	$supplier_number=$_POST['supplier_number'];
	$supplier_balance=$_POST['supplier_balance'];
	$supplier_status=$_POST['supplier_status'];
	$supplier_notes=$_POST['supplier_notes'];

	$sql="UPDATE add_supplier SET supplier_image='$supplier_image',store_name='$store_name', supplier_name='$supplier_name', supplier_address='supplier_address',supplier_number='supplier_number',supplier_balance='supplier_balance', supplier_status='supplier_status',supplier_notes='supplier_notes'";

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