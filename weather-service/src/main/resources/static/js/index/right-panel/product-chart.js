var ProductChart = function () {
    this.Timer = null;
    this.thisWeek = null;
    this.thisMonth = null;
    this.thisYear = null;
    this.product = $('.product-chart');
    this.TimeUtil = new TimeUtil();

    this.Startup = function () {
        this.Reload();
        $('.number-select a').on('click', this.OnTimeSelect.bind(this));
        $('.number-select').on('mouseover', this.RemoveSetInterval.bind(this));
        $('.number-select').on('mouseout', this.AddSetInterval.bind(this));
        this.product.on('mouseover', this.RemoveSetInterval.bind(this));
        this.product.on('mouseout', this.AddSetInterval.bind(this));
        this.AutoPlay(0);
    };

    this.SetChartSize = function () {
        $('#product-chart').highcharts().reflow();
    };

    this.OnTimeSelect = function (event) {
        $('.number-select a').removeClass("active");
        $(event.target).addClass("active");

        this.getDataByPortClick(event.target.text);
    };

    this.AutoPlay = function (index) {
        var rows = $('.port-select a');
        this.Timer = setInterval(function () {
            index++;
            if (index >= rows.length)
                index = 0;

            $('.number-select a').removeClass("active");
            $('.number-select a').eq(index).addClass("active");
            this.getDataByPortClick($('.port-select a.active').text());
        }.bind(this), 5000);
    };

    this.RemoveSetInterval = function () {
        if (this.Timer !== null)
            clearInterval(this.Timer);
    };

    this.AddSetInterval = function () {
        var index = $('.number-select a.active').index();
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
        if (startTime === undefined || endTime === undefined) {
            startTime = this.TimeUtil.GetWeekStartTime();
            endTime = this.TimeUtil.GetWeekEndTime();
        } else {
            startTime = startTime;
            endTime = endTime;
        }
        $.ajax({
            type: 'POST',
            data: {
                startTime: startTime,
                endTime: endTime
            },
            url: 'stat/findTypeCallByTimeRange',
            success: function (data) {
                this.ReloadChartData(data);
            }.bind(this)
        })
    };

    this.ReloadChartData = function (result) {
        var elementSeries = {};
        var value = this.GetElementValues(result);
        var series = this.GetElementSeries(value);
        elementSeries = series;
        this.ShowProductChart(elementSeries);
    };

    this.GetElementValues = function (result) {
        var names = [];
        var count = [];
        result.forEach(function (item) {
            names.push(item.name);
            count.push(item.count);
        }.bind(this));

        return {
            name: names,
            count: count
        };
    };

    this.GetElementSeries = function (data) {
        var values = [];
        for (var i = 0; i < data.name.length; i++) {
            values.push({
                "name": data.name[i],
                "y": data.count[i]
            });
        }
        return values;
    };

    this.ShowProductChart = function (series){
        var chart = Highcharts.chart('product-chart', {
            chart: {
                backgroundColor: null
            },
            title: {
                floating:true,
                text: '<span style="font-size: 24px;color: #28edff;">{0}<br><span style="font-size: 16px;color: #a2d9ff">总量</span></span>'.format(series[0].y),
                //useHTML: true,
                style: {
                    color: '#00e7ff',
                    fontSize: '20px',
                    fontFamily: '微软雅黑'
                }
            },
            credits: {
                enabled: false
            },
            colors: ['#5338f4','#ffc844', '#148ce2', '#ea4379', '#08bcd2', '#3cf9ff'],
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}</b>'
            },
            legend: {
                layout: 'horizontal',
                align: 'right',
                verticalAlign: 'middle',
                symbolHeight: 12,
                symbolWidth: 12,
                symbolRadius: 0,
                symbolPadding: 6,
                itemDistance: 30,
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
            plotOptions: {
                pie: {
                    size: '100%',
                    borderWidth: 0,
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false,
                        format: '<b class="">{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: '#ffffff'
                        }
                    },
                    point: {
                        events: {
                            mouseOver: function(e) {
                                chart.setTitle({
                                    text: '<span style="font-size: 30px;color: #28edff;">' + e.target.y + '</span>' + '<br>'+ '<span style="font-size: 16px;color: #a2d9ff">' + '总量' + '</span>'
                                });
                            }
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                innerSize: '78%',
                name: '总量',
                data: series,
                showInLegend: true
            }]
        }, function(c) {
            var centerY = c.series[0].center[1];
            var centerX = c.series[0].center[1];
            var titleHeight = parseInt(c.title.styles.fontSize);

            c.setTitle({
                y: centerY + titleHeight/2 - 5,
                x: -centerX + 30
            });
        });
    };
};