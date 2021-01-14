<%@ page import="model.bean.User" %>
<%@ page import="model.bean.Admin" %>
<%@ page import="model.bean.Operator" %>
<%@ page import="model.bean.Moderator" %><%--
  Created by IntelliJ IDEA.
  User: Roberto Esposito
  Date: 1/1/2021
  Time: 3:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <title>Admin Area</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminArea.css" type="text/css">
    <%@include file="header.jsp"%>
</head>
<body class="admin-body">

    <div class="admin-fieldset">
        <button id="admin" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%; margin-right: 20%">Admins</button>
        <button id="moderator" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Moderators</button>
        <button id="operator" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Operators</button>
        <button id="tag" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Tags</button>
        <button id="category" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Categories</button>
        <button id="product" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Products</button>
        <button id="user" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Users</button>
    </div>

    <div id="users-div" style="display: block">
        <h1 class='manage-header-user'>Manage users</h1>
        <div class='admin-fieldset'>
            <h3>Remove user</h3>
            <div class="table-div users-table-div">
                <table border='1' id='users-table' class='content-table'>
                    <thead>
                    <tr class='users-table-header'>
                        <th>Username</th>
                        <th>Mail</th>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Date of Birth</th>
                        <th>Remove</th>
                    </tr>
                    </thead>
                    <tbody class='users-table-body'>
                    <c:forEach items='${firstUsers}' var='user'>
                        <tr id="${user.username}UserRow" class='users-table-body-row'>
                            <td>  ${user.username}  </td>
                            <td>  ${user.mail}  </td>
                            <td>  ${user.name}  </td>
                            <td>  ${user.surname}  </td>
                            <td>  ${user.birthDate}  </td>
                            <td class='form-container'>
                                <form name='removeUserForm' class='removeUserForm' method='post' action='removeUser-servlet'>
                                    <input type='hidden' value='${user.username}' name='removeUser' class="usernameForRemove">
                                    <input type='submit' value='✗' class='removeUserAdminButton'>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class='paginationUsers'>
                    <span id='previousPageUsers' class='visible'>&laquo;</span>
                    <span id='ellipseSxUsers'>...</span>
                    <c:set var='maxPageUsers' value='${Math.ceil(usersLength/4)}'/>
                    <c:forEach var='i' begin='1' end='${maxPageUsers}'>
                        <c:if test='${i == 1}'>
                            <span class='current visible pageNumBtnUserAdmin' id='pageUsers${i}'>${i}</span>
                        </c:if>
                        <c:if test='${i != 1}'>
                            <c:if test='${i <= 4}'>
                                <span class='pageNumBtnUserAdmin visible' id='pageUsers${i}'>${i}</span>
                            </c:if>
                            <c:if test='${i > 4}'>
                                <span class='pageNumBtnUserAdmin' id='pageUsers${i}'>${i}</span>
                            </c:if>
                        </c:if>
                    </c:forEach>
                    <c:if test='${maxPageUsers > 4}'>
                        <span id='ellipseDxUsers' class='visible'>...</span>
                    </c:if>
                    <c:if test='${maxPageUsers <= 4}'>
                        <span id='ellipseDxUsers'>...</span>
                    </c:if>
                    <span id='nextPageUsers' class='visible'>&raquo;</span>
                </div>
            </div>
        </div>

    </div>

    <div id="products-div" style="display: none">
        <p>Products</p>
    </div>

    <div id="categories-div" style="display: none">
        <h1 class='manage-header-category'>Manage categories</h1>
        <div class='admin-fieldset'>
            <h3>Add category</h3>
            <form id='addCategoryForm' name='addCategoryForm' action='manage-category' method='post' enctype='multipart/form-data'>
                <div class='admin-textbox'>
                    <input type='text' id='categoryName' class='admin-textbox' name='categoryName' placeholder='Category name'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='file' id='image_path' name='image_path'>
                </div>
                <div class='admin-textbox-textarea'>
                    <textarea id='description_category' placeholder="Description" name='description_category'></textarea>
                </div>
                <div class='admin-textbox' style='border: none'>
                    <span id='errorMessageAddCategory' class='Error'></span><br>
                </div>
                <div id='submitAdminButtonContainerAddCategory'>
                    <input type='submit' class='btnAdmin submitBtn' disabled>
                    <input type="hidden" name="manage_category" value="add_category">
                </div>
            </form>
        </div>
        <div class='admin-fieldset'>
            <h3>Update category</h3>
            <div class="table-div categories-table-div">
                <table border='1' id='categories-table' class='content-table'>
                    <thead>
                    <tr class='categories-table-header'>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Image Path</th>
                        <th>Change</th>
                        <th>Remove</th>
                    </tr>
                    </thead>
                    <tbody class='categories-table-body'>
                    <c:forEach items='${firstCategories}' var='category'>
                        <tr id="${category.name}CategoryRow" class='categories-table-body-row'>
                            <td class='can-be-editable editable-name'>  ${category.name}  </td>
                            <td class='can-be-editable editable-description'>  ${category.description}  </td>
                            <td class='can-be-editable editable-imagePath'>
                                <input type='file' name='fileCategory' style='display: none'>
                                <span>${category.image}</span>
                            </td>
                            <td class='form-container'>
                                <form class='changeCategoryForm' name='changeCategoryForm' method='post' action='manage-category'>
                                    <input type='hidden' value='${category.name}' name='changeCategory' class='changeCategoryOldName'>
                                    <input type="hidden" name="manage_category" value="update_category">
                                    <input type='submit' value='📝' class='changeCategoryAdminButton'>
                                    <span class="errorCategoryMessage" style="color: #c75450; display: none"></span>
                                </form>
                            </td>
                            <td class='form-container'>
                                <form class='removeCategoryForm' name='removeCategoryForm' method='post' action='manage-category'>
                                    <input type='hidden' value='${category.name}' name='removeCategory' class='removeCategoryOldName'>
                                    <input type="hidden" name="manage_category" value="remove_category">
                                    <input type='submit' value='✗' class='removeCategoryAdminButton'>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class='paginationCategories'>
                <span id='previousPageCategories' class='visible'>&laquo;</span>
                <span id='ellipseSxCategories'>...</span>
                <c:set var='maxPageCategories' value='${Math.ceil(categoriesLength/4)}'/>
                <c:forEach var='i' begin='1' end='${maxPageCategories}'>
                    <c:if test='${i == 1}'>
                        <span class='current visible pageNumBtnCategoryAdmin' id='pageCategories${i}'>${i}</span>
                    </c:if>
                    <c:if test='${i != 1}'>
                        <c:if test='${i <= 4}'>
                            <span class='pageNumBtnCategoryAdmin visible' id='pageCategories${i}'>${i}</span>
                        </c:if>
                        <c:if test='${i > 4}'>
                            <span class='pageNumBtnCategoryAdmin' id='pageCategories${i}'>${i}</span>
                        </c:if>
                    </c:if>
                </c:forEach>
                <c:if test='${maxPageCategories > 4}'>
                    <span id='ellipseDxCategories' class='visible'>...</span>
                </c:if>
                <c:if test='${maxPageCategories <= 4}'>
                    <span id='ellipseDxCategories'>...</span>
                </c:if>
                <span id='nextPageCategories' class='visible'>&raquo;</span>
            </div>
        </div>
    </div>

    <div id="tags-div" style="display: none">
        <h1 class='manage-header-tag'>Manage tags</h1>
        <div class='admin-fieldset'>
            <h3>Add tag</h3>
            <form id='addTagForm' name='addTagForm' action='manage-tag' method='post' enctype='multipart/form-data'>
                <div class='admin-textbox'>
                    <input type='text' id='tagName' class='admin-textbox' name='tagName' placeholder='Tag name'><br>
                </div>
                <div class='admin-textbox' style='border: none'>
                    <span id='errorMessageAddTag' class='Error'></span><br>
                </div>
                <div id='submitAdminButtonContainerAddTag'>
                    <input type='submit' class='btnAdmin submitBtn' disabled>
                    <input type="hidden" name="manage_tag" value="add_tag">
                </div>
            </form>
        </div>
        <div class='admin-fieldset'>
            <h3>Update tag</h3>
            <div class="table-div tags-table-div">
                <table border='1' id='tags-table' class='content-table'>
                    <thead>
                    <tr class='tags-table-header'>
                        <th>Name</th>
                        <th>Change</th>
                        <th>Remove</th>
                    </tr>
                    </thead>
                    <tbody class='tags-table-body'>
                    <c:forEach items='${firstTags}' var='tag'>
                        <tr id="${tag.name}TagRow" class='tags-table-body-row'>
                            <td class='can-be-editable editable-name'>  ${tag.name}  </td>
                            </td>
                            <td class='form-container'>
                                <form class='changeTagForm' name='changeTagForm' method='post' action='manage-tag'>
                                    <input type='hidden' value='${tag.name}' name='changeTag' class='changeTagOldName'>
                                    <input type="hidden" name="manage_tag" value="update_tag">
                                    <input type='submit' value='📝' class='changeTagAdminButton'>
                                    <span class="errorTagMessage" style="color: #c75450; display: none"></span>
                                </form>
                            </td>
                            <td class='form-container'>
                                <form class='removeTagForm' name='removeTagForm' method='post' action='manage-tag'>
                                    <input type='hidden' value='${tag.name}' name='removeTag' class='removeTagOldName'>
                                    <input type="hidden" name="manage_tag" value="remove_tag">
                                    <input type='submit' value='✗' class='removeTagAdminButton'>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class='paginationTags'>
                <span id='previousPageTags' class='visible'>&laquo;</span>
                <span id='ellipseSxTags'>...</span>
                <c:set var='maxPageTags' value='${Math.ceil(tagsLength/4)}'/>
                <c:forEach var='i' begin='1' end='${maxPageTags}'>
                    <c:if test='${i == 1}'>
                        <span class='current visible pageNumBtnTagAdmin' id='pageTags${i}'>${i}</span>
                    </c:if>
                    <c:if test='${i != 1}'>
                        <c:if test='${i <= 4}'>
                            <span class='pageNumBtnTagAdmin visible' id='pageTags${i}'>${i}</span>
                        </c:if>
                        <c:if test='${i > 4}'>
                            <span class='pageNumBtnTagAdmin' id='pageTags${i}'>${i}</span>
                        </c:if>
                    </c:if>
                </c:forEach>
                <c:if test='${maxPageTags > 4}'>
                    <span id='ellipseDxTags' class='visible'>...</span>
                </c:if>
                <c:if test='${maxPageTags <= 4}'>
                    <span id='ellipseDxTags'>...</span>
                </c:if>
                <span id='nextPageTags' class='visible'>&raquo;</span>
            </div>
        </div>
    </div>

    <div id="operators-div" style="display: none">
        <p>Operators</p>
    </div>

    <div id="moderators-div" style="display: none">
        <p>Moderators</p>
    </div>

    <div id="admins-div" style="display: none">
        <p>Admins</p>
    </div>

    <%@include file="footer.jsp"%> <!--footer-->
    <script>
        function resizeFooter(){ //per evitare strani ridimensionamenti su iPadPro
            if(window.matchMedia("screen and (min-width: 629px) and (min-height: 1010px)").matches)
                $(".footer").css("position", "absolute").css("bottom", "0");
            else
                $(".footer").css("position", "").css("bottom", "");
        }
        resizeFooter();
        window.onresize = ev => resizeFooter();
    </script>
    <script>var maxPageCategories = ${(maxPageCategories > 0)?(maxPageCategories):(1)}; //mantiena l'indice dell'ultima pagina</script>
    <script>var maxPageTags = ${(maxPageTags > 0)?(maxPageTags):(1)}; //mantiena l'indice dell'ultima pagina</script>
    <script>var maxPageUsers = ${(maxPageUsers > 0)?(maxPageUsers):(1)}; //mantiena l'indice dell'ultima pagina</script>
    <script src="${pageContext.request.contextPath}/js/adminArea.js"></script>
    <script src="${pageContext.request.contextPath}/js/utility.js"></script>
    <script src="${pageContext.request.contextPath}/js/usersAdmin.js"></script>
    <script src="${pageContext.request.contextPath}/js/categoriesAdmin.js"></script>
    <script src="${pageContext.request.contextPath}/js/adminForms.js"></script>
    <script src="${pageContext.request.contextPath}/js/tagsAdmin.js"></script>
</body>
</html>