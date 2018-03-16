<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:ns1="www.product.vn" xmlns:ns2="www.products.vn" exclude-result-prefixes="ns1 ns2">
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="Cart">
        <div class="shopping-cart">
            <div class="column-labels">
                <label class="product-image">Image</label>
                <label class="product-details">Product</label>
                <label class="product-price">Price</label>
                <label class="product-quantity">Quantity</label>
                <label class="product-removal">Remove</label>
                <label class="product-line-price">Total</label>
            </div>          
            
            <xsl:apply-templates select="CartItem"/>
            
            <div class="totals">
                <div class="totals-item">
                    <label>Subtotal</label>
                    <div class="totals-value" id="cart-subtotal">71.97</div>
                </div>
                <div class="totals-item">
                    <label>Tax (5%)</label>
                    <div class="totals-value" id="cart-tax">3.60</div>
                </div>
                <div class="totals-item">
                    <label>Shipping</label>
                    <div class="totals-value" id="cart-shipping">15.00</div>
                </div>
                <div class="totals-item totals-item-total">
                    <label>Grand Total</label>
                    <div class="totals-value" id="cart-total">90.57</div>
                </div>
            </div>

            <button class="checkout">Checkout</button>
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
                <xsl:value-of select="Price"/>
            </div>
            <div class="product-quantity">
                <input type="number" value="{Quantity}" min="1"/>
            </div>
            <div class="product-removal">
                <button class="remove-product">
                    Remove
                </button>
            </div>
            <div class="product-line-price">
                default
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>