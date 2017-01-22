var app = app || {};

$(function () {
    app.AbstractFormView = Backbone.View.extend({

    showErrors: function (errors) {
        this.hideErrors();
        _.each(errors, function (error) {
            var $el = $('[name=' + error.name + ']');
            var $group = $el.closest('.form-group');

            $group.addClass('has-error');
            $group.find('.help-block').html(error.message);
        }, this);
    },

    hideErrors: function () {
        this.$('.form-group').removeClass('has-error');
        this.$('.help-block').html('');
    }
    });

});