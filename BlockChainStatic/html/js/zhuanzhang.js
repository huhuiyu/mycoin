$(function () {
  function query() {
    mydialog.showWait('查询中...', '等候');
    dataService.send('/user/queryUserInfoByKey', {}, function (data) {
      mydialog.hideWait();
      console.log(data);

      $('#sxf1').html(data.dataMap.siteBaseConfig.poundage * 100);
      $('#kc1').html(data.dataMap.siteBaseConfig.poundage * 100 + 100);
    });
  }

  query();
  $('#btntren').click(function () {
    location = 'debi.html';
  });

  $('#btnmai').click(function () {
    var id = dataService.trim($('#txtid').val());
    var number = dataService.trim($('#txtnumber').val());
    var piace = dataService.trim($('#txtpiace').val());
    var cpiace = dataService.fixed(piace, 2);

    console.log(piace);

    if (id.length == 0) {
      mydialog.showAlert('id不能为空', '信息');
      return;
    }
    if (number.length == 0) {
      mydialog.showAlert('数量不能为空', '信息');
      return;
    }
    if (!dataService.isInt(number)) {
      mydialog.showAlert('数量必须是整数', '信息');
      return;
    }
    if (piace.length == 0) {
      mydialog.showAlert('价格不能为空', '信息');
      return;
    }
    if (piace == false || cpiace != piace) {
      mydialog.showAlert('必须是数字且最多两位小数', '信息');
      return;
    }
    mydialog.showConfirm(
      '是否转账给id为' +
      id +
      '的人?' +
      '<br>数量:' +
      number +
      '<br>约定价：' +
      piace +
      '<br>总价：' +
      dataService.fixed(number * piace, 2),
      '确认',
      function () {
        mydialog.showCustom($('#dialogPayPwd'), '交易密码');
      }
    );
  });

  $('#btnCanclePay').click(function () {
    mydialog.hideCustom();
  });

  $('#btnToPay').click(function () {
    mydialog.hideCustom();
    sendData($('#txtPaypwd').val());
  });

  function sendData(pwd) {
    mydialog.showWait('交易中。。。', '等待');
    dataService.send(
      '/userBalanceRecord/sendToUser', {
        'record.auser.tokenId': $('#txtid').val(),
        'record.balance': $('#txtnumber').val(),
        'record.price': $('#txtpiace').val(),
        paypwd: pwd
      },
      function (data) {
        mydialog.hideWait();

        mydialog.showAlert(data.message, '信息', function () {
          location = 'debi.html';
        });
      }
    );
  }
});