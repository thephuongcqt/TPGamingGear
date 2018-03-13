<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns="www.product.vn" xmlns:ns2="www.products.vn">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <xsl:for-each select="//*[local-name()='ProductType']">                                        
            <div class="gridProductItem">
                <img class="productThumbnail">
                    <xsl:attribute name="src">
                        <xsl:value-of select="./*[local-name()='Thumbnail']" />
                    </xsl:attribute>
                </img>
                <div class="productName">
                    <xsl:value-of select="./*[local-name()='ProductName']" />
                </div>
                <div class="productPrice">
                    <xsl:value-of select="./*[local-name()='Price']" />
                </div>
            </div>
        </xsl:for-each> 
    </xsl:template>

</xsl:stylesheet>