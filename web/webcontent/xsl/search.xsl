<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" 
                xmlns:ns1="www.product.vn" xmlns:ns2="www.products.vn" exclude-result-prefixes="ns1 ns2">
    <xsl:output method="html" indent="yes" endcoding="UTF-8"/>
    
    <xsl:decimal-format name="priceFormat" grouping-separator=","/>
    
    <xsl:param name ="searchValue"/>
    <xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
    <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
    
    <xsl:template match="ns2:ProductType">
        <xsl:if test="ns1:ProductName[contains(translate(text(), $uppercase, $smallcase), $searchValue)]">
            <div class="gridProductItem">
                <xsl:attribute name="ProductID">
                    <xsl:value-of select="@ProductID"/>
                </xsl:attribute>
                <xsl:attribute name="ProductID">
                    <xsl:value-of select="@ProductID"/>
                </xsl:attribute>
                <xsl:attribute name="CategoryID">
                    <xsl:value-of select="@CategoryID"/>
                </xsl:attribute>
                <xsl:attribute name="IsActive">
                    <xsl:value-of select="@IsActive"/>
                </xsl:attribute>
                
                <img class="productThumbnail">
                    <xsl:attribute name="src">                
                        <xsl:value-of select="ns1:Thumbnail" />             
                    </xsl:attribute>
                </img> 
                          
                <div class="productName">
                    <xsl:value-of select="ns1:ProductName" />             
                </div>    
                       
                <div class="productPrice">
                    <xsl:value-of select="format-number(ns1:Price, '#,###', 'priceFormat')" />             
                </div>  
                <div class="layerBlur"></div>
                <div class="layerAddToCart">
                    <button class="btnAddToCart" onClick="Controller.addToCart(this)" >Add to cart</button>
                </div>
            </div>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>