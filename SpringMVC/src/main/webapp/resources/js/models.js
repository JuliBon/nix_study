var UserModel = Backbone.Model.extend({
    urlRoot: '/rest/users',
/*
    defaults: {
        "login": "",
        "password": "",
        "email": "",
        "firstName": "",
        "lastName": "",
        "birthday": "",
        "role": {"id": "", "name": ""}
    },
*/

    idAttribute: 'id',
    initialize: function () {
        Backbone.Model.prototype.initialize.apply(this, arguments);
        this.on("change", function (model, options) {
            if (options && options.save === false) return;
            model.save();
        });
    }
});

var UserCollection = Backbone.Collection.extend({
    model: UserModel,
    url: "/rest/users"
});