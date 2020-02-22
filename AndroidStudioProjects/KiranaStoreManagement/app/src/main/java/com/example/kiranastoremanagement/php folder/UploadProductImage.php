
<?php
       
        include'DatabaseHelper.php';
    	
        $conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
        
        
        $ImageData=$_POST['product_image'];
        $product_name= $_POST['product_name'];  
        $path="UploadImage/$product_name.jpeg";
        $product_image="http://gurungonlineshopping.com/StoreManagement/$path";
       
        
      
    	$sql = "INSERT INTO add_price (product_image,product_name) VALUES('$product_image','$product_name')";
    
    		if(mysqli_query($conn,$sql)){
    		    file_put_contents($path, base64_decode($ImageData));
    		    
    		 
    			echo 'Product successfully added';
    	
    	}
    		else{
    				
    			echo 'Please try again';
    		
    		}
    		
        }
    		
	        mysqli_close($conn);
		
        }
           
?>	    