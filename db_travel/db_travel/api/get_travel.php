<?php

//menampilkan seluruh produk

include '../config/config.php';

/**
 * @var $connection PDO
 */

//Method selain GET tidak boleh
if ($_SERVER['REQUEST_METHOD'] != 'GET') {

    header('Content-Type: application/json');
    http_response_code(400);
    $reply['meta'] = [
        'message' => 'GET method required',
        'status' =>  false
    ];
    echo json_encode($reply);
    exit();
}
/*
 * GET
 */
//meta
$status = false;
$message = "";
$code = 200;
try {

    //menampilkan seluruh product
    /**
     * SELECT * FROM NAMA_TABLE QUERY LAINNYA (WHERE, ORDER BY)
     */
    $boking = "SELECT * FROM boking ORDER BY id DESC";
    $statement = $connection->prepare($boking);
    $statement->execute();

    //ambil seluruh isi table
    $boking = $statement->fetchAll(PDO::FETCH_ASSOC);

    $reply['list_boking'] = $boking;
    $message = "boking Found";
    $status = true;
    $code = 200;


} catch (PDOException $exception) {
    $reply['list_boking'] = null;
    $message = $exception->getMessage();
    $status = false;
    $code = 400;
}

//kalau product kosong
if (empty($boking)) {
    $reply['list_boking'] = null;
    $message = "boking Not Found";
    $status = false;
    $code = 200;
}

/*$reply['list_boking'] = $boking;
$message = "Product Found";
$status = true;
$code = 200;*/

//HEADER API
header('Content-Type: application/json');
$reply['meta'] = [
    'message' => $message,
    'status' => $status
];
http_response_code($code);
echo json_encode($reply);