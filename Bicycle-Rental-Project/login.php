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

        // Function to validate credentials
        function validateCredentials($db, $username, $password, $table, $passwordColumn) {
            $stmt = $db->prepare("SELECT * FROM $table WHERE userID = :username");
            $stmt->bindParam(':username', $username);
            $stmt->execute();
            $user = $stmt->fetch(PDO::FETCH_ASSOC);

            if ($user && $password === $user[$passwordColumn]) {
                return $user;
            }
            return null;
        }

        // Check user table first
        $user = validateCredentials($db, $username, $password, 'user', 'passwordU');
        if ($user) {
            $_SESSION['loggedInUser'] = $username;
            echo json_encode(["status" => "success", "role" => "user"]);
        } else {
            // Check staff table
            $staff = validateCredentials($db, $username, $password, 'staff', 'password');
            if ($staff) {
                $_SESSION['loggedInUser'] = $username;
                echo json_encode(["status" => "success", "role" => "staff"]);
            } else {
                // Invalid credentials
                echo json_encode(["status" => "error", "message" => "Invalid username or password"]);
            }
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
