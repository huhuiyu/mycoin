$(function() {
  console.log('test/index.js');
  $('#btnTest').click(function() {
    dataService.send('/', {}, function(data) {
      console.log(data);
    });
  });
});
