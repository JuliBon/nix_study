var app = app || {};

$(function () {
    app.UsersView = Backbone.View.extend({
        el: $('#actionBlock'),

        template: _.template($('#usersTemplate').html()),

        initialize: function () {
            this.listenTo(app.Users, 'sync', this.render);
            this.render();
            return this;
        },

        render: function () {
            this.$el.empty().html(this.template());
            this.addAll();

            this.$el.closest("#usersApp").find('#actionHeader').text("Users");
            return this;
        },

        addOne: function (user) {
            var view = new app.UserView({model: user});
            this.$("#tableBody").append(view.render().el);
        },

        addAll: function () {
            app.Users.each(this.addOne);
        }
    });
});