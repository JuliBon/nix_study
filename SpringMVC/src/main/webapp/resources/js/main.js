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


    window.grid = new Backgrid.Grid({

        collection: Users,

        columns: [{
            name: "id",
            label: "ID",
            editable: false,
            cell: Backgrid.IntegerCell.extend({
                orderSeparator: ''
            })
        }, {
            name: "login",
            label: "Login",
            cell: "string",
            editable: function () {
                return this.model.id === null;
            }
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
        }],

        events: {
            'backgrid:next': 'newRow'
        },

        newRow: function (i, j, outOfBound) {
            if (outOfBound) grid.collection.add({});
        }
    });

    window.AppView = Backbone.View.extend({
        el: $("#usersApp"),

        initialize: function () {
            Users.bind('reset', this.addAll, this);

            Users.fetch({reset: true});
        },

        events: {
            "click #addRow": "addRow"
        },

        addRow: function (e) {
            alert("hohoh!");
            grid.collection.add({});
        },

        addAll: function () {
            this.$("#userGrid").append(grid.render().el);
        }
    });

    window.App = new AppView();
});