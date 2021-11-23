<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>-WeChat网页版-</title>
    <script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/user.css"/>
    <style>
        input[type=text], select, textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-top: 6px;
            margin-bottom: 16px;
            resize: vertical;
        }

        input[type=submit] {
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type=submit]:hover {
            background-color: #45a049;
        }

        .container {
            border-radius: 5px;
            background-color: #f2f2f2;
            padding: 20px;
            position: absolute;
            top: 68px;
            left: 263px;
            z-index: -1;
        }
    </style>
</head>
<body style="background: url(../image/background/login.jpg); background-size: cover">
<ul>
    <li>
        <img src="${sessionScope.user.headPortrait}"
             style="width: 60px;height: 60px;position: absolute;top: 3px;left: 31px;border-radius: 45px;"
             title="这是你的头像">
        <span style="position: absolute;top: 22px;left: 102px;">${sessionScope.user.nickName}</span>
    </li>
    <div style="position: relative;left: 36%;">
        <li><a href="../ChatServlet?operation=query">聊天</a></li>
        <li><a href="../AddressBookServlet?operation=queryAll&pages=1">通讯录</a></li>
        <li><a href="../CircleFriendServlet?operation=queryAll&pages=1&commentPage=1">朋友圈</a></li>
        <li>
            <div class="dropdown">
                <button class="dropbtn">个人中心</button>
                <div class="dropdown-content">
                    <a href="personCenter.jsp?message=1">账户与安全</a>
                    <a href="personCenter.jsp?message=2">修改资料</a>
                    <a href="../ModifyServlet?operation=modifyBg&pages=1">聊天设置</a>
                </div>
            </div>
        </li>
        <li>
            <a href="../login.jsp" onclick="return logout();">注销</a>
        </li>
        <li>
            <a class="active" href="">反馈</a>
        </li>
    </div>
</ul>
<div class="container">
    <form action="##">
        <label for="cname">联系人</label>
        <input type="text" id="cname" name="cname" placeholder="请输入姓名..">

        <label for="email">邮箱</label>
        <input type="text" id="email" name="email" placeholder="请输入邮箱..">

        <label for="country">城市</label>
        <select id="country" name="country">
            <option value="australia">北京</option>
            <option value="canada">上海</option>
            <option value="usa">广州</option>
        </select>

        <label for="subject">反馈信息</label>
        <textarea id="subject" name="subject" placeholder="反馈内容.." style="height:200px"></textarea>

        <input type="submit" value="提交">
    </form>
</div>
</body>
</html>