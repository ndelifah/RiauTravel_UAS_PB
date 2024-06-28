<?php

include '../config/config.php';

date_default_timezone_set('Asia/Jakarta');

/**
 *  @var $connection PDO
 */

if ($_SERVER['REQUEST_METHOD'] != 'POST') {
    header('Content-Type: application/json');
    http_response_code(400);
    $reply['meta'] = [
        'message' => 'POST method required',
        'status' => false
    ];
    echo json_encode($reply);
    exit();
}

/**
 *  Input data
 */

$nama = $_POST['nama'] ?? '';
$no_telpon = $_POST['no_telpon'] ?? '';
$destinasi = $_POST['destinasi'] ?? '';
$jumlah_tiket = isset($_POST['jumlah_tiket']) ? (int)$_POST['jumlah_tiket'] : null; // Cast jumlah_tiket to an integer
$tanggal = date('Y-m-d H:i:s'); // Get current date and time
$harga = isset($_POST['harga']) ? (int)$_POST['harga'] : null; // Cast harga to an integer
$gambar = $_FILES['gambar'] ?? null; // File upload

/**
 * Validation
 */
$status = false;
$message = "";
$code = 200;
$isValidate = true;

if (empty($nama)) {
    $message = "Nama harus diisi";
    $isValidate = false;
}
if (empty($no_telpon)) {
    $message = "Nomor telepon harus diisi";
    $isValidate = false;
}
if (empty($destinasi)) {
    $message = "Destinasi harus diisi";
    $isValidate = false;
}
if (empty($jumlah_tiket) || $jumlah_tiket <= 0) { // Ensure jumlah_tiket is a positive integer
    $message = "Jumlah tiket harus angka positif";
    $isValidate = false;
}
if (empty($harga) || $harga <= 0) { // Ensure harga is a positive integer
    $message = "Harga harus angka positif";
    $isValidate = false;
}
if (empty($gambar) || $gambar['error'] != UPLOAD_ERR_OK) {
    $message = "Gambar harus diunggah";
    $isValidate = false;
}

if (!$isValidate) {
    http_response_code(400);
    $reply['meta'] = [
        'message' => $message,
        'status' => $isValidate
    ];
    echo json_encode($reply);
    exit();
}

try {
    // Ensure the uploads directory exists and is writable
    $uploadDir = '../uploads/';
    if (!is_dir($uploadDir)) {
        mkdir($uploadDir, 0755, true);
    }

    // Generate a unique file name to prevent overwriting
    $uniqueName = uniqid() . '-' . basename($gambar['name']);
    $uploadFile = $uploadDir . $uniqueName;

    if (!move_uploaded_file($gambar['tmp_name'], $uploadFile)) {
        throw new Exception("Gagal mengunggah gambar.");
    }

    // Insert into database
    $insertboking = "INSERT INTO boking (nama, no_telpon, destinasi, jumlah_tiket, tanggal, harga, gambar, createdAt, updatedAt) VALUES (:nama, :no_telpon, :destinasi, :jumlah_tiket, :tanggal, :harga, :gambar, :createdAt, :updatedAt)";
    $statement = $connection->prepare($insertboking);

    // Bind values
    $statement->bindValue(":nama", $nama);
    $statement->bindValue(":no_telpon", $no_telpon);
    $statement->bindValue(":destinasi", $destinasi);
    $statement->bindValue(":jumlah_tiket", $jumlah_tiket, PDO::PARAM_INT); // Ensure jumlah_tiket is bound as an integer
    $statement->bindValue(":tanggal", $tanggal); // Bind the current datetime
    $statement->bindValue(":harga", $harga, PDO::PARAM_INT); // Ensure harga is bound as an integer
    $statement->bindValue(":gambar", $uniqueName); // Store the unique file name
    $statement->bindValue(":createdAt", $tanggal); // createdAt is set to current datetime
    $statement->bindValue(":updatedAt", $tanggal); // updatedAt is set to current datetime

    $execute = $statement->execute();
    if ($execute) {
        $message = "boking berhasil ditambahkan";
        $status = true;
    } else {
        $message = $statement->errorInfo();
        $code = 400;
    }

} catch (Exception $exception) {
    $message = $exception->getMessage();
    $status = false;
    $code = 400;
}

// HEADER API
header('Content-Type: application/json');
$reply['meta'] = [
    'message' => $message,
    'status' => $status
];
http_response_code($code);
echo json_encode($reply);

?>
