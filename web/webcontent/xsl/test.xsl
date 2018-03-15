<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" 
                xmlns:ns1="www.product.vn" xmlns:ns2="www.products.vn" exclude-result-prefixes="ns1 ns2">
    <xsl:output method="html"/>
    
    <xsl:param name ="searchValue"/>
    
    <xsl:template match="ns2:ProductType">
        <xsl:if test="ns1:ProductName[contains(text(), 'our']">
            <div class="gridProductItem">
                <xsl:attribute name="ProductID">
                    <xsl:value-of select="@ProductID"/>
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
                    <xsl:value-of select="ns1:Price" />             
                </div>  
            </div>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>