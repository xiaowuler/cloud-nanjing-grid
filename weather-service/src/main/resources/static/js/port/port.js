var App = function () {
    this.timer = null;
    this.ShowMenu = new ShowMenu(this);

    this.Startup = function () {
        this.ReLayout();
        this.InitCurrentTime();
        this.SetTimeOut();
        this.ShowMenu.Startup();
        window.onresize = this.ReLayout.bind(this);
    };

    this.ReLayout = function () {
        var width = $(window).width();
        var height = $(window).height();
        var content = $('.content');
        content.width((width - 20)).height(height - 94);
        $('.aside-content').height(content.height() - 37);
        $('.port-result').height(content.height() - 478);
    };

    this.InitCurrentTime = function () {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        var weekend = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
        var currentDate = year + '-' + this.GetTimeFormatter(month) + '-' + this.GetTimeFormatter(day) + ' ' + this.GetTimeFormatter(hour) + ':' + this.GetTimeFormatter(minute) + ':' + this.GetTimeFormatter(second) + ' ' + weekend[date.getDay()];

        $('#current-date').text(currentDate);
    };

    this.SetTimeOut = function () {
        this.timer = setInterval(this.InitCurrentTime.bind(this), 1000)
    };

    this.GetTimeFormatter = function (time) {
        var format = time < 10 ? '0' + time : time;
        return format;
    };

};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});