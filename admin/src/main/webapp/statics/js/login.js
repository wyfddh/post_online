//设置验证码
// refreshCode();


layui.config({
    base: "js/"
}).use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;
    //隐藏验证码
    $("#formCode").hide();
    //登录按钮事件
    form.on("submit(login)", function (data) {
        var password = hex_md5(data.field.password);
        // parent.location.href = 'http://localhost:63342/chinayouzheng/index.html';
        var datas = "userId=" + data.field.username + "&password=" + password+ "&verification=" + data.field.captcha;
        $.ajax({
            type: "POST",
            url: property.getProjectPath()+"login.do",
            data: datas,
            dataType: "json",
            success: function (result) {
                if (result.success == 1) {//登录成功
                    localStorage.userInfo = JSON.stringify(result.data);
                    parent.location.href = property.getProjectPath()+'index.html';
                } else {
                    $("#formCode").show();
                    layer.msg(result.error.message, {icon: 5});
                    refreshCode();
                }
            }
        });
        return false;
    })
});
function refreshCode(){
    // var url = "192.168.5.32:8080/admin/";
    var captcha = document.getElementById("captcha");
    captcha.src = property.getProjectPath()+"Tools/getImgCode.do";

}
