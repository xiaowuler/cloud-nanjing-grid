var LeftPanel = function() {

    this.PortChart = new PortChart();
    this.PortTable = new PortTable();

    this.Startup = function(){
        this.PortChart.Reload();
        this.PortTable.Reload();
    }
};