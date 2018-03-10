<%--
  Created by IntelliJ IDEA.
  User: junjun
  Date: 2018/2/26
  Time: 上午10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
    <form action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
        <input type="file" name="upload_file">
        <input type="submit" value="springMvc上传文件">
    </form>
    <form action="/manage/product/rich_text.do" method="post" enctype="multipart/form-data">
        <input type="file" name="upload_file">
        <input type="submit" value="富文本图片上传文件">
    </form>
</body>
</html>
