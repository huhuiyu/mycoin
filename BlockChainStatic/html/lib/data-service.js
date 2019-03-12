$(function () {
  var emptyfn = function () {};
  var tokenKey = 'sessiontoken';

  var dataService = {
    baseurl: '/dataservice'
  };

  window.dataService = dataService;

  function saveToken(token) {
    console.log('dataService.saveToken', token);
    localStorage.setItem(tokenKey, token);
  }

  function loadToken() {
    var t = localStorage.getItem(tokenKey);
    t = t ? t : '';
    console.log('dataService.loadToken', t);
    return t;
  }

  function removeToken() {
    localStorage.removeItem(tokenKey);
  }

  dataService.setBaseUrl = function (url) {
    dataService.baseurl = url;
  };

  dataService.getImageCodeUrl = function () {
    return (
      dataService.baseurl +
      '/util/validate.jpg?servertoken=' +
      loadToken() +
      '&ts=' +
      new Date().getTime()
    );
  };

  dataService.send = function (url, params, cb) {
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
      .done(function (data) {
        if (data && data.servertoken) {
          saveToken(data.servertoken);
        }
        //登陆权限统一处理=============================================
        var indexpages = ['/android_asset/index.html', '/index.html', '/', ''];
        indexpages.indexOf(location.pathname);

        console.log(location.pathname);
        if (data && data.code && data.code == 2000) {
          mydialog.hideWait();
          mydialog.showWait('系统维护中...', '信息');
          setTimeout(function () {
            if (indexpages.indexOf(location.pathname) == -1) {
              location = 'index.html';
            } else {
              mydialog.hideWait();
              cb(data);
            }
          }, 5000);
          return;
        }
        if (
          data &&
          data.code &&
          (data.code == 1000 || data.code == 1001) &&
          indexpages.indexOf(location.pathname) == -1
        ) {
          mydialog.hideWait();
          mydialog.showAlert(data.message, '信息', function () {
            location = 'index.html';
          });
          return;
        }
        //登陆权限统一处理=============================================
        cb(data);
      })
      .fail(function (xhr, status, errorThrown) {
        cb({
          success: false,
          code: 500,
          message: '处理数据发生错误:' + errorThrown
        });
      });
  };

  dataService.saveFile = function (url, file, params, cb) {
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
      success: function (data) {
        if (data && data.servertoken) {
          saveToken(data.servertoken);
        }
        //登陆权限统一处理=============================================
        if (data && data.code && data.code == 2000) {
          mydialog.hideWait();
          mydialog.showWait('系统维护中...', '信息');
          setTimeout(function () {
            if (indexpages.indexOf(location.pathname) == -1) {
              location = 'index.html';
            } else {
              mydialog.hideWait();
              cb(data);
            }
          }, 5000);
          return;
        }

        if (data && data.code && (data.code == 1000 || data.code == 1001)) {
          mydialog.hideWait();
          mydialog.showAlert(data.message, '信息', function () {
            location = 'index.html';
          });
          return;
        }
        //登陆权限统一处理=============================================
        cb(data);
      },
      error: function (data) {
        cb({
          success: false,
          code: 500,
          message: '处理数据发生错误:' + JSON.stringify(data)
        });
      }
    });
  };

  dataService.trim = function (str) {
    if (str) {
      str = str + '';
      return str.replace(/(^\s*)|(\s*$)/g, '');
    }
    return '';
  };

  dataService.trimZero = function (str) {
    if (str) {
      str = str + '';
      str = str.replace(/([0]*$)/g, '');
      //console.log('=====>', str.substr(-1));
      if (str.substr(-1) == '.') {
        str = str.substr(0, str.length - 1);
      }
      return str;
    }
    return '';
  };

  dataService.copyContent = function (btnCopy, eleContent, cb, precb) {
    if (!precb) {
      precb = emptyfn;
    }
    if (!cb) {
      cb = emptyfn;
    }
    document.getElementById(btnCopy).addEventListener(
      'click',
      function (event) {
        precb();
        const range = document.createRange();
        range.selectNode(document.getElementById(eleContent));
        const selection = window.getSelection();
        if (selection.rangeCount > 0) {
          selection.removeAllRanges();
        }
        selection.addRange(range);
        document.execCommand('copy');
        cb();
      },
      false
    );
  };

  dataService.fixed = function (num, len) {
    if (!num || isNaN(num)) {
      return false;
    }
    len = len ? len : 1;
    var inum = parseInt(num);
    var fnum = parseFloat(num);
    if (inum == fnum) {
      return inum;
    }
    num = dataService.trimZero(
      parseFloat(dataService.trim(num)).toFixed(len) + ''
    );
    return num;
  };

  dataService.isInt = function (num) {
    if (!num || isNaN(num)) {
      return false;
    }

    var inum = parseInt(num);
    var fnum = parseFloat(num);
    return inum == fnum;
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