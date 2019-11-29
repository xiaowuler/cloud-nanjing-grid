var CenterPanel = function () {

    this.dateTimes = null;

    this.map = new Map();
    this.timeUtils = new TimeUtil();
    this.UpdateTimeLine = new TimeLine(this, $('.update-liner'), 'update');
    this.NewspaperTimeLine = new TimeLine(this, $('.newspaper-liner'), 'start');
    this.ForecastTimeLine = new TimeLine(this, $('.forecast-liner'), 'forecast');

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
                this.setUpdateTimes(data, 0)
            }.bind(this)
        })
    }

    this.setUpdateTimes = function (data, i) {
        var updateDateStr = "";
        $(data).each(function(index, item){
            var moment = this.timeUtils.parseStr(item.updateTime, 'YYYY-MM-DD HH:mm:ss');
            var updateTime = moment.format('YYMMDDHH');
            if (index == i){
                updateDateStr += '<li class="active">'+ updateTime +'</li>';
            }else {
                updateDateStr += '<li>'+ updateTime +'</li>';
            }
        }.bind(this));
        $('.update-time').html(updateDateStr);

        this.setStartTime(data[i].startTimes, 0);
    }

    this.setStartTime = function(startTimes, i){
        var startDates = this.getStartDate(startTimes);
        var startDateStr = "";
        $(startDates).each(function(index, item){
            var startTime = moment(item.startTime).format('YYMMDDHH');
            if (index == i){
                startDateStr += '<li class="active">'+ startTime +'</li>';
            }else {
                startDateStr += '<li>'+ startTime +'</li>';
            }
        }.bind(this));
        $('.start-time').html(startDateStr);
        this.setForecastTime(startDates[i].forecasts, 0)
    }

    this.setForecastTime = function(forecastTimes, i){
        forecastTimes.sort(this.sortForecastDate);
        var forecastDateStr = "";
        $(forecastTimes).each(function(index, item){
            var forecastTime = moment(item.forecastTime).format('YYMMDDHH');
            if (index == i){
                forecastDateStr += '<li class="active">'+ forecastTime +'</li>';
            }else {
                forecastDateStr += '<li>'+ forecastTime +'</li>';
            }
        }.bind(this));
        $('.forecast-time').html(forecastDateStr);

        this.UpdateTimeLine.bindClickEvent();
        this.NewspaperTimeLine.bindClickEvent();
        this.ForecastTimeLine.bindClickEvent();
        this.loadMap();
    }

    this.loadMap = function(){
        $.ajax({
            type: 'POST',
            data: {
                startDate: moment($('.start-time li.active').text(), 'YYMMDDHH').toDate(),
                forecastDate: moment($('.forecast-time li.active').text(), 'YYMMDDHH').toDate(),
                updateDate: moment($('.update-time li.active').text(), 'YYMMDDHH').toDate(),
                forecastModel: $('#model').combobox('getValue'),
                elementCode: $('#element').combobox('getValue')
            },
            url: 'baseSearch/findNJGridsByNonArea',
            success: function (data) {
                this.map.clearLayers();
                this.map.drawPolygon(data);
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