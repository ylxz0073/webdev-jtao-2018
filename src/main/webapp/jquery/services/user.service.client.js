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
    this.getProfile = getProfile;
    this.url = 'http://localhost:8080/api/user';
    this.registerUrl = 'http://localhost:8080/api/register';
    this.loginUrl = 'http://localhost:8080/api/login';
    this.logoutUrl = 'http://localhost:8080/api/logout';
    this.profileUrl = 'http://localhost:8080/api/profile';
    var self = this

    function getProfile() {
        return fetch(self.profileUrl, {
            credentials: 'same-origin'
        }).then(function(response){

            return response.json();
        // .then(function(result){
        //         return result;
        //     });
        })
    }

    function register(userCredential) {

        return fetch(self.registerUrl, {
            method: 'post',
            credentials: 'same-origin',
            body: JSON.stringify(userCredential),
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
        return fetch(self.profileUrl, {
            method: 'put',
            credentials: 'same-origin',
            body: JSON.stringify(user),
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
        }).then(function(response){
            if(response.ok){
                return response.json();
            } else {
                return false;
            }
        });
    }

    function deleteUser(userId, callback) {
        return fetch(self.url + '/' + userId, {
            method: 'delete'
        });
    }


}