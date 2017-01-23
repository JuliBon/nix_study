var app = app || {};

$(function () {

    app.UserModel = Backbone.Model.extend({
        urlRoot: '/rest/users',
        defaults: {
            "login": "",
            "password": "",
            "passwordConfirm": "",
            "email": "",
            "firstName": "",
            "lastName": "",
            "birthday": "",
            "role": {"id": 2, "name": "USER"}
        },

        idAttribute: 'id',

        ignored: ['passwordConfirm'],

        toJSON: function(options) {
            return _.omit(this.attributes, this.ignored);
        },

        validate: function (attrs) {
            var loginRegExp = new RegExp('^[a-zA-Z](([._-][a-zA-Z0-9])|[a-zA-Z0-9])*$');
            var emailRegExp = new RegExp('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$');
            var passwordRegExp = new RegExp('^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$');
            var nameRegExp = new RegExp('^[A-Za-z]+$');
            var dateRegExp = new RegExp(/^\d{4}-(0\d|10|11|12)-([012]\d|30|31)$/);

            var errors = [];

            if(!loginRegExp.test(attrs.login)){
                errors.push({name: 'login', message: "3-15 characters, beginning with letter. Can include letters, numbers, dashes, and underscores"});
            }
            if(!emailRegExp.test(attrs.email)){
                errors.push({name: 'email', message: "not a well-formed email address"});
            }
            if(!passwordRegExp.test(attrs.password)){
                errors.push({name: 'password', message: "at least one number and one uppercase and lowercase letters"});
            }
            if(attrs.password !== attrs.passwordConfirm){
                errors.push({name: 'passwordConfirm', message: "confirm password"});
            }
            if(!nameRegExp.test(attrs.firstName)){
                errors.push({name: 'firstName', message: "one or more letters"});
            }
            if(!nameRegExp.test(attrs.lastName)){
                errors.push({name: 'lastName', message: "one or more letters"});
            }
            if(!dateRegExp.test(attrs.birthday)){
                errors.push({name: 'birthday', message: "bad date format"});
            }
            return errors.length > 0 ? errors : false;
        }
    });

    app.UserCollection = Backbone.Collection.extend({
        model: app.UserModel,
        url: "/rest/users"
    });
});
