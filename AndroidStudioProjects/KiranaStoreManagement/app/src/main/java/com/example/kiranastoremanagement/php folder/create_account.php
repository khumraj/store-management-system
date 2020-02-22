
<?php
       
         define('HOST','localhost');
       
        define('USER','gurungon_store_management_system');
        
       	define('PASS','khumraj@123');
       	
    	define('DB','gurungon_store_management');
    	
        $conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	    
        $first_name = $_POST['first_name'];
        $last_name = $_POST['last_name'];
        $email_id = $_POST['email_id'];
        $mobile_number= $_POST['mobile_number'];
        $new_password = $_POST['new_password'];
        $conform_password = $_POST['conform_password'];
        
        if($first_name == '' || $last_name == '' || $email_id == '' || $mobile_number == '' ||  $new_password == '' || $conform_password == '')
    		{
    	
    	    	echo 'please fill all values';
    		}else{
        		    
        	    $sql = "SELECT * FROM  create_account  WHERE email_id='$email_id' OR mobile_number='$mobile_number' ";
        		
        	    $check = mysqli_fetch_array(mysqli_query($conn,$sql));
        	   
        		if(isset($check)){
        		    
        		echo 'email or mobile number already exist';
        		
        	}elseif($new_password !=$conform_password){
        	    echo 'Passwords doesnot match.';
        	    
        	}else{
    		    
    		$sql = "INSERT INTO create_account (first_name,last_name,email_id,mobile_number,new_password,conform_password) VALUES('$first_name','$last_name','$email_id','$mobile_number','$new_password', '$conform_password')";
    
    		if(mysqli_query($conn,$sql)){
    		    
    			echo 'successfully registered';
    	
    	}
    		else{
    				
    			echo 'Please try again';
    		
    		}
    		
      }
			
	        mysqli_close($conn);
		
        }
           
?>	    
    	
    	

        

