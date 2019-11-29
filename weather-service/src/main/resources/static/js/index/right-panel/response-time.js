var ResponseTime = function (){
    this.TimeUtil = new TimeUtil();

    this.SetChartSize = function () {
        $('#response-chart').highcharts().reflow();
    };

    this.Reload = function(){
        var startTime = this.TimeUtil.GetWeekStartTime();
        var endTime = this.TimeUtil.GetWeekEndTime();
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
        var xMarks = this.GetXMarks(result);
        var value = this.GetElementValues(result);
        var series = this.GetElementSeries(value);
        elementSeries = series;
        this.ShowResponseChart(xMarks, elementSeries);
    };

    this.GetXMarks = function (result) {
        var marks = [];
        result.forEach(function (item, index) {
            marks.push(item.name);
        });
        return marks;
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

    this.ShowResponseChart = function (xMarks, series){
        Highcharts.chart('response-chart', {
            chart: {
                type: 'column',
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
            colors: ['#fdc674','#93d458', '#ea4379','#acd598', '#f19ec2','#00e7ff', '#00a0e9','#89c997', '#f19ec2','#fdc674'],
            legend: {
                enabled: false
            },
            tooltip: {
                pointFormat: '次数: <b>{point.y}</b>'
            },
            plotOptions: {
                column: {
                    borderWidth: 0,
                    maxPointWidth: 25,
                    borderRadius: 5
                },
                series: {
                    colorByPoint: true
                }
            },
            series: [{
                data: series
            }]
        });
    };
};