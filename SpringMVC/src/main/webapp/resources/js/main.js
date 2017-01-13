$(function () {
    window.Users = new UserCollection;

    window.UserView = Backbone.View.extend({
        tagName: "li",

        className: 'user-item',

        template: _.template($('#itemTemplate').html()),

        initialize: function () {
            this.model.bind('change', this.render, this);
        },

        render: function () {
            $(this.el).html(this.template(this.model.toJSON()));
            this.setContent();
            return this;
        },

        setContent: function () {
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
            var roleName = role.name;
            this.$('.user-role-name').text(roleName);
        }

    });

    window.DeleteCell = Backgrid.Cell.extend({
        template: _.template('<button class="btnDelete">Delete</button>'),
        events: {
            'click .btnDelete': 'deleteRow'
        },
        deleteRow: function(e) {
            e.preventDefault();
            this.model.destroy();
        },
        render: function () {
            this.$el.html(this.template());
            this.delegateEvents();
            return this;
        }
    });

    window.columns = [{
        name: "id",
        label: "ID",
        editable: false,
        cell: Backgrid.IntegerCell.extend({
            orderSeparator: ''
        })
    }, {
        name: "login",
        label: "Login",
        cell: "string"
    }, {
        name: "password",
        label: "Password",
        cell: "string"
    }, {
        name: "email",
        label: "Email",
        cell: "email"
    }, {
        name: "firstName",
        label: "First name",
        cell: "string"
    }, {
        name: "lastName",
        label: "Last name",
        cell: "string"
    }, {
        name: "birthday",
        label: "Birthday",
        cell: "date"
    } , {
        name: "",
        label: 'Delete',
        cell: DeleteCell
    } ];


    window.grid = new Backgrid.Grid({
        columns: columns,
        collection: Users
    });

    window.AppView = Backbone.View.extend({
        el: $("#usersApp"),

        initialize: function () {
            Users.bind('reset', this.addAll, this);

            Users.fetch({reset: true});
        },

        addAll: function () {
            this.$("#userGrid").append(grid.render().el);
        }
    });

    window.App = new AppView();
});