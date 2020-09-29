<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="product" type="de.hybris.platform.commercefacades.product.data.ProductData" scope="request"/>
<div style="font-size: ${fontSize}" >
    <p>Questions ${numberOfQuestionsToShow} ${product.questions.size()}</p>
    <c:if test="${product.questions.size() > numberOfQuestionsToShow}">
        <c:forEach var="i" begin="0" end="${numberOfQuestionsToShow - 1}" >
            <h4><c:out value="${product.questions[i].questionCustomer} asked question" /> </h4>
            <h5>
                <c:out value="${product.questions[i].question}" />
                <h6>Answer: <c:out value="${product.questions[i].answerCustomer}" /> </h6>
                <c:out value="${product.questions[i].answer}" />
            </h5>
        </c:forEach>
    </c:if>
    <c:if test="${product.questions.size() < numberOfQuestionsToShow}">
        <c:forEach var="question" items="${product.questions}" >
            <h4><c:out value="${question.questionCustomer} asked question" /> </h4>
            <h5>
                <c:out value="${question.question}" />
                <h6>Answer: <c:out value="${question.answerCustomer}" /> </h6>
                <c:out value="${question.answer}" />
            </h5>
        </c:forEach>
    </c:if>
</div>
