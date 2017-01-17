$(function () {
    window.Users = new UserCollection;


    window.UserView = Backbone.View.extend({
        tagName: "tr",

        className: "user-item",

        template: _.template($('#itemTemplate').html()),

        events: {
            "click .btn-delete": "deleteUser",
            "change input.user-input": "changed"
            /*,
             "change select.user-input": "update"*/
        },

        initialize: function () {
            _.bindAll(this, "changed");
            this.model.bind('change', this.render, this);
            this.model.bind('destroy', this.remove, this);
            Backbone.Validation.bind(this);
        },

        render: function () {
            $(this.el).html(this.template(this.model.toJSON()));
            this.setContent();
            return this;
        },

        deleteUser: function () {
            this.model.destroy();
        },

        changed: function (evt) {
            var changed = evt.currentTarget;
            var value = $(evt.currentTarget).val();
            var obj = {};
            obj[changed.name] = value;
            this.model.set(obj);
        },

        setContent: function () {
            var id = this.model.get('id');
            this.$('.user-id').text(id);

            var login = this.model.get('login');
            this.$('input[name="login"]').val(login);

            var password = this.model.get('password');
            this.$('input[name="password"]').val(password);

            var email = this.model.get('email');
            this.$('input[name="email"]').val(email);

            var firstName = this.model.get('firstName');
            this.$('input[name="firstName"]').val(firstName);

            var lastName = this.model.get('lastName');
            this.$('input[name="lastName"]').val(lastName);

            var birthday = this.model.get('birthday');
            this.$('input[name="birthday"]').val(birthday);

            var role = this.model.get('role');
            this.$('input[name="role"]').val(role.name);
        }
    });

    _.extend(Backbone.Validation.callbacks, {
        valid: function(view, attr, selector) {
            alert("Valid " + attr);
        },
        invalid: function(view, attr, error, selector) {
            alert("Invalid. " + attr + " "+ error);
        }
    });

    window.AppView = Backbone.View.extend({
        el: $("#usersApp"),

        initialize: function () {
            Users.bind('add', this.addOne, this);
            Users.bind('reset', this.addAll, this);

            Users.fetch();
        },

        addOne: function (user) {
            var view = new UserView({model: user});
            this.$("#table-body").append(view.render().el);
        },

        addAll: function () {
            Users.each(this.addOne);
        }
    });

    window.App = new AppView();
});