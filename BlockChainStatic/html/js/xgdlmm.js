$(function() {
  $('#btnback').click(function() {
    location = 'gerenziliao.html';
  });

  $('#btnsubmit').click(function() {
    mydialog.showConfirm(
      '是否将登录密码修改为：<br>' + $('#txtpassword2').val(),
      '确认',
      function() {
        sendData();
      }
    );
  });

  console.log('in xgdlmm.html');
  function sendData() {
    var password1 = dataService.trim($('#txtpassword').val());
    var password2 = dataService.trim($('#txtpassword2').val());
    if (password1.length > 16 || password1 < 6) {
      $('#txtpassword').focus();
      mydialog.showAlert('新密码必须填写且大于6小于16!', '信息');
      return;
    }
    if (password2.length > 16 || password2 < 6) {
      $('#txtpassword2').focus();
      mydialog.showAlert('确认密码必须填写且大于6小于16!', '信息');
      return;
    }
    if (password1 != password2) {
      $('#txtpassword2').focus();
      mydialog.showAlert('两次密码不一致!', '信息');
      return;
    }
    mydialog.showWait('修改中....', '等候');
    dataService.send(
      '/user/modifyPassword',
      { 'user.password': password1 },
      function(data) {
        mydialog.hideWait();
        mydialog.showAlert(data.message, '信息');
      }
    );
  }
});
