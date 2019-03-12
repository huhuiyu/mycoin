$(function () {
  //隐藏对话框退出按钮
  $('#spantc').click(function () {
    mydialog.hideCustom();
  });
  //隐藏对话框确定按钮
  $('#spanqd').click(function () {
    //校验
    if ($('#txtxpwd').val().length > 12) {
      mydialog.showAlert('密码不能超过长度,请重新输入', '错误');
      return;
    }
    var username = dataService.trim($('#txtjpwd').val());
    var password = dataService.trim($('#txtxpwd').val());
    var password1 = dataService.trim($('#txtpwd').val());
    if (username.length == 0) {
      $('#txtjpwd').focus();
      mydialog.showAlert('原密码必须填写', '信息');
      return;
    }
    if (password.length == 0) {
      $('#txtxpwd').focus();
      mydialog.showAlert('请填写新密码', '信息');
      return;
    }
    if (password1.length == 0) {
      $('#txtpwd').focus();
      mydialog.showAlert('请填写确认新密码', '信息');
      return;
    }
    if (password != password1) {
      mydialog.showAlert('两次密码不一样请从新填写', '信息');
      return;
    }
    //后台处理
    mydialog.showWait('修改中...', '等待');
    dataService.send(
      '/manage/user/updatePassword', {
        'admin.password': password
      },
      function (data) {
        mydialog.hideWait();
        mydialog.showAlert(data.message, '信息', function () {
          mydialog.hideCustom();
        });
      }
    );
  });
  //调用对话框按钮
  $('#btnGzmm').click(function () {
    $('#txtjpwd').val('');
    $('#txtxpwd').val('');
    $('#txtpwd').val('');
    mydialog.showCustom($('#xgmm'), '修改管理员密码', function () {});
  });

  $('#btnbaocun1').click(function () {
    mydialog.showConfirm('<span class="text-danger">是否确认修改?</span>', '确认', function () {
      mydialog.showWait('修改数据处理中...', '等待');
      dataService.send(
        '/manage/config/modifySiteConfig', {
          'siteConfig.poundage': $('#txtPoundage').val(),
          'siteConfig.payback': $('#txtpayback').val(),
          'siteConfig.freezeHour': $('#txtfreezeHour').val(),
          'siteConfig.vip': $('#txtvip').val(),
          'siteConfig.dayPrice': $('#txtdayPrice').val(),
          'siteConfig.dayMaxPrice': $('#txtdayMaxPrice').val(),
          'siteConfig.parentPayReward': $('#txtparentPayReward').val(),
          'siteConfig.parentReward': $('#txtparentReward').val(),
          'siteConfig.parentReward2': $('#txtparentReward2').val(),
          'siteConfig.parentReward3': $('#txtparentReward3').val(),
          'siteConfig.userReward': $('#txtuserReward').val(),
          'siteConfig.shareUrl': $('#txtshareUrl').val(),
          'siteConfig.closed': $('#txtclosed').val(),
          'siteConfig.exchange': $('#txtexchange').val(),
          'siteConfig.exchangeReward': $('#txtexchangeReward').val(),
          'siteConfig.exchangeReward2': $('#txtexchangeReward2').val(),
          'siteConfig.exchangeReward3': $('#txtexchangeReward3').val(),
          'siteConfig.apkUrl': $('#txtapkUrl').val(),
          'siteConfig.versionUrl': $('#txtversionUrl').val()
        },
        function (data) {
          mydialog.hideWait();
          mydialog.showAlert(data.message, '信息', function () {
            query();
          });
        }
      );
    });
  });

  $('#btnbaocun2').click(function () {
    mydialog.showConfirm('是否确认修改?', '确认', function () {
      mydialog.showWait('修改数据处理中...', '等待');
      dataService.send(
        '/manage/config/modifyPhoneCheck', {
          'phoneCheck.appCode': $('#txtappCode').val(),
          'phoneCheck.url': $('#txturl').val()
        },
        function (data) {
          mydialog.hideWait();
          mydialog.showAlert(data.message, '信息', function () {
            query();
          });
        }
      );
    });
  });

  $('#btnbaocun3').click(function () {
    mydialog.showConfirm('是否确认修改?', '确认', function () {
      mydialog.showWait('修改数据处理中...', '等待');
      dataService.send(
        '/manage/config/modifySms', {
          'smsBean.product': $('#txtproduct').val(),
          'smsBean.domain': $('#txtdomain').val(),
          'smsBean.region1': $('#txtregion1').val(),
          'smsBean.region2': $('#txtregion2').val(),
          'smsBean.accesskeyid': $('#txtaccesskeyid').val(),
          'smsBean.accesskeysecret': $('#txtaccesskeysecret').val(),
          'smsBean.sign': $('#txtsign').val(),
          'smsBean.template.validateCode': $('#txtvalidateCode').val()
        },
        function (data) {
          mydialog.hideWait();
          mydialog.showAlert(data.message, '信息', function () {
            query();
          });
        }
      );
    });
  });

  //退出管理界面
  $('#btnTc').click(function () {
    mydialog.showWait('安全退出中，请稍候...', '退出');
    dataService.send('/manage/admin/logout', {}, function (data) {
      mydialog.hideWait();
      if (data.success) {
        mydialog.showAlert(data.message, '退出', function () {
          location = 'index.html';
        });
        return;
      }
      mydialog.showAlert(data.message, '信息');
    });
  });

  //获取后台数据
  function query() {
    mydialog.showWait('查询数据中', '等待');
    dataService.send('/manage/config/loadConfig', {}, function (data) {
      mydialog.hideWait();
      console.log(data);
      if (!data.success) {
        mydialog.showAlert(data.message, '');
        return;
      }
      //============================================================
      $('#spUser').html(data.loginUser.username);
      //===================================================
      //网站配置
      $('#txtPoundage').val(data.dataMap.siteConfig.poundage);
      $('#txtpayback').val(data.dataMap.siteConfig.payback);
      $('#txtfreezeHour').val(data.dataMap.siteConfig.freezeHour);
      $('#txtvip').val(data.dataMap.siteConfig.vip);
      $('#txtdayPrice').val(data.dataMap.siteConfig.dayPrice);
      $('#txtdayMaxPrice').val(data.dataMap.siteConfig.dayMaxPrice);
      $('#txtparentReward').val(data.dataMap.siteConfig.parentReward);
      $('#txtparentReward2').val(data.dataMap.siteConfig.parentReward2);
      $('#txtparentReward3').val(data.dataMap.siteConfig.parentReward3);
      $('#txtparentPayReward').val(data.dataMap.siteConfig.parentPayReward);
      $('#txtuserReward').val(data.dataMap.siteConfig.userReward);
      $('#txtshareUrl').val(data.dataMap.siteConfig.shareUrl);
      $('#txtexchange').val(data.dataMap.siteConfig.exchange);
      $('#txtexchangeReward').val(data.dataMap.siteConfig.exchangeReward);
      $('#txtexchangeReward2').val(data.dataMap.siteConfig.exchangeReward2);
      $('#txtexchangeReward3').val(data.dataMap.siteConfig.exchangeReward3);
      $('#txtapkUrl').val(data.dataMap.siteConfig.apkUrl);
      $('#txtversionUrl').val(data.dataMap.siteConfig.versionUrl);


      //电话认证
      $('#txtappCode').val(data.dataMap.phoneCheck.appCode);
      $('#txturl').val(data.dataMap.phoneCheck.url);

      //短信验证
      $('#txtaccesskeyid').val(data.dataMap.sms.accesskeyid);
      $('#txtaccesskeysecret').val(data.dataMap.sms.accesskeysecret);
      $('#txtdomain').val(data.dataMap.sms.domain);
      $('#txtproduct').val(data.dataMap.sms.product);
      $('#txtregion1').val(data.dataMap.sms.region1);
      $('#txtregion2').val(data.dataMap.sms.region2);
      $('#txtsign').val(data.dataMap.sms.sign);
      $('#txtvalidateCode').val(data.dataMap.sms.template.validateCode);
    });
  }

  query();
});