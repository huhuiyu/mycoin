$(function() {
  var emptyfn = function() {};
  var tokenKey = 'sessiontoken';

  var dataService = {
    baseurl: '/dataservice'
  };
  window.dataService = dataService;

  function saveToken(token) {
    console.log('dataService.saveToken', token);
    sessionStorage.setItem(tokenKey, token);
  }

  function loadToken() {
    var t = sessionStorage.getItem(tokenKey);
    t = t ? t : '';
    console.log('dataService.loadToken', t);
    return t;
  }

  function removeToken() {
    sessionStorage.removeItem(tokenKey);
  }

  dataService.setBaseUrl = function(url) {
    dataService.baseurl = url;
  };

  dataService.send = function(url, params, cb) {
    if (!params) {
      params = {};
    }
    params.servertoken = loadToken();

    if (!cb) {
      cb = emptyfn;
    }
    console.log('dataService.send', params);
    $.ajax({
      url: dataService.baseurl + url,
      data: params,
      type: 'POST',
      dataType: 'json'
    })
      .done(function(data) {
        if (data && data.servertoken) {
          saveToken(data.servertoken);
        }
        //登陆权限统一处理=============================================
        if (data && data.code && data.code == 1000) {
          mydialog.hideWait();
          mydialog.showAlert(data.message, '信息', function() {
            location = '/manage/index.html';
          });
          return;
        }
         //登陆权限统一处理=============================================
        cb(data);
      })
      .fail(function(xhr, status, errorThrown) {
        cb({
          success: false,
          code: 500,
          message: '处理数据发生错误:' + errorThrown
        });
      });
  };

  dataService.saveFile = function(url, file, params, cb) {
    if (!params) {
      params = {};
    }
    params.servertoken = loadToken();
    if (!cb) {
      cb = emptyfn;
    }

    var formData = new FormData();
    formData.append('file', file[0].files[0]);
    for (var p in params) {
      formData.append('' + p, params[p]);
      console.log('' + p, params[p]);
    }
    console.log('dataService.send', params);
    $.ajax({
      url: dataService.baseurl + url,
      type: 'POST',
      data: formData,
      cache: false,
      contentType: false,
      processData: false,
      success: function(data) {
        if (data && data.servertoken) {
          saveToken(data.servertoken);
        }
        cb(data);
      },
      error: function(data) {
        cb({
          success: false,
          code: 500,
          message: '处理数据发生错误:' + JSON.stringify(data)
        });
      }
    });
  };

  dataService.trim = function(str) {
    return str.replace(/(^\s*)|(\s*$)/g, '');
  };

  // dataService.sendText = function(url, params, cb) {
  //   if (!params) {
  //     params = {};
  //   }
  //   params.servertoken = loadToken();
  //   if (!cb) {
  //     cb = emptyfn;
  //   }

  //   $.ajax({
  //     url: dataService.baseurl + url,
  //     data: params,
  //     type: 'POST'
  //   })
  //     .done(function(data) {
  //       if (data && data.servertoken) {
  //         saveToken(data.servertoken);
  //       }
  //       cb({
  //         message: data,
  //         success: true,
  //         code: 200
  //       });
  //     })
  //     .fail(function(xhr, status, errorThrown) {
  //       cb({
  //         success: false,
  //         code: 500,
  //         message: '处理数据发生错误:' + errorThrown
  //       });
  //     });
  // };
});
