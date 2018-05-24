(function(){
    var $usernameFld,
        $phoneFld,
        $roleFld,
        $emailFld,
        $dateOfBirthFld;
    var $updateBtn,
        $logoutBtn;
    var userService = new UserServiceClient();

    $(main);

    function main() {
        $usernameFld = $('#usernameFld');
        $phoneFld = $('#phoneFld');
        $roleFld = $('#roleFld');
        $emailFld = $('#emailFld');
        $dateOfBirthFld = $('#dateOfBirthFld');
        $updateBtn = $('#wbdv-update');
        $logoutBtn = $('#wbdv-logout');

        $updateBtn.click(updateProfile);
        $logoutBtn.click(logout);

        userService.getProfile().then(function(currentUser){
            // console.log(currentUser);
            renderUser(currentUser);
        });


    }

    function logout() {
        userService.logout();
        localStorage.setItem("currentLoginUser", null);
        window.location.href = "http://localhost:8080/jquery/components/login/login.template.client.html";
    }

    function updateProfile() {
        var phone = $phoneFld.val(),
            email = $emailFld.val(),
            role = $roleFld.val(),
            dateOfBirth = $dateOfBirthFld.val();
        var user = {
            phone: phone,
            email: email,
            role: role,
            dateOfBirth: dateOfBirth
        }
        // console.log(user);
        userService.updateProfile(user).then(function(updated){
            if(updated) {
                console.log(updated);
                alert("update success!");
            }
        }) ;
    }

    function renderUser(user) {
        // console.log(user);
        $usernameFld.val(user.username);
        $phoneFld.val(user.phone);
        $roleFld.val(user.role);
        $emailFld.val(user.email);
        $dateOfBirthFld.val(user.dateOfBirth);
    }
})()