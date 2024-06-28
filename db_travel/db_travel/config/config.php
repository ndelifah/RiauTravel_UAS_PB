<?php

$dbserver = 'localhost';
$dbname = 'db_travel';
$dbuser = 'root';
$dbpassword = '';
$dsn = "mysql:host={$dbserver};dbname={$dbname}";

$connection = null;

try {
    // Membuat koneksi dengan PDO
    $connection = new PDO($dsn, $dbuser, $dbpassword);
    // Mengatur atribut PDO untuk menampilkan kesalahan dalam bentuk exception
    $connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (Exception $exception) {
    // Menampilkan pesan kesalahan jika koneksi gagal
    die("Terjadi error: " . $exception->getMessage());
}

?>
