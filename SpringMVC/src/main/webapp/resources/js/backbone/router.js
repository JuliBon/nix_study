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
            $("#usersBlock").show();

            app.Users.fetch();
            app.usersView = new app.UsersView();
        },

        create: function () {
            $(".block").hide();
            $("#createUserBlock").show();

            app.createView = new app.CreateView();
        },

        edit: function () {
            $(".block").hide();
            $("#editUserBlock").show();

            app.editView = new app.EditView();
        }
    });

    app.AdminRouter = new app.AdminRouter;

    Backbone.history.start();
});