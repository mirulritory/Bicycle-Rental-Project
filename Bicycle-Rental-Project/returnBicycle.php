<?php
require_once("redmineDb.php");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Retrieve POST data
    $bicycleID = $_POST['bicycleID'];
    $username = $_POST['username'];

    try {
        // Begin transaction
        $db->beginTransaction();

        // Update bicycle status to available
        $stmt1 = $db->prepare("UPDATE bicycle SET status = 'available' WHERE bicycleID = :bicycleID");
        $stmt1->bindParam(':bicycleID', $bicycleID);
        $stmt1->execute();

        // Update rental returnStat to returned
        $stmt2 = $db->prepare("UPDATE rental SET returnStat = 'returned' WHERE bicycleID = :bicycleID AND userID = :username AND returnStat = 'not return'");
        $stmt2->bindParam(':bicycleID', $bicycleID);
        $stmt2->bindParam(':username', $username);
        $stmt2->execute();

        // Commit transaction
        $db->commit();

        echo json_encode(["status" => "success"]);
    } catch (PDOException $e) {
        // Rollback transaction in case of error
        $db->rollBack();
        echo json_encode(["status" => "error", "message" => $e->getMessage()]);
    }
}
