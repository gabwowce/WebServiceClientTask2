<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:ns2="http://viko.eif.lt/gtamaseviciute/studentWebSerice"
                version="1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://viko.eif.lt/gtamaseviciute/studentWebSerice ">

    <xsl:output encoding="UTF-8" indent="yes" method="xml" standalone="no" omit-xml-declaration="no"/>

    <xsl:template match="/">
        <fo:root language="EN">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4-portrait" page-height="297mm" page-width="210mm"
                                       margin-top="5mm" margin-bottom="5mm" margin-left="5mm" margin-right="5mm">
                    <fo:region-body margin-top="25mm" margin-bottom="20mm"/>
                    <fo:region-before region-name="xsl-region-before" extent="25mm" display-align="before"
                                      precedence="true"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4-portrait">
                <fo:flow flow-name="xsl-region-body" border-collapse="collapse" reference-orientation="0">
                    <fo:block vertical-align="top" text-align="center">
                        <fo:external-graphic src="students.jpg"/>
                    </fo:block>

                    <fo:block text-align="center">
                        <fo:table table-layout="fixed" width="95%" font-size="10pt"
                                  border-color="grey" border-width="0.1mm"
                                  border-style="solid" text-align="center"
                                  display-align="center" space-after="5mm">

                            <fo:table-column column-width="proportional-column-width(20)"/>
                            <fo:table-column column-width="proportional-column-width(20)"/>
                            <fo:table-column column-width="proportional-column-width(20)"/>
                            <fo:table-column column-width="proportional-column-width(20)"/>
                            <fo:table-column column-width="proportional-column-width(20)"/>
                            <fo:table-column column-width="proportional-column-width(20)"/>
                            <fo:table-column column-width="proportional-column-width(60)"/>


                            <fo:table-body>
                                <fo:table-row height="8mm" border-color="grey" border-width="0.1mm"
                                              border-style="solid" text-align="start" display-align="center"
                                              space-after="5mm">
                                    <fo:table-cell>
                                        <fo:block>Name</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>Age</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>Group</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>Active</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>Average grade</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>Gender</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>Subjects</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>


                                <xsl:for-each select="ns2:getStudentsByGroupResponse/ns2:students | ns2:getStudentsResponse/ns2:students | ns2:getStudentResponse/ns2:student">
                                <fo:table-row text-align="start">
                                        <fo:table-cell>
                                            <fo:block>
                                                <xsl:value-of select="ns2:name"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell>
                                            <fo:block>
                                                <xsl:value-of select="ns2:age"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell>
                                            <fo:block>
                                                <xsl:value-of select="ns2:group"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell>
                                            <fo:block>
                                                <xsl:value-of select="ns2:active"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell>
                                            <fo:block>
                                                <xsl:value-of select="ns2:averageGrade"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell>
                                            <fo:block>
                                                <xsl:value-of select="ns2:gender"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell>
                                            <fo:block>
                                                <xsl:for-each select="ns2:subjects">
                                                    <xsl:value-of select="ns2:title"/>
                                                    (<xsl:value-of select="ns2:grade"/>)
                                                    <fo:block/>
                                                </xsl:for-each>
                                            </fo:block>
                                        </fo:table-cell>

                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

</xsl:stylesheet>
