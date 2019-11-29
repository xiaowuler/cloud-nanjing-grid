var ProductChart = function () {
    this.Reload = function () {
        this.ShowProductChart();
        $('.number-select a').on('click', this.OnTimeSelect.bind(this));
    };

    this.OnTimeSelect = function (event) {
        $('.number-select a').removeClass("active");
        $(event.target).addClass("active");
    };

    this.SetChartSize = function () {
        $('#product-chart').highcharts().reflow();
    };

    this.ShowProductChart = function (){
        var chart = Highcharts.chart('product-chart', {
            chart: {
                backgroundColor: null
            },
            title: {
                floating:true,
                text: '<span style="font-size: 24px;color: #28edff;">2300<br><span style="font-size: 16px;color: #a2d9ff">总量</span></span>',
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
                data: [
                    ['GRAPES-3KM预报产品', 2300],
                    ['5KM实况预报', 2400],
                    ['短期预报产品',  3200],
                    ['中期预报产品', 1500],
                    ['短临预报产品', 700],
                    ['本省自动站产品', 600]
                ],
                showInLegend: true
            }]
        }, function(c) {
            var centerY = c.series[0].center[1];
            var centerX = c.series[0].center[1];
            var titleHeight = parseInt(c.title.styles.fontSize);

            c.setTitle({
                y: centerY + titleHeight/2 - 5,
                x: -centerX
            });
        });
    };
};