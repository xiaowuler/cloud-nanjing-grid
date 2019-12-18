var Dialog = function () {
    this.Startup = function(){
        this.InitDate();
        this.ShowChart();
        //$('.test-btn').on('click', this.OpenDialog.bind(this));
        $('.close-btn').on('click', this.CloseDialog.bind(this));
    };

    this.CloseDialog = function () {
        $('.dialog').hide();
    };

    this.OpenDialog = function () {
        $('.dialog').show();
    };

    this.InitDate = function () {
        $('#start-date').datebox({
            panelWidth: 190,
            panelHeight: 200
        });
        $('#end-date').datebox({
            panelWidth: 190,
            panelHeight: 200
        });
    };

    this.ShowChart = function() {
        var chart = Highcharts.chart('dialog-chart', {
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
                categories: ['江苏', '南通', '无锡', '扬州', '南京', '苏州', '常州', '镇江', '泰州', '盐城', '淮安', '宿迁', '徐州', '连云港'],
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
                shared: true
            },
            series: [{
                name: '站点',
                data: [39, 25, 21, 33, 25, 19, 20, 11, 21, 33, 25, 19, 20, 11]
            }]
        });
    };
};