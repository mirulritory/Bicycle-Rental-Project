<?php
require_once("redmineDb.php");
session_start();

// Enable error reporting
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Retrieve POST data
    $username = $_POST['username'];
    $password = $_POST['password'];

    // Debug: Print received data
    error_log("Received username: $username");
    error_log("Received password: $password");

    try {
        // Access the global $db variable
        global $db;

        // Debug: Verify if $db is set
        if ($db === null) {
            throw new Exception("Database connection is null.");
        }

        // Prepare the SQL statement
        $stmt = $db->prepare("SELECT * FROM user WHERE userID = :username");
        $stmt->bindParam(':username', $username);
        $stmt->execute();

        // Fetch the user data
        $user = $stmt->fetch(PDO::FETCH_ASSOC);

        // Debug: Print fetched user data
        error_log("Fetched user: " . json_encode($user));

        // Check if user exists and the password is correct
        if ($user) {
            error_log("Password from database: " . $user['passwordU']);
            if ($password === $user['passwordU']) {
                // Valid credentials
                $_SESSION['loggedInUser'] = $username;
                echo json_encode(["status" => "success"]);
            } else {
                // Password mismatch
                error_log("Password mismatch");
                echo json_encode(["status" => "error", "message" => "Invalid password"]);
            }
        } else {
            // User not found
            error_log("User not found");
            echo json_encode(["status" => "error", "message" => "User not found"]);
        }
    } catch (PDOException $e) {
        // Handle SQL error
        echo json_encode(["status" => "error", "message" => $e->getMessage()]);
        error_log("SQL Error: " . $e->getMessage());
    } catch (Exception $e) {
        // Handle other exceptions
        echo json_encode(["status" => "error", "message" => $e->getMessage()]);
        error_log("General Error: " . $e->getMessage());
    }
}
?>
