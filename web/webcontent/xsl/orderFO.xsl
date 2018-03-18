<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
    <xsl:output encoding="UTF-8" indent="yes" method="xml" standalone="no" omit-xml-declaration="no"/>
    
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
                        Hoa Don Ban Hang - TPGamingGear
                    </fo:block>
                </fo:static-content>
                
                <fo:static-content flow-name="xsl-region-after" >
                    <fo:block font-size="14pt" font-family="sans-serif" line-height="24pt" background-color="cyan"
                              space-after.optimum="15pt" text-align="center" padding-top="3pt">
                        
                    </fo:block>
                </fo:static-content>
                
                <fo:flow flow-name="xsl-region-body" >
                    <fo:block>
                        <fo:table border-collapse="separate" table-layout="fixed">
                            <fo:table-column column-width="2.8cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">No.</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">Product Name</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">Price</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">Quantity</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">Line Price</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                
                                <xsl:apply-templates select="Cart/CartItem"/>
                                
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>                
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    
    <xsl:template match="Cart/CartItem">
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
                    <xsl:value-of select="format-number(Price, '#,###', 'priceFormat')"/> VND
                </fo:block>
            </fo:table-cell>
            <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                <fo:block text-align="center">
                    <xsl:value-of select="Quantity"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell border-color="blue" border-width="0.5pt" border-style="solid">
                <fo:block text-align="center">
                    <xsl:value-of select="format-number(Price * Quantity, '#,###', 'priceFormat')"/> VND
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
    
</xsl:stylesheet>