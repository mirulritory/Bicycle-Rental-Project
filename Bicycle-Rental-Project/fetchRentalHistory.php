<?php
require_once("redmineDb.php");

try {
    // Prepare the SQL statement to fetch rental history
    $stmt = $db->prepare("
        SELECT u.Name, u.phoneNo, r.bicycleID
        FROM rental r
        JOIN user u ON r.userID = u.userID
    ");
    $stmt->execute();

    // Fetch all rental history with user details
    $rentals = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Output the rental history as JSON
    echo json_encode($rentals);
} catch (PDOException $e) {
    echo json_encode(["status" => "error", "message" => $e->getMessage()]);
}
