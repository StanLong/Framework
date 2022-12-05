**1.原生js ajax请求有几个步骤？分别是什么**

```js
//创建 XMLHttpRequest 对象
var ajax = new XMLHttpRequest();
//规定请求的类型、URL 以及是否异步处理请求。
ajax.open('GET',url,true);
//发送信息至服务器时内容编码类型
ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded"); 
//发送请求
ajax.send(null);  
//接受服务器响应数据
ajax.onreadystatechange = function () {
    if (obj.readyState == 4 && (obj.status == 200 || obj.status == 304)) { 
    }
};
```

**2.get和post请求的区别**

- get通过url传递参数,post设置请求头  规定请求数据类型
- post比get安全,因为post参数在请求体中。get参数在url上面
- get传输速度比post快 根据传参决定的。post通过请求体传参，后台通过数据流接收。速度稍微慢一些。而get通过url传参可以直接获取
- post传输文件大理论没有限制  get传输文件小大概7-8k ie4k左右
- get获取数据	post上传数据

