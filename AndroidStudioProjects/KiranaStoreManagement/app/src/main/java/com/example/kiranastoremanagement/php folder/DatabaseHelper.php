<?php

define('HOST','localhost'); //defining host name
           
define('USER','gurungon_store_management_system'); //defining database username
            
define('PASS','khumraj@123'); //defining database password
           	
define('DB','gurungon_store_management'); //defining database name

$conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
        	

?>