
<?php
require_once("redmineDb.php");

$username = $_GET['username'];

try {
    // Prepare the SQL statement to fetch rented bicycles for the logged-in user
    $stmt = $db->prepare("SELECT bicycleID FROM rental WHERE userID = :username AND returnStat = 'not return'");
    $stmt->bindParam(':username', $username);
    $stmt->execute();

    // Fetch all rented bicycles
    $bicycles = $stmt->fetchAll(PDO::FETCH_ASSOC);

    foreach ($bicycles as $bicycle) {
        echo $bicycle['bicycleID'] . "\n"; // Output the bicycleID
    }
} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>
