$(function() {
  var version = appConfig.version;
  console.log('flash.js...');
  $('#div-info').html('程序加载中...');

  function loadConfig() {
    dataService.send('/util/getConfig', {}, function(data) {
      console.log(data);
      if (!data.success) {
        $('#div-info').html(
          '更新错误：' + data.message + '<br/>两秒后会自动重试'
        );
        setTimeout(function() {
          loadConfig();
        }, 2000);
      } else {
        checkVer(data.dataMap.config);
      }
    });
  }

  function checkVer(config) {
    console.log('=========>', config);
    $('#div-info').html('检测版本更新...');
    $.get(config.versionUrl + '?ts=' + new Date().getTime(), {}, function(
      data
    ) {
      data = dataService.trim(data);
      console.log(data, version, data == version);
      if (data != version) {
        downloadFile(config);
      } else {
        location.href = 'index.html';
      }
    });
  }

  function downloadFile(config) {
    window.open(config.apkUrl, '_blank');
  }

  loadConfig();
});
