/**
 * author: zhangwei
 * 账户设置
 */
var pageType = "edit";
var basePassword = ''
var userID = '';
var main = {

    init: function (){

        $('#submit').text('保存');
        var  id = JSON.parse(localStorage.userInfo).userId;
        loadData(id);
        this.initTable();
    },

    initTable: function () {
        var _this = this;
        layui.use('form', function () {
            var form = layui.form;

            form.verify({
                'oldpassword': function () {
                    var oldpassword = $("#oldpassword").val();
                    oldpassword = hex_md5(oldpassword)
                    if(oldpassword != basePassword)  {
                        return '密码不正确，请输入正确的密码'
                    }
                },
                'surePassword':function () {
                    var password = $("#newpassword").val();
                    var surePassword = $("#surePassword").val();
                    if(surePassword != password) {
                        return '密码和确认密码不一致'
                    }
                }
            })
            //监听提交
            form.on('submit(formSubmit)', function (data) {
                debugger;
                var  url = "sysUser/changePassword.do";
                var newPassword = $("#newpassword").val();
                var surePassword = $("#surePassword").val();
                newPassword = hex_md5(newPassword);
                surePassword = hex_md5(surePassword);

                var  data = {
                    id: userID,
                    password: newPassword,
                    surePassword: surePassword,
                    oldPassword: basePassword
                }

                $.ajax({
                    contentType: 'application/json;charset=UTF-8',
                    url: property.getProjectPath() + yc.checkDo(url),
                    async: false,
                    data: JSON.stringify(data),
                    dataType: 'json',
                    type: 'post',
                    success: function (result) {
                        if (result.success == 1) {
                            successMsg("密码修改成功,请重新登陆");
                            parent.$t.goback("login.html");
                        } else {
                            //top.layer.msg(result.error);
                            errorMsg("密码修改失败");
                        }
                    },
                    error: function (result) {
                        errorMsg("系统异常");
                    }
                });
                return false;
            })
        });

        //监听重置
        $("#cancel").click(function(){
            layer.confirm('确认取消吗?', function(){
                parent.$t.goback("page/systemSetting/userManage/list.html");
            });
            return false;
        });

        // 监听密码是否修改
        $("#password").click(function () {
            $("#password").val('');
            $("#surePassword").val('');
        })


    },
}


main.init();


/**
 * 加载表单数据
 * @param id 角色id
 */
function loadData(id) {
    layui.use('form', function () {
        var form = layui.form;
        var index = parent.layer.getFrameIndex(window.name);
        var data = {"id": id};
        //加载数据
        var datas = yc.ajaxGetByParams('sysUser/getSysUserById.do', data, null, null);
        $.ajax({
            type:"post",
            data:data,
            async:false,
            url:property.getProjectPath()+'sysUser/getSysUserById.do',
            success:function(result) {
                if (result.success == 1) {
                    var jsonData = result.data;
                    jsonData.passwordId = jsonData.password;
                    jsonData.surePasswordId = jsonData.surePassword;
                    var show = yc.password;
                    jsonData.password = show;
                    jsonData.surePassword = show;
                    jsonData.surePassword = "";  //清空密码值
                    setFormData(jsonData);
                    form.render();
                } else {
                    errorMsg(result.error.message);
                }
            },
            error:function(result) {
                errorMsg("系统异常");
            }
        });


    });
}


/**
 * 设置表单数据
 * @param data
 */
function setFormData(data) {
    property.setForm($("#userForm"), data);
    $("#userName").attr("disabled","disabled");
    $("#email").attr("disabled","disabled");
    basePassword = data.passwordId
    userID = data.id
}




function checkPassword(password, surePassword){

    if (yc.isNull(password)) {
        errorMsg("密码不能为空！");
        return false;
    } else {
        if (password != surePassword) {
            errorMsg("前后密码不一致!");
            return false;
        } else {
            return true;
        }
    }
}



