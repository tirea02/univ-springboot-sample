document.getElementById("registrationForm").onsubmit = function() {
    // Get form inputs
    var name = document.getElementsByName("name")[0].value;
    var userId = document.getElementsByName("userId")[0].value; // Changed from accountId to userId
    var password = document.getElementsByName("password")[0].value;
    var passwordConfirm = document.getElementsByName("passwordConfirm")[0].value;
    var phone = document.getElementsByName("phone")[0].value;

    // Clear previous error messages
    document.getElementById("nameError").innerHTML = "";
    document.getElementById("userIdError").innerHTML = "";
    document.getElementById("passwordError").innerHTML = "";
    document.getElementById("passwordConfirmError").innerHTML = "";
    document.getElementById("phoneError").innerHTML = "";

    // Perform name validation
    if (name.trim() === "") {
        document.getElementById("nameError").innerHTML = "Please enter your name.";
        return false;
    }

    // Perform userId validation
    if (userId.trim() === "") {
        document.getElementById("userIdError").innerHTML = "Please enter your userId."; // Changed from accountId to userId
        return false;
    }

    // Perform password match validation
    if (password !== passwordConfirm) {
        document.getElementById("passwordConfirmError").innerHTML = "Passwords do not match.";
        return false;
    }

    // Perform phone validation (optional, add your validation logic if needed)
    if (phone.trim() === "") {
        document.getElementById("phoneError").innerHTML = "Please enter your phone number.";
        return false;
    }

    // Disable submit button to prevent multiple submissions
    document.getElementById("submitButton").disabled = true;

    // If all validations pass, allow form submission
    return true;
};
