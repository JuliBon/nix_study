var app = app || {};

$(function () {
    app.CreateView = app.AbstractFormView.extend({
        id: 'createUser',

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
            var user = this.readUser();

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