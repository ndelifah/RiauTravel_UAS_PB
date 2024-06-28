<?php

include '../config/config.php';

// Mengambil data dari tabel user
$userQuery = $connection->query("SELECT * FROM users");

// Inisialisasi array untuk menyimpan data user
$users = [];

// Mengambil setiap baris data dan menambahkannya ke array users
while ($row = $userQuery->fetch(PDO::FETCH_ASSOC)) {
    $users[] = $row;
}

// Menyiapkan respons JSON
$response = [
    'status' => true,
    'message' => 'Data pengguna berhasil diambil',
    'data' => $users
];

// Mengonversi respons menjadi format JSON
$json = json_encode($response, JSON_PRETTY_PRINT);

// Menampilkan JSON
echo $json;
?>
