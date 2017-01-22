var app = app || {};

$(function () {
    app.AdminRouter = Backbone.Router.extend({
        routes: {
            "": "users",
            "!/": "users",
            "!/create": "create",
            "!/edit/:id": "edit"
        },

        users: function () {
            $(".block").hide();
            $("#usersBlock").show();

            app.Users.fetch();
            app.usersView = new app.UsersView();
        },

        create: function () {
            $(".block").hide();
            $("#createUserBlock").show();

            app.createView = new app.CreateView({model: new app.UserModel});
        },

        edit: function (userId) {
            $(".block").hide();
            $("#editUserBlock").show();

            var userModel = app.Users.findWhere({id: parseInt(userId)});
            app.editView = new app.EditView({model: userModel});
        }
    });

    app.AdminRouter = new app.AdminRouter;

    Backbone.history.start();
});