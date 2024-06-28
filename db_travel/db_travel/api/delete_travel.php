<?php

include '../config/config.php';

/**
 *  @var $connection PDO
 */


if ($_SERVER['REQUEST_METHOD'] != 'DELETE') {
    header('Content-Type: application/json');
    http_response_code(400);
    $reply['meta'] = [
        'message' => 'DELETE method required',
        'status' => false
    ];
    echo json_encode($reply);
    exit();
}

// Mengambil parameter ID dari URL
$id = isset($_GET['id']) ? $_GET['id'] : 0;

/**
 * Validation
 */
$isValidate = true;
$message = "";
$code = 200;

if (empty($id)) {
    $message = 'Id barang harus diisi';
    $isValidate = false;
}

if (!$isValidate) {
    http_response_code(400);
    $reply['meta'] = [
        'message' => $message,
        'status' => false
    ];
    echo json_encode($reply);
    exit();
}

try {
    $queryCheck = "SELECT * FROM boking WHERE id = :id";
    $statement = $connection->prepare($queryCheck);
    $statement->bindValue(":id", $id);
    $statement->execute();

    $row = $statement->rowCount();

    if ($row === 0) {
        http_response_code(200);
        $reply['meta'] = [
            'message' => "Data dengan ID " . $id . " Tidak ditemukan",
            'status' => false
        ];
        echo json_encode($reply);
        exit();
    }

} catch (Exception $exception) {
    http_response_code(400);
    $reply['meta'] = [
        'message' => $exception->getMessage(),
        'status' => false
    ];
    echo json_encode($reply);
    exit();
}

try {
    $queryDelete = "DELETE FROM boking WHERE id = :id";
    $statement = $connection->prepare($queryDelete);
    $statement->bindValue(":id", $id);
    $statement->execute();

} catch (Exception $exception) {
    http_response_code(400);
    $reply['meta'] = [
        'message' => $exception->getMessage(),
        'status' => false
    ];
    echo json_encode($reply);
    exit();
}

header('Content-Type: application/json');
$reply['meta'] = [
    'message' => "boking berhasil di Delete",
    'status' => $isValidate
];
http_response_code($code);
echo json_encode($reply);
?>
