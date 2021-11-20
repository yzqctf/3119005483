<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>-WeChat网页版-</title>
    <link rel="stylesheet" type="text/css" href="../css/addressBook.css"/>
    <script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
    <script type="text/javascript">
        function logout() {
            if (confirm("确认注销吗？")) {
                return true;
            }
            return false;
        }
    </script>
</head>
<body style="background: url(../image/background/login.jpg);background-size: cover;margin: 0;position: relative;top: 62px;z-index: -2;">
<ul>
    <li>
        <img src="${sessionScope.user.headPortrait}"
             style="width: 60px;height: 60px;position: absolute;top: 3px;left: 31px;border-radius: 45px;"
             title="这是你的头像">
        <span style="position: absolute;top: 22px;left: 102px;">${sessionScope.user.nickName}</span>
    </li>
    <div style="position: relative;left: 36%;">
        <li><a href="../ChatServlet?operation=query">聊天</a></li>
        <li><a class="active" href="../AddressBookServlet?operation=queryAll&pages=1">通讯录</a></li>
        <li><a href="../CircleFriendServlet?operation=queryAll&amp;pages=1&amp;commentPage=1">朋友圈</a></li>
        <li>
            <div class="dropdown">
                <button class="dropbtn">个人中心</button>
                <div class="dropdown-content">
                    <a href="personCenter.jsp?message=1">账户与安全</a>
                    <a href="personCenter.jsp?message=2">修改资料</a>
                    <a href="../ModifyServlet?operation=modifyBg&amp;pages=1">聊天设置</a>
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
    <div id="userList">
        <div class="search">
            <input id="likeSearch" placeholder="搜索">
            <img src="../image/icon/search.png" style="width: 19px;position: absolute;top: 23px;left: 167px;"
                 onclick="searchFriend()">
        </div>
        <a href="###" id="aApply" onclick="addSearch()">好友申请</a>
        <c:forEach items="${sessionScope.friendList}" var="friend">
            <button id="${friend.userId}" class="buttonShow">${friend.nickName}</button>
        </c:forEach>
    </div>
    <div id="userMsg" style="display: none">
        <c:if test="${param.message == 1}">
            <script>
                $("#userMsg").show();
            </script>
        </c:if>
        <img src="${sessionScope.friendMsg.headPortrait}"
             style="width: 178px;height: 178px;position: absolute;left: 28px;top: -18px;">
        <div style="position: absolute;bottom: 49px;">
            <span class="msg">账户:</span>
            <span class="msg">昵称:</span>
            <span class="msg">邮箱:</span>
            <span class="msg">电话:</span>
        </div>
        <div style="position: absolute;top: -16px;">
            <span class="show">${sessionScope.friendMsg.loginId}</span>
            <span class="show">${sessionScope.friendMsg.nickName}</span>
            <span class="show">${sessionScope.friendMsg.mailBox}</span>
            <span class="show">${sessionScope.friendMsg.phone}</span>
        </div>
        <span class="msg" id="signature">个性签名:</span>
        <span class="show" id="signatureShow">${sessionScope.friendMsg.signature}</span>
        <a class="chatBtn" id="${sessionScope.friendMsg.userId}"
           href="../ChatServlet?operation=chatOnly&friendId=${sessionScope.friendMsg.userId}">发起聊天</a>
        <a href="###"
           style="position: absolute;top: 388px;font-size: 15px;text-decoration: none;color: gray;width: -webkit-fill-available;"
           class="chatA" id="${sessionScope.friendMsg.userId}" onclick="chatA()">删除这位好友</a>
    </div>
    <div id="addFriend" style="display: none;">
        <c:if test="${param.message == 2}">
            <script>
                var unAddPage = ${sessionScope.unAddPage};
                $("#addFriend").show();
            </script>
        </c:if>
        <c:forEach items="${sessionScope.unAddFriend}" var="friend">
            <div style=" width: 246px; height: 54px;position: relative;top: -14px;border-bottom-style:dotted;">
                <span id="loginAdd">${friend.loginId}</span>
                <span id="nickAdd">${friend.nickName}</span>
                <a href="####" style="" id="aAdd" name="${friend.userId}" class="aAdd">加为好友</a>
            </div>
        </c:forEach>
        <div style="position: absolute;top: 493px;left: 96px;">
            <c:if test="${sessionScope.unAddPage > 1}">
                <a href="##" class="aPage" onclick="changeUnAddPage1()"><img src="../image/icon/left.png"></a>
            </c:if>
            <c:if test="${sessionScope.unAddPage < sessionScope.unAddPages}">
                <a href="##" class="aPage" onclick="changeUnAddPage2()"><img src="../image/icon/right.png"></a>
            </c:if>
        </div>
    </div>
    <div id="agreeFriend" style="display: none;">
        <c:if test="${param.message == 3}">
            <script>
                var requestPage = ${sessionScope.requestPage};
                $("#agreeFriend").show();
            </script>
        </c:if>
        <c:forEach items="${sessionScope.requestFriends}" var="friend">
            <div style=" width: 246px; height: 54px;position: relative;top: -14px;border-bottom-style:dotted;">
                <span id="loginConfirm">${friend.loginId}</span>
                <span id="nickConfirm">${friend.nickName}</span>
                <a href="###" style="" id="${friend.userId}" class="agree" onclick="agree()">同意</a>
                <a href="###" style="left: 214px;top: 15px;" id="${friend.userId}" class="agree"
                   onclick="refuse()">拒绝</a>
            </div>
        </c:forEach>
        <div style="position: absolute;top: 497px;left: 96px;">
            <c:if test="${sessionScope.requestPage > 1}">
                <a class="aPage" onclick="changeRequest1()"><img src="../image/icon/left.png"></a>
            </c:if>
            <c:if test="${sessionScope.requestPage < sessionScope.requestPages}">
                <a class="aPage" onclick="changeRequest2()"><img src="../image/icon/right.png"></a>
            </c:if>
        </div>
    </div>
</div>
<div style="position: absolute;display: grid;top: 272px;left: 290px;">
    <c:if test="${sessionScope.friendPage > 1}">
        <a href="../AddressBookServlet?operation=queryAll&pages=${sessionScope.friendPage-1}" class="aPage"><img
                src="../image/icon/up.png"></a>
    </c:if>
    <c:if test="${sessionScope.friendPage < sessionScope.friendPages}">
        <a href="addressBook.jsp" class="aPage"><img src="../image/icon/down.png"></a>
    </c:if>
</div>
<script type="text/javascript" src="../js/addressBook.js"></script>
</body>
</html>