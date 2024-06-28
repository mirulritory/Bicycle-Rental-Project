<?php
require_once("redmineDb.php");

try {
    // Prepare the SQL statement to fetch available bicycles
    $stmt = $db->prepare("SELECT bicycleID FROM bicycle WHERE status = 'available'");
    $stmt->execute();

    // Fetch all available bicycles
    $bicycles = $stmt->fetchAll(PDO::FETCH_ASSOC);

    foreach ($bicycles as $bicycle) {
        echo $bicycle['bicycleID'] . "\n"; // Output the bicycleID
    }
} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>