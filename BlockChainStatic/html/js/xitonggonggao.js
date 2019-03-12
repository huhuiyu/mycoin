$(function() {

  $('#btnback').click(function() {
    location = 'wode.html';
  });


  var page = {
    pageNumber: 1,
    pageSize: 20
  };
  
  function query() {
    mydialog.showWait('查询中...', '等候');
    dataService.send(
      '/user/siteNotice/queryAll',
      {
        'page.pageNumber': page.pageNumber,
        'page.pageSize': page.pageSize
      },
      function(data) {
        mydialog.hideWait();
        console.log(data);
        if (!data.success) {
          mydialog.showAlert(data.message, '信息');
          return;
        }
       
        $('#divData').hide();
        $('#divDetail').hide();
        $('#divData').show();
        $.each(data.dataMap.list, function(i, v) {
          var div = $(document.createElement('div'));
          div.attr('class', 'bianxian4 bj');

          var divTime = $(document.createElement('div'));
          divTime.attr('class', 'text-center');
          divTime.append(v.lastupdate);
          div.append(divTime);

          var divTitle = $(document.createElement('div'));
          divTitle.attr('class', 'text-left  zt');
          divTitle.append(v.title + '<br>');
          var spContent = $(document.createElement('span'));
          spContent.append('[点击查看详细...]');
          spContent.attr('class', 'span-hover');
          spContent.click(function() {
            $('#divData').hide();
            $('#divDetail').hide();
            $('#divContentDetail').html(v.content);
            $('#divDetail').show();
          });
          divTitle.append(spContent);
          div.append(divTitle);

      
         

         // div.append(divContent);

          $('#divData').append(div);
        });

        
      }
      
    );
  }
  $('#divDetail .text-center span').click(function() {
    $('#divData').hide();
    $('#divDetail').hide();
    $('#divData').show();
  });
  query();
});
