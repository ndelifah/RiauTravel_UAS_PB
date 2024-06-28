<?php

include '../config/config.php';

if($_POST){

    //POST DATA
    $email = filter_input(INPUT_POST, 'email',FILTER_SANITIZE_STRING);
    $password = filter_input(INPUT_POST, 'password',FILTER_SANITIZE_STRING);
    $fullname = filter_input(INPUT_POST, 'fullname',FILTER_SANITIZE_STRING);
    $response = [];

    //Cek email didalam database
    $userQuery = $connection->prepare("SELECT * FROM users WHERE email = ?");
    $userQuery->execute(array($email));

    // Cek apakah email sudah ada atau belum
    if($userQuery->rowCount() != 0){
        // Beri response
        $response['status'] = false;
        $response['message'] = 'Akun sudah digunakan';
    } else {
        $insertAccount = 'INSERT INTO users (email, password, fullname) VALUES (:email, :password, :fullname)';
        $statement = $connection->prepare($insertAccount);

        try{
            //Eksekusi statement db
            $statement->execute([
                ':email' => $email,
                ':password' => md5($password),
                ':fullname' => $fullname
            ]);

            //Beri response
            $response['status'] = true;
            $response['message'] = 'Akun berhasil didaftar';
            $response['data'] = [
                'email' => $email,
                'fullname' => $fullname
            ];
        } catch (Exception $e){
            die($e->getMessage());
        }

    }
    
    //Jadikan data JSON
    $json = json_encode($response, JSON_PRETTY_PRINT);

    //Print JSON
    echo $json;
}
?>
