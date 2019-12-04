var App = function () {
    this.CurrentTime = new CurrentTime(this);
    this.ShowMenu = new InitAside(this);

    this.Startup = function () {
        this.ReLayout();
        this.CurrentTime.InitTime();
        this.ShowMenu.Startup();
        window.onresize = this.ReLayout.bind(this);
    };

    this.ReLayout = function () {
        var width = $(window).width();
        var height = $(window).height();
        var content = $('.content');
        content.width((width - 20)).height(height - 94);
        $('.aside-content').height(content.height() - 37);
        $('.port-result').height(content.height() - 458);
    };
};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});