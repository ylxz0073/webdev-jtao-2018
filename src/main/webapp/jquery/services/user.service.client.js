function UserServiceClient() {
    this.createUser = createUser;
    this.findAllUsers = findAllUsers;
    this.findUserById = findUserById;
    this.deleteUser = deleteUser;
    this.updateUser = updateUser;
    this.register = register;
    this.login = login;
    this.url = 'http://localhost:8080/api/user';
    var self = this;

    function register() {

    }

    function login() {

    }

    function createUser(user, callback) {
        return fetch(self.url, {
            method: 'post',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        });
    }

    function findAllUsers(callback) {
        return fetch(self.url)
            .then(function(response){
                return response.json();
            });
    }

    function findUserById(userId, callback) {
        return fetch(self.url + '/' +userId)
            .then(function(response){
                return response.json();
            });
    }

    function updateUser(userId, user, callback) {
        return fetch(self.url + '/' + userId, {
            method: 'put',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        })
            .then(function(response){
                if (response.bodyUsed) {
                    return response.json();
                } else {
                    return null;
                }
            });
    }

    function deleteUser(userId, callback) {
        return fetch(self.url + '/' + userId, {
            method: 'delete'
        });
    }


}