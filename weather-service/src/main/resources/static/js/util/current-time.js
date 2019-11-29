var CurrentTime = function () {
    this.timer = null;
    this.InitTime = function () {
        this.InitCurrentTime();
        this.SetTimeOut();
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