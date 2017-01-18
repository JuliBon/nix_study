var AdminRouter = Backbone.Router.extend({
    routes: {
        "": "users",
        "!/": "users",
        "!/create": "create",
        "!/edit": "edit"
    },

    users: function () {
        $(".block").hide(); // Прячем все блоки
        $("#users").show(); // Показываем нужный
    },

    create: function () {
        $(".block").hide();
        $("#createUser").show();
    },

    edit: function () {
        $(".block").hide();
        $("#editUser").show();
    }

});

var adminRouter = new AdminRouter();

Backbone.history.start();
