<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" href="/css/app.css">
</head>

<body>
    <div class="container">
        <header>
            <h1>${title}</h1>
            <a href="/domains/product/new" class="btn btn-primary">Add New Product</a>
        </header>

        <!-- Flash Messages -->
        <#if flash.hasMessages()>
            <div class="flash-messages">
                <#list flash.messages as message>
                    <div class="alert alert-${message.type}">
                        ${message.message}
                    </div>
                </#list>
            </div>
        </#if>

        <main>
            <div class="products-grid">
                <#list products as product>
                    <div class="product-card">
                        <h3>${product.name}</h3>
                        <p class="price">$${product.price}</p>
                        <div class="actions">
                            <a href="/domains/product/${product.id}" class="btn btn-info">View</a>
                            <a href="/domains/product/${product.id}/edit" class="btn btn-warning">Edit</a>
                            <form action="/domains/product/${product.id}/delete" method="post" style="display: inline;">
                                <button type="submit" class="btn btn-danger"
                                    onclick="return confirm('Are you sure?')">Delete</button>
                            </form>
                        </div>
                    </div>
                </#list>
            </div>

            <#if !products?has_content>
                <div class="empty-state">
                    <p>No products found. <a href="/domains/product/new">Add the first product</a></p>
                </div>
            </#if>
        </main>
    </div>
</body>

</html>