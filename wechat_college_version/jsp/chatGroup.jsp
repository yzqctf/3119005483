<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>-WeChat网页版-</title>
    <script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="../js/chatGroup.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/user.css"/>
    <script type="text/javascript">
        var ws;
        var url = "ws://localhost:8080/mywechat_war_exploded/chatSocket?username=${sessionScope.user.loginId}?friendId=${param.groupId}";
        var type = ${param.type};
        var toId = ${param.groupId};
        var fromId = ${sessionScope.user.userId};
        var fromLoginId = "${sessionScope.user.loginId}";
        if (type == 1) {
            connect();
        }
        if (${sessionScope.groupMemDelete == null}) {

        } else if (${sessionScope.groupMemDelete == true}) {
            alert("退出成功！");
        } else if (${sessionScope.groupMemDelete == false}) {
            alert("你没有权限！");
            ${sessionScope.remove("groupMemDelete")};
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
        <li><a class="active" href="#home">聊天</a></li>
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
    <div id="header">${sessionScope.groupName}(${sessionScope.groupMember.size()}人)
        <a href="../GroupServlet?operation=queryAll&groupId=${param.groupId}"
           style="text-decoration: none;font-size: 13px;position: absolute;left: 475px; top: 40px; color: unset;">聊天记录</a>
    </div>
    <div id="text" style="background: url('${sessionScope.user.background}'); background-size: cover">
        <c:if test="${param.type!=0}">
            <c:forEach items="${sessionScope.unAcceptGroup}" var="chat">
                <div id="sendMsg">
                        ${chat.from}
                        ${chat.date}
                </div>
                <div class="left">
                    <p>${chat.sendMsg}</p>
                </div>
                <br/><br/>
            </c:forEach>
        </c:if>
    </div>
    <div id="userList">
        <c:if test="${param.type != 0}">
            <c:forEach items="${sessionScope.groupMember}" var="member">
                <div class="aMember">
                    <a href="../GroupServlet?operation=delete&groupId=${param.groupId}&userId=${member.userId}"
                       onclick="return deleteMem()">${member.nickName}</a>
                    <c:if test="${sessionScope.thisUserStatus > 0 && member.status == 0}">
                        <a href="../GroupServlet?operation=update&groupId=${param.groupId}&userId=${member.userId}"
                           class="up">提拔</a>
                    </c:if>
                    <c:if test="${member.status == 2}">
                        <span>群主</span>
                    </c:if>
                    <c:if test="${member.status == 1}">
                        <span>管理员</span>
                    </c:if>
                </div>
            </c:forEach>
            <div class="aMember">
                <a href="###" onclick="showAdd()">
                    增加成员
                </a>
            </div>
        </c:if>
    </div>
    <div id="addMem" style="display: none;">
        <c:forEach items="${sessionScope.friendMem}" var="friend">
            <div>
                <input type="checkbox" value="${friend.userId}">${friend.nickName}
            </div>
        </c:forEach>
        <a href="###" onclick="add()" style="position: relative;text-decoration: none;color: gray;" id="commit">确认添加</a>
    </div>
    <div id="addGroup" style="display: none">
        <c:forEach items="${sessionScope.friendMem}" var="friend">
            <div>
                <input type="checkbox" value="${friend.userId}">${friend.nickName}
            </div>
        </c:forEach>
        群聊名字： <input type="text" id="groupName"/>
        <a href="###" onclick="addGroup()" style="position: relative;text-decoration: none;color: gray;" id="commit">确定生成</a>
    </div>
    <div id="send">
        <textarea id="sendText" placeholder="输入你想发送的内容" maxlength="200" onblur="AntiSqlValid(this)"></textarea>
        <button id="sendBtn" onclick="send()">发送</button>
    </div>
    <div class="chat">
        <div style=" width: 291px;position: absolute;top: 43px;">
            <a href="user.jsp?type=0&friendId=0" class="header">最近聊天(仅私聊)</a>
            <a href="###" class="header" style="border-bottom-style: solid">群聊</a>
            <a href="###" onclick="showAddGroup()"
               style=" position: absolute; font-size: 11px; color: white; top: -17px; left: 237px;  text-decoration: none;">创建群聊</a>
            <div>
                <button>
                    <a href="../traverse.jsp">
                        <img src="../image/headPortrait/聊天选中.png"
                             style="width: 30px;max-width: 30px;height: 30px;max-height: 30px;position: relative;left: -15%;top: 55%;background: white;border-radius: 5px;">
                        <span style="color: white;position: relative;left: -22px;top: -10px;">聊天总群</span>
                    </a>
                </button>
            </div>
            <div style="overflow-x: hidden; width: 100%; height: 471px;">
                <c:forEach items="${sessionScope.groupList}" var="group">
                    <div class="userList">
                        <button onclick="location='../ChatServlet?operation=chatGroup&groupId=${group.groupId}'">
                            <img src="${group.groupIcon}" class="img">
                            <img src="../image/icon/notice.png" id="${group.groupId}" class="spanBtn"/>
                            <span class="span">${group.groupName}</span>
                            <a class="aout"
                               href="../GroupServlet?operation=delete&groupId=${group.groupId}&userId=${sessionScope.user.userId}"
                               onclick="return out()">退出群聊</a>
                        </button>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>