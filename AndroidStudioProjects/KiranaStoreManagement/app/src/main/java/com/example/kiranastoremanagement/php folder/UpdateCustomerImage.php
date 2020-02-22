<?php

	 include "DatabaseHelper.php";
	 
	$conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	$id=$_POST['product_id'];
	$CustomerImage=$_POST['product_image'];
	
	$path = "UploadImage/$id.jpeg";

    $finalPath = "http://gurungonlineshopping.com/StoreManagement/$path";


	$sql="UPDATE add_customer SET customer_image='$finalPath' WHERE id='$id'";

	if (mysqli_query($conn,$sql)) {
	     file_put_contents( $path, base64_decode($CustomerImage) );

		echo "Record Update Successfully";
	
	}else
		{

			echo "Something went wrong";
		}

    
    mysqli_close($conn);
?>