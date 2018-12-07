layui.use(['form','layer'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    //更换图片
    $('.pad').on("click",".img3",function(){
      $('.uploadBtn').click();

    })
    //删除图片
    $('.pad').on("click",".img4",function(){

        $("#picids").val("");
        $(".picDiv").remove();
        $(".uploadBtn").before("<div class='picDiv'></div>");
        $(".uploadBtn").show();
    })

})
//上传单图片
function uploadPicture(projectName){
    var indexUpload = layui.layer.open({
        title : "裁剪图片",
        type : 2,
        area: ['80%', '700px'],
        content : "../../../statics/libs/picture/cropHead.html",
        success : function(layero, indexUpload){
            localStorage.removeItem('map');
            var body = layui.layer.getChildFrame('body', indexUpload);
            body.find("#projectName").val(projectName);
            setTimeout(function(){
                layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            },500)
        },
        end :function() {
            var map = JSON.parse(localStorage["map"]);
            console.log(map);
            //插入图片
            var picStr = '<div class="img picDiv" id="img'+ map.id +'">'
                +'<div class="img1"><img src='+ map.absolutePath +' alt="" ></div>'
                +'<div class="img2"><span class="img3" id="span'+ map.id +'" mark='+ map.id +'>更换图片</span><span class="img4" mark='+ map.id +'>删除图片</span></div>'
                +'</div>'
            if($('.picDiv').length > 0) {
                $('.picDiv').remove();
            }
            $(".uploadBtn").before(picStr);
            var picids = map.id;
            $("#picids").val(picids);
            $(".uploadBtn").hide();
        }
    })
    layui.layer.full(indexUpload);
    window.sessionStorage.setItem("index",indexUpload);
    //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
    // $(window).on("resize",function(){
    //     layui.layer.full(window.sessionStorage.getItem("index"));
    // })
}

function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == "")	{
        return true;
    }else{
        return false;
    }
}