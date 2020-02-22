
<?php
       
        include'DatabaseHelper.php';
    	
        $conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
        
        $ImageData=$_POST['customer_image'];
        $customer_name= $_POST['customer_name'];
        $customer_address=$_POST['customer_address'];
        $customer_number= $_POST['customer_number'];
        $customer_balance=$_POST['customer_balance'];
        $customer_status=$_POST['customer_status'];
        $customer_notes= $_POST['customer_notes'];
        $path="UploadImage/$customer_name.png";
        $customer_image="http://gurungonlineshopping.com/StoreManagement/$path";
	   
        
        if($customer_name == '' || $customer_address == '' || $customer_number == '' || $customer_balance=='' || $customer_status=='' || $customer_notes== '')
    		{
    	
    	    	echo 'please fill all values';
    		}else{
        		    
        	    $sql = "SELECT * FROM  add_customer  WHERE customer_name='$customer_name' OR customer_number='$customer_number' ";
        		
        	    $check = mysqli_fetch_array(mysqli_query($conn,$sql));
        	   
        		if(isset($check)){
        		    
        		echo 'customer name or phone number already saved';
        	    
        	}else{
    		    
    		$sql = "INSERT INTO add_customer (customer_image,customer_name,customer_address,customer_number,customer_balance,customer_status,customer_note) VALUES('$customer_image','$customer_name','$customer_address','$customer_number','$customer_balance','$customer_status','$customer_notes')";
    
    		if(mysqli_query($conn,$sql)){
    		    file_put_contents($path, base64_decode($ImageData));
    		    
    			echo 'customer successfully added';
    	
    	}
    		else{
    				
    			echo 'Please try again';
    		
    		}
    		
        }
    		
	        mysqli_close($conn);
		
        }
           
?>	    