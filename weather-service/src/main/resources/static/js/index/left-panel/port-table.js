var PortTable = function(){
    this.Timer = null;
    this.table = $('.port-table');
    this.StateGrid = $('#state-table');

    this.Reload = function(){
        this.ReloadPortTable();
        this.SetStateGridScroll();
        this.table.on('mouseover', this.RemoveSetInterval.bind(this));
        this.table.on('mouseout', this.AutoPlay.bind(this));
    };

    this.ReloadPortTable = function () {
        this.StateGrid.datagrid({
            striped: true,
            singleSelect: true,
            fitColumns: true,
            fit: true,
            scrollbarSize: 0
        });

    };

    this.SetStateGridScroll = function () {
        var rows = this.StateGrid.datagrid("getRows");
        var index = -1;
        if (rows.length * 28 > ($('.port-table').height() - 28)){
            this.Timer = setInterval(function () {
                index++;
                if (index >= rows.length)
                    index = 0;

                this.StateGrid.datagrid('scrollTo', index);
                this.StateGrid.datagrid('selectRow', index);
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