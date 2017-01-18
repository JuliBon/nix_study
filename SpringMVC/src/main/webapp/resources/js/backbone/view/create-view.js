var app = app || {};

$(function () {
    app.CreateView = Backbone.View.extend({
        el: $('#createUser'),
        template: _.template($('#userCreateEditTemplate').html()),

        events: {
            "click #btnOk": "createUser"
        },

        initialize: function (opt) {
            this.render();
            return this;
        },

        render: function () {
            this.$el.empty().html(this.template());
            return this;
        },

        createUser: function () {
            var login = this.$('[name="login"]').val();
            var password = this.$('[name="password"]').val();
            var email = this.$('[name="email"]').val();
            var firstName = this.$('[name="firstName"]').val();
            var lastName = this.$('[name="lastName"]').val();
            var birthday = this.$('[name="birthday"]').val();
            var role = jQuery.parseJSON(this.$('[name="role"]').val());

            var user = {
                "login": login,
                "password": password,
                "email": email,
                "firstName": firstName,
                "lastName": lastName,
                "birthday": birthday,
                "role": role
            };

            app.Users.create(user, {
                type: 'POST',
                success: function (evt) {
                    app.AdminRouter.navigate("!/", {trigger: true});

                    alert("New user has been created");
                }
            });
        }
    });
});