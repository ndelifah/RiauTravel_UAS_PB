<?php

include '../config/config.php';

if ($_POST) {

    //Data
    $email = $_POST['email'] ?? '';
    $password = $_POST['password'] ?? '';

    $response = []; //Data Response

    try {
        //Cek email didalam databse
        $userQuery = $connection->prepare("SELECT * FROM users where email = ? ");
        $userQuery->execute(array($email));
        $query = $userQuery->fetch();

        if ($userQuery->rowCount() == 0) {
            $response['status'] = false;
            $response['message'] = "email Tidak Terdaftar";
        } else {
            // Ambil password di db

            $passwordDB = $query['password'];

            if (strcmp(md5($password), $passwordDB) === 0) {
                $response['status'] = true;
                $response['message'] = "Login Berhasil";
                $response['data'] = [
                    'user_id' => $query['id'],
                    'email' => $query['email'],
                    'fullname' => $query['fullname']
                ];
            } else {
                $response['status'] = false;
                $response['message'] = "Password anda salah";
            }
        }
    } catch (PDOException $e) {
        // Tangani kesalahan koneksi database
        $response['status'] = false;
        $response['message'] = "Kesalahan database: " . $e->getMessage();
    }

    //Jadikan data JSON
    $json = json_encode($response, JSON_PRETTY_PRINT);

    //Print
    echo $json;
}
