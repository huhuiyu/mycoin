function copyArticle(event) {
  const range = document.createRange();
  range.selectNode(document.getElementById('content'));
  const selection = window.getSelection();
  if (selection.rangeCount > 0) selection.removeAllRanges();
  selection.addRange(range);
  document.execCommand('copy');
  alert('复制微信成功！');
}

document.getElementById('copyBT').addEventListener('click', copyArticle, false);

function copyArticle1(event) {
  const range = document.createRange();
  range.selectNode(document.getElementById('content1'));
  const selection = window.getSelection();
  if (selection.rangeCount > 0) selection.removeAllRanges();
  selection.addRange(range);
  document.execCommand('copy');
  alert('复制支付宝成功！');
}

document
  .getElementById('copyBT1')
  .addEventListener('click', copyArticle1, false);

function copyArticle2(event) {
  const range = document.createRange();
  range.selectNode(document.getElementById('content2'));
  const selection = window.getSelection();
  if (selection.rangeCount > 0) selection.removeAllRanges();
  selection.addRange(range);
  document.execCommand('copy');
  alert('复制手机成功！');
}

document
  .getElementById('copyBT2')
  .addEventListener('click', copyArticle2, false);

$(function() {
  $('#btntzr').click(function() {
    location = 'jiaoyixinxiang.html';
  });
});
