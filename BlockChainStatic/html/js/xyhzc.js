// var test = {
//   node: null,
//   count: 60,
//   start: function() {
//     if (this.count > 0) {
//       this.node.innerHTML = this.count-- + '秒后重新获取';
//       var _this = this;
//       setTimeout(function() {
//         _this.start();
//       }, 1000);
//     } else {
//       this.node.removeAttribute('disabled');
//       this.node.innerHTML = '重新获取';

//       //60秒读完，变回开始背景颜色，在这里添加。
//       this.count = 60;
//     }
//   }, //初始化
//   init: function(node) {
//     this.node = node;
//     this.node.setAttribute('disabled', true);
//     this.start();
//   }
// };
// var btn = document.getElementById('btn');
// btn.onclick = function() {
//   test.init(btn);
// };

$(function() {
  $('#imgCode').attr('src', dataService.getImageCodeUrl());

  $('#imgCode').click(function() {
    $('#imgCode').attr('src', dataService.getImageCodeUrl());
  });

  $('#btnback').click(function() {
    location = 'index.html';
  });

  $('#btnzhuche').click(function() {
    var phone = dataService.trim($('#txtPhone').val());
    var password = dataService.trim($('#txtPassword').val());
    var zfpassword = dataService.trim($('#txtzfPassword').val());
    var jdid = dataService.trim($('#txtjdID').val());
    var tpcode = dataService.trim($('#txtTpyzm').val());
    var code = dataService.trim($('#txtCode').val());
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
    if (zfpassword.length == 0) {
      $('#txtzfPassword').focus();
      mydialog.showAlert('支付密码必须填写', '信息');
      return;
    }

    if (jdid.length != 7) {
      $('#txtjdID').focus();
      mydialog.showAlert('邀请码必须填写却是七位数', '信息');
      return;
    }

    // if (tpcode.length == 0) {
    //   $('#txtTpyzm').focus();
    //   mydialog.showAlert('图片校验码必须填写', '信息');
    //   return;
    // }

    if (code.length == 0) {
      $('#txtCode').focus();
      mydialog.showAlert('验证码必须填写', '信息');
      return;
    }

    mydialog.showWait('注册中...', '等候');
    dataService.send(
      '/user/add',
      {
        'user.phone': phone,
        'user.password': password,
        'user.paypwd': zfpassword,
        'user.blockId': jdid,
        phoneCode: code
      },
      function(data) {
        mydialog.hideWait();
        if (data.success) {
          mydialog.showAlert(data.message, '信息', function() {
            location = 'index.html';
          });

          return;
        }
        mydialog.showAlert(data.message, '信息');
      }
    );
  });

  $('#btnSend').click(function() {
    mydialog.showWait('发送校验码中，请稍候...', '发送');
    dataService.send(
      '/util/sendPhoneCode',
      {
        imageTokenCode: $('#txtTpyzm').val(),
        'phoneCode.phone': $('#txtPhone').val()
      },
      function(data) {
        mydialog.hideWait();
        mydialog.showAlert(data.message, '信息');
        $('#imgCode').attr('src', dataService.getImageCodeUrl());
      }
    );
  });
});
