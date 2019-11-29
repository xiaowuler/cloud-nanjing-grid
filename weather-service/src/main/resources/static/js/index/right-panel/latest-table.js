var LatestTable = function () {
    this.Timer = null;
    this.table = $('.data-table');
    this.DataGrid = $('#data-table');

    this.Reload = function(){
        this.ReloadLatestTable();
        this.SetStateGridScroll();
        this.table.on('mouseover', this.RemoveSetInterval.bind(this));
    };

    this.ReloadLatestTable = function () {
        this.DataGrid.datagrid({
            striped: true,
            singleSelect: true,
            fitColumns: true,
            fit: true,
            scrollbarSize: 0
        });
    };

    this.SetStateGridScroll = function () {
        var rows = this.DataGrid.datagrid("getRows");
        var index = -1;

        if (rows.length * 28 > ($('.data-table').height() - 28)){
            this.Timer = setInterval(function () {
                index++;
                if (index >= rows.length)
                    index = 0;

                this.DataGrid.datagrid('scrollTo', index);
                this.DataGrid.datagrid('selectRow', index);
            }.bind(this), 3000);
        } else {
            clearInterval(this.Timer);
        }

    };

    this.RemoveSetInterval = function (){
        if (this.Timer !== null)
            clearInterval(this.Timer);
    };

    this.AutoPlay = function () {

    };
};