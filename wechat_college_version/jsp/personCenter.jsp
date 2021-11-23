<%@ page import="com.mywechat.model.Constant" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>-WeChat网页版-</title>
    <link rel="stylesheet" type="text/css" href="../css/personCenter.css"/>
    <script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
    <script type="text/javascript">
        var oldPsw = ${sessionScope.password};

        function logout() {
            if (confirm("确认注销吗？")) {
                return true;
            }
            return false;
        }
    </script>
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
            <a href="">反馈</a>
        </li>
    </div>
</ul>
<div id="main">
    <div id="pswMain" style="display: none">
        <c:if test="${param.message==1}">
            <script>
                $("#pswMain").show();
            </script>
        </c:if>
        <h2 style="border-bottom-style: solid;color: #49a4f3;height: 50px;position: absolute;width: 95%;top: 167px;">
            账户与安全</h2>
        <div class="responsive">
            <div class="img">
                <a target="_blank" href="${sessionScope.user.headPortrait}">
                    <img src="${sessionScope.user.headPortrait}" alt="你的头像" width="300" height="200">
                </a>
                <div class="desc">${sessionScope.user.nickName}</div>
            </div>
        </div>
        <div id="pswDiv">
            <span class="pswSpan" style="position: absolute;top: 277px;left: 37%;">用户名：</span>
            <span style="position: absolute;top: 277px;left: 44%;" class="pswSpan">${sessionScope.user.nickName}</span>
            <span class="pswSpan" style="position: absolute;top: 312px;left: 37%;">账户：</span>
            <input id="loginId" style="position: absolute;top: 312px;left: 44%;background: transparent;border: none;"
                   class="pswInput" value="${sessionScope.user.loginId}" readonly="">
            <span class="pswSpan" style="position: absolute;top: 347px;left: 37%;">输入旧密码：</span>
            <span id="oldPsw" style="position: absolute;top: 347px;left: 64%;color: red;"></span>
            <input id="oldInput" style="position: absolute;top: 347px;left: 44%;" type="password" class="pswInput"
                   onblur="checkOld()">
            <span class="pswSpan" style="position: absolute;top: 382px;left: 37%;">输入新密码：</span>
            <input id="newPsw1" style="position: absolute;top: 382px;left: 44%;" type="password" class="pswInput"
                   onblur="checkPsw()">
            <span class="pswSpan" style="position: absolute;top: 417px;left: 37%;">确认新密码：</span>
            <input id="newPsw2" style="position: absolute;top: 417px;left: 44%;" type="password" class="pswInput"
                   onblur="checkPsw()">
            <span id="pswSpan" style="position: absolute;top: 438px;left: 45%;color: red;"></span>
        </div>
        <input id="pswSubmit" type="button" value="确认" onclick="return submitMsg()"/>
    </div>
    <div id="modifyMain" style="display: none">
        <c:if test="${param.message==2}">
            <script>
                $("#modifyMain").show();
            </script>
        </c:if>
        <h2 style="border-bottom-style: solid;color: #49a4f3;height: 50px;position: absolute;width: 95%;top: 167px;">
            修改资料</h2>
        <div class="responsive">
            <div class="img">
                <a target="_blank" href="${sessionScope.user.headPortrait}">
                    <img src="${sessionScope.user.headPortrait}" alt="你的头像" width="300" height="200">
                </a>
                <div class="desc">${sessionScope.user.nickName}</div>
            </div>
        </div>
        <form method="post" enctype="multipart/form-data" id="file_upload" onsubmit="return checkImg();"
              action="../PhotoServlet?operation=portrait">
            <input type="file" id="test-image-file" name="upload"
                   style="position: absolute;top: 273px;left: 21%;width: 180px;height: 191px;max-width: 180px;opacity: 0;max-height: 191px;"
                   onblur="doImg()">
            <span id="uploadSpan" style="position: absolute;top: 510px;left: 22%;font-size: 10px;color: darkslategray;">点击头像选择新的图片作为头像</span>
            <input type="submit" value="更换头像" id="aUpLoad">
        </form>
        <div id="modifyDiv">
            <span class="pswSpan" style="position: absolute;top: 277px;left: 37%;">用户名：</span>
            <span style="position: absolute;top: 277px;left: 44%;" class="pswSpan">${sessionScope.user.nickName}</span>
            <span class="pswSpan" style="position: absolute;top: 312px;left: 37%;">账户：</span>
            <span style="position: absolute;top: 312px;left: 44%;" class="pswSpan">${sessionScope.user.loginId}</span>
            <span class="pswSpan" style="position: absolute;top: 347px;left: 37%;">新用户名：</span>
            <input id="nickInput" style="position: absolute;top: 347px;left: 44%;" type="text" class="pswInput"
                   value="${sessionScope.user.nickName}" onblur="checkNick()">
            <span class="pswSpan" style="position: absolute;top: 382px;left: 37%;">个人简介：</span>
            <textarea id="signature" style="position: absolute;top: 382px;left: 44%;" class="pswInput" maxlength="54"
                      onblur="AntiSqlValid(this)">${sessionScope.user.signature}</textarea>
            <span id="modifySpan" style="position: absolute;top: 345px;left: 64%;color: red;"></span>
        </div>
        <input id="modifySubmit" type="button" value="确认" onclick="return updateMsg()"/>
    </div>
    <div id="bgMain" style="display: none">
        <c:if test="${param.message==3}">
            <script>
                $("#bgMain").show();
            </script>
        </c:if>
        <h2 style="border-bottom-style: solid;color: #49a4f3;height: 50px;position: absolute;width: 95%;top: 167px;">
            更换聊天背景</h2>
        <c:forEach items="${sessionScope.photoList}" var="photo">
            <div class="responsive">
                <div class="img1">
                    <a target="_blank" href="<%=Constant.DEFAULTBG%>${photo.photoName}">
                        <img src="<%=Constant.DEFAULTBG%>${photo.photoName}" alt="背景" width="300" height="200">
                    </a>
                    <a class="desc" href="../ModifyServlet?operation=background&photoName=${photo.photoName}"
                       method="post" onclick="return changeBg();">选择这张</a>
                </div>
            </div>
        </c:forEach>
        <div class="cp-item">
            <a href="../ModifyServlet?operation=modifyBg&pages=1" class="a1">首页</a>
            <c:if test="${sessionScope.pages!=1}">
                <a href="../ModifyServlet?operation=modifyBg&pages=${sessionScope.pages-1}" class="a1">上页</a>
            </c:if>
            <c:if test="${sessionScope.pages < sessionScope.photoPages}">
                <a href="../ModifyServlet?operation=modifyBg&pages=${sessionScope.pages+1}" class="a1">下页</a>
            </c:if>
            <a href="../ModifyServlet?operation=modifyBg&pages=${sessionScope.photoPages}" class="a1">尾页</a>
        </div>
    </div>
</div>
<script type="text/javascript" src="../js/personCenter.js"></script>
</body>
</html>