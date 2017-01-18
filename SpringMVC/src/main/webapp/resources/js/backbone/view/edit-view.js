var app = app || {};

$(function () {
    app.EditView = Backbone.View.extend({
        el: $('#editUser'),
        template: _.template($('#userCreateEditTemplate').html()),

        events: {
            "click #btnOk": "editUser"
        },

        initialize: function (opt) {
            this.render();
            return this;
        },

        render: function () {
            this.$el.empty().html(this.template());
            this.$('[name="login"]').attr("readonly", "readonly");
            return this;
        },

        editUser: function () {
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

            app.Users.update(user, {
                type: 'PUT',
                success: function (evt) {
                    app.AdminRouter.navigate("!/", {trigger: true});

                    alert("User has been saved");
                }
            });
        }
    });
});