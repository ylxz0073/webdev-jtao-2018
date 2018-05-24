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
                    alert("try another username!");
                }
            });

        } else {
            alert("verify password error!");
        }

    }


})()