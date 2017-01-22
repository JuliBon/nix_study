var app = app || {};

$(function () {

    app.UserView = Backbone.View.extend({
        tagName: "tr",
        className: "user-item",
        template: _.template($('#itemTemplate').html()),

        initialize: function () {
            this.model.bind('destroy', this.remove, this);
        },

        render: function () {
            $(this.el).html(this.template());

            var login = this.model.get('login');
            this.$('.login').text(login);

            var email = this.model.get('email');
            this.$('.email').text(email);

            var firstName = this.model.get('firstName');
            this.$('.first-name').text(firstName);

            var lastName = this.model.get('lastName');
            this.$('.last-name').text(lastName);

            var birthday = this.model.get('birthday');
            this.$('.age').text(this.calculateAge(birthday));

            var role = this.model.get('role');
            this.$('.role').text(role.name);

            return this;
        },

        events: {
            "click .link-delete": "deleteUser",
            "click .link-edit": "navigateEdit"
        },

        calculateAge: function (birthday) {
            var dateSplit = birthday.split("-");
            var birthdayDate = new Date(dateSplit[0], dateSplit[1] - 1, dateSplit[2]);

            var ageDifMs = Date.now() - birthdayDate.getTime();
            var ageDate = new Date(ageDifMs);
            return Math.abs(ageDate.getUTCFullYear() - 1970);
        },

        deleteUser: function () {
            if (confirm('Delete user with id = ' + this.model.id + '?')) {
                this.model.destroy();
            }
        },

        navigateEdit: function () {
            app.AdminRouter.navigate("!/edit/" + this.model.id, {trigger: true});
        }
    });

    app.UsersView = Backbone.View.extend({
        el: $('#users'),
        template: _.template($('#usersTemplate').html()),

        initialize: function () {
            this.listenTo(app.Users, 'sync', this.render);
            this.render();
            return this;
        },

        render: function () {
            this.$el.empty().html(this.template());
            this.addAll();
            return this;
        },

        addOne: function (user) {
            var view = new app.UserView({model: user});
            this.$("#tableBody").append(view.render().el);
        },

        addAll: function () {
            app.Users.each(this.addOne);
        }
    });

    app.AppView = Backbone.View.extend({
        el: $('#usersApp'),

        initialize: function () {
            app.Users = new app.UserCollection;
        }
    });

    app.App = new app.AppView();
});