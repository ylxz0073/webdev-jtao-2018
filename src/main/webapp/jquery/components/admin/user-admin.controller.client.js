(function () {
    var $usernameFld,
        $passwordFld;
    var $removeBtn, $editBtn,
        $createBtn, $updateBtn;
    var $firstNameFld,
        $lastNameFld;
    var $roleFld;
    var $userRowTemplate, $tbody;
    var $form;
    var userService;

    jQuery(main);




    function main() {
        $tbody = $('tbody');
        $userRowTemplate = $('.wbdv-template');
        $createBtn = $('.wbdv-create');
        $updateBtn = $('.wbdv-update')

        $usernameFld = $('#usernameFld');
        $passwordFld = $('#passwordFld');
        $firstNameFld = $('#firstNameFld');
        $lastNameFld = $('#lastNameFld');
        $roleFld = $('#roleFld');
        $form = $('.wbdv-form');

        userService = new UserServiceClient();

        $createBtn.click(createUser);
        $updateBtn.click(updateUser);


        findAllUsers();
    }

    function createUser() {

        var user = getUserFromForm();

        clearForm();
        userService.createUser(user).then(findAllUsers);

    }

    function getUserFromForm(){
        var username = $usernameFld.val();
        var password = $passwordFld.val();
        var firstName = $firstNameFld.val();
        var lastName = $lastNameFld.val();
        var role = $('#roleFld').val();


        var user = new User(username, password, firstName, lastName);
        user.setRole(role);
        return user;
    }

    function findAllUsers() {
        userService.findAllUsers().then(renderUsers);
    }

    function findUserById(userId) {
        userService
            .findUserById(userId)
            .then(renderUser);
    }

    function deleteUser() {
        $removeBtn = $(event.currentTarget);
        $userRowTemplate = $removeBtn.parent().parent().parent();
        var userId = $userRowTemplate.attr('id');
        userService
            .deleteUser(userId)
            .then(findAllUsers);
    }

    function selectUser() {
        $editBtn = $(event.currentTarget);
        $userRowTemplate = $editBtn.parent().parent().parent();
        var userId = $userRowTemplate.attr('id');
        userService
            .findUserById(userId)
            .then(renderUser);
    }

    function updateUser() {
        // put user information on form
        var userId = $form.attr('id');
        var newUser = getUserFromForm();

        if (userId != null && userId != -1) {
            $form.attr('id', -1);
            clearForm();
            userService
                .updateUser(userId, newUser)
                .then(findAllUsers);

        }

    }

    function clearForm(){
        $usernameFld.val("");
        $passwordFld.val("");
        $firstNameFld.val("");
        $lastNameFld.val("");
        $('#roleFld').val("FACULTY");
    }


    function success(response) {
        if(response === null) {
            alert("unable to update!");
        } else {
            alert("success!");
        }
    }

    function renderUser(user) {
        console.log(user);
        $usernameFld.val(user.username);
        $passwordFld.val(user.password);
        $firstNameFld.val(user.firstName);
        $lastNameFld.val(user.lastName);
        $roleFld.val(user.role);
        $form.attr('id', user.id);

    }

    function renderUsers(users) {
        $tbody.empty();
        for(var i=0; i<users.length; i++) {
            var user = users[i];
            var clone = $userRowTemplate.clone();
            clone.attr('id', user.id);


            clone.find('.wbdv-remove').click(deleteUser);
            clone.find('.wbdv-edit').click(selectUser);

            clone.find('.wbdv-username').html(user.username);
            clone.find('.wbdv-first-name').html(user.firstName);
            clone.find('.wbdv-last-name').html(user.lastName);
            clone.find('.wbdv-role').html(user.role);
            $tbody.append(clone);
        }
    }
})();