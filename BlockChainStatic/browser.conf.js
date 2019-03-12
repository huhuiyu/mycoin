const browser = '"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe" http://127.0.0.1:40000 http://127.0.0.1:40000/app/ http://127.0.0.1:40000/manage/';
var exec = require('child_process').exec;
exec(browser, function(error, std, err) {
  console.log(error, std, err);
});
