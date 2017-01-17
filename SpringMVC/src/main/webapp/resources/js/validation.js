_.extend(Backbone.Validation.patterns, {
    loginPattern: "^[a-zA-Z](([._-][a-zA-Z0-9])|[a-zA-Z0-9])*$",
    emailPattern: "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$",
    passwordPattern: "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$",
    namePattern: "^[A-Za-z]+$"
});

_.extend(Backbone.Validation.messages, {
    loginPattern: '3-15 characters, beginning with letter. Can include letters, numbers, dashes, and underscores',
    emailPattern: "not a well-formed email address",
    passwordPattern: "at least one number and one uppercase and lowercase letters",
    namePattern: "one or more letters"
});