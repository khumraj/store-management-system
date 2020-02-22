
<?php
       
        include'DatabaseHelper.php';
    	
        $conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
        
        
        $ImageData=$_POST['supplier_image'];
        $supplier_name= $_POST['supplier_name'];  
        $path="UploadImage/$supplier_name.jpeg";
        $supplier_image="http://gurungonlineshopping.com/StoreManagement/$path";
       
        
      
    	$sql = "INSERT INTO add_supplier (supplier_image,supplier_name) VALUES('$supplier_image','supplier_name')";
    
    		if(mysqli_query($conn,$sql)){
    		    file_put_contents($path, base64_decode($ImageData));
    		    
    		 
    			echo 'Supplier successfully added';
    	
    	}
    		else{
    				
    			echo 'Please try again';
    		
    		}
    		
        }
    		
	        mysqli_close($conn);
		
        }
           
?>	    