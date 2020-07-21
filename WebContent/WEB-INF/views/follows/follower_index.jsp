<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}" />
            </div>
        </c:if>
        <c:if test="${hasError}">
            <div id="flush_error">
                エラーが発生しました。
            </div>
        </c:if>
        <h2>フォロワーリスト</h2>
        <table id="follower_list">
            <tbody>
                <c:choose>
                    <c:when test="${login_employee.admin_flag} == true">
                        <tr>
                            <th class="follower_name">氏名</th>
                            <th class="follow_report_index">日報一覧</th>
                            <th class="follower_show">詳細ページ</th>
                        </tr>
                        <c:forEach var="follower" items="${followers}" varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="follower_name"><c:out value="${follower.e_follow.name}"></c:out></td>
                                <td class="follow_report_index">
                                    <a href="<c:url value='/follows/show?id=${follow.e_followed.id}' />">
                                        一覧を見る
                                    </a>
                                </td>
                                <td class="follower_show">
                                    <a href="<c:url value='/employees/show?id=${follower.e_follow.id}' />">
                                        詳細を見る
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <th class="follower_name">氏名</th>
                            <th class="follow_report_index">日報一覧</th>
                        </tr>
                        <c:forEach var="follower" items="${followers}" varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="follower_name"><c:out value="${follower.e_follow.name}"></c:out></td>
                                <td class="follow_report_index">
                                    <a href="<c:url value='/follows/show?id=${follow.e_followed.id}' />">
                                        一覧を見る
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <p><a href="<c:url value='/follows/index?id=${login_employee.id}' />">フォローリストに戻る</a></p>
        <div id="pagination">
            （全 ${followers_count} 件） <br />
            <c:forEach var="i" begin="1" end="${((followers_count - 1) /15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?page=${i}' />"><c:out value="${i}"></c:out></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        </c:param>
</c:import>