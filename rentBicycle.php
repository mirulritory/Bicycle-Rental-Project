<?php
require_once("redmineDb.php");
session_start();

// Enable error reporting
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $bicycleID = $_POST['bicycleID'];
    $username = $_POST['username'];

    // Log received data
    error_log("Received bicycleID: $bicycleID");
    error_log("Received username: $username");

    try {
        // Access the global $db variable
        global $db;

        // Prepare the SQL statement to update bicycle status
        $stmt = $db->prepare("UPDATE bicycle SET status = 'rented' WHERE bicycleID = :bicycleID AND status = 'available'");
        $stmt->bindParam(':bicycleID', $bicycleID);
        $stmt->execute();

        // Log the number of affected rows
        error_log("Number of affected rows: " . $stmt->rowCount());

        if ($stmt->rowCount() > 0) {
            // Prepare the SQL statement to insert rental record
            $stmt = $db->prepare("INSERT INTO rental (userID, bicycleID, rentalTime, returnStat) VALUES (:userID, :bicycleID, NOW(), 'not return')");
            $stmt->bindParam(':userID', $username);
            $stmt->bindParam(':bicycleID', $bicycleID);
            $stmt->execute();
        
            echo json_encode(["status" => "success"]);
        } else {
            echo json_encode(["status" => "error", "message" => "Bicycle is not available"]);
        }
    } catch (PDOException $e) {
        echo json_encode(["status" => "error", "message" => $e->getMessage()]);
        error_log("SQL Error: " . $e->getMessage());
    }
}
?>
