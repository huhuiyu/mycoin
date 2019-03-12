$(function() {
  var UMtxtlr1 = UM.getEditor('txtlr1');
  //var UMtxtlr2 = UM.getEditor('txtlr2');
  var UMtxtlr = UM.getEditor('txtlr');

  //===========================================================
  $('#btnTc').click(function() {
    mydialog.showWait('安全退出中，请稍候...', '退出');
    dataService.send('/manage/admin/logout', {}, function(data) {
      mydialog.hideWait();
      if (data.success) {
        mydialog.showAlert(data.message, '退出', function() {
          location = 'index.html';
        });
        return;
      }
      mydialog.showAlert(data.message, '信息');
    });
  });
  //=================================================================

  $('#btnspantc').click(function() {
    mydialog.hideCustom();
  });

  $('#btnspantc1').click(function() {
    mydialog.hideCustom();
  });
  $('#btnspanqd').click(function() {
    if ($('#txtbt1').val().length > 20) {
      mydialog.showAlert('标题不能超过长度,请重新输入', '错误');
      return;
    }
    var username = dataService.trim($('#txtbt1').val());

    if (username.length == 0) {
      $('#txtbt1').focus();
      mydialog.showAlert('标题不能为空', '信息');
      return;
    }

    //后台处理
    mydialog.showWait('修改中...', '等待');
    dataService.send(
      '	/manage/siteNotice/update',
      {
        'siteNotice.title': $('#txtbt1').val(),
        'siteNotice.content': UMtxtlr1.getContent(),
        'siteNotice.intop': $('#selIsEnable1').val(),
        'siteNotice.isEnable': $('#selIsEnable2').val(),
        'siteNotice.snid': $('#txtSbUid').val()
      },
      function(data) {
        mydialog.hideWait();
        mydialog.showAlert(data.message, '信息', function() {
          mydialog.hideCustom();
          query();
        });
      }
    );
  });

  $('#spantc').click(function() {
    mydialog.hideCustom();
  });

  $('#spanqd').click(function() {
    //校验
    if ($('#txtbt').val().length > 20) {
      mydialog.showAlert('标题不能超过长度,请重新输入', '错误');
      return;
    }
    var username = dataService.trim($('#txtbt').val());
    if (username.length == 0) {
      $('#txtlr').focus();
      mydialog.showAlert('标题必须填写', '信息');
      return;
    }

    //后台处理
    mydialog.showWait('建新中...', '等待');
    dataService.send(
      '/manage/siteNotice/save',
      {
        'siteNotice.title': $('#txtbt').val(),
        'siteNotice.content': UMtxtlr.getContent()
      },
      function(data) {
        mydialog.hideWait();
        mydialog.showAlert(data.message, '信息', function() {
          if (data.success) {
            mydialog.hideCustom();
            query();
          }
        });
      }
    );
  });
  $('#btnXj').click(function() {
    $('#txtbt').val('');
    UMtxtlr.setContent('');
    //$('#txtlr').val('');
    mydialog.showCustom($('#ggjx'), '添加', function() {});
  });

  var page = {
    pageNumber: 1,
    pageSize: 12
  };

  $('#btnGo').click(function() {
    page.pageNumber = $('#txtGoPage').val();
    query();
  });

  $('#btnNext').click(function() {
    //下一页
    page.pageNumber = page.pageNumber + 1;
    if (page.pageCount < 1) {
      page.pageNumber = 1;
    } else if (page.pageNumber > page.pageCount) {
      page.pageNumber = page.pageCount;
      return;
    }
    query();
  });

  $('#btnPre').click(function() {
    //上一页
    page.pageNumber = page.pageNumber - 1;
    if (page.pageNumber < 1) {
      page.pageNumber = 1;
      return;
    }
    query();
  });

  function query() {
    mydialog.showWait('查询数据中', '等待');
    dataService.send(
      '/manage/siteNotice/queryAll',
      {
        'page.pageNumber': page.pageNumber,
        'page.pageSize': page.pageSize
      },
      function(data) {
        mydialog.hideWait();
        console.log(data);
        if (!data.success) {
          mydialog.showAlert(data.message, '');
          return;
        }
        //============================================================
        $('#spUser').html(data.loginUser.username);
        //==================================================
        page = data.dataMap.page;
        console.log('分页信息：', page);
        $('#spPageInfo').html(
          '总记录/当前页/分页总数' +
            page.total +
            '/' +
            page.pageNumber +
            '/' +
            page.pageCount
        );

        $('#tbData').html('');
        $.each(data.dataMap.list, function(i, v) {
          var tr = $(document.createElement('tr'));
          var td;

          td = $(document.createElement('td'));
          td.append(v.title);
          tr.append(td);

          td = $(document.createElement('td'));
          var divCon = $(document.createElement('div'));

          divCon.append('查看内容...');
          divCon.attr('class', 'div-zhiduan');
          divCon.click(function() {
            xxxx(v.content);
          });

          td.append(divCon);

          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.intop == 'y' ? '已置顶' : 'NO置顶');
          tr.append(td);

          td = $(document.createElement('td'));
          td.append(v.isEnable == 'y' ? '显示' : '隐藏');
          tr.append(td);
          td = $(document.createElement('td'));
          td.append(v.lastupdate);
          tr.append(td);

          td = $(document.createElement('td'));
          var span = $(document.createElement('span'));
          var spancon = $(document.createElement('span'));
          spancon.attr('class', 'iconfont');
          spancon.append('&#xe6e5;');
          span.append(spancon);
          span.append('修改');
          span.attr('class', 'btn btn-default');
          span.click(function() {
            ggxg(v);
          });

          td.append(span);
          tr.append(td);

          $('#tbData').append(tr);
        });
      }
    );
  }

  function xxxx(xx) {
    $('#txtlr2').html(xx);
    mydialog.showCustom($('#xxxx'), '详细信息', function() {});
  }

  function ggxg(sq) {
    console.log(sq);
    $('#txtSbUid').val(sq.snid);
    $('#txtbt1').val(sq.title);
    //$('#txtlr1').val(sq.content);

    UMtxtlr1.setContent(sq.content);

    $('#selIsEnable1').val(sq.intop);
    $('#selIsEnable2').val(sq.isEnable);

    mydialog.showCustom($('#ggxg'), '修改', function() {}, function() {});
  }

  query();
});
