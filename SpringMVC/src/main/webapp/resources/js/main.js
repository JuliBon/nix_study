$(function () {
    window.Users = new UserCollection;

    window.ActionCell = Backgrid.Cell.extend({
        template: _.template(
            '<button class="btnDelete">' +
            'Delete' +
            '</button>'),
        events: {
            'click .btnDelete': 'deleteRow'
        },
        deleteRow: function (e) {
            e.preventDefault();
            this.model.destroy();
        },
        render: function () {
            this.$el.html(this.template());
            this.delegateEvents();
            return this;
        }
    });

    var Columns = [{
        name: "id",
        label: "ID",
        editable: false,
        cell: Backgrid.IntegerCell.extend({
            orderSeparator: ''
        })
    }, {
        name: "login",
        label: "Login",
        editable: false,
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
    }, {
        name: "",
        label: 'Actions',
        cell: ActionCell
    }];

    window.grid = new Backgrid.Grid({
        columns: Columns,
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