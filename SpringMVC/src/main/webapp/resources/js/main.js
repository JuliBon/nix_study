$(function () {
    window.Users = new UserCollection;

    window.UserView = Backbone.View.extend({
        tagName: "li",

        className: 'user-item',

        template: _.template($('#itemTemplate').html()),

        initialize: function() {
            this.model.bind('change', this.render, this);
        },

        render: function() {
            $(this.el).html(this.template(this.model.toJSON()));
            this.setContent();
            return this;
        },

        setContent: function() {
            var id = this.model.get('id');
            this.$('.user-id').text(id);

            var login = this.model.get('login');
            this.$('.user-login').text(login);

            var password = this.model.get('password');
            this.$('.user-password').text(password);

            var email = this.model.get('email');
            this.$('.user-email').text(email);

            var firstName = this.model.get('firstName');
            this.$('.user-first-name').text(firstName);

            var lastName = this.model.get('lastName');
            this.$('.user-last-name').text(lastName);

            var birthday = this.model.get('birthday');
            this.$('.user-birthday').text(birthday);

            var role = this.model.get('role');
            var roleId = role.id;
            this.$('.user-role-id').text(roleId);
            var roleName =  role.name;
            this.$('.user-role-name').text(roleName);
        }

    });

    window.AppView = Backbone.View.extend({
        el: $("#usersApp"),

        initialize: function () {
            Users.bind('add',   this.addOne, this);
            Users.bind('reset', this.addAll, this);

            Users.fetch();
        },

        addOne: function(user) {
            var view = new UserView({model: user});
            this.$("#userList").append(view.render().el);
        },

        addAll: function() {
            Users.each(this.addOne);
        }
    });

    window.App = new AppView();
});