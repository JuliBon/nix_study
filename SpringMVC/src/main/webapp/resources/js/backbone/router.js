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

            if (app.createView != null) {
                app.createView.remove();
            }
            app.createView = new app.CreateView({model: new app.UserModel});
            var $createUserBlock = $("#createUserBlock");
            $createUserBlock.append(app.createView.el);
            $createUserBlock.show();
        },

        edit: function (userId) {
            $(".block").hide();

            if (app.editView != null) {
                app.editView.remove();
            }
            var userModel = app.Users.findWhere({id: parseInt(userId)});
            app.editView = new app.EditView({model: userModel});
            var $editUserBlock = $("#editUserBlock");
            $editUserBlock.append(app.editView.el);
            $editUserBlock.show();
        }
    });

    app.AdminRouter = new app.AdminRouter;

    Backbone.history.start();
});