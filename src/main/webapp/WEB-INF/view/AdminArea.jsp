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
        <button id="admin" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%; margin-right: 10%">Admins</button>
        <button id="moderator" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Moderators</button>
        <button id="operator" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Operators</button>
        <button id="tag" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Tags</button>
        <button id="category" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Categories</button>
        <button id="productDigital" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Digital Product</button>
        <button id="productPhysical" type="submit" class="btn btn-warning" style="float: right; margin-left: 1%">Physical Product</button>
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
                        <c:if test='${not user.username.equals(loggedUser.username)}'>
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
                        </c:if>
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

    <div id="productsDigital-div" style="display: none">
        <h1 class='manage-header-digitalProduct'>Manage digital products</h1>
        <div class='admin-fieldset'>
            <h3>Add digital product</h3>
            <form id='addDigitalProductForm' name='addDigitalProductForm' action='manage-product' method='post' enctype='multipart/form-data'>
                <div class='admin-textbox'>
                    <input type='text' id='digitalProductName' class='admin-textbox' name='name' placeholder='Digital product name'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='digitalProductPrice' class='admin-textbox' name='price' placeholder='Digital product price'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='digitalProductDescription' class='admin-textbox' name='description' placeholder='Digital product description'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='digitalProductPlatform' class='admin-textbox' name='platform' placeholder='Digital product platform'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='digitalProductreleaseDate' class='admin-textbox' name='releaseDate' placeholder='Digital product release date'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='digitalProductRequiredAge' class='admin-textbox' name='requiredAge' placeholder='Digital product requiredAge'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='digitalProductSoftwareHouse' class='admin-textbox' name='softwareHouse' placeholder='Digital product software house'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='digitalProductPublisher' class='admin-textbox' name='publisher' placeholder='Digital product publisher'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='digitalProductQuantity' class='admin-textbox' name='quantity' placeholder='Digital product quantity'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='file' id='imageDigitalProduct_path' name='image'>
                </div>
                <div class='admin-textbox' style='border: none'>
                    <span id='errorMessageAddDigitalProduct' class='Error'></span><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='digitalProductCategories' class='admin-textbox' name='categories' placeholder='Digital product categories'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='digitalProductTags' class='admin-textbox' name='tags' placeholder='Digital product tags'><br>
                </div>
                <div id='submitAdminButtonContainerAddDigitalProduct'>
                    <input type='submit' class='btnAdmin submitBtn'>
                    <input type="hidden" name="manage_product" value="add_category">
                    <input type="hidden" name="product_type" value="digitalProduct">
                </div>
            </form>
        </div>
        <div class='admin-fieldset'>
            <h3>Update digital product</h3>
            <div class="table-div digitalProducts-table-div">
                <table border='1' id='digitalProducts-table' class='content-table'>
                    <thead>
                    <tr class='digitalProducts-table-header'>
                        <th>Name</th>
                        <th>Categories</th>
                        <th>Tags</th>
                        <th>Price</th>
                        <th>Description</th>
                        <th>Image Path</th>
                        <th>Platform</th>
                        <th>Release Date</th>
                        <th>Required Age</th>
                        <th>Software House</th>
                        <th>Publisher</th>
                        <th>Quantity</th>
                        <th>Change</th>
                        <th>Remove</th>
                    </tr>
                    </thead>
                    <tbody class='digitalProducts-table-body'>
                    <c:forEach items='${firstDigitalProducts}' var='digitalProduct'>
                        <tr id="${digitalProduct.id}DigitalProductRow" class='digitalProducts-table-body-row'>
                            <td class='can-be-editable editable-name'>  ${digitalProduct.name}  </td>
                            <td class='can-be-editable editable-category'>
                                <c:forEach  items='${digitalProduct.categories}' var='category'>
                                    ${category.name},
                                </c:forEach>
                            </td>
                            <td class='can-be-editable editable-tag'>
                                <c:forEach items='${digitalProduct.tags}' var='tag'>
                                    ${tag.name},
                                </c:forEach>
                            </td>
                            <td class='can-be-editable editable-price'>  ${digitalProduct.price}  </td>
                            <td class='can-be-editable editable-description'>  ${digitalProduct.description}  </td>
                            <td class='can-be-editable editable-imagePath'>
                                <input type='file' name='fileDigitalProduct' style='display: none'>
                                <span>${digitalProduct.image}</span>
                            </td>
                            <td class='can-be-editable editable-platform'>  ${digitalProduct.platform}  </td>
                            <td class='can-be-editable editable-releaseDate'>  ${digitalProduct.releaseDate}  </td>
                            <td class='can-be-editable editable-requiredAge'>  ${digitalProduct.requiredAge}  </td>
                            <td class='can-be-editable editable-softwareHouse'>  ${digitalProduct.softwareHouse}  </td>
                            <td class='can-be-editable editable-publisher'>  ${digitalProduct.publisher}  </td>
                            <td class='can-be-editable editable-quantity'>  ${digitalProduct.quantity}  </td>
                            <td class='form-container'>
                                <form class='changeDigitalProductForm' name='changeDigitalProductForm' method='post' action='manage-product'>
                                    <input type='hidden' value='${digitalProduct.id}' name='changeDigitalProduct' class='changeDigitalProductOldName'>
                                    <input type="hidden" name="manage_product" value="update_product">
                                    <input type="hidden" name="product_type" value="digitalProduct">
                                    <input type='submit' value='📝' class='changeDigitalProductAdminButton'>
                                    <span class="errorDigitalProductMessage" style="color: #c75450; display: none"></span>
                                </form>
                            </td>
                            <td class='form-container'>
                                <form class='removeDigitalProductForm' name='removeDigitalProductForm' method='post' action='manage-product'>
                                    <input type='hidden' value='${digitalProduct.id}' name='removeDigitalProduct' class='removeDigitalProductOldName'>
                                    <input type="hidden" name="manage_product" value="remove_product">
                                    <input type="hidden" name="product_type" value="digitalProduct">
                                    <input type='submit' value='✗' class='removeDigitalProductAdminButton'>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class='paginationDigitalProducts'>
                <span id='previousPageDigitalProducts' class='visible'>&laquo;</span>
                <span id='ellipseSxDigitalProducts'>...</span>
                <c:set var='maxPageDigitalProducts' value='${Math.ceil(digitalProductsLength/4)}'/>
                <c:forEach var='i' begin='1' end='${maxPageDigitalProducts}'>
                    <c:if test='${i == 1}'>
                        <span class='current visible pageNumBtnDigitalProductAdmin' id='pageDigitalProducts${i}'>${i}</span>
                    </c:if>
                    <c:if test='${i != 1}'>
                        <c:if test='${i <= 4}'>
                            <span class='pageNumBtnDigitalProductAdmin visible' id='pageDigitalProducts${i}'>${i}</span>
                        </c:if>
                        <c:if test='${i > 4}'>
                            <span class='pageNumBtnDigitalProductAdmin' id='pageDigitalProducts${i}'>${i}</span>
                        </c:if>
                    </c:if>
                </c:forEach>
                <c:if test='${maxPageDigitalProducts > 4}'>
                    <span id='ellipseDxDigitalProductss' class='visible'>...</span>
                </c:if>
                <c:if test='${maxPageDigitalProductss <= 4}'>
                    <span id='ellipseDxDigitalProducts'>...</span>
                </c:if>
                <span id='nextPageDigitalProducts' class='visible'>&raquo;</span>
            </div>
        </div>
    </div>

    <div id="productsPhysical-div" style="display: none">
        <h1 class='manage-header-physicalProduct'>Manage physical products</h1>
        <div class='admin-fieldset'>
            <h3>Add physical product</h3>
            <form id='addPhysicalProductForm' name='addPhysicalProductForm' action='manage-product' method='post' enctype='multipart/form-data'>
                <div class='admin-textbox'>
                    <input type='text' id='physicalProductName' class='admin-textbox' name='name' placeholder='Physical product name'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='physicalProductPrice' class='admin-textbox' name='price' placeholder='Physical product price'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='physicalProductDescription' class='admin-textbox' name='description' placeholder='Physical product description'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='physicalProductQuantity' class='admin-textbox' name='quantity' placeholder='Physical product quantity'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='file' id='imagePhysicalProduct_path' name='image'>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='physicalProductCategories' class='admin-textbox' name='categories' placeholder='Physical product categories'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='physicalProductTags' class='admin-textbox' name='tags' placeholder='Physical product tags'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='physicalProductSize' class='admin-textbox' name='size' placeholder='Physical product size'><br>
                </div>
                <div class='admin-textbox'>
                    <input type='text' id='physicalProductWeight' class='admin-textbox' name='weight' placeholder='Physical product weight'><br>
                </div>
                <div class='admin-textbox' style='border: none'>
                    <span id='errorMessageAddPhysicalProduct' class='Error'></span><br>
                </div>
                <div id='submitAdminButtonContainerAddPhysicalProduct'>
                    <input type='submit' class='btnAdmin submitBtn'>
                    <input type="hidden" name="manage_product" value="add_category">
                    <input type="hidden" name="product_type" value="physicalProduct">
                </div>
            </form>
        </div>
        <div class='admin-fieldset'>
            <h3>Update physical product</h3>
            <div class="table-div physicalProducts-table-div">
                <table border='1' id='physicalProducts-table' class='content-table'>
                    <thead>
                    <tr class='physicalProducts-table-header'>
                        <th>Name</th>
                        <th>Categories</th>
                        <th>Tags</th>
                        <th>Price</th>
                        <th>Description</th>
                        <th>Image Path</th>
                        <th>Quantity</th>
                        <th>Size</th>
                        <th>Weight</th>
                        <th>Change</th>
                        <th>Remove</th>
                    </tr>
                    </thead>
                    <tbody class='physicalProducts-table-body'>
                    <c:forEach items='${firstPhysicalProducts}' var='physicalProduct'>
                        <tr id="${physicalProduct.id}PhysicalProductRow" class='physicalProducts-table-body-row'>
                            <td class='can-be-editable editable-name'>  ${physicalProduct.name}  </td>
                            <td class='can-be-editable editable-category'>
                                <c:forEach  items='${physicalProduct.categories}' var='physical'>
                                    ${category.name},
                                </c:forEach>
                            </td>
                            <td class='can-be-editable editable-tag'>
                                <c:forEach items='${physicalProduct.tags}' var='tag'>
                                    ${tag.name},
                                </c:forEach>
                            </td>
                            <td class='can-be-editable editable-price'>  ${physicalProduct.price}  </td>
                            <td class='can-be-editable editable-description'>  ${physicalProduct.description}  </td>
                            <td class='can-be-editable editable-imagePath'>
                                <input type='file' name='filePhysicalProduct' style='display: none'>
                                <span>${physicalProduct.image}</span>
                            </td>
                            <td class='can-be-editable editable-quantity'>  ${physicalProduct.quantity}  </td>
                            <td class='can-be-editable editable-size'>  ${physicalProduct.size}  </td>
                            <td class='can-be-editable editable-weight'>  ${physicalProduct.weight}  </td>
                            <td class='form-container'>
                                <form class='changePhysicalProductForm' name='changePhysicalProductForm' method='post' action='manage-product'>
                                    <input type='hidden' value='${physicalProduct.id}' name='changePhysicalProduct' class='changePhysicalProductOldName'>
                                    <input type="hidden" name="manage_product" value="update_product">
                                    <input type="hidden" name="product_type" value="physicalProduct">
                                    <input type='submit' value='📝' class='changePhysicalProductAdminButton'>
                                    <span class="errorPhysicalProductMessage" style="color: #c75450; display: none"></span>
                                </form>
                            </td>
                            <td class='form-container'>
                                <form class='removePhysicalProductForm' name='removePhysicalProductForm' method='post' action='manage-product'>
                                    <input type='hidden' value='${physicalProduct.id}' name='removePhysicalProduct' class='removePhysicalProductOldName'>
                                    <input type="hidden" name="manage_product" value="remove_product">
                                    <input type="hidden" name="product_type" value="physicalProduct">
                                    <input type='submit' value='✗' class='removePhysicalProductAdminButton'>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class='paginationPhysicalProducts'>
                <span id='previousPagePhysicalProducts' class='visible'>&laquo;</span>
                <span id='ellipseSxPhysicalProducts'>...</span>
                <c:set var='maxPagePhysicalProducts' value='${Math.ceil(physicalProductsLength/4)}'/>
                <c:forEach var='i' begin='1' end='${maxPagePhysicalProducts}'>
                    <c:if test='${i == 1}'>
                        <span class='current visible pageNumBtnPhysicalProductAdmin' id='pagePhysicalProducts${i}'>${i}</span>
                    </c:if>
                    <c:if test='${i != 1}'>
                        <c:if test='${i <= 4}'>
                            <span class='pageNumBtnPhysicalProductAdmin visible' id='pagePhysicalProducts${i}'>${i}</span>
                        </c:if>
                        <c:if test='${i > 4}'>
                            <span class='pageNumBtnPhysicalProductAdmin' id='pagePhysicalProducts${i}'>${i}</span>
                        </c:if>
                    </c:if>
                </c:forEach>
                <c:if test='${maxPagePhysicalProducts > 4}'>
                    <span id='ellipseDxPhysicalProductss' class='visible'>...</span>
                </c:if>
                <c:if test='${maxPagePhysicalProductss <= 4}'>
                    <span id='ellipseDxPhysicalProducts'>...</span>
                </c:if>
                <span id='nextPagePhysicalProducts' class='visible'>&raquo;</span>
            </div>
        </div>
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
                    <input type='text' minlength="1" maxlength="45" id='tagName' class='admin-textbox' name='tagName' placeholder='Tag name'><br>
                </div>
                <div class='admin-textbox' style='border: none'>
                    <span id='errorMessageAddTag' class='Error'></span><br>
                </div>
                <div id='submitAdminButtonContainerAddTag'>
                    <input type='submit' class='btnAdmin submitBtn'>
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
        <h1 class='manage-header-operator'>Manage operators</h1>
        <div class='admin-fieldset'>
            <h3>Add operator</h3>
            <form id='addOperatorForm' name='addOperatorForm' action='manageOperator-servlet' method='post'>
                <div class='admin-textbox'>
                    <input type='text' id='userName' pattern="^[A-Za-z0-9]{6,20}$" class='admin-textbox' name='userName' placeholder='Username'><br>
                </div>
                <div class='admin-textbox-textarea'>
                    <textarea id='curriculum' minlength="3" maxlength="10000" placeholder="Curriculum" name='curriculum'></textarea><br>
                </div>
                <div class="admin-textbox">
                    <input type="text" pattern="^\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$" id="contractTime" class="admin-textbox" name="contractTime" placeholder="contractTime yyyy-mm-dd"><br>
                </div>
                <div class='admin-textbox' style='border: none'>
                    <span id='errorMessageAddOperator' class='Error'></span><br>
                </div>
                <div id='submitAdminButtonContainerAddOperator'>
                    <input type="hidden" value="add_operator" name="manage_operator">
                    <input type='submit' class='btnAdmin submitBtn' disabled>
                </div>
            </form>
        </div>
        <div class='admin-fieldset'>
            <h3>All operators</h3>
            <div class="table-div operators-table-div">
                <table border='1' id='operators-table' class='content-table'>
                    <thead>
                    <tr class='operators-table-header'>
                        <th>Username</th>
                        <th>Curriculum</th>
                        <th>Contract Time</th>
                        <th>Change</th>
                        <th>Remove</th>
                    </tr>
                    </thead>
                    <tbody class='operators-table-body'>
                    <c:forEach items='${firstOperators}' var='operator'>
                        <c:if test='${not operator.username.equals(loggedUser.username)}'>
                            <tr id="${operator.username}OperatorRow" class='operators-table-body-row'>
                                <td>  ${operator.username}  </td>
                                <td class="can-be-editable editable-cv">  ${operator.cv}</td>
                                <td class="can-be-editable editable-contractTime">  ${operator.contractTime}</td>
                                <td class='form-container'>
                                    <form class='changeOperatorForm' name='changeOperatorForm' method='post' action='manage-category'>
                                        <input type='hidden' value='${operator.username}' name='changeOperator' class='changeOperatorOldName'>
                                        <input type="hidden" name="manage_operator" value="update_operator">
                                        <input type='submit' value='📝' class='changeOperatorAdminButton'>
                                        <span class="errorOperatorMessage" style="color: #c75450; display: none"></span>
                                    </form>
                                </td>
                                <td class='form-container'>
                                    <form name='removeOperatorForm' class='removeOperatorForm' method='post' action='manageOperator-servlet'>
                                        <input type='hidden' value='${operator.username}' name='removeOperator' class='operatorNameForRemove'>
                                        <input type='submit' value='✗' class='removeOperatorAdminButton'>
                                    </form>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class='paginationOperators'>
                <span id='previousPageOperators' class='visible'>&laquo;</span>
                <span id='ellipseSxOperators'>...</span>
                <c:set var='maxPageOperators' value='${Math.ceil(operatorsLength/4)}'/>
                <c:forEach var='i' begin='1' end='${maxPageOperators}'>
                    <c:if test='${i == 1}'>
                        <span class='current visible pageNumBtnOperatorAdmin' id='pageOperators${i}'>${i}</span>
                    </c:if>
                    <c:if test='${i != 1}'>
                        <c:if test='${i <= 4}'>
                            <span class='pageNumBtnOperatorAdmin visible' id='pageOperators${i}'>${i}</span>
                        </c:if>
                        <c:if test='${i > 4}'>
                            <span class='pageNumBtnOperatorAdmin' id='pageOperators${i}'>${i}</span>
                        </c:if>
                    </c:if>
                </c:forEach>
                <c:if test='${maxPageOperators > 4}'>
                    <span id='ellipseDxOperators' class='visible'>...</span>
                </c:if>
                <c:if test='${maxPageOperators <= 4}'>
                    <span id='ellipseDxOperators'>...</span>
                </c:if>
                <span id='nextPageOperators' class='visible'>&raquo;</span>
            </div>
        </div>
    </div>

    <div id="moderators-div" style="display: none">
        <h1 class='manage-header-moderator'>Manage moderators</h1>
        <div class='admin-fieldset'>
            <h3>Add moderator</h3>
            <form id='addModeratorForm' name='addModeratorForm' action='addModerator-servlet' method='post'>
                <div class='admin-textbox'>
                    <input type='text' id='moderatorName' class='admin-textbox' name='moderatorName' placeholder='Username'><br>
                </div>
                <div class="admin-textbox">
                    <input type="text" pattern="^\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$" id="contractTimeModerator" class="admin-textbox" name="contractTime" placeholder="contractTime yyyy-mm-dd"><br>
                </div>
                <div class='admin-textbox' style='border: none'>
                    <span id='errorMessageAddModerator' class='Error'></span><br>
                </div>
                <div id='submitAdminButtonContainerAddModerator'>
                    <input type='submit' class='btnAdmin submitBtn' disabled>
                </div>
            </form>
        </div>
        <div class='admin-fieldset'>
            <h3>All moderators</h3>
            <div class="table-div moderators-table-div">
                <table border='1' id='moderators-table' class='content-table'>
                    <thead>
                    <tr class='moderators-table-header'>
                        <th>Username</th>
                        <th>Contract Time</th>
                        <th>Update</th>
                        <th>Remove</th>
                    </tr>
                    </thead>
                    <tbody class='moderators-table-body'>
                    <c:forEach items='${firstModerators}' var='moderator'>
                        <c:if test='${not moderator.username.equals(loggedUser.username)}'>
                            <tr id="${moderator.username}ModeratorRow" class='moderators-table-body-row'>
                                <td>  ${moderator.username}  </td>
                                <td>  ${moderator.contractTime}</td>
                                <td class='form-container'>
                                    <form class='changeModeratorForm' name='changeModeratorForm' method='post' action='manage-category'>
                                        <input type='hidden' value='${moderator.username}' name='changeModerator' class='changeModeratorOldName'>
                                        <input type="hidden" name="manage_moderator" value="update_moderator">
                                        <input type='submit' value='📝' class='changeModeratorAdminButton'>
                                        <span class="errorModeratorMessage" style="color: #c75450; display: none"></span>
                                    </form>
                                </td>
                                <td class='form-container'>
                                    <form name='removeModeratorForm' class='removeModeratorForm' method='post' action='removeModerator-servlet'>
                                        <input type='hidden' value='${moderator.username}' name='removeModerator' class='moderatorNameForRemove'>
                                        <input type='submit' value='✗' class='removeModeratorAdminButton'>
                                    </form>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class='paginationModerators'>
                <span id='previousPageModerators' class='visible'>&laquo;</span>
                <span id='ellipseSxModerators'>...</span>
                <c:set var='maxPageModerators' value='${Math.ceil(moderatorsLength/4)}'/>
                <c:forEach var='i' begin='1' end='${maxPageModerators}'>
                    <c:if test='${i == 1}'>
                        <span class='current visible pageNumBtnModeratorAdmin' id='pageModerators${i}'>${i}</span>
                    </c:if>
                    <c:if test='${i != 1}'>
                        <c:if test='${i <= 4}'>
                            <span class='pageNumBtnModeratorAdmin visible' id='pageModerators${i}'>${i}</span>
                        </c:if>
                        <c:if test='${i > 4}'>
                            <span class='pageNumBtnModeratorAdmin' id='pageModerators${i}'>${i}</span>
                        </c:if>
                    </c:if>
                </c:forEach>
                <c:if test='${maxPageModerators > 4}'>
                    <span id='ellipseDxModerators' class='visible'>...</span>
                </c:if>
                <c:if test='${maxPageModerators <= 4}'>
                    <span id='ellipseDxModerators'>...</span>
                </c:if>
                <span id='nextPageModerators' class='visible'>&raquo;</span>
            </div>
        </div>
    </div>
    <c:if test="${loggedUser.isSuperAdmin()}">
        <div id="admins-div" style="display: none">
            <h1 class='manage-header-admin'>Manage admins</h1>
            <div class='admin-fieldset'>
                <h3>Add admin</h3>
                <form id='addAdminForm' name='addAdminForm' action='addAdmin-servlet' method='post'>
                    <div class='admin-textbox'>
                        <input type='text' id='adminName' class='admin-textbox' name='adminName' placeholder='Username'><br>
                    </div>
                    <div class="admin-textbox">
                        <input type="text" id="superRoot" class="admin-textbox" name="superRoot" placeholder="superRoot"><br>
                    </div>
                    <div class='admin-textbox' style='border: none'>
                        <span id='errorMessageAddAdmin' class='Error'></span><br>
                    </div>
                    <div id='submitAdminButtonContainerAddAdmin'>
                        <input type='submit' class='btnAdmin submitBtn'>
                    </div>
                </form>
            </div>
            <div class='admin-fieldset'>
                <h3>All admins</h3>
                <div class="table-div admins-table-div">
                    <table border='1' id='admins-table' class='content-table'>
                        <thead>
                        <tr class='admins-table-header'>
                            <th>Username</th>
                            <th>SuperRoot</th>
                            <th>Change</th>
                            <th>Remove</th>
                        </tr>
                        </thead>
                        <tbody class='admins-table-body'>
                        <c:forEach items='${firstAdmins}' var='admin'>
                            <c:if test="${not admin.username.equals(loggedUser.username)}">
                                <tr id="${admin.username}AdminRow" class='admins-table-body-row'>
                                    <td>${admin.username}</td>
                                    <td class="can-be-editable editable-isSuperAdmin">${admin.isSuperAdmin()}</td>
                                    <td class='form-container'>
                                        <form class='changeAdminForm' name='changeAdminForm' method='post' action='manage-admin'>
                                            <input type='hidden' value='${admin.username}' name='changeAdmin' class='changeAdminOldName'>
                                            <input type="hidden" name="manage_admin" value="update_admin">
                                            <input type='submit' value='📝' class='changeAdminAdminButton'>
                                            <span class="errorMessage" style="color: #c75450; display: none"></span>
                                        </form>
                                    </td>
                                    <td class='form-container'>
                                        <form class='removeAdminForm' name='removeAdminForm' method='post' action='manage-admin'>
                                            <input type='hidden' value='${admin.username}' name='removeAdmin' class='removeAdminOldName'>
                                            <input type="hidden" name="manage_admin" value="remove_admin">
                                            <input type='submit' value='✗' class='removeAdminAdminButton'>
                                        </form>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class='paginationAdmins'>
                    <span id='previousPageAdmins' class='visible'>&laquo;</span>
                    <span id='ellipseSxAdmins'>...</span>
                    <c:set var='maxPageAdmins' value='${Math.ceil(adminsLength/4)}'/>
                    <c:forEach var='i' begin='1' end='${maxPageAdmins}'>
                        <c:if test='${i == 1}'>
                            <span class='current visible pageNumBtnAdminsAdmin' id='pageAdmins${i}'>${i}</span>
                        </c:if>
                        <c:if test='${i != 1}'>
                            <c:if test='${i <= 4}'>
                                <span class='pageNumBtnAdminsAdmin visible' id='pageAdmins${i}'>${i}</span>
                            </c:if>
                            <c:if test='${i > 4}'>
                                <span class='pageNumBtnAdminsAdmin' id='pageAdmins${i}'>${i}</span>
                            </c:if>
                        </c:if>
                    </c:forEach>
                    <c:if test='${maxPageAdmins > 4}'>
                        <span id='ellipseDxAdmins' class='visible'>...</span>
                    </c:if>
                    <c:if test='${maxPageAdmins <= 4}'>
                        <span id='ellipseDxAdmins'>...</span>
                    </c:if>
                    <span id='nextPageAdmins' class='visible'>&raquo;</span>
                </div>
            </div>
        </div>
    </c:if>
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
    <script>var maxPageOperators = ${(maxPageOperators > 0)?(maxPageOperators):(1)}; //mantiena l'indice dell'ultima pagina</script>
    <script>var maxPageModerators = ${(maxPageModerators > 0)?(maxPageModerators):(1)}; //mantiena l'indice dell'ultima pagina</script>
    <script>var maxPageAdmins = ${(maxPageAdmins > 0)?(maxPageAdmins):(1)}; //mantiena l'indice dell'ultima pagina</script>
    <script>var maxPageCategories = ${(maxPageCategories > 0)?(maxPageCategories):(1)}; //mantiena l'indice dell'ultima pagina</script>
    <script>var maxPageTags = ${(maxPageTags > 0)?(maxPageTags):(1)}; //mantiena l'indice dell'ultima pagina</script>
    <script>var maxPageUsers = ${(maxPageUsers > 0)?(maxPageUsers):(1)}; //mantiena l'indice dell'ultima pagina</script>
    <script>var maxPageDigitalProducts = ${(maxPageDigitalProducts > 0)?(maxPageDigitalProducts):(1)};</script>
    <script>var maxPagePhysicalProducts = ${(maxPagePhysicalProducts > 0)?(maxPagePhysicalProducts):(1)};</script>
    <script src="${pageContext.request.contextPath}/js/adminArea.js"></script>
    <script src="${pageContext.request.contextPath}/js/utility.js"></script>
    <script src="${pageContext.request.contextPath}/js/usersAdmin.js"></script>
    <script src="${pageContext.request.contextPath}/js/categoriesAdmin.js"></script>
    <script src="${pageContext.request.contextPath}/js/adminForms.js"></script>
    <script src="${pageContext.request.contextPath}/js/tagsAdmin.js"></script>
    <script src="${pageContext.request.contextPath}/js/adminAdmin.js"></script>
    <script src="${pageContext.request.contextPath}/js/moderatorAdmin.js"></script>
    <script src="${pageContext.request.contextPath}/js/operatorAdmin.js"></script>
    <script src="${pageContext.request.contextPath}/js/digitalProductAdmin.js"></script>
    <script src="${pageContext.request.contextPath}/js/physicalProductAdmin.js"></script>
</body>
</html>