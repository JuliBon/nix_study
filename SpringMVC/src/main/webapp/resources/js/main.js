$(function () {
    window.Users = new UserCollection;


    window.UserView = Backbone.View.extend({
        tagName: "tr",

        className: "user-item",

        template: _.template($('#itemTemplate').html()),

        events: {
            "click .btn-delete": "deleteUser",
            "keyup": "updateOnEnter"
        },

        initialize: function () {
            this.model.bind('change', this.render, this);
            this.model.bind('destroy', this.remove, this);
        },

        render: function () {
            $(this.el).html(this.template(this.model.toJSON()));
            this.setContent();
            return this;
        },

        deleteUser: function () {
            this.model.destroy();
        },

        updateOnEnter: function (e) {
            if (e.keyCode == 13) {
                this.model.save({
                    login: this.$('.user-login').val(),
                    password: this.$('.user-password').val(),
                    email: this.$('.user-email').val(),
                    firstName: this.$('.user-first-name').val(),
                    lastName: this.$('.user-last-name').val(),
                    birthday: this.$('.user-birthday').val()
                });
            }
        },


        setContent: function () {
            var id = this.model.get('id');
            this.$('.user-id').val(id);

            var login = this.model.get('login');
            this.$('.user-login').val(login);

            var password = this.model.get('password');
            this.$('.user-password').val(password);

            var email = this.model.get('email');
            this.$('.user-email').val(email);

            var firstName = this.model.get('firstName');
            this.$('.user-first-name').val(firstName);

            var lastName = this.model.get('lastName');
            this.$('.user-last-name').val(lastName);

            var birthday = this.model.get('birthday');
            this.$('.user-birthday').val(birthday);

            var role = this.model.get('role');
            this.$('.user-role').val(role.name);
        }
    });

    window.AppView = Backbone.View.extend({
        el: $("#usersApp"),

        initialize: function () {
            Users.bind('add', this.addOne, this);
            Users.bind('reset', this.addAll, this);

            Users.fetch();
        },

        addOne: function (user) {
            var view = new UserView({model: user});
            this.$("#table-body").append(view.render().el);
        },

        addAll: function () {
            Users.each(this.addOne);
        }
    });

    window.App = new AppView();
});