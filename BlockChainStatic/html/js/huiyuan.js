$(function() {
  $("#btnbrck").click(function() {
    location = "qianbao.html";
  });

  $("#btnExit").click(function() {
    mydialog.showConfirm('是否确认成为会员?', '确认', function() {
      mydialog.showCustom($('#dialogPayPwd'), '交易密码');
    }); 
  });
  $('#btnCanclePay').click(function() {
    mydialog.hideCustom();
  });

  $('#btnToPay').click(function() {
    mydialog.hideCustom();
    sendData($('#txtPaypwd').val());
  });   
  function sendData(pwd) {
    mydialog.showWait("升级中.......", "等待");
    dataService.send("/user/vip", { paypwd: pwd}, function(data) {
      mydialog.hideWait();
      console.log(data);
      mydialog.showAlert(data.message, "信息", function() {
        query();
      });
    });
  }
  

  function query() {
    mydialog.showWait("查询中.....", "等待");
    dataService.send("/user/queryUserInfoByKey", {}, function(data) {
      mydialog.hideWait();
      console.log(data);
      $("#divVipInfo").hide();
      $("#divToVip").hide();
      $("#spanprin").html(data.dataMap.siteBaseConfig.vip);
      if (
        data.dataMap.userInfo &&
        data.dataMap.userInfo.vip &&
        data.dataMap.userInfo.vip == "y"
      ) {
        $("#divVipInfo").show();
      } else {
        $("#divToVip").show();
      }
    });
  }
  query();
});
