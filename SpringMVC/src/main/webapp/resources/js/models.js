var UserModel = Backbone.Model.extend({
    urlRoot: '/rest/users',
    defaults: {
        "id": 7,
        "login": "admin",
        "password": "Admin123",
        "email": "admin@mail.ru",
        "firstName": "admin",
        "lastName": "admin",
        "birthday": "15-05-1985",
        "role": {"id": 1, "name": "ADMIN"}
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