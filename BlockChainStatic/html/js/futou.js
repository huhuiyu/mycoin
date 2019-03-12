$(function() {
  $('#btnyr').click(function() {
    location = 'debi.html';
  });

  function query() {
    mydialog.showWait('查询中...', '等候');
    dataService.send('/user/queryUserInfoByKey', {}, function(data) {
      mydialog.hideWait();
      console.log(data);
      $('#beilu').html(data.dataMap.siteBaseConfig.exchange);
      $('#beilu1').html(data.dataMap.siteBaseConfig.exchange);
      futouInfo();
    });
  }
  query();

  function futouInfo() {
    $('#number1').val('');
    $('#daifanshu').html('');
    $('#number1').trigger('focus');
  }

  $('#number1').keyup(changeValue);
  $('#number1').change(changeValue);

  function changeValue() {
    console.log($('#number1').val(), $('#beilu').val());

    if (isNaN($('#number1').val()) || isNaN($('#beilu').val())) {
      return;
    }


    var number1 = parseInt($('#number1').val());
    var beilu = parseFloat($('#beilu').html());
    //都有填值都计算乘积的结果到
    $('#daifanshu').html(dataService.fixed(number1 * beilu, 2));
  }

  $('#btnft').click(function() {
    var number1 = dataService.trim($('#number1').val());
    var beilu = dataService.trim($('#beilu').val());
    var daifanshu = dataService.fixed(number1, 2);

    if (number1.length == 0) {
      mydialog.showAlert('数量必须填', '信息', function() {
        $('#number1').focus();
      });
      return;
    }

    if (!dataService.isInt(number1)) {
      mydialog.showAlert('数量必须是整数', '信息');
      return;
    }

    mydialog.showConfirm(
      '是否提交信息<br>复投数量为' +
        $('#number1').val() +
        '<br>倍率为' +
        $('#beilu').html()+'<br>待返fxcoin数量为'+
        $('#daifanshu').html(),
      '确认',
      function() {
        mydialog.showCustom($('#dialogPayPwd'), '交易密码');
      }
    );
  });

  $('#btnCanclePay').click(function() {
    mydialog.hideCustom();
  });

  $('#btnToPay').click(function() {
    mydialog.hideCustom();
    sendData($('#txtPaypwd').val());
  });

  function sendData(pwd) {
    mydialog.showWait('提交中...', '等候');
    dataService.send(
      '/userBalanceRecord/recast',
      {
        'record.balance': $('#number1').val(),
        paypwd: pwd
      },
      function(data) {
        mydialog.hideWait();
        console.log(data);
        mydialog.showAlert(data.message, '信息', function() {
          if (data.success) {
            location.href = 'debi.html';
          }
        });
      }
    );
  }
});
