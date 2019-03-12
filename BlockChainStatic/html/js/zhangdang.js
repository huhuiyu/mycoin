$(function() {
  $('#btnback').click(function() {
    location = 'qianbao.html';
  });
  var page = { pageSize: 6, pageNumber: 1 };
  var types = {
    '10': '待释放币发放',
    '11': '交易购买',
    '12': '交易卖出',
    '13': '点对点转入',
    '14': '点对点转出',
    '15': '开通会员扣币',
    '16': '交易奖励待释放币',
    '17': '直接发放待释放币',
    '18': '直接发放币',
    '19':'复投扣款',
    '20':'复投获取待释放币',
    '21':'复投上级奖励'

  };

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
    $('#divData').html('');
    query();
  });

  function query() {
    mydialog.showWait('查询中...', '等候');
    dataService.send(
      '/user/queryBalanceRecord',
      { 'page.pageSize': page.pageSize, 'page.pageNumber': page.pageNumber },
      function(data) {
        mydialog.hideWait();
        console.log(data);
        if (!data.success) {
          mydialog.showAlert(data.message, '信息');
          return;
        }
        page = data.dataMap.page; //更新为服务端分页信息
        if (page.total == 0 || page.pageCount == 1) {
          $('#divPage').hide();
        } else {
          $('#divPage').show();
        }
        $.each(data.dataMap.list, function(i, v) {
          var divflewdi = $(document.createElement('div'));
          divflewdi.attr('class', 'flewdi');

          var divfliud = $(document.createElement('div'));
          divfliud.attr('class', 'fliud-div');

          var div50l = $(document.createElement('div'));
          div50l.attr('class', 'div40 pd10  font');
          div50l.append(v.bdir == 1 ? '增加' : '减少');
          divfliud.append(div50l);

          var div50r = $(document.createElement('div'));
          div50r.attr('class', 'div60 pd10 font');
          div50r.append('数量：' + v.balance);
          divfliud.append(div50r);
          divflewdi.append(divfliud);

          divfliud = $(document.createElement('div'));
          divfliud.attr('class', 'fliud-div');

          div50l = $(document.createElement('div'));
          div50l.attr('class', 'div40 pd10 font');
          div50l.append('时间');
          divfliud.append(div50l);

          div50r = $(document.createElement('div'));
          div50r.attr('class', 'div60 pd10 font');
          div50r.append(v.created);
          divfliud.append(div50r);
          divflewdi.append(divfliud);

          divfliud = $(document.createElement('div'));
          divfliud.attr('class', 'fliud-div');

          div50l = $(document.createElement('div'));
          div50l.attr('class', 'div40 pd10 font');
          div50l.append('类型');
          divfliud.append(div50l);

          div50r = $(document.createElement('div'));
          div50r.attr('class', 'div60 pd10 font');
          div50r.append(types[v.btype]);
          divfliud.append(div50r);
          divflewdi.append(divfliud);

          $('#divData').append(divflewdi);
        });
      }
    );
  }

  query();
});
