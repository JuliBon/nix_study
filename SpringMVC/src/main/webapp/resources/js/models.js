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

    idAttribute: 'id'
});

var UserCollection = Backbone.Collection.extend({
    model: UserModel,
    url: "/rest/users"
});