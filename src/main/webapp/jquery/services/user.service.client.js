function UserServiceClient() {
    this.createUser = createUser;
    this.findAllUsers = findAllUsers;
    this.findUserById = findUserById;
    this.deleteUser = deleteUser;
    this.updateUser = updateUser;
    this.register = register;
    this.login = login;
    this.logout = logout;
    this.updateProfile = updateProfile;
    this.url = 'http://localhost:8080/api/user';
    this.registerUrl = 'http://localhost:8080/api/register';
    this.loginUrl = 'http://localhost:8080/api/login';
    this.logoutUrl = 'http://localhost:8080/api/logout';
    this.profileUrl = 'http://localhost:8080/api/profile';
    var self = this;

    function register(userCredential) {

        return fetch(self.registerUrl, {
            method: 'post',
            body: JSON.stringify(userCredential),
            headers: {
                'content-type': 'application/json'
            }
        }).then(function(response){
            if(response.ok){
                return true;
            } else {
                return false;
            }
        });

    }

    function login(userCredential) {
        return fetch(self.loginUrl, {
            method: 'post',
            body: JSON.stringify(userCredential),
            credentials: 'same-origin',
            headers: {
                'content-type': 'application/json'
            }
        }).then(function(response){
            if(response.ok){
                return response.json();
            } else {
                return false;
            }
        });
    }

    function logout() {
        return fetch(self.logoutUrl, {
            method: 'post',
            credentials: 'same-origin',
            headers: {
                'content-type': 'application/json'
            }
        }).then(function(response){
            return response.json();
        });

    }

    function updateProfile(user) {
        fetch(self.profileUrl, {
            method: 'put',
            credentials: 'same-origin',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then(function(response){
            return response.json();
        });
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