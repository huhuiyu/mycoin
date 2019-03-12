$(function() {
  function query() {
    mydialog.showWait('查询中...', '等候');
    dataService.send('/user/queryUserInfoByKey', {}, function(data) {
      mydialog.hideWait();
      console.log(data);

      $('#sxf').html(data.dataMap.siteBaseConfig.poundage * 100);
      $('#kc').html(data.dataMap.siteBaseConfig.poundage * 100 + 100);
    });
  }

  query();
  $('#btnRyn').click(function() {
    location = 'jiaoyi.html';
  });
  function resetInfo() {
    $('#inpnum').val('');
    $('#inprice').val('');
    $('#inpzprice').val('');
    $('#inpnum').trigger('focus');
  }

  resetInfo();
  $('#inpnum').keyup(changeValue);
  $('#inprice').keyup(changeValue);
  $('#inpnum').change(changeValue);
  $('#inprice').change(changeValue);

  function changeValue() {
    console.log($('#inpnum').val(), $('#inprice').val());
    //inpnum,inprice校验是不是输入的数
    if (isNaN($('#inpnum').val()) || isNaN($('#inprice').val())) {
      return;
    }
    var inpnum = parseInt($('#inpnum').val());
    var inprice = parseFloat($('#inprice').val());
    //都有填值都计算乘积的结果到
    $('#inpzprice').html(dataService.fixed(inpnum * inprice, 2));
  }

  // mydialog.showWait("请稍后", "信息");
  $('#btnguamai').click(function() {
    var inptnum = dataService.trim($('#inpnum').val());
    var inprice = dataService.trim($('#inprice').val());
    var cporice = dataService.fixed(inprice, 2);
    // console.log('============>', cporice);

    if (inptnum.length == 0) {
      mydialog.showAlert('数量必须填', '信息', function() {
        $('#inpnum').focus();
      });
      return;
    }
    if (!dataService.isInt(inptnum)) {
      mydialog.showAlert('数量必须是整数', '信息');
      return;
    }
    if (inprice.length == 0) {
      mydialog.showAlert('单价必须填写', '信息', function() {
        $('#inprice').focus();
      });
      return;
    }
    console.log(cporice,inprice);
    if (inprice == false || cporice != inprice) {
      mydialog.showAlert('必须是数不超过两位小数', '信息');
      return;
    }
    mydialog.showConfirm(
      '是否提交信息<br>数量为' +
        $('#inpnum').val() +
        '<br>单价为' +
        $('#inprice').val(),
      '确认',
      function() {
        sendData();
      }
    );
  });

  function sendData() {
    mydialog.showWait('提交中...', '等候');
    dataService.send(
      '/userBalanceRecord/addBuyBalanceRecord',
      {
        'record.balance': $('#inpnum').val(),
        'record.price': $('#inprice').val()
      },
      function(data) {
        mydialog.hideWait();
        console.log(data);
        mydialog.showAlert(data.message, '信息', function() {
          if (data.success) {
            location.href = 'jiaoyixinxiang.html';
          }
        });
      }
    );
  }
});
