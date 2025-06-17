function insertContent() {
      const body = document.querySelector('body');
      const content = `
        <c:forEach var="e" items="${role}">
          <c:set var="role" value="${e}" />
          <c:out value="${e}" />
          <c:forEach var="e" items="${roleList}">
            <c:if test="${role == e.role}">
              <ul>
                <li><c:out value="${e.housework_name}" /></li>
              </ul>
            </c:if>
          </c:forEach>
        </c:forEach>
      `;
      body.innerHTML = content;
    }