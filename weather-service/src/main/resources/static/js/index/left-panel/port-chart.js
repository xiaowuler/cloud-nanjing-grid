var PortChart = function () {
    this.Timer = null;
    this.thisWeek = null;
    this.thisMonth = null;
    this.thisYear = null;
    this.port = $('.port-chart');
    this.TimeUtil = new TimeUtil();

    this.Startup = function(){
        this.Reload();
        $('.port-select a').on('click', this.OnTimeSelect.bind(this));
        this.port.on('mouseover', this.RemoveSetInterval.bind(this));
        this.port.on('mouseout', this.AddSetInterval.bind(this));
        this.AutoPlay(0);
    };

    this.SetChartSize = function () {
        $('#port-chart').highcharts().reflow();
    };

    this.OnTimeSelect = function (event) {
        $('.port-select a').removeClass("active");
        $(event.target).addClass("active");

        this.getDataByPortClick(event.target.text);
    };

    this.AutoPlay = function (index) {
        var rows = $('.port-select a');
        this.Timer = setInterval(function () {
            index++;
            if (index >= rows.length)
                index = 0;

            $('.port-select a').removeClass("active");
            $('.port-select a').eq(index).addClass("active");
            this.getDataByPortClick($('.port-select a.active').text());
        }.bind(this), 20000);
    };

    this.RemoveSetInterval = function () {
        if (this.Timer !== null)
            clearInterval(this.Timer);
    };

    this.AddSetInterval = function () {
        var index = $('.port-select a.active').index();
        this.AutoPlay(index);
    };

    this.getDataByPortClick = function(time){
        if (time === '本周'){
            if (this.thisWeek == null){
                var startTime = this.TimeUtil.GetWeekStartTime();
                var endTime = this.TimeUtil.GetWeekEndTime();
                this.Reload(startTime, endTime);
                return false;
            }
        }else if(time === '本月'){
            if (this.thisMonth == null){
                var startMonthTime = this.TimeUtil.GetMonthStartTime();
                var endMonthTime = this.TimeUtil.GetMonthEndTime();
                this.Reload(startMonthTime, endMonthTime);
                return false;
            }
        }else {
            if (this.thisYear === null){
                var startDate = this.TimeUtil.GetYearStartTime();
                var endDate = this.TimeUtil.GetYearEndTime();
                this.Reload(startDate, endDate);
                return false;
            }
        }
    };

    this.Reload = function(startTime, endTime){
        $.ajax({
            type: 'POST',
            data: {
                startTime: startTime,
                endTime: endTime
            },
            url: 'stat/findAreaCallByTimeRange',
            success: function (data) {
               this.ReloadChartData(data);
            }.bind(this)
        })
    };

    this.ReloadChartData = function (result) {
        var elementSeries = {};
        var names = ['次数', '格点'];
        var xMarks = this.GetXMarks(result);
        var value = this.GetElementValues(result);
        var series = this.GetElementSeries(names, value);
        elementSeries = series;
        this.ShowPortChart(xMarks, elementSeries);
    };

    this.GetXMarks = function (result) {
        var marks = [];
        result.forEach(function (item, index) {
            marks.push(item.name);
        });
        return marks;
    };

    this.GetElementValues = function (result) {
        var count = [];
        var gridCount = [];

        result.forEach(function (item) {
            count.push(item.count);
            gridCount.push(parseInt(item.gridCount));
        });

        return [count, gridCount]
    };

    this.GetElementSeries = function (name, data) {
        var values = [];
        for (var i = 0; i < data.length; i++) {
            var obj = {};
            obj["name"] = name[i];
            obj["data"] = data[i];
            values.push(obj);
        }
        return values;
    };

    this.ShowPortChart = function(xMarks, series) {
        var chart = Highcharts.chart('port-chart', {
            chart: {
                type: 'spline',
                backgroundColor: null
            },
            title: {
                text: null
            },
            subtitle: {
                text: null
            },
            credits: {
                enabled: false
            },
            xAxis: {
                categories: xMarks,
                lineColor: '#115d93',
                labels: {
                    style:{
                        color: '#a2d9ff',
                        fontFamily: '微软雅黑'
                    }
                }
            },
            yAxis: {
                title: {
                    text: null
                },
                gridLineWidth: 1,
                lineWidth : 1,
                lineColor: '#115d93',
                gridLineColor: '#115d93',
                gridLineDashStyle: 'ShortDot',
                labels: {
                    style:{
                        color: '#a2d9ff'
                    }
                }
            },
            colors: ['#acd598','#fdc674'],
            legend: {
                layout: 'horizontal',
                align: 'left',
                verticalAlign: 'top',
                symbolHeight: 12,
                symbolWidth: 12,
                symbolRadius: 0,
                itemHoverStyle : {
                    color : '#a2d9ff'
                },
                itemStyle: {
                    color: '#a2d9ff',
                    fontSize: '12px',
                    fontFamily: '微软雅黑',
                    fontWeight: 'normal'
                }
            },
            tooltip: {
                shared: true
            },
            series: series
        });
    };
};