var CenterPanel = function () {

    this.dateTimes = null;

    this.map = new Map();
    this.timeUtils = new TimeUtil();
    this.UpdateTimeLine = new TimeLine(this, $('#update'), 'update', '#update');
    this.NewspaperTimeLine = new TimeLine(this, $('#start'), 'start', '#start');
    this.ForecastTimeLine = new TimeLine(this, $('#forecast'), 'forecast', '#forecast');

    this.Startup = function(){
        this.UpdateTimeLine.Startup();
        this.NewspaperTimeLine.Startup();
        this.ForecastTimeLine.Startup();

        this.setForecastModelChange();

        this.map.Startup();
        this.ResetComboBox();
        this.loadTimes();
    };

    this.setForecastModelChange = function () {
        $("#element").combobox({
            //相当于html >> select >> onChange事件
            onChange: function(){
                this.loadTimes();
            }.bind(this)
        });

        $("#model").combobox({
            //相当于html >> select >> onChange事件
            onChange: function(){
                this.loadTimes();
            }.bind(this)
        });
    }

    this.loadTimes = function () {
        $.ajax({
            type: 'POST',
            data: {
                modeCode: $('#model').combobox('getValue'),
                elementCode: $('#element').combobox('getValue')
            },
            url: 'other/findNewestTimeByModeCode',
            success: function (data) {
                if(data == null){
                    return;
                }
                this.dateTimes = data;
                this.setUpdateTimes(data, data.length - 1)
            }.bind(this)
        })
    }

    this.setUpdateTimes = function (data, i) {
        var updateDateStr = "";
        $(data).each(function(index, item){
            var moment = this.timeUtils.parseStr(item.updateTime, 'YYYY-MM-DD HH:mm:ss');
            var updateTime = moment.format('YYMMDDHH');
            if (index == i){
                updateDateStr += '<li class="active"><span>'+ updateTime +'</span></li>';
            }else {
                updateDateStr += '<li><span>'+ updateTime +'</span></li>';
            }
        }.bind(this));
        $('.update-time').html(updateDateStr);

        this.setStartTime(data[i].startTimes, data[i].startTimes.length - 1, i);
    }

    this.setStartTime = function(startTimes, i, updateIndex){
        var startDates = this.getStartDate(startTimes);
        var startDateStr = "";
        $(startDates).each(function(index, item){
            var startTime = moment(item.startTime).format('YYMMDDHH');
            if (index == i){
                startDateStr += '<li class="active"><span>'+ startTime +'</span></li>';
            }else {
                startDateStr += '<li><span>'+ startTime +'</span></li>';
            }
        }.bind(this));
        $('.start-time').html(startDateStr);
        this.setForecastTime(startDates[i].forecasts, 0, updateIndex, i)
    }

    this.setForecastTime = function(forecastTimes, i, updateIndex, startIndex){
        forecastTimes.sort(this.sortForecastDate);
        var forecastDateStr = "";
        $(forecastTimes).each(function(index, item){
            var forecastTime = moment(item.forecastTime).format('YYMMDDHH');
            if (index == i){
                forecastDateStr += '<li class="active"><span>'+ forecastTime +'</span></li>';
            }else {
                forecastDateStr += '<li><span>'+ forecastTime +'</span></li>';
            }
        }.bind(this));
        $('.forecast-time').html(forecastDateStr);

        this.NewspaperTimeLine.bindClickEvent(startIndex, updateIndex, i);
        this.UpdateTimeLine.bindClickEvent(startIndex, updateIndex, i);
        this.ForecastTimeLine.bindClickEvent(startIndex, updateIndex, i);

        this.loadMap();
    }

    this.loadMap = function(){
        $.ajax({
            type: 'POST',
            data: {
                startDate: moment($('.start-time li.active span').text(), 'YYMMDDHH').toDate(),
                forecastDate: moment($('.forecast-time li.active span').text(), 'YYMMDDHH').toDate(),
                updateDate: moment($('.update-time li.active span').text(), 'YYMMDDHH').toDate(),
                forecastModel: $('#model').combobox('getValue'),
                elementCode: $('#element').combobox('getValue')
            },
            url: 'baseSearch/findNJGridsByNonArea',
            success: function (data) {
                this.map.clearLayers();
                this.map.draw(data, $('#element').combobox('getValue'));
            }.bind(this)
        })
    }

    this.sortForecastDate = function (a, b) {
        return Date.parse(a.forecastTime) - Date.parse(b.forecastTime);
    }

    this.getStartDate = function (startTimes) {
        startTimes.sort(this.sortStartDate);
        return startTimes;
    }

    this.sortStartDate = function(a, b) {
        return Date.parse(a.startTime) - Date.parse(b.startTime);
    }

    this.ResetComboBox = function () {
        $('#model').combobox({
            panelHeight: 'auto'
        });

        $('#element').combobox({
            panelHeight: 'auto'
        });
    };
};