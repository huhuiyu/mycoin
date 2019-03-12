$(function() {
  $('#btnqianbao').click(function() {
    location = 'qianbao.html';
  });

  $('#btnjiaoyi').click(function() {
    location = 'jiaoyi.html';
  });

  $('#btnwode').click(function() {
    location = 'wode.html';
  });

  $('#btnxinxiang').click(function() {
    location = 'jiaoyixinxiang.html';
  });
  $('#btnguamai').click(function() {
    location = 'guamaidan.html';
  });
  var page = { pageSize: 6, pageNumber: 1 };

  $('#btnMore').click(function() {
    page.pageNumber = page.pageNumber + 1;
    if (page.pageNumber > page.pageCount) {
      page.pageNumber = page.pageCount;
      mydialog.showAlert('已经全部显示', '信息');
      return;
    }
    query();
  });

  $('#btnRefresh').click(function() {
    page.pageNumber = 1;
    $('#div-sellinfos').html('');
    query();
  });

  function query() {
    mydialog.showWait('查询中...', '等候');
    dataService.send(
      '/userBalanceRecord/queryBuys',
      { 'page.pageSize': page.pageSize, 'page.pageNumber': page.pageNumber },
      function(data) {
        mydialog.hideWait();
        console.log(data);
        if (data.code == 1000) {
          mydialog.showAlert(data.message, '信息', function() {});
          return;
        }
        page = data.dataMap.page; //更新为服务端分页信息
        if (page.total == 0 || page.pageCount == 1) {
          $('#divPage').hide();
        } else {
          $('#divPage').show();
        }
        $('#zgjg').html(data.dataMap.siteBaseConfig.dayMaxPrice);
        $('#zdjg').html(data.dataMap.siteBaseConfig.dayPrice);
        $('#jyfl').html(data.dataMap.siteBaseConfig.poundage * 100);
        //$('#div-sellinfos').html('');
        $.each(data.dataMap.list, function(i, v) {
          // $("#ru").html(v.buser.blockId);
          // $("#tu").html(v.balance);
          // $("#ty").html(v.price);
          // $("#ti").html(v.balance * v.price);

          var div = $(document.createElement('div'));
          div.attr('class', 'div-min');
          var divid = $(document.createElement('div'));
          divid.append('ID:' + v.buser.blockId);
          div.append(divid);
          var divinfo = $(document.createElement('div'));
          divinfo.append(
            '数量:' +
              v.balance +
              '&nbsp;价格:' +
              v.price +
              '&nbsp;总价:￥' +
              dataService.fixed(v.balance * v.price, 2)
          );
          div.append(divinfo);
          var divSell = $(document.createElement('div'));
          divSell.attr('class', 'btn-mai');
          divSell.append('卖出');
          divSell.click(function() {
            console.log(v);
            maichu(v);
          });
          div.append(divSell);

          $('#div-sellinfos').append(div);
          // var tr = $(document.createElement("tr"));
          // var td;

          // td = $(document.createElement("td"));
          // td.append(v.buser.blockId);
          // tr.append(td);

          // td = $(document.createElement("td"));
          // td.append(v.balance);
          // tr.append(td);

          // td = $(document.createElement("td"));
          // td.append(v.price);
          // tr.append(td);

          // td = $(document.createElement("td"));
          // td.append(v.balance * v.price);
          // tr.append(td);

          // var span;
          // td = $(document.createElement("td"));
          // td.attr("class", "text-center");

          // span = $(document.createElement("span"));
          // span.append("卖出");
          // span.attr("class", "btn btn-primary");
          // span.click(function() {
          //   maichu(v);
          // });
          // td.append(span);
          // tr.append(td);
          // $("#Data").append(tr);
        });
      }
    );
  }
  function maichu(v) {
    mydialog.showConfirm(
      '是否卖出给ID为' +
        v.buser.blockId +
        '的人?<br>数量：' +
        v.balance +
        '<br>价格:' +
        v.price +
        '<br>总价：' +
        v.balance * v.price,
      '确认',
      function() {
        mydialog.showWait('售卖中。。。', '等待');
        dataService.send(
          '/userBalanceRecord/sell',
          { 'record.brid': v.brid },
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
    );
  }
  query();
});
