<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" 
                xmlns:ctgr="www.category.vn" xmlns:ctgrs="www.categories.vn" exclude-result-prefixes="ctgr ctgrs">
    <xsl:output method="html" indent="yes"/>
        
    <xsl:template match="ctgrs:Categories/ctgrs:Category">
        <div onclick="Controller.onCategoryClick('{@CategoryId}')">
            <div class="category" categoryid="{@CategoryId}">
                <xsl:value-of select="ctgr:CategoryName"/>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>