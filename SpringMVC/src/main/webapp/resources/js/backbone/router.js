var app = app || {};

$(function () {
    app.AdminRouter = Backbone.Router.extend({
        routes: {
            "": "users",
            "!/": "users",
            "!/create": "create",
            "!/edit": "edit"
        },

        users: function () {
            $(".block").hide();
            $("#users").show();

            app.Users.fetch();
            app.usersView = new app.UsersView();
        },

        create: function () {
            $(".block").hide();
            $("#createUser").show();

            app.createView = new app.CreateView();
        },

        edit: function () {
            $(".block").hide();
            $("#editUser").show();
        }
    });

    app.AdminRouter = new app.AdminRouter;

    Backbone.history.start();
});