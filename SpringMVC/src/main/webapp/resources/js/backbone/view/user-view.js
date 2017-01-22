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
            if (confirm('Delete user ' + this.model.get('login') + '?')) {
                this.model.destroy();
            }
        },

        navigateEdit: function () {
            app.AdminRouter.navigate("!/edit/" + this.model.id, {trigger: true});
        }
    });
});