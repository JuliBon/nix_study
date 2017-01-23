var app = app || {};

$(function () {
    app.AdminRouter = Backbone.Router.extend({
        routes: {
            "": "users",
            "!/": "users",
            "!/users": "users",
            "!/create": "create",
            "!/edit/:id": "edit"
        },

        initialize: function(){
            app.Users = new app.UserCollection;
        },

        users: function () {
            app.Users.fetch();
            app.usersView = new app.UsersView();
        },

        create: function () {
            app.createView = new app.CreateView({model: new app.UserModel});
        },

        edit: function (userId) {
            var userModel = app.Users.findWhere({id: parseInt(userId)});
            app.editView = new app.EditView({model: userModel});
        }
    });

    app.AdminRouter = new app.AdminRouter;
    Backbone.history.start();
});