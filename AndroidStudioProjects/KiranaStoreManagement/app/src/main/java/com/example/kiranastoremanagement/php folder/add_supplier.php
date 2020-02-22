<?php
       
         define('HOST','localhost');
       
        define('USER','gurungon_store_management_system');
        
       	define('PASS','khumraj@123');
       	
    	define('DB','gurungon_store_management');
    	
        $conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	    
        $store_name = $_POST['store_name'];
        $supplier_name = $_POST['supplier_name'];
        $supplier_address = $_POST['supplier_address'];
        $mobile_number= $_POST['mobile_number'];
        
        if($store_name == '' || $supplier_name == '' || $supplier_address == '' || $mobile_number == '')
    		{
    	
    	    	echo 'please fill all values';
    		}else{
        		    
        	    $sql = "SELECT * FROM  add_supplier  WHERE store_name='$store_name' OR supplier_name='$supplier_name' ";
        		
        	    $check = mysqli_fetch_array(mysqli_query($conn,$sql));
        	   
        		if(isset($check)){
        		    
        		echo 'store name or suppiler name already registered';
        	    
        	}else{
    		    
    		$sql = "INSERT INTO add_supplier (store_name,supplier_name,supplier_address,mobile_number) VALUES('$store_name','$suppiler_name','$supplier_address','$mobile_number')";
    
    		if(mysqli_query($conn,$sql)){
    		    
    			echo 'suppliers successfully added';
    	
    	}
    		else{
    				
    			echo 'Please try again';
    		
    		}
    		
        }
    		
	        mysqli_close($conn);
		
        }
           
?>	    