<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>-WeChat网页版-</title>
    <link rel="stylesheet" type="text/css" href="../css/friendCircle.css"/>
    <script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
    <script type="text/javascript">
        var circleId = ${sessionScope.friendCircle.circleId};
        var likes = ${sessionScope.like};
        var pages = ${sessionScope.pages};

        function logout() {
            if (confirm("确认注销吗？")) {
                return true;
            }
            return false;
        }
    </script>
</head>
<body style="background: url(../image/background/login.jpg); background-size: cover; z-index: -2;position:relative;">

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
        <li><a class="active" href="../CircleFriendServlet?operation=queryAll&pages=1&commentPage=1">朋友圈</a></li>
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
<!-- 弹窗 -->
<div id="myModal" class="modal">

    <!-- 弹窗内容 -->
    <div class="modal-content">
        <div class="modal-header">
            <span id="close" class="close">&times;</span>
            <h2>发个朋友圈</h2>
        </div>
        <div class="modal-body">
            <textarea id="issue" maxlength="100" onblur="AntiSqlValid(this)"></textarea>
            <input id="issueSubmit" type="button" value="发送" onclick="return issueCF()"/>
        </div>
        <div class="modal-footer">
            <h3>记录美好生活</h3>
        </div>
    </div>
</div>
<!-- 弹窗 -->
<div id="myModal2" class="modal">

    <!-- 弹窗内容 -->
    <div class="modal-content">
        <div class="modal-header">
            <span id="close2" class="close">&times;</span>
            <h2>发出我的评论</h2>
        </div>
        <div class="modal-body">
            <textarea id="issue2" maxlength="100" onblur="AntiSqlValid(this)"></textarea>
            <input id="issueSubmit2" type="button" value="发送" onclick="return issueCM()"/>
        </div>
        <div class="modal-footer">
            <h3>评论</h3>
        </div>
    </div>
</div>
<div class="card">
    <div class="content">
        <div>
            <img src="../image/icon/circle.png" style="position: absolute;left: 334px;top: 6px;">
            <button id="myBtn">发布我的朋友圈</button>
        </div>
        <div style="position: absolute; left: 190px;top: 43px;">
            <button id="like" style="width: 36px;height: 33px;padding: 0; margin: 0;background: none; border: none;"
                    onclick="like()">
                <c:choose>
                    <c:when test="${sessionScope.like == 0}">
                        <img src="../image/icon/unlike.png" id="likeImg">
                    </c:when>
                    <c:otherwise>
                        <img src="../image/icon/alreadyLike.png" id="likeImg">
                    </c:otherwise>
                </c:choose>
            </button>
        </div>
        <div style="position: relative;right: 126px;">
            <img src="../image/icon/comment.png" style="position: absolute;left: 323px;top: 6px;">
            <button id="myBtn2">发表我的评论</button>
        </div>
        <h2>${sessionScope.friendCircle.sendUserName}</h2>
        <h5>${sessionScope.friendCircle.sendTime}</h5>
        <div class="fakeimg" style="height:200px;"><img style="width: 407px;height: 200px;position: absolute;top: 23px;"
                                                        src="/mywechat_war_exploded/image/background/161442-1554970482fb48.jpg">
        </div>
        <p style="width: 449px;height:107px;position: absolute;top: 326px;">${sessionScope.friendCircle.text}</p>
    </div>
    <div class="remark">
        <h2>评论</h2>
        <c:forEach items="${sessionScope.friendCircle.comments}" var="comment">
            <div class="comment">
                <span style="position: relative;font-size: 15px;">${comment.sendUserName}</span>
                <span style="position: relative;left: 305px;font-size: 12px;">${comment.sendTime}</span>
                <span style="position: relative;top: 27px;color: darkslategray;font-size: 12px;">${comment.commentText}</span>
            </div>
        </c:forEach>
    </div>
</div>
<div class="cp-item">
    <a href="../CircleFriendServlet?operation=queryAll&pages=1&commentPage=1" class="a1">首页</a>
    <c:if test="${sessionScope.pages!=1}">
        <a href="../CircleFriendServlet?operation=queryAll&pages=${sessionScope.pages-1}&commentPage=1"
           class="a1">上页</a>
    </c:if>
    <c:if test="${sessionScope.pages < sessionScope.circlePages}">
        <a href="../CircleFriendServlet?operation=queryAll&pages=${sessionScope.pages+1}&commentPage=1"
           class="a1">下页</a>
    </c:if>
    <a href="../CircleFriendServlet?operation=queryAll&pages=${sessionScope.circlePages}&commentPage=1"
       class="a1">尾页</a>
</div>
<div id="comment">
    <a href="../CircleFriendServlet?operation=queryAll&pages=${sessionScope.pages}&commentPage=1" class="a2">首页</a>
    <c:if test="${sessionScope.commentPage!=1}">
        <a href="../CircleFriendServlet?operation=queryAll&pages=${sessionScope.pages}&commentPage=${sessionScope.commentPage-1}"
           class="a2">上页</a>
    </c:if>
    <c:if test="${sessionScope.commentPage < sessionScope.commentPages}">
        <a href="../CircleFriendServlet?operation=queryAll&pages=${sessionScope.pages}&commentPage=${sessionScope.commentPage+1}"
           class="a2">下页</a>
    </c:if>
    <a href="../CircleFriendServlet?operation=queryAll&pages=${sessionScope.pages}&commentPage=${sessionScope.commentPages}"
       class="a2">尾页</a>
</div>
<script type="text/javascript" src="../js/circleFriend.js"></script>
</body>
</html>