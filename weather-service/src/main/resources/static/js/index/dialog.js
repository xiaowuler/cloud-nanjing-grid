var Dialog = function (parent) {
    this.parent = parent;
    this.updateTime = null;
    this.startTime = null;
    this.forecastModel = null;
    this.elementCode = null;
    this.location = [];
    this.lat = null;
    this.lng = null;
    this.chart = null;

    this.initial = function(updateTime, startTime, forecastModel, elementCode, latlng){
        this.updateTime = updateTime;
        this.startTime = startTime;
        this.forecastModel = forecastModel;
        this.elementCode = elementCode;
        this.lng = latlng.lng;
        this.lat = latlng.lat;

        $('#slider-query').off('click').on('click', this.search.bind(this));
        $('.close-btn').on('click', this.CloseDialog.bind(this));
    };

    this.search = function () {
        $.ajax({
            type: 'POST',
            data: {
                startDate: this.startTime,
                startForecastDate: moment($('#start-date').datetimebox('getText'), 'YYYY/MM/DD HH:mm:ss').toDate(),
                endForecastDate: moment($('#end-date').datetimebox('getText'), 'YYYY/MM/DD HH:mm:ss').toDate(),
                updateDate: this.updateTime,
                forecastModel: this.forecastModel,
                elementCode: this.elementCode,
                lng: this.lng,
                lat: this.lat
            },
            url: 'baseSearch/findNJGridsByForecastTimeRangeAllElement',
            success: function (data) {
                if (data === null){
                    return;
                }
                this.showChart(data.type, data.times, this.getSeries(data.name, data.values), data.unit, data.windDirection, data.flags);
            }.bind(this)
        })
    };

    this.getSeries = function (name, values) {
        var series = [];
        series.push({
            name: name,
            data: values
        });
        return series;
    }

    this.CloseDialog = function () {
        $('.dialog').hide();
    };

    this.OpenDialog = function (title) {
        $('.dialog-title h2').html(title);
        $('.dialog').show();
    };

    this.InitDate = function (startTime, endTime) {
        $('#start-date').datetimebox({
            panelWidth: 190,
            panelHeight: 240,
            showSeconds: false
        });
        $('#end-date').datetimebox({
            panelWidth: 190,
            panelHeight: 240,
            showSeconds: false
        });
        $('#end-date').datetimebox('setValue', moment(endTime).format("YYYY/MM/DD HH:mm:ss"));
        $('#start-date').datetimebox('setValue', moment(startTime).format("YYYY/MM/DD HH:mm:ss"));
        $('#slider-query').trigger('click');
    };

    this.showChart = function(type, times, series, unit, windDirections, flags) {
        var parent = this.parent;
        var elementCode = this.elementCode;
        var flag = elementCode != 'WTYPE' && elementCode != 'PTYPE';
        this.chart = new Highcharts.Chart({
            chart: {
                renderTo: 'dialog-chart',
                type: type,
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
                max: 23,
                categories: times,
                lineColor: '#115d93',
                labels: {
                    style:{
                        color: '#a2d9ff'
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
                    enabled: flag,
                    style:{
                        color: '#a2d9ff'
                    }
                }
            },
            colors: ['#acd598','#fdc674'],
            legend: {
                enabled: false
            },
            tooltip: {
                backgroundColor: "#1a516b",
                useHTML: true,
                //pointFormat: '{series.name}: <b>{point.y:1f}{0}</b>'.format(unit),
                formatter: function () {

                    var pointStr = '<table style="font-family: \'微软雅黑\'; color: #E0ECFF">';
                    if (elementCode === "Wind"){
                        var direcation = windDirections[this.points[0].point.index];
                        pointStr += '<tr><td>{4}</td></tr><tr><td>风向:{0}</td></tr><tr><td style="padding:0"><b>{3}:{2}{5}</b></td></tr></table>'.format(direcation, null, this.y, series[0].name, moment(this.x, "YYMMDDHH").format("YYYY/MM/DD HH:mm"), unit);
                        return pointStr;
                    }else if (elementCode === "WTYPE" || elementCode === 'PTYPE'){
                        var wea = flags[this.points[0].point.index];
                        pointStr += '<tr><td>{4}</td></tr><tr><td>{1}:{0}</td></tr></table>'.format(wea, (elementCode == "WTYPE" ? '天气现象': '相态'), this.y, series[0].name, moment(this.x, "YYMMDDHH").format("YYYY/MM/DD HH:mm"));
                        return pointStr;
                    }else {
                        pointStr += '<tr><td>{2}</td></tr><tr><td>{1}:{0}{3}</td></tr></table>'.format(this.y, series[0].name, moment(this.x, "YYMMDDHH").format("YYYY/MM/DD HH:mm"), unit);
                        return pointStr;
                    }
                },
                shared: true
            },
            plotOptions:{
                spline:{
                    dataLabels:{
                        enabled: true,
                        useHTML: true,
                        formatter: function () {
                            if (elementCode === "Wind"){
                                var direcation = windDirections[this.point.index];
                                return  '<img src="{0}">'.format(parent.getWindDirectionImageUrl(direcation));
                            }else if (elementCode === "WTYPE" || elementCode === 'PTYPE'){
                                var wea = flags[this.point.index];
                                return  '<img src="{0}">'.format(parent.getWeatherImageUrl(wea));
                            }else {
                                return;
                            }
                        }
                    }
                }
            },
            scrollbar: {
                enabled: true,
                height: 8,
                barBackgroundColor: '#00538D',
                barBorderRadius: 0,
                barBorderWidth: 0,
                buttonBackgroundColor: '#004D83',
                buttonBorderWidth: 0,
                buttonBorderRadius: 0,
                trackBackgroundColor: '#00497C',
                trackBorderWidth: 1,
                trackBorderRadius: 0,
                trackBorderColor: '#00497C'
            },
            series: series
        });
    };
};