$(function() {
  $('#btnRtn').click(function() {
    location = 'jiaoyi.html';
  });

  //撤单按钮=======================================================================
  $('#btnchedan').click(function() {
    mydialog.showConfirm('是否撤单', '确认', function() {
      mydialog.showCustom($('#dialogCanclePayPwd'), '交易密码');
    });
  });

  $('#btnCancleOrder').click(function() {
    mydialog.hideCustom();
  });

  $('#btnToCancleOrder').click(function() {
    mydialog.hideCustom();
    sendCancleData($('#txtCanclePaypwd').val());
  });

  function sendCancleData(pwd) {
    //mydialog.showWait('撤单中.....', '等待');
    dataService.send(
      '/userBalanceRecord/cancel',
      {
        'record.brid': $('#txtRecordId').val(),
        paypwd: pwd
      },
      function(data) {
        mydialog.hideWait();
        mydialog.showAlert(data.message, '信息', function() {
          if (data.success) {
            query();
          }
        });
      }
    );
  }
  //撤单按钮=======================================================================

  //交易完成=======================================================================
  $('#btnqrjy').click(function() {
    mydialog.showConfirm('是否是否确认完成交易?', '确认', function() {
      mydialog.showCustom($('#dialogFinishPayPwd'), '交易密码');
    });
  });

  $('#btnCancleFinish').click(function() {
    mydialog.hideCustom();
  });

  $('#btnToFinish').click(function() {
    mydialog.hideCustom();
    sendFinishData($('#txtFinishPaypwd').val());
  });

  function sendFinishData(pwd) {
    dataService.send(
      '/userBalanceRecord/finish',
      {
        'record.brid': $('#txtRecordId').val(),
        paypwd: pwd
      },
      function(data) {
        mydialog.hideWait();
        mydialog.showAlert(data.message, '信息', function() {
          if (data.success) {
            query();
          }
        });
      }
    );
  }

  //确认打款=================================================================
  $('#btnmaijzl').click(function() {
    mydialog.showConfirm('是否转账', '确认', function() {
      mydialog.showCustom($('#dialogPayPwd'), '交易密码');
    });
  });

  $('#btnCanclePay').click(function() {
    mydialog.hideCustom();
  });

  $('#btnToPay').click(function() {
    mydialog.hideCustom();
    sendPayData($('#txtPaypwd').val());
  });

  function sendPayData(pwd) {
    mydialog.showWait('交易中。。。', '等待');
    dataService.send(
      '/userBalanceRecord/pay',
      {
        'record.brid': $('#txtRecordId').val(),
        paypwd: pwd
      },
      function(data) {
        mydialog.hideWait();

        mydialog.showAlert(data.message, '信息', function() {
          if (data.success) {
            query();
          }
        });
      }
    );
  }
  //确认打款=================================================================

  //复制代码==================================
  dataService.copyContent('copyBT', 'content', function() {
    mydialog.showAlert('复制微信成功！', '信息');
  });

  dataService.copyContent('copyBT1', 'content1', function() {
    mydialog.showAlert('复制支付宝成功！', '信息');
  });

  dataService.copyContent('copyBT2', 'content2', function() {
    mydialog.showAlert('复制手机成功！', '信息');
  });

  dataService.copyContent('copyBT3', 'spUsername', function() {
    mydialog.showAlert('复制姓名成功！', '信息');
  });

  //复制代码完成==================================

  //查询挂单信息=================================
  var statusList = {
    '01': '挂单',
    '02': '交易中',
    '03': '已经付款'
  };
  function query() {
    mydialog.showWait('查询中.....', '等待');
    dataService.send('/userBalanceRecord/queryUserNowOrder', {}, function(
      data
    ) {
      console.log(data);
      mydialog.hideWait();
      if (!data.success) {
        mydialog.showAlert(data.message, '信息');
        return;
      }

      if (data.dataMap.record) {
        $('#divNoOrder').hide();
        $('#divMyOrder').show();
        var record = data.dataMap.record;
        //基本信息
        $('#txtRecordId').val(record.brid);
        $('#spRecordId').html(record.brid);
        $('#spRecordTime').html(record.created);
        $('#spRecordBalance').html(record.balance);
        $('#spRecordPrice').html(record.price);
        $('#spRecordCost').html(
          dataService.fixed(record.balance * record.price, 2)
        );
        $('#spRecoreStatus').html(statusList[record.bstatus]);
        //按钮状态
        var isbuyer = record.buid > 0 && data.loginUser.uid == record.buid;
        var isseller = record.auid > 0 && data.loginUser.uid == record.auid;
        console.log('买卖家', isbuyer, isseller, record.bstatus);
        $('#btnqrjy').hide();
        $('#btnmaijzl').hide();
        $('#btnchedan').hide();
        if (record.bstatus == '01') {
          $('#btnchedan').show();
        }
        if (isbuyer && record.bstatus == '02') {
          $('#btnmaijzl').show();
        }
        if (isseller && record.bstatus == '03') {
          $('#btnqrjy').show();
        }
      } else {
        $('#divNoOrder').show();
        $('#divMyOrder').hide();
      }
      $('#divContactInfo').hide();
      if (data.dataMap.contact) {
        if (
          data.dataMap.contact.userInfo &&
          data.dataMap.contact.userInfo.wechat
        ) {
          $('#content').html(data.dataMap.contact.userInfo.wechat);
        }
        if (
          data.dataMap.contact.userInfo &&
          data.dataMap.contact.userInfo.alipay
        ) {
          $('#content1').html(data.dataMap.contact.userInfo.alipay);
        }
        if (data.dataMap.contact.phone) {
          $('#content2').html(data.dataMap.contact.phone);
        }
        if (
          data.dataMap.contact.userInfo &&
          data.dataMap.contact.userInfo.nickname
        ) {
          $('#spUsername').html(data.dataMap.contact.userInfo.nickname);
        }
        $('#divContactInfo').show();
      }
    });
  }
  query();
  //============================================
});
