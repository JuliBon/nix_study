$(function () {
    window.Users = new UserCollection;


    window.UserView = Backbone.View.extend({
        tagName: "tr",

        className: "user-item",

        template: _.template($('#itemTemplate').html()),

        events: {
            "click .btn-delete": "deleteUser",
            "change .form-control": "changed"
        },

        initialize: function () {
            _.bindAll(this, "changed");
            this.model.bind('destroy', this.remove, this);
            Backbone.Validation.bind(this);
        },

        render: function () {
            $(this.el).html(this.template(this.model.toJSON()));
            this.setContent();
            return this;
        },

        deleteUser: function () {
            if(confirm('Delete user with id = '+ this.model.id +'?')){
                this.model.destroy();
            }
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
            this.$('[name="role"]').val(role.name);
        }
    });

    _.extend(Backbone.Validation.callbacks, {
        valid: function(view, attr, selector) {
            var $el = view.$('[name=' + attr + ']'),
                $group = $el.closest('.form-group');

            $group.removeClass('has-error');
            $group.find('.help-block').html('');
        },
        invalid: function(view, attr, error, selector) {
            var $el = view.$('[name=' + attr + ']'),
                $group = $el.closest('.form-group');

            $group.addClass('has-error');
            $group.find('.help-block').html(error);
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