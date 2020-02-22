
<?php
       
        include'DatabaseHelper.php';
    	
        $conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
        
        
        $ImageData=$_POST['customer_image'];
        $customer_name= $_POST['customer_name'];  
        $path="UploadImage/$customer_name.jpeg";
        $customer_image="http://gurungonlineshopping.com/StoreManagement/$path";
       
        
      
    	$sql = "INSERT INTO add_customer (customer_image,customer_name) VALUES('$customer_image','$customer_name')";
    
    		if(mysqli_query($conn,$sql)){
    		    file_put_contents($path, base64_decode($ImageData));
    		    
    		 
    			echo 'Customer successfully added';
    	
    	}
    		else{
    				
    			echo 'Please try again';
    		
    		}
    		
        }
    		
	        mysqli_close($conn);
		
        }
           
?>	    