<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:ns2="http://viko.eif.lt/gtamaseviciute/studentWebSerice"
                version="1.0">

  <xsl:output method="xml" indent="yes"/>

  <xsl:template match="/">
    <fo:root>
      <fo:layout-master-set>
        <fo:simple-page-master master-name="A4"
                               page-height="297mm" page-width="210mm"
                               margin-top="20mm" margin-bottom="20mm"
                               margin-left="20mm" margin-right="20mm">
          <fo:region-body/>
        </fo:simple-page-master>
      </fo:layout-master-set>

      <fo:page-sequence master-reference="A4">
        <fo:flow flow-name="xsl-region-body">

          <!-- Pavadinimas -->
          <fo:block font-size="18pt" font-weight="bold" text-align="center" color="#2c3e50" space-after="10mm">
            Students Report
          </fo:block>

          <!-- Lentelė -->
          <fo:table table-layout="fixed" width="100%" font-family="Helvetica" font-size="10pt" border-collapse="collapse">
            <!-- Stulpelių plotis -->
            <fo:table-column column-width="15%"/>
            <fo:table-column column-width="8%"/>
            <fo:table-column column-width="12%"/>
            <fo:table-column column-width="8%"/>
            <fo:table-column column-width="12%"/>
            <fo:table-column column-width="10%"/>
            <fo:table-column column-width="35%"/>

            <!-- Header -->
            <fo:table-header>
              <fo:table-row background-color="#2980b9" color="white">
                <fo:table-cell><fo:block font-weight="bold">Name</fo:block></fo:table-cell>
                <fo:table-cell><fo:block font-weight="bold">Age</fo:block></fo:table-cell>
                <fo:table-cell><fo:block font-weight="bold">Group</fo:block></fo:table-cell>
                <fo:table-cell><fo:block font-weight="bold">Active</fo:block></fo:table-cell>
                <fo:table-cell><fo:block font-weight="bold">Average Grade</fo:block></fo:table-cell>
                <fo:table-cell><fo:block font-weight="bold">Gender</fo:block></fo:table-cell>
                <fo:table-cell><fo:block font-weight="bold">Subjects</fo:block></fo:table-cell>
              </fo:table-row>
            </fo:table-header>

            <!-- Kūnas -->
            <fo:table-body>
              <xsl:for-each select="ns2:getStudentsByGroupResponse/ns2:students | ns2:getStudentsResponse/ns2:students | ns2:getStudentResponse/ns2:student">
                <fo:table-row>
                  <xsl:attribute name="background-color">
                    <xsl:choose>
                      <xsl:when test="position() mod 2 = 0">#f2f2f2</xsl:when>
                      <xsl:otherwise>#ffffff</xsl:otherwise>
                    </xsl:choose>
                  </xsl:attribute>

                  <fo:table-cell><fo:block><xsl:value-of select="ns2:name"/></fo:block></fo:table-cell>
                  <fo:table-cell><fo:block><xsl:value-of select="ns2:age"/></fo:block></fo:table-cell>
                  <fo:table-cell><fo:block><xsl:value-of select="ns2:group"/></fo:block></fo:table-cell>
                  <fo:table-cell>
                    <fo:block>
                      <xsl:choose>
                        <xsl:when test="ns2:active='true'">Yes</xsl:when>
                        <xsl:otherwise>No</xsl:otherwise>
                      </xsl:choose>
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell><fo:block><xsl:value-of select="ns2:averageGrade"/></fo:block></fo:table-cell>
                  <fo:table-cell><fo:block><xsl:value-of select="ns2:gender"/></fo:block></fo:table-cell>
                  <fo:table-cell>
                    <fo:block>
                      <xsl:for-each select="ns2:subjects">
                        <xsl:value-of select="ns2:title"/> (<xsl:value-of select="ns2:grade"/>)
                        <fo:block/>
                      </xsl:for-each>
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </xsl:for-each>
            </fo:table-body>
          </fo:table>

        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>
</xsl:stylesheet>
