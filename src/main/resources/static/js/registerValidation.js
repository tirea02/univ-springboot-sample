$(document).ready(function() {
    $('#registrationForm').submit(function(event) {
        event.preventDefault(); // Prevent the default form submission

        var name = document.getElementsByName("name")[0].value;
        var userId = document.getElementsByName("userId")[0].value; // Changed from accountId to userId
        var password = document.getElementsByName("password")[0].value;
        var passwordConfirm = document.getElementsByName("passwordConfirm")[0].value;
        var phone = document.getElementsByName("phone")[0].value;

        var isValid = true;

        // Clear previous error messages
        document.getElementById("nameError").innerHTML = "";
        document.getElementById("userIdError").innerHTML = "";
        document.getElementById("passwordError").innerHTML = "";
        document.getElementById("passwordConfirmError").innerHTML = "";
        document.getElementById("phoneError").innerHTML = "";

        // Perform name validation
        if (name.trim() === "") {
            document.getElementById("nameError").innerHTML = "Please enter your name.";
            isValid = false;
        }

        // Perform userId validation
        if (userId.trim() === "") {
            document.getElementById("userIdError").innerHTML = "Please enter your userId."; // Changed from accountId to userId
            isValid = false;

        }

        // Perform password match validation
        if (password !== passwordConfirm) {
            document.getElementById("passwordConfirmError").innerHTML = "Passwords do not match.";
            isValid = false;

        }

        // Perform phone validation (optional, add your validation logic if needed)
        if (phone.trim() === "") {
            document.getElementById("phoneError").innerHTML = "Please enter your phone number.";
            isValid = false;
        }

        // Disable submit button to prevent multiple submissions
        document.getElementById("submitButton").disabled = true;


        if (isValid) {
            var formData = $(this).serialize(); // Serialize the form data

            $.ajax({
                type: 'POST',
                url: '/user/register',
                contentType: 'application/json',
                data: JSON.stringify({
                    name: $('input[name="name"]').val(),
                    userId: $('input[name="userId"]').val(),
                    password: $('input[name="password"]').val(),
                    phone: $('input[name="phone"]').val()
                    // Include other form fields as necessary
                }),
                success: function(response) {
                    console.log('Success:', response);
                    window.location.href = '/login.html';
                },
                error: function(xhr, status, error) {
                    console.log('Error:', error);
                }
            });
        }
    });
});




