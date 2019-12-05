var RightPanel = function () {
    this.baseUrl = 'http://localhost:8003/';

    this.Reload = function (id) {
        $.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            data:{
                id: parseInt(id)
            },
            url: 'interface/findInterfaceParameter',
            success: function (data) {
                console.log(data);
                this.ShowParamList(data);
            }.bind(this)
        })
    };

    this.ShowParamList = function (data) {
        var list = $('.param-list');
        list.find('.param-block').remove();

        data.forEach(function (item) {
            var name = item.name;
            var password = item.chineseName;
            var type = item.type;
            var describe = item.description;
            var isRequired = item.isRequired === '1' ? '是' : '否';
            var label = '<div class="param-block port-input clearfix"><label>{0}</label><input type="text" placeholder="{1}"><span class="param-type">参数类型：{2}</span><span class="param-describe">是否必填：{3}</span><span class="param-required">描述：{4}</span></div></div></div>';

            list.append(label.format(name, password, type, isRequired, describe));

            var block = $('.param-block');
            if (block.length <= 4)
                list.height(block.length * (block.height() + 10));
            else
                list.height(186);
            $('.port-result').height($('.content').height() - list.height() - 381);
        }.bind(this));
    };

    this.ReloadInterfaceUrl = function (name) {
        $.ajax({
            type: "GET",
            //dataType: 'json',
            async: true,
            //url: 'http://localhost:8003/{0}'.format(name),
            url: this.baseUrl + name,
            success: function (data) {
                console.log(data);
            }.bind(this)
        })
    }
};