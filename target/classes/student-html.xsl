<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns2="http://viko.eif.lt/gtamaseviciute/studentWebSerice"
                version="1.0">

    <xsl:output method="html" encoding="UTF-8" indent="yes" />

    <xsl:template match="/">
        <html>
            <head>
                <title>Students List</title>
                <style>
                    table { border-collapse: collapse; width: 100%; }
                    th, td { border: 1px solid #aaa; padding: 8px; text-align: left; }
                    th { background-color: #f2f2f2; }
                </style>
            </head>
            <body>
                <h2>Students</h2>
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Group</th>
                        <th>Active</th>
                        <th>Average Grade</th>
                        <th>Gender</th>
                        <th>Subjects</th>
                    </tr>

                    <xsl:for-each select="ns2:getStudentsByGroupResponse/ns2:students | ns2:getStudentsResponse/ns2:students | ns2:getStudentResponse/ns2:student">
                        <tr>
                            <td><xsl:value-of select="ns2:name"/></td>
                            <td><xsl:value-of select="ns2:age"/></td>
                            <td><xsl:value-of select="ns2:group"/></td>
                            <td><xsl:value-of select="ns2:active"/></td>
                            <td><xsl:value-of select="ns2:averageGrade"/></td>
                            <td><xsl:value-of select="ns2:gender"/></td>
                            <td>
                                <xsl:for-each select="ns2:subjects">
                                    <xsl:value-of select="ns2:title"/> (<xsl:value-of select="ns2:grade"/>)
                                    <br/>
                                </xsl:for-each>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
