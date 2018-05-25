(function() {
    var $usernameFld,
        $passwordFld,
        $verifyPasswordFld;
    var $registerBtn;
    var userService = new UserServiceClient();

    $(main);

    function main() {
        $usernameFld = $('#inputUsernameFld');
        $passwordFld = $('#inputPasswordFld');
        $verifyPasswordFld = $("#verifyPasswordFld");
        $registerBtn = $("#wbdv-sign-up");

        $registerBtn.click(register);
    }

    function register() {

        var username = $usernameFld.val(),
            password = $passwordFld.val(),
            verifypassword = $verifyPasswordFld.val();
        if (password === verifypassword) {

            var userCredential = new User(username, password);
            userService.register(userCredential).then(function(result){

                if (result) {
                    // console.log(result);
                    window.location.href = "http://localhost:8080/jquery/components/profile/profile.template.client.html";
                } else {
                    $('#wbdv-alert-username').css('display', 'block');
                    $('#wbdv-alert-username').delay(2000).slideUp(200);
                }
            });

        } else {
            // alert("password doesn't match");
            $('#wbdv-alert-pasword').css('display', 'block');
            $('#wbdv-alert-pasword').delay(2000).slideUp(200);
        }

    }


})()