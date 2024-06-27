<?php
// Database configuration
$host = 'localhost';
$dbname = 'bicycle_rental';
$username = 'root';
$password = '';

try {
    // Create a new PDO instance
    $db = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
    // Set the PDO error mode to exception
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    //echo "Connected to database successfully.";
} catch (PDOException $e) {
    // Handle connection error
    echo "Connection failed: " . $e->getMessage();
    error_log("Connection failed: " . $e->getMessage());
}
?>
