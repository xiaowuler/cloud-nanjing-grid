var RightPanel = function () {
    this.Reload = function (id) {
        $.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            data:{
                id: id
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
            var isRequired = item.isRequired === 1 ? '是' : '否';
            //var label = '<div class="param-block clearfix"><div class="fl"><div class="port-input"><label>用户名：</label><input type="text" placeholder="请输入用户名" value="{0}"></div><div class="port-input"><label>密码密文：</label><input type="text" placeholder="{1}"></div></div><div class="fl"><div><span class="param-type">参数类型：{2}</span><span class="param-describe">是否必填：{3}</span><span class="param-required">描述：{4}</span></div><div><span class="param-type">参数类型：{5}</span><span class="param-describe">是否必填：{6}</span><span class="param-required">描述：{7}</span></div></div></div>';
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
};