
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:ns1="www.product.vn" xmlns:ns2="www.products.vn" exclude-result-prefixes="ns1 ns2">
    <xsl:output method="html" indent="yes"/>
    
    <xsl:decimal-format name="priceFormat" grouping-separator=","/>
    
    <xsl:template match="Cart">
        <div class="shopping-cart">
            <div class="column-labels">
                <label class="product-image">Hình ảnh</label>
                <label class="product-details">Sản phẩm</label>
                <label class="product-price">Đơn giá</label>
                <label class="product-quantity">Số lượng</label>
                <label class="product-removal">Xóa</label>
                <label class="product-line-price">Thành tiền</label>
            </div>
            
            <xsl:apply-templates select="CartItem"/>
            
            <div class="totals">                
                <div class="totals-item totals-item-total">
                    <label>Tổng cộng</label>
                    <div class="totals-value" id="cart-total">0</div>
                </div>
            </div>
            <button class="checkout" onclick="return Controller.checkOut()">Thanh toán</button>
        </div>        
    </xsl:template>
    <xsl:template match="CartItem">
        <div class="product" ProductId="{@ProductID}">
            <div class="product-image">
                <img>
                    <xsl:attribute name="src">
                        <xsl:value-of select="Thumbnail"/>
                    </xsl:attribute>
                </img>
            </div>
            <div class="product-details">
                <div class="product-title">
                    <xsl:value-of select="ProductName"/>
                </div>
            </div>
            <div class="product-price">
                <xsl:value-of select="format-number(Price, '#,###', 'priceFormat')"/>
            </div>
            <div class="product-quantity">
                <input type="number" value="{Quantity}" min="1" onchange='return Controller.onQuantityChange(this, {@ProductID})'/>
            </div>
            <div class="product-removal">
                <button class="remove-product" onClick="return Controller.removeItemFromCart(this, {@ProductID})">
                    Xóa Sản Phẩm
                </button>
            </div>
            <div class="product-line-price">
                <xsl:value-of select="format-number(Price * Quantity, '#,###', 'priceFormat')"/>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>