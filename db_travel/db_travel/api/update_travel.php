<?php

include '../config/config.php';

/**
 *  @var $connection PDO
 */

if ($_SERVER['REQUEST_METHOD'] != 'POST' || ($_SERVER['REQUEST_METHOD'] == 'POST' && (!isset($_POST['_method']) || $_POST['_method'] != 'PATCH'))) {
    header('Content-Type: application/json');
    http_response_code(400);
    $reply['meta'] = [
        'message' => 'PATCH method required',
        'status' => false
    ];
    echo json_encode($reply);
    exit();
}

// Mendapatkan data dari form-data
$id = isset($_POST['id']) ? intval($_POST['id']) : 0;
$nama = isset($_POST['nama']) ? $_POST['nama'] : "";
$no_telpon = isset($_POST['no_telpon']) ? $_POST['no_telpon'] : "";
$destinasi = isset($_POST['destinasi']) ? $_POST['destinasi'] : "";
$jumlah_tiket = isset($_POST['jumlah_tiket']) ? intval($_POST['jumlah_tiket']) : 0;
$tanggal = date('Y-m-d H:i:s'); // Waktu saat ini untuk createdAt dan updatedAt
$harga = isset($_POST['harga']) ? intval($_POST['harga']) : 0;
$createdAt = $tanggal; // Waktu saat ini
$updatedAt = $tanggal; // Waktu saat ini
$gambar = $_FILES['gambar'] ?? null; // File upload
$uploadedFilePath = ''; // Inisialisasi untuk penanganan string

/**
 * Validation
 */
$status = false;
$message = "";
$code = 200;
$isValidate = true;

if (empty($id)) {
    $message = 'Id harus diisi';
    $isValidate = false;
}

if (empty($nama)) {
    $message = 'Nama harus diisi';
    $isValidate = false;
}

if (empty($no_telpon)) {
    $message = 'Nomor telepon harus diisi';
    $isValidate = false;
}

if (empty($destinasi)) {
    $message = 'Destinasi harus diisi';
    $isValidate = false;
}

if (empty($jumlah_tiket) || $jumlah_tiket <= 0) {
    $message = 'Jumlah tiket harus angka positif';
    $isValidate = false;
}

if (empty($harga) || $harga <= 0) {
    $message = 'Harga harus angka positif';
    $isValidate = false;
}

/**
 * Filter gagal
 */
if (!$isValidate) {
    http_response_code($code);
    $reply['meta'] = [
        'message'=> $message,
        'status' => $isValidate
    ];
    echo json_encode($reply);
    exit();
}

try {
    // Memastikan direktori upload ada dan memiliki izin yang tepat
    $uploadDir = '../uploads/';
    if (!is_dir($uploadDir)) {
        mkdir($uploadDir, 0755, true);
    }

    // Handle gambar jika diunggah
    if (!empty($gambar['name'])) {
        $fileName = basename($gambar['name']);
        $targetFilePath = $uploadDir . $fileName;

        // Memastikan file diunggah melalui HTTP POST
        if (move_uploaded_file($gambar['tmp_name'], $targetFilePath)) {
            $uploadedFilePath = $fileName; // Menggunakan nama file jika berhasil diunggah
        } else {
            throw new Exception("Gagal mengunggah gambar.");
        }
    }

    // Prepare SQL statement
    if (!empty($uploadedFilePath)) {
        $updateQuery = "UPDATE boking SET nama = :nama, no_telpon = :no_telpon, destinasi = :destinasi, jumlah_tiket = :jumlah_tiket, harga = :harga, gambar = :gambar, updatedAt = :updatedAt WHERE id = :id";
    } else {
        $updateQuery = "UPDATE boking SET nama = :nama, no_telpon = :no_telpon, destinasi = :destinasi, jumlah_tiket = :jumlah_tiket, harga = :harga, updatedAt = :updatedAt WHERE id = :id";
    }
    $statement = $connection->prepare($updateQuery);

    // Bind parameters
    $statement->bindParam(':nama', $nama);
    $statement->bindParam(':no_telpon', $no_telpon);
    $statement->bindParam(':destinasi', $destinasi);
    $statement->bindParam(':jumlah_tiket', $jumlah_tiket);
    $statement->bindParam(':harga', $harga);
    $statement->bindParam(':updatedAt', $updatedAt);
    $statement->bindParam(':id', $id);
    
    if (!empty($uploadedFilePath)) {
        $statement->bindParam(':gambar', $uploadedFilePath); // Menggunakan PDO::PARAM_STR untuk file path
    }

    // Execute the update statement
    $execute = $statement->execute();

    if ($execute) {
        $message = "Data boking berhasil diupdate";
        $status = true;
    } else {
        $message = "Gagal mengupdate data boking";
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
