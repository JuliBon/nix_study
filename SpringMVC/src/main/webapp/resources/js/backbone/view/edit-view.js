var app = app || {};

$(function () {
    app.EditView = app.AbstractFormView.extend({
        el: $('#actionBlock'),

        events: function () {
            return _.extend({}, app.AbstractFormView.prototype.events, {
                "click #btnOk": "editUser"
            });
        },

        initialize: function () {
            this.render();
            return this;
        },

        render: function () {
            this.$el.empty().html(this.template());
            this.$('[name="login"]').attr("readonly", "readonly");
            this.setContent();

            this.$el.closest("#usersApp").find('#actionHeader').text("Edit user");
            return this;
        },

        setContent: function () {
            var id = this.model.get('id');
            this.$('[name="id"]').val(id);

            var login = this.model.get('login');
            this.$('[name="login"]').val(login);

            var email = this.model.get('email');
            this.$('[name="email"]').val(email);

            var firstName = this.model.get('firstName');
            this.$('[name="firstName"]').val(firstName);

            var lastName = this.model.get('lastName');
            this.$('[name="lastName"]').val(lastName);

            var birthday = this.model.get('birthday');
            this.$('[name="birthday"]').val(birthday);

            var role = this.model.get('role');
            this.$('[name="role"]').val(JSON.stringify(role));
        },

        editUser: function () {
            var user = this.readUser();
            user.id = this.$('[name="id"]').val();

            this.model.set(user,
                {
                    validate: true,
                    save: false
                });
            var errors = this.model.validationError;
            if (!errors) {
                this.hideErrors();
                this.model.save(user, {
                    validate: false,
                    dataType: "text",
                    success: function (model, resp) {
                        app.AdminRouter.navigate("!/", {trigger: true});
                    },
                    error: function (model, resp) {
                        var errors = jQuery.parseJSON(resp.responseText);
                        app.editView.showErrors(errors);
                    }
                });
            } else {
                this.showErrors(errors);
            }
        }
    });
});