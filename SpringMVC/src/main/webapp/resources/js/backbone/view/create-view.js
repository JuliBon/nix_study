var app = app || {};

$(function () {
    app.CreateView = Backbone.View.extend({
        id: 'createUser',

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
            var passwordConfirm = this.$('[name="passwordConfirm"]').val();
            var email = this.$('[name="email"]').val();
            var firstName = this.$('[name="firstName"]').val();
            var lastName = this.$('[name="lastName"]').val();
            var birthday = this.$('[name="birthday"]').val();
            var role = jQuery.parseJSON(this.$('[name="role"]').val());

            var user = {
                "login": login,
                "password": password,
                "passwordConfirm": passwordConfirm,
                "email": email,
                "firstName": firstName,
                "lastName": lastName,
                "birthday": birthday,
                "role": role
            };

            this.model.set(user,
                {
                    validate: true,
                    save: false
                });
            var errors = this.model.validationError;
            if (!errors) {
                this.hideErrors();
                app.Users.create(user, {
                    validate: false,
                    type: 'POST',
                    dataType: "text",
                    success: function (model, resp) {
                        app.AdminRouter.navigate("!/", {trigger: true});
                        alert("New user has been created");
                    },
                    error: function (model, resp) {
                        alert("Error while creating user");
                    }
                });
            } else {
                this.showErrors(errors);
            }
        },

        showErrors: function (errors) {
            this.hideErrors();
            _.each(errors, function (error) {
                var $el = $('[name=' + error.name + ']');
                var $group = $el.closest('.form-group');

                $group.addClass('has-error');
                $group.find('.help-block').html(error.message);
            }, this);
        },

        hideErrors: function () {
            this.$('.form-group').removeClass('has-error');
            this.$('.help-block').html('');
        }
    });
});