function onCategoryClick(categoryId) {
    var url = 'ProcessServlet?btnAction=loadCategory&categoryID=' + categoryId;
    window.location.href = url;
}

function loadCategories(categories) {
    var divCategories = document.getElementsByClassName("categories")[0];
    for (var i = 0; i < categories.length; i++) {
        var category = categories[i];
        var categoryNameTag = category.childNodes[0];
        var categoryId = category.getAttribute("CategoryId");

        var divCategory = document.createElement("div");
        divCategory.setAttribute("class", "category");
        var divContent = document.createElement("div");
        divCategories.appendChild(divContent);
        divContent.appendChild(divCategory);

        var categoryName = categoryNameTag.childNodes[0].nodeValue;
        divCategory.innerHTML = categoryName;
        divCategory.setAttribute("CategoryID", categoryId);
        divContent.addEventListener('click', function () {
            onCategoryClick(this.childNodes[0].getAttribute("CategoryId"));
        });
    }
}