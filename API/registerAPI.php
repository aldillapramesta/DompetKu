<?php   
  require_once 'connection.php';  
  $response = array();  
  if(isset($_GET['apicall'])){  
  switch($_GET['apicall']){  

  case 'signup':  
    if(isTheseParametersAvailable(array('username','password','fullname','city','wallet'))){  
    $username = $_POST['username'];    
    $password = password_hash($_POST['password'], PASSWORD_DEFAULT);  
    $fullname = $_POST['fullname'];
    $city = $_POST['city'];
    $wallet = 0;   
   
    // $stmt = $conn->prepare("SELECT id_user FROM users WHERE username =:username");
    // $params = array(
    //   ":username" => $username,
    // ); 
    // $stmt->execute($params);  
    // $stmt->store_result();
    
    $sql = "SELECT id_user FROM users WHERE username=:username";
    $stmt = $db->prepare($sql);
    $params = array(
      ":username" => $username,
    );
    $stmt->execute($params);
    //$stmt->store_result();
    
    if($stmt->rowCount() > 0){  
        $response['error'] = true;  
        $response['message'] = 'User already registered';  
    //     //$stmt->close();  
    }  
    else{  
        // $stmt = $conn->prepare("INSERT INTO users (username, password, fullname, city, wallet) VALUES (?, ?, ?, ?, ?)");  
        // $stmt->bind_param("ssss", $username, $password, $fullname, $city, $wallet);
        $sql = "INSERT INTO users (username, password, fullname, city, wallet) VALUES ('$username', '$password', '$fullname', '$city', '$wallet')";  
        $stmt = $db->prepare($sql);
        if($stmt->execute()){  
            // $stmt = $conn->prepare("SELECT id_user, id_user, username, fullname, city, wallet FROM users WHERE username = ?");   
            // $stmt->bind_param("s",$username);
            $sql = "SELECT * FROM users WHERE username=:username";
            $stmt = $db->prepare($sql);
            $params = array(
              ":username" => $username,
            );  
            $stmt->execute($params);  
            //$stmt->bind_result($userid, $id, $username, $fullname, $city, $wallet);  
            $user = $stmt->fetch(PDO::FETCH_ASSOC);  
   
            // $user = array(  
            // 'id'=>$id,   
            // 'username'=>$username,   
            // 'fullname'=>$fullname,
            // 'city'=>$city,
            // 'wallet'=>$wallet,
            // );  
   
            //$stmt->close();  
   
            $response['error'] = false;   
            $response['message'] = 'User registered successfully';   
            $response['user'] = $user;   
        }  
    }  
   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  

break;   

// case 'change_password':  
// if(isTheseParametersAvailable(array('userid','oldpassword','newpassword'))){  
//     $userid = $_POST['userid'];   
//     $oldpassword = md5($_POST['oldpassword']);   
//     $newpassword = md5($_POST['newpassword']);  
   
//     $stmt = $conn->prepare("SELECT user_id FROM user WHERE user_id = ? AND user_password = ?");  
//     $stmt->bind_param("ss", $userid, $oldpassword);  
//     $stmt->execute();  
//     $stmt->store_result();  
   
//     if($stmt->num_rows > 0){  
//         $stmt = $conn->prepare ("UPDATE user SET user_password='".$newpassword."' where user_id='".$userid."'");  
//         //$stmt->bind_param("ss", $userid);  
   
//         if($stmt->execute()){  
//             $response['error'] = false;   
//             $response['message'] = 'Password successfully changed';   
//             $response['user'] = $userid;   
//         }  
//     }  
//     else{  
//         $response['error'] = true;  
//         $response['message'] = 'Password is not match';  
//         $stmt->close();  
//     }  
   
// }  
// else{  
//     $response['error'] = true;   
//     $response['message'] = 'required parameters are not available user id' .$_POST['userid']. 'old psswrd ' .$_POST['oldpassword'] . ' new password ' .$_POST['newpassword'] ;   
// }  

// break;   


case 'login':  
  if(isTheseParametersAvailable(array('username', 'password'))){  
    $username = $_POST['username'];  
    $password = $_POST['password'];   
    
    $sql = "SELECT * FROM users WHERE username =:username";
    $stmt = $db->prepare($sql);
    $params = array(
      ":username" => $username,
    );
    $stmt->execute($params);
    $user = $stmt->fetch(PDO::FETCH_ASSOC);  
 
    if(password_verify($password, $user["password"])){  
      $response['error'] = false;   
      $response['message'] = 'Login successfull';   
      $response['user'] = $user;   
    }  
    else{  
      $response['error'] = true;   
      $response['message'] = 'Invalid username or password ';  
    }
  }
  else{  
      $response['error'] = true;   
      $response['message'] = 'required parameters are not available';
  }    
break;

case 'add_pendapatan':
  if(isTheseParametersAvailable(array('id_user', 'nominal', 'tanggal'))){
    $id_user = $_POST['id_user'];
    $jenis = 'pendapatan';
    $nominal = $_POST['nominal'];
    $tanggal = $_POST['tanggal'];

    $sql = "INSERT INTO history (id_user, jenis, nominal, tanggal) VALUES ('$id_user', '$jenis', '$nominal', '$tanggal')";
    $stmt = $db->prepare($sql);
    $stmt->execute();
    
    $sql = "SELECT * FROM users WHERE id_user=:id_user";
    $stmt = $db->prepare($sql);
    $params = array(
      ":id_user" => $id_user,
    );
    $stmt->execute($params);
    $user = $stmt->fetch(PDO::FETCH_ASSOC);
   
    $sql = "UPDATE users SET wallet=:wallet WHERE id_user=:id_user";
    $stmt = $db->prepare($sql);
    $params = array(
      ":wallet" => ($user['wallet'] + $nominal),
      ":id_user" => $id_user,
    );
    $stmt->execute($params);

    $sql = "SELECT * FROM history WHERE id_user=:id_user";
    $stmt = $db->prepare($sql);
    $params = array(
      ":id_user" => $id_user,
    );
    $stmt->execute($params);
    $list = $stmt->fetch(PDO::FETCH_ASSOC);

    $response['error'] = false;   
    $response['message'] = 'Data pendapatan berhasil ditambahkan!';
    $response['list'] = $list; 
  }
  else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';
  }
break;

case 'list_pendapatan':
  if(isTheseParametersAvailable(array('id_user'))){
    $id_user = $_POST['id_user'];

    $sql = "SELECT * FROM history WHERE id_user =:id_user AND jenis =:jenis";
    $stmt = $db->prepare($sql);
    $params = array(
      ":id_user" => $id_user,
      ":jenis" => 'pendapatan',
    );
    $stmt->execute($params);

    if($stmt->rowCount() == 0){
      $response['error'] = true;
      $response['message'] = 'List pendapatan kosong!';
    }
    else{
      while($list = $stmt->fetch(PDO::FETCH_ASSOC)){
        //$response['error'] = false;
        $response[] = $list;
      }
      //$response['message'] = 'Terdapat '.$stmt->rowCount().' list pendapatan';   
    }
    //echo json_encode(array("list"=>$response));
  }
  else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
  } 
break;

case 'add_pengeluaran':
  if(isTheseParametersAvailable(array('id_user', 'nominal', 'kegiatan'))){
    $id_user = $_POST['id_user'];
    $jenis = 'pengeluaran';
    $nominal = $_POST['nominal'];
    $kegiatan = $_POST['kegiatan'];

    $sql = "INSERT INTO history (id_user, jenis, nominal, kegiatan) VALUES ('$id_user', '$jenis', '$nominal', '$kegiatan')";
    $stmt = $db->prepare($sql);
    $stmt->execute();
    
    $sql = "SELECT * FROM users WHERE id_user=:id_user";
    $stmt = $db->prepare($sql);
    $params = array(
      ":id_user" => $id_user,
    );
    $stmt->execute($params);
    $user = $stmt->fetch(PDO::FETCH_ASSOC);
   
    $sql = "UPDATE users SET wallet=:wallet WHERE id_user=:id_user";
    $stmt = $db->prepare($sql);
    $params = array(
      ":wallet" => ($user['wallet'] - $nominal),
      ":id_user" => $id_user,
    );
    $stmt->execute($params);

    $sql = "SELECT * FROM history WHERE id_user=:id_user";
    $stmt = $db->prepare($sql);
    $params = array(
      ":id_user" => $id_user,
    );
    $stmt->execute($params);
    $list = $stmt->fetch(PDO::FETCH_ASSOC);
   
    $response['error'] = false;   
    $response['message'] = 'Data pengeluaran berhasil ditambahkan!';     
    $response['list'] = $list;
  }
  else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';
  }
break;

case 'list_pengeluaran':
  if(isTheseParametersAvailable(array('id_user'))){
    $id_user = $_POST['id_user'];

    $sql = "SELECT * FROM history WHERE id_user =:id_user AND jenis =:jenis";
    $stmt = $db->prepare($sql);
    $params = array(
      ":id_user" => $id_user,
      ":jenis" => 'pengeluaran',
    );
    $stmt->execute($params);

    if($stmt->rowCount() == 0){
      $response['error'] = true;
      $response['message'] = 'List pengeluaran kosong!';
    }
    else{
      while($list = $stmt->fetch(PDO::FETCH_ASSOC)){
        $response[] = $list;
      }
      //$response['error'] = false;
      //$response['message'] = 'Terdapat '.$stmt->rowCount().' list pengeluaran';
    }
  }
  else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
  }
break;

case 'add_wishlist':
  if(isTheseParametersAvailable(array('id_user', 'wish', 'tahun', 'harga'))){
    $id_user = $_POST['id_user'];
    $wish = $_POST['wish'];
    $tahun = $_POST['tahun'];
    $harga = $_POST['harga'];

    $sql = "INSERT INTO wishlist (id_user, wish, tahun, harga) VALUES ('$id_user', '$wish', '$tahun', '$harga')";
    $stmt = $db->prepare($sql);
    $stmt->execute();
    
    $sql = "SELECT * FROM wishlist WHERE id_user=:id_user";
    $stmt = $db->prepare($sql);
    $params = array(
      ":id_user" => $id_user,
    );
    $stmt->execute($params);
    $list = $stmt->fetch(PDO::FETCH_ASSOC);
   
    $response['error'] = false;   
    $response['message'] = 'Wishlist berhasil ditambahkan!';
    $response['list'] = $list;     
  }
  else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';
  }
break;

case 'wishlist':
  if(isTheseParametersAvailable(array('id_user'))){
    $id_user = $_POST['id_user'];

    $sql = "SELECT * FROM wishlist WHERE id_user =:id_user";
    $stmt = $db->prepare($sql);
    $params = array(
      ":id_user" => $id_user,
    );
    $stmt->execute($params);

    if($stmt->rowCount() == 0){
      $response['error'] = true;
      $response['message'] = 'Wishlist kosong!';
    }
    else{
      while($list = $stmt->fetch(PDO::FETCH_ASSOC)){
        $response[] = $list;
      }
      //$response['error'] = false;
      //$response['message'] = 'Terdapat '.$stmt->rowCount().' wishlist';
      
    }
  }
  else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
  }
break;

case 'list_history':
  if(isTheseParametersAvailable(array('id_user'))){
    $id_user = $_POST['id_user'];

    $sql = "SELECT * FROM history WHERE id_user =:id_user";
    $stmt = $db->prepare($sql);
    $params = array(
      ":id_user" => $id_user,
    );
    $stmt->execute($params);

    if($stmt->rowCount() == 0){
      $response['error'] = true;
      $response['message'] = 'Wishlist kosong!';
    }
    else{
      while($list = $stmt->fetch(PDO::FETCH_ASSOC)){
        $response[] = $list;
      }
      //$response['error'] = false;
      //$response['message'] = 'Terdapat '.$stmt->rowCount().' wishlist';
      
    }
  }
  else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
  }
break;

default:   
 $response['error'] = true;   
 $response['message'] = 'Invalid Operation Called';  
}  
}  
else{  
 $response['error'] = true;   
 $response['message'] = 'Invalid API Call';  
}
header('Content-Type: application/json');  
echo json_encode($response);  
function isTheseParametersAvailable($params){  
foreach($params as $param){  
 if(!isset($_POST[$param])){  
     return false;   
  }  
}  
return true;   
}  
?>  