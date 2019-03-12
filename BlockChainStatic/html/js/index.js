$(function () {
  console.log('index.js');

  // function query() {
  //   dataService.send('/user/queryUserInfoByKey', {}, function(data) {
  //     if (!data.success) {
  //       $('#divWait').hide();
  //       $('#divLogin').show();
  //     } else {
  //       setTimeout(function() {
  //         location = 'qianbao.html';
  //       }, 2);
  //     }
  //   });
  // }

  // query();

  $('#divWait').hide();
  $('#divLogin').show();

  $('#btnkf').click(function () {
    location = 'zaixiankefu.html';
  });

  $('#btnFoundPwd').click(function () {
    location = 'findpwd.html';
  });

  $('#btnReg').click(function () {
    location = 'xyhzc.html';
  });

  $('#btnLogin').click(function () {
    var phone = dataService.trim($('#txtPhone').val());
    var password = dataService.trim($('#txtPassword').val());
    if (phone.length == 0) {
      $('#txtPhone').focus();
      mydialog.showAlert('电话必须填写', '信息');
      return;
    }
    if (password.length == 0) {
      $('#txtPassword').focus();
      mydialog.showAlert('密码必须填写', '信息');
      return;
    }
    mydialog.showWait('登陆中。。。', '等待');
    dataService.send(
      '/user/login', {
        'user.phone': phone,
        'user.password': password
      },
      function (data) {
        mydialog.hideWait();
        if (data.success) {
          location = 'qianbao.html';
          return;
        }
        mydialog.showAlert(data.message, '信息');
      }
    );
  });

  function resetForm() {
    $('#txtPhone').val('');
    $('#txtPassword').val('');
    $('#txtPhone').focus();
  }

  resetForm();
});