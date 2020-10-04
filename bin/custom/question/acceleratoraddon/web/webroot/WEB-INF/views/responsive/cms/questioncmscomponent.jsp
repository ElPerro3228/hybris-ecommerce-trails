<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:useBean id="product" scope="request" type="de.hybris.platform.commercefacades.product.data.ProductData" />
<div class="container" style="font-size: ${fontSize}">
    <p>Questions</p>
    <div class="row">
        <c:if test="${product.questions.size() > numberOfQuestionsToShow}">
            <c:forEach var="i" begin="0" end="${numberOfQuestionsToShow}" >
                <h6><c:out value="${product.questions[i].questionCustomer}" /> </h6>
                <div class="col-md-6 col-lg-4">
                    <c:out value="${product.questions[i].question}" />
                    <div class="container">
                        <h6>Answer: <c:out value="${product.questions[i].answerCustomer}" /> </h6>
                        <c:out value="${product.questions[i].answer}" />
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${product.questions.size() < numberOfQuestionsToShow}">
            <c:forEach var="question" items="${product.questions}" >
                <h6><c:out value="${question.questionCustomer}" /> </h6>
                <div class="col-md-6 col-lg-4">
                    <c:out value="${question.question}" />
                    <div class="container">
                        <h6>Answer: <c:out value="${question.answerCustomer}" /> </h6>
                        <c:out value="${question.answer}" />
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</div>
