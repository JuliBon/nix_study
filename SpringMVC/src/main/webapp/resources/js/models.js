var UserModel = Backbone.Model.extend({
    urlRoot: '/rest/users',
    defaults: {
        "id": "",
        "login": "",
        "password": "",
        "email": "",
        "firstName": "",
        "lastName": "",
        "birthday": "",
        "role": {"id": 2, "name": "USER"}
    },

    idAttribute: 'id',

    initialize: function () {
        Backbone.Model.prototype.initialize.apply(this, arguments);
        this.on("change", function (model, options) {
            if (options && options.save === false) return;
            model.save();
        });
    },

    validation: {
        login: {
            required: true,
            pattern: 'loginPattern'
        },
        password: {
            required: true,
            pattern: 'passwordPattern'
        },
        email: {
            required: true,
            pattern: 'emailPattern'
        },
        firstName: {
            required: true,
            pattern: 'namePattern'
        },
        lastName: {
            required: true,
            pattern: 'namePattern'
        },
        birthday: {
            required: true
        }
    }
});

var UserCollection = Backbone.Collection.extend({
    model: UserModel,
    url: "/rest/users"
});