// https://qiita.com/ShinyaKato/items/64b6726c361f5377b0f3

var xhr = new XMLHttpRequest();
xhr.onreadystatechange = function() {
  if (xhr.readyState == 4 && xhr.status == 200) {
    console.log(xhr.responseText);
  }
};
xhr.open("GET", "https://raw.githubusercontent.com/Kashio907/codesandbox-test/main/test", true);
// xhr.open("GET", "https://raw.githubusercontent.com/Kashio907/codesandbox-test/main/src/index.js", true);
// xhr.open("GET", "https://raw.githubusercontent.com/Kashio907/codesandbox-test/blob/main/src/index.js", true);
xhr.send();
