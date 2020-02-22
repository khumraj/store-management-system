<?php 
if ($_SERVER['REQUEST_METHOD']=='POST') {
	include "DatabaseHelper.php";
	$id=$_POST['id']
	$sql="DELETE FROM add_supplier WHERE id='$id'";

	if (mysqli_query($conn,$sql)) {
		echo "Record Deleted Successfully";
		
	}else
		{
			echo "Something went to worng";
		}

}
mysql_close($conn);


 ?>