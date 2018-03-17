<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:ns1="www.product.vn" xmlns:ns2="www.products.vn" exclude-result-prefixes="ns1 ns2">
    <xsl:output method="xml"/>
    
    <xsl:decimal-format name="priceFormat" grouping-separator=","/>
    
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4" page-height="8.5in" page-width="11in" margin-top="0.5in" 
                                       margin-bottom="0.5in" margin-left="1in" margin-right="1in">
                    <fo:region-body margin-top="0.5in" />
                    <fo:region-before extent="1in" />
                    <fo:region-after extent="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4">
                <fo:static-content flow-name="xsl-region-before" >
                    <fo:block font-size="14pt" font-family="sans-serif" line-height="24pt" background-color="cyan"
                              space-after.optimum="15pt" text-align="center" padding-top="3pt">
                        Hoa don mua hang
                    </fo:block>
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after" >
                    <fo:block font-size="14pt" font-family="sans-serif" line-height="24pt" background-color="cyan"
                              space-after.optimum="15pt" text-align="center" padding-top="3pt">
                        
                    </fo:block>
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-body" >
                    <fo:block>
                        <fo:table boder-collapse="separate" table-layout="fixed">
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">No.</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">ProductName</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">Quantity</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each select="Cart/CartItem">
                                    <fo:table-row>
                                        <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="center">
                                                <xsl:number level="single" count="CartItem"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="center">
                                                <xsl:value-of select="ProductName"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="center">
                                                <xsl:value-of select="Quantity"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:static-content>
                
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    
</xsl:stylesheet>