var ResponseTime = function (){
    this.Reload = function (){
        this.ShowResponseChart();
    };

    this.SetChartSize = function () {
        $('#response-chart').highcharts().reflow();
    };

    this.ShowResponseChart = function (){
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
                categories: ['短期站点预报', '中短格点期预报', '中短站点期预报','短期站点预报', '短临格点预报', '短临站点预报','3KM预报', '5KM实况', '自助站实况', 'WARMS预报'],
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
            colors: ['#fdc674','#93d458', '#ea4379','#acd598', '#f19ec2','#00e7ff', '#00a0e9','#89c997', '#f19ec2','#fdc674'],
            legend: {
                enabled: false
            },
            tooltip: {
                pointFormat: '占比: <b>{point.y:.1f} %</b>'
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
                data: [
                    ['短期站点预报', 210],
                    ['中短格点期预报', 100],
                    ['中短站点期预报', 25],
                    ['短期站点预报', 295],
                    ['短临格点预报', 160],
                    ['短临站点预报', 110],
                    ['3KM实况', 185],
                    ['5KM实况', 98],
                    ['自助站实况', 45],
                    ['WARMS预报', 90]
                ]
            }]
        });
    };
};