<?php

	 include "DatabaseHelper.php";
	 
	 $conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	$id=$_POST['product_id'];
	$ProductImage=$_POST['product_image'];
	$product_name=$_POST['product_name'];
	$available_stock=$_POST['available_stock'];
	$min_stock=$_POST['min_stock'];
	$measurement_unit=$_POST['measurement_unit'];
	$purchase_cost=$_POST['purchase_cost'];
	$sale_cost=$_POST['sale_cost'];
	
	$product_image = "UploadImage/$id.jpeg";

    $finalPath = "http://gurungonlineshopping.com/StoreManagement/$path";


	$sql="UPDATE add_price SET product_image='$product_image',  product_name='$product_name',  available_stock='$available_stock' min_stock='$min_stock', measurement_unit='$measurement_unit', purchase_cost='$purchase_cost', sale_cost='$sale_cost' WHERE id=$id";

	if (mysqli_query($conn,$sql)) {
	     file_put_contents( $path, base64_decode($ProductImage) );

		echo "Record Update Successfully";
	
	}else
		{

			echo "Something went wrong";
		}

    
    mysqli_close($conn);
?>


<?php



	 include "DatabaseHelper.php";
	 
	$conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	$id=7;
	$ProductImage='khumraj.png';
	$product_name='khumraj gurung';
	$available_stock='20';
	$min_stock='5';
	$measurement_unit='Pcs';
	$purchase_cost='120';
	$sale_cost='200';
	
	$path = "UploadImage/$id.png";

    $product_image = "http://gurungonlineshopping.com/StoreManagement/$path";


	$sql="UPDATE add_price SET product_image='$product_image',product_name='$product_name',available_stock='$available_stock',min_stock='$min_stock', measurement_unit='$measurement_unit',purchase_cost='$purchase_cost', sale_cost='$sale_cost' WHERE id=$id";

	if (mysqli_query($conn,$sql)) {
	     file_put_contents( $path, base64_decode($ProductImage) );

		echo "Record Update Successfully";
	
	}else
		{

			echo "Something went wrong";
		}

    
    mysqli_close($conn);
?>


<?php

	 include "DatabaseHelper.php";
	 
	 $conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	$id=$_POST['product_id'];
	$product_name=$_POST['product_name'];
	$available_stock=$_POST['available_stock'];
	$min_stock=$_POST['min_stock'];
	$measurement_unit=$_POST['measurement_unit'];
	$purchase_cost=$_POST['purchase_cost'];
	$sale_cost=$_POST['sale_cost'];
	
	
	$sql="UPDATE add_price SET product_name='$product_name',available_stock='$available_stock',min_stock='$min_stock',measurement_unit='$measurement_unit',purchase_cost='$purchase_cost',sale_cost='$sale_cost' WHERE id='$id'";

	if (mysqli_query($conn,$sql)) {

		echo "Record Update Successfully";
	
	}else
		{

			echo "Something went wrong";
		}

    
    mysqli_close($conn);
?>