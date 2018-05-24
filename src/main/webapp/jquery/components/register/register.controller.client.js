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
            var userCredential = {
                username: username,
                password: password
            }
            userService.register(userCredential).then(function(result){
                // console.log(result);
                if (result == true) {
                    alert("register success!");
                } else {
                    alert("try another username!");
                }
            });

        } else {
            alert("verify password error!");
        }

    }


})()