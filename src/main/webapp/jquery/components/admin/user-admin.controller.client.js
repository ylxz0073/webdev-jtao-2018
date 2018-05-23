(function () {
    var $usernameFld,
        $passwordFld;
    var $removeBtn, $editBtn,
        $createBtn;
    var $firstNameFld,
        $lastNameFld;
    var $userRowTemplate, $tbody;
    var userService;
    jQuery(main);




    function main() {
        $tbody = $('tbody');
        $userRowTemplate = $('.wbdv-template');
        $createBtn = $('.wbdv-create');

        $usernameFld = $('#usernameFld');
        $passwordFld = $('#passwordFld');
        $firstNameFld = $('#firstNameFld');
        $lastNameFld = $('#lastNameFld');

        userService = new UserServiceClient();

        $createBtn.click(createUser);

        findAllUsers();
    }

    function createUser() {
        var username = $usernameFld.val();
        var password = $passwordFld.val();
        var firstName = $firstNameFld.val();
        var lastName = $lastNameFld.val();
        var role = $('#roleFld').val();

        var user = {
            username: username,
            password: password,
            firstName: firstName,
            lastName: lastName,
            role: role
        };

        // console.log(user);
        userService.createUser(user).then(findAllUsers);

    }

    function findAllUsers() {
        userService.findAllUsers().then(renderUsers);
    }

    function findUserById(user) {
        return user.attr('id');
    }

    function deleteUser() {
        $removeBtn = $(event.currentTarget);
        var userId = findUserById(selectUser($removeBtn));
        userService
            .deleteUser(userId)
            .then(findAllUsers);
    }

    function selectUser(button) {
        return button
            .parent()
            .parent()
            .parent();
    }

    function updateUser() {
        // ??? dont get it
        var user = selectUser(event.currentTarget);
        var userId = findUserById(user);
        userService
            .updateUser(userId, user)
            .then(success);
    }

    function success(response) {
        if(response === null) {
            alert("unable to update!");
        } else {
            alert("success!");
        }
    }

    function renderUser(user) {
        var clone = $userRowTemplate.clone();
        clone.attr('id', user.id);


        clone.find('.wbdv-remove').click(deleteUser);
        clone.find('.wbdv-edit').click(updateUser);

        clone.find('.wbdv-username').html(user.username);
        clone.find('.wbdv-first-name').html(user.firstName);
        clone.find('.wbdv-last-name').html(user.lastName);
        clone.find('.wbdv-role').html(user.role);
        $tbody.append(clone);
    }

    function renderUsers(users) {
        $tbody.empty();
        for(var i=0; i<users.length; i++) {
            var user = users[i];
            renderUser(user);
        }
    }
})();