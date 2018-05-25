(function(){
    var $usernameFld, $passwordFld;
    var $loginBtn;
    var userService = new UserServiceClient();

    $(main);

    function main() {
        $usernameFld = $('#usernameFld');
        $passwordFld = $('#passwordFld');
        $loginBtn = $('#wbdv-sign-in');

        $loginBtn.click(login);

    }


    function login() {
        var username = $usernameFld.val();
        var password = $passwordFld.val();
        var user = {
            username: username,
            password: password
        }

        userService.login(user).then(function(loggedin){
            console.log(loggedin);
            if (loggedin) {

                window.location.href = "http://localhost:8080/jquery/components/profile/profile.template.client.html";
            } else {

                // alert("invalid credential");
                $('#wbdv-alert').css('display', 'block');
                $('#wbdv-alert').delay(2000).slideUp(200);
            }
        });
    }
})()