var informationSourceList = null;
var id = null;
var info = null;
var main={

    init:function () {
        this.initTable();
        this.tabBind();
    },
    initTable:function(){
        layui.use('laydate', function(){
            var laydate = layui.laydate;

            //执行一个laydate实例
            laydate.render({
                elem: '#planReturnDate' //指定元素
            });
        });
        layui.use('form', function(){
            var form = layui.form;

            //监听提交
            form.on('submit(formDemo)', function(data){
                layer.msg(JSON.stringify(data.field));
                return false;
            });
        });
        setSelect();
        id = localStorage.id;
        loadData(id);

    },

    tabBind:function () {
        //导出函数
        $(".layui-btn-green").on({
            'click':function () {
                return false
            }
        })
        //时间切换
        $(".searchBtn").on({
            'click':function () {
                var index=$(this).index();
                if($(this).hasClass('active'))return false
                if(index==1){
                    $(".searchBtn").removeClass("active");
                    $(".searchBtn").eq(0).addClass("active");
                }else{
                    $(".searchBtn").removeClass("active");
                    $(".searchBtn").eq(1).addClass("active");
                }

                return false;
            }
        })
        //借阅
        $("#borrow").click(function () {
            localStorage["dataObject"]=JSON.stringify(info);
            parent.$t.goToPageSimple("page/literature/borrowing.html","文献资料-借阅申请","page/menu/menuList.html");
        });
        //归还
        $("#back").click(function () {
            layer.confirm('您确定要归还吗？', {
                title: '归还确认',
                area: ['450px', '218px'],
                skin: 'demo-class',
                btn: ['取消', '确认']
            }, function (index, layero) {
                layer.close(index);
            }, function (index) {
                $.ajax({
                    url: property.getProjectPath() + '/postLiteratureProcess/modifyState.do',
                    type: 'post',
                    data: {"id": id, "status": "3"},
                    success: function (result) {
                        if (result.success == "1") {
                            successMsg("归还成功！");
                        } else {
                            var resultMsg = result.error.message;
                            errorMsg(resultMsg);
                        }
                        tableIns.reload();
                        layer.close(index);
                    }
                })
            });
        });
        //打印
        $("#print").click(function () {
                // $("#content").print({
                //     globalStyles: false,
                //     mediaPrint: false,
                //     stylesheet: null,
                //     noPrintSelector: ".no-print",
                //     iframe: false,
                //     append: null,
                //     prepend: null,
                //     deferred: $.Deferred()
                // })
            window.print();
        });
    }
}
main.init();
//日历切换
function cDayFunc() {
    main.initTable()
}
function xhrOnProgress(fun) {
    xhrOnProgress.onprogress = fun;
    return function() {
        var xhr = $.ajaxSettings.xhr();
        if (typeof xhrOnProgress.onprogress !== 'function')
            return xhr;
        if (xhrOnProgress.onprogress && xhr.upload) {
            xhr.upload.onprogress = xhrOnProgress.onprogress;
        }                return xhr;
    }     }

function loadData(id) {
    layui.use('form', function(){
        var form = layui.form;
        var index = parent.layer.getFrameIndex(window.name);
        var json = {"id":id};
        //加载数据
        $.ajax({
            type:"get",
            data:json,
            async:false,
            url:property.getProjectPath()+"postLiteratureProcess/getLiteratureProcessById.do",
            success:function(result) {
                if (result.success == 1) {
                    info = result.data;
                    setFormData(result.data);
                    form.render('select');
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

function setFormData(data) {
    console.log(data);
    property.setForm($("#borrowingForm"),data);
    $("#informationSources").val(data.informationSources);

    $("#planReturnDate").val(formatSimpleDate(data.planReturnDate));
    var applicant = getUserName(data.applicant);
    var approveId = getUserName(data.approveId);
    $("#applicant").val(applicant);
    $("#approveId").val(approveId);
    var text = "当前订单状态：";
    var status = property.getDictData("apply_status");
    text = text+property.getTextByValuePlus(status,data.applyStatus,"dictCode","dictName");
    $("#status").text(text);
    //已申请
    if (data.applyStatus == "0" || data.applyStatus == "1"){
        $("#apply").text(applicant);
        $("#applyTime").text(formatDate(data.createTime));

        $("#back").hide();
    }
    //已借阅
    else if (data.applyStatus == "2"){
        $("#apply").text(applicant);
        $("#applyTime").text(formatDate(data.createTime));
        $("#approval").text(approveId);
        $("#approvalTime").text(formatDate(data.borrowingDate));

        $("#borrow").hide();
    }
    //已归还
    else if (data.applyStatus == "3"){
        $("#apply").text(applicant);
        $("#applyTime").text(formatDate(data.createTime));
        $("#approval").text(approveId);
        $("#approvalTime").text(formatDate(data.borrowingDate));
        $("#return").text(applicant);
        $("#returnTime").text(formatDate(data.realReturnDate));

        $("#borrow").hide();
        $("#back").hide();

    }
}

function setSelect() {
    // informationSourceList = property.getDictDataMulti(['']);
}

function getUserName(id) {
    var data = null;
    var json = {"id":id};
    console.log(id);
    $.ajax({
        type:"get",
        data:json,
        async:false,
        url:property.getProjectPath()+"RoleAuth/getUserById.do",
        success:function(result) {
            if (result.success == 1) {
                data = result.data.name;
            } else {
                errorMsg(result.error.message);
            }
        },
        error:function(result) {
            errorMsg("系统异常");
        }
    });
    return data;
}

// function getDetail(id) {
//     var json = {"processId":id};
//     $.ajax({
//         type:"get",
//         data:json,
//         async:false,
//         url:property.getProjectPath()+"postLiteratureProcessDetail/getProcessDetailByProcess.do",
//         success:function(result) {
//             if (result.success == 1) {
//                 data = result.data;
//                 if (data.length>0){
//                     for (var i=0;i<data.length;i++){
//                         var temp = data[i];
//                         if (temp.processOperation == "-1"){
//                             $("#apply").text(getUserName(temp.processUserId));
//                             $("#applyTime").text(formatDate(temp.createTime));
//                         }else if(temp.processOperation == "1"){
//                             $("#approval").text(getUserName(temp.processUserId));
//                             $("#approvalTime").text(formatDate(temp.createTime));
//                         }else if(temp.processOperation == "1"){
//                             $("#approval").text(getUserName(temp.processUserId));
//                             $("#approvalTime").text(formatDate(temp.createTime));
//                         }
//                     }
//                 }
//             } else {
//                 top.layer.msg(result.error.message);
//             }
//         },
//         error:function(result) {
//             top.layer.msg("系统异常");
//         }
//     });
//
// }