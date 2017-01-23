var app = app || {};

$(function () {
    app.AbstractFormView = Backbone.View.extend({

        template: _.template($('#userCreateEditTemplate').html()),

        events: {
            "click #btnCancel": "cancel"
        },

        destroy: function(){
            this.$el.empty().off();
            this.stopListening();
        },

        cancel: function () {
            this.destroy();
            app.AdminRouter.navigate("!/", {trigger: true});
        },

        readUser: function(){
            var login = this.$('[name="login"]').val();
            var password = this.$('[name="password"]').val();
            var passwordConfirm = this.$('[name="passwordConfirm"]').val();
            var email = this.$('[name="email"]').val();
            var firstName = this.$('[name="firstName"]').val();
            var lastName = this.$('[name="lastName"]').val();
            var birthday = this.$('[name="birthday"]').val();
            var role = jQuery.parseJSON(this.$('[name="role"]').val());

            return {
                "login": login,
                "password": password,
                "passwordConfirm": passwordConfirm,
                "email": email,
                "firstName": firstName,
                "lastName": lastName,
                "birthday": birthday,
                "role": role
            };
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