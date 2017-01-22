var app = app || {};

$(function () {
    app.EditView = Backbone.View.extend({
        el: $('#editUser'),
        template: _.template($('#userCreateEditTemplate').html()),

        events: {
            "click #btnOk": "editUser"
        },

        initialize: function () {
            this.render();
            return this;
        },

        render: function () {
            this.$el.empty().html(this.template());
            this.$('[name="login"]').attr("readonly", "readonly");
            this.setContent();
            return this;
        },

        setContent: function(){
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
            var id = this.$('[name="id"]').val();
            var login = this.$('[name="login"]').val();
            var password = this.$('[name="password"]').val();
            var email = this.$('[name="email"]').val();
            var firstName = this.$('[name="firstName"]').val();
            var lastName = this.$('[name="lastName"]').val();
            var birthday = this.$('[name="birthday"]').val();
            var role = jQuery.parseJSON(this.$('[name="role"]').val());

            var user = {
                "id": id,
                "login": login,
                "password": password,
                "email": email,
                "firstName": firstName,
                "lastName": lastName,
                "birthday": birthday,
                "role": role
            };

            this.model.save(user, {
                dataType:"text",
                success: function (model, resp) {
                    app.AdminRouter.navigate("!/", {trigger: true});
                    alert("User {id:" +  model.id + "} has been updated");
                },
                error: function(model, resp){
                    alert("Error while updating user");
                }
            });
        }
    });
});