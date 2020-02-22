<?php 
if ($_SERVER['REQUEST_METHOD']=='POST') {


	include "DatabaseHelper.php";
	$id=$_POST['product_id']
	$sql="DELETE FROM add_price WHERE id='$id";

	if (mysqli_query($conn,$sql)) {
		echo "Record Deleted Successfully";
		# code...
	}else
		{
			echo "Something went wrong ";
		}
	
}
mysql_close($conn);

 ?>