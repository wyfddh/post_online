/**
 * author: zhangwei
 * 账户设置
 */
var main = {
    init: function () {
        this.getData();
        this.initTable();
    },
    initTable: function() {
        layui.use('form', function() {
            var form = layui.form;

            //监听提交
            form.on('submit(formSubmit)', function(data) {
                console.log(data);
                layer.msg(JSON.stringify(data.field));
                return false;
            });

            //监听取消
            $("#cancel").click(function () {
                layer.confirm('确认取消吗?', function(index){
                    // parent.$(".myRefresh").click();
                    layer.close(index);
                });
                return false;
            });
        });
    },
    getData: function() {
        layui.use('form', function() {
            var form = layui.form;
            form.val("myform", {
                "username": "贤心"
                ,"email": '123@sina.com'
                ,"name": "vvvvv"
                ,"oldPassword": "1234"
            });
        });
    }
}
main.init();
